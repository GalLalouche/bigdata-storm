package foo;

import foo.lift.Lift;
import foo.lift.LiftGetter;
import foo.lift.LiftRating;
import foo.recommender.Recommender;
import foo.window.MovieRating;
import foo.window.Rating;
import foo.window.Window;

import java.util.LinkedList;
import java.util.List;

public class Ranker {
  private final LiftGetter liftGetter;
  private final Recommender recommender;

  public Ranker(LiftGetter liftGetter, Recommender recommender) {
    this.liftGetter = liftGetter;
    this.recommender = recommender;
  }

  /** Returns the rank of the movie returns by the recommender, or null if no match. */
  public Integer recommend(Window w, Movie m) {
    List<Lift> lifts = new LinkedList<>();
    for (MovieRating mr : w)
      lifts.add(getLift(mr));
    int index = recommender.recommend(lifts).indexOf(m);
    return index >= 0 ? index + 1 : null;
  }

  private Lift getLift(MovieRating mr) {
    switch (mr.r) {
      case NEGATIVE:
        return liftGetter.getLift(mr.m, LiftRating.NEG);
      case POSITIVE:
        return liftGetter.getLift(mr.m, LiftRating.POS);
      default:
        assert mr.r == Rating.NEUTRAL;
        return Lift.empty(mr.m);
    }
  }
}
