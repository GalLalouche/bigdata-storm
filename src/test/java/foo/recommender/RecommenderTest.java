package foo.recommender;

import foo.Movie;
import foo.lift.Lift;

import static javax.swing.UIManager.put;

public class RecommenderTest {
  protected static Lift createLift(boolean isPositive, final long id, final double rating) {
//    return new Lift(isPositive) {
//      {
//        put(new Movie(id), rating);
//      }
//    };
    return null;
  }

  protected static Lift createLift(boolean isPositive,
                                   final long id1, final double rating1,
                                   final long id2, final double rating2) {
    return null;
//    return new Lift(isPositive) {
//      {
//        put(new Movie(id1), rating1);
//        put(new Movie(id2), rating2);
//      }
//    };
  }
}
