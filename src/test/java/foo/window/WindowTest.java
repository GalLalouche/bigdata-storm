package foo.window;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import foo.User;
import org.junit.Test;

import java.util.Iterator;

import foo.Movie;

public class WindowTest {
  private final Window $ = new Window(new User(1));

  @Test
  public void noPreviousMovies_addMovie() throws Exception {
    $.addMovie(new MovieRating(666, Rating.POSITIVE));
    assertSize($, 1);
  }

  private void assertSize(Window $, int expected) {
    int actual = 0;
    Iterator i = $.iterator();
    while (i.hasNext()) {
      i.next();
      actual++;
    }
    assertEquals("Wrong iterable size", expected, actual);
  }

  @Test
  public void somePreviousMovies_addMovie() throws Exception {
    $.addMovie(new MovieRating(666, Rating.POSITIVE));
    $.addMovie(new MovieRating(123, Rating.POSITIVE));
    assertSize($, 2);
  }

  @Test
  public void fullMovies_addMovie_remainsAtFive() throws Exception {
    $.addMovie(new MovieRating(666, Rating.POSITIVE));
    $.addMovie(new MovieRating(123, Rating.POSITIVE));
    $.addMovie(new MovieRating(13, Rating.POSITIVE));
    $.addMovie(new MovieRating(12, Rating.POSITIVE));
    $.addMovie(new MovieRating(23, Rating.POSITIVE));
    assertSize($, 5);
    $.addMovie(new MovieRating(66, Rating.NEUTRAL));
    assertSize($, 5);
  }

  @Test
  public void isFull_whenFull() throws Exception {
    $.addMovie(new MovieRating(666, Rating.POSITIVE));
    $.addMovie(new MovieRating(123, Rating.POSITIVE));
    $.addMovie(new MovieRating(13, Rating.POSITIVE));
    $.addMovie(new MovieRating(12, Rating.POSITIVE));
    $.addMovie(new MovieRating(23, Rating.POSITIVE));
    assertTrue($.isFull());
  }

  @Test
  public void isFull_whenNotFull() throws Exception {
    $.addMovie(new MovieRating(12, Rating.POSITIVE));
    assertFalse($.isFull());
  }
}
