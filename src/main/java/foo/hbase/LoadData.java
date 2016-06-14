package foo.hbase;

import foo.lift.Lift;
import foo.lift.LiftConverter;
import foo.window.Rating;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class LoadData {
  private static Iterable<String> readLines(File f) throws FileNotFoundException {
    final Scanner s = new Scanner(f).useDelimiter("\n");
    return new Iterable<String>() {
      @Override
      public Iterator<String> iterator() {
        return new Iterator<String>() {
          @Override
          public boolean hasNext() {
            return s.hasNext();
          }

          @Override
          public String next() {
            return s.next();
          }
        };
      }
    };
  }

  public static void main(String[] args) throws Exception {
    Configuration config = HBaseConfiguration.create();
    config.set("hbase.zookeeper.property.clientPort", "2181");
    config.set("hbase.zookeeper.quorum", "sandbox.hortonworks.com");
    config.set("zookeeper.znode.parent", "/hbase-unsecure");

    Connection connection = ConnectionFactory.createConnection(config);
    Table table = connection.getTable(TableName.valueOf(Bytes.toBytes("lifts")));

    table.put(createPuts(LiftType.POS));
    table.put(createPuts(LiftType.NEG));
  }

  private static List<Put> createPuts(LiftType type) throws FileNotFoundException {
    return createPuts(type, getRatings(type.getFile()));
  }

  private static List<Put> createPuts(LiftType type, Map<Long, List<Pair<Long, Double>>> map) {
    List<Put> puts = new LinkedList<>();
    for (Map.Entry<Long, List<Pair<Long, Double>>> foo : map.entrySet()) {
      Put p = new Put(Bytes.toBytes(foo + ", " + type.name().toLowerCase()));
      Lift topK = new Lift(type.rating, foo.getValue());
      topK.addAll(foo.getValue());
      p.addColumn(Bytes.toBytes("top-k"), Bytes.toBytes(""), new LiftConverter().toBytes(topK));
      puts.add(p);
    }
    return puts;
  }

  private static Map<Long, List<Pair<Long, Double>>> getRatings(File file) throws FileNotFoundException {
    Map<Long, List<Pair<Long, Double>>> map = new HashMap<>();
    for (String line : readLines(file)) {
      String[] split = line.split(",");
      long x = Long.parseLong(split[0]);
      long y = Long.parseLong(split[1]);
      double lift = Double.parseDouble(split[2]);
      if (map.containsKey(x) == false)
        map.put(x, new LinkedList<Pair<Long, Double>>());
      map.get(x).add(Pair.newPair(y, lift));
    }
    return map;
  }

  private enum LiftType {
    POS("D:\\Dropbox\\Big Data\\pig\\output1.txt", Rating.POSITIVE),
    NEG("D:\\Dropbox\\Big Data\\pig\\output2.txt", Rating.NEGATIVE);

    private final String filePath;
    private final Rating rating;

    LiftType(String filePath, Rating r) {
      this.filePath = filePath;
      this.rating = r;
    }

    public File getFile() {
      return new File(filePath);
    }
  }
}
