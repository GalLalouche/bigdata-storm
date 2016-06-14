package foo.lift;

import foo.Movie;
import foo.hbase.BasicTable;
import foo.window.Rating;
import org.apache.commons.lang.NotImplementedException;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.util.Pair;

import java.util.List;

public class LiftGetter {
  private final LiftConverter converter = new LiftConverter();
  private final BasicTable table = new BasicTable();

  public Lift getNegative(Movie m) {
    List<Pair<Long, Double>> pairs = converter.fromBytes(table.load(Bytes.toBytes(m.id + ", pos")));
    return new Lift(Rating.POSITIVE, pairs);
  }

  public Lift getPositive(Movie m){
    List<Pair<Long, Double>> pairs = converter.fromBytes(table.load(Bytes.toBytes(m.id + ", neg")));
    return new Lift(Rating.NEGATIVE, pairs);
  }
}
