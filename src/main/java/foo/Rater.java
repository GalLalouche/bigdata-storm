package foo;

import foo.window.MovieRating;
import foo.window.Rating;
import foo.window.Window;

/** Handles quality aggregation for a single user */
public class Rater {
  private final Ranker recommender;
  private final QualityAggregator qAggregator;
  private final Window currentWindow;

  public Rater(Ranker recommender, QualityAggregator qAggregator, Window window) {
    this.recommender = recommender;
    this.qAggregator = qAggregator;
    this.currentWindow = window;
  }

  /**
   * Adds the next movie the user rated
   */
  public void addMovie(MovieRating mr) {
    try {
      if (currentWindow.isFull() == false)
        return;

      Integer recommendations = recommender.recommend(currentWindow, mr.m);
      if (recommendations == null || mr.r == Rating.NEUTRAL)
        return;

      double updatedQ = (mr.r == Rating.POSITIVE ? 1.0 : -1.0) / recommendations;
      qAggregator.update(updatedQ);
    } finally {
      currentWindow.addMovie(mr);
    }
  }
}
