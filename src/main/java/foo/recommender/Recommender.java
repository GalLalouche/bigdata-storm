package foo.recommender;

import foo.Movie;
import foo.lift.Lift;
import org.apache.hadoop.hbase.util.Pair;

import java.util.*;

public abstract class Recommender {
  public abstract List<Movie> recommend(List<Lift> lifts);

  static Map<Movie, Double> groupSum(List<Pair<Movie, Double>> pairs) {
    Map<Movie, Double> $ = new HashMap<>();
    for (Pair<Movie, Double> p : pairs) {
      Double existingValue = $.get(p.getFirst());
      if (existingValue == null)
        existingValue = 0.0;
      $.put(p.getFirst(), existingValue + p.getSecond());
    }
    return $;
  }

  static List<Movie> sort(Map<Movie, Double> input) {
    List<Pair<Movie, Double>> list = new LinkedList<>();
    for (Map.Entry<Movie, Double> e : input.entrySet())
      list.add(Pair.newPair(e.getKey(), e.getValue()));
    Collections.sort(list, new Comparator<Pair<Movie, Double>>() {
      @Override
      public int compare(Pair<Movie, Double> o1, Pair<Movie, Double> o2) {
        return -1 * Double.compare(o1.getSecond(), o2.getSecond());
      }
    });
    List<Movie> $ = new LinkedList<>();
    for (Pair<Movie, Double> r : list) {
      $.add(r.getFirst());
    }
    return $;
  }
}
