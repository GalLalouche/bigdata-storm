package foo;

import foo.window.MovieRating;
import foo.window.Rating;
import foo.window.Window;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class RaterTest {
  private final Ranker recommender = mock(Ranker.class);
  private final QualityAggregator q = mock(QualityAggregator.class);
  private final Window window = mock(Window.class);
  private final Rater $ = new Rater(recommender, q, window);
  private final MovieRating mr = new MovieRating(mock(Movie.class), Rating.POSITIVE);

  @Test
  public void ifWindowIsntFull_addsMovie() throws Exception {
    when(window.isFull()).thenReturn(false);
    $.addMovie(mr);
    verify(window).addMovie(mr);
    verifyNoMoreInteractions(recommender);
    verifyNoMoreInteractions(q);
  }

  @Test
  public void ifWindowIsFull_andMovieIsntRecommended_addsMovie() throws Exception {
    when(window.isFull()).thenReturn(true);
    when(recommender.recommend(window, mr.m)).thenReturn(null);
    $.addMovie(mr);
    verify(window).addMovie(mr);
    verifyNoMoreInteractions(q);
  }

  @Test
  public void ifWindowIsFull_andMovieIsRecommendedButMovieIsntRated_addsMovie() throws Exception {
    when(window.isFull()).thenReturn(true);
    MovieRating mr = new MovieRating(mock(Movie.class), Rating.NEUTRAL);
    when(recommender.recommend(window, mr.m)).thenReturn(4);
    $.addMovie(mr);
    verify(window).addMovie(mr);
    verifyNoMoreInteractions(q);
  }

  @Test
  public void ifWindowIsFullAndMovieIsRecommendedAndRatedPositively_updatesQ() throws Exception {
    when(window.isFull()).thenReturn(true);
    MovieRating mr = new MovieRating(mock(Movie.class), Rating.POSITIVE);
    when(recommender.recommend(window, mr.m)).thenReturn(4);
    $.addMovie(mr);
    verify(window).addMovie(mr);
    verify(q).update(0.25);
  }

  @Test
  public void ifWindowIsFull_andMovieIsRecommendedAndRatedNegatively_updatesQ() throws Exception {
    when(window.isFull()).thenReturn(true);
    MovieRating mr = new MovieRating(mock(Movie.class), Rating.NEGATIVE);
    when(recommender.recommend(window, mr.m)).thenReturn(5);
    $.addMovie(mr);
    verify(window).addMovie(mr);
    verify(q).update(-0.2);
  }
}
