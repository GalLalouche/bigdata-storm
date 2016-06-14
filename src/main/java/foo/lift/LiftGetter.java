package foo.lift;

import java.io.IOException;

import foo.Movie;
import foo.hbase.BasicTable;
import foo.hbase.BasicTableFactory;

public class LiftGetter {
  private final LiftConverter converter = new LiftConverter();
  private final BasicTable table = BasicTableFactory.create("lifts");

  public LiftGetter() throws IOException {}

  public Lift getLift(Movie m, LiftRating rating) {
    try {
      return converter.fromBytes(table.load(converter.extractKey(Lift.empty(m, rating))));
    } catch (IOException e) {
      throw new AssertionError(e);
    }
  }
}
