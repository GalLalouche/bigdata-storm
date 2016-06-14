package foo.recommender;

import foo.Movie;
import foo.lift.Lift;
import org.apache.hadoop.hbase.util.Pair;

import java.util.*;

/** Gives increased weight to the the last movies watched. */
public class RecentRecommender extends Recommender {

  public static final double STARTING_COEFFICIENT = 2.0;

  private static Collection<Pair<Movie, Double>> extractLifts(Lift lift, double v) {
    Collection<Pair<Movie, Double>> $ = new LinkedList<>();
    for (Pair<Long, Double> entry : lift)
      $.add(Pair.newPair(new Movie(entry.getFirst()), entry.getSecond() * v));
    return $;
  }

  @Override
  public List<Movie> recommend(List<Lift> lifts) {
    List<Pair<Movie, Double>> ratings = new ArrayList<>();
    for (int i = 0; i < lifts.size(); i++) {
      ratings.addAll(extractLifts(lifts.get(i), STARTING_COEFFICIENT - 0.2 * i));
    }
    return sort(groupSum(ratings));
  }
}
