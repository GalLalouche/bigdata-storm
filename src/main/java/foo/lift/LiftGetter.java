package foo.lift;

import foo.Movie;
import foo.hbase.BasicTable;
import foo.hbase.BasicTableFactory;

import java.io.IOException;

public class LiftGetter {
  private final LiftConverter converter = new LiftConverter();
  private final BasicTable table = BasicTableFactory.create("lifts");

  public LiftGetter() throws IOException {}

  public Lift getLift(Movie m, LiftRating rating) {
    try {
      byte[] load = table.load(converter.extractKey(Lift.empty(m, rating)));
      if (load == null)
        return Lift.empty(m, rating);
      return converter.fromBytes(load);
    } catch (IOException e) {
      throw new AssertionError(e);
    }
  }
}
