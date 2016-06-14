package foo.lift;

import org.apache.hadoop.hbase.util.Pair;

import java.util.Collections;
import java.util.Iterator;

import foo.Movie;

public class Lift implements Iterable<Pair<Long, Double>> {
  public final LiftRating rating;
  public final Movie m;
  private final Iterable<Pair<Long, Double>> pairs;

  public Lift(Movie m, LiftRating rating, Iterable<Pair<Long, Double>> pairs) {
    this.rating = rating;
    this.m = m;
    this.pairs = pairs;
  }

  public static Lift empty(Movie m) {
    return empty(m, LiftRating.NEG);
  }

  public static Lift empty(Movie m, LiftRating rating) {
    return new Lift(m, rating, Collections.EMPTY_LIST);
  }

  @Override
  public Iterator<Pair<Long, Double>> iterator() {
    return pairs.iterator();
  }
}
