package foo.lift;

import foo.window.Rating;
import org.apache.hadoop.hbase.util.Pair;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Lift extends LinkedList<Pair<Long, Double>> {
  public static final Lift EMPTY = new Lift(Rating.NEUTRAL, Collections.EMPTY_LIST);
  public final Rating rating;

  public Lift(Rating rating, List<Pair<Long, Double>> pairs) {
    this.rating = rating;
    addAll(pairs);
  }

  public void add(long l, double v) {
    add(Pair.newPair(l, v));
  }
}
