package foo.recommender;

import org.apache.hadoop.hbase.util.Pair;

import java.util.LinkedList;
import java.util.List;

import foo.Movie;
import foo.lift.Lift;
import foo.lift.LiftRating;

/** Gives a higher weight to PosLift. */
public class PosRecommender extends Recommender {

  @Override
  public List<Movie> recommend(List<Lift> lifts) {
    List<Pair<Movie, Double>> list = new LinkedList<>();
    for (Lift lift : lifts) {
      double coef = lift.rating == LiftRating.POS ? 2.0 : 1.0;
      for (Pair<Long, Double> foo : lift) {
        list.add(Pair.newPair(new Movie(foo.getFirst()), coef * foo.getSecond()));
      }
    }
    return sort(groupSum(list));
  }
}
