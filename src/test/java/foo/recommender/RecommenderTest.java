package foo.recommender;

import org.apache.hadoop.hbase.util.Pair;

import java.util.Collection;
import java.util.LinkedList;

import foo.Movie;
import foo.lift.Lift;
import foo.lift.LiftRating;

public class RecommenderTest {
  protected static Lift createLift(boolean isPositive, final long id, final double rating) {
    Collection<Pair<Long, Double>> collection = new LinkedList<>();
    collection.add(Pair.newPair(id, rating));
    return new Lift(new Movie(1), LiftRating.isPositive(isPositive), collection);
  }

  protected static Lift createLift(boolean isPositive,
                                   final long id1, final double rating1,
                                   final long id2, final double rating2) {
    Collection<Pair<Long, Double>> collection = new LinkedList<>();
    collection.add(Pair.newPair(id1, rating1));
    collection.add(Pair.newPair(id2, rating2));
    return new Lift(new Movie(1), LiftRating.isPositive(isPositive), collection);
  }
}
