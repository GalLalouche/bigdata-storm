package foo.hbase;

import foo.Movie;
import foo.lift.Lift;
import foo.lift.LiftConverter;
import foo.lift.LiftRating;
import org.apache.hadoop.hbase.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class LoadLifts {
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

          @Override
          public void remove() {
            throw new UnsupportedOperationException();
          }
        };
      }
    };
  }

  public static void main(String[] args) throws Exception {
    BasicTable table = BasicTableFactory.create("lifts");
    table.clear();
    HBaseAPI hbase = new HBaseAPI(new LiftConverter(), table);
    hbase.save(createLifts(LiftType.POS));
    hbase.save(createLifts(LiftType.NEG));
  }

  private static Iterable<Lift> createLifts(LiftType type) throws FileNotFoundException {
    Map<Long, List<Pair<Long, Double>>> ratings = getRatings(type.getFile());
    Collection<Lift> $ = new LinkedList<>();
    for (Map.Entry<Long, List<Pair<Long, Double>>> foo : ratings.entrySet()) {
      $.add(new Lift(new Movie(foo.getKey()), type.rating, foo.getValue()));
    }
    return $;
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
    POS("D:\\Dropbox\\Big Data\\pig\\output1.txt", LiftRating.POS),
    NEG("D:\\Dropbox\\Big Data\\pig\\output2.txt", LiftRating.NEG);

    private final String filePath;
    private final LiftRating rating;

    LiftType(String filePath, LiftRating r) {
      this.filePath = filePath;
      this.rating = r;
    }

    public File getFile() {
      return new File(filePath);
    }
  }
}
