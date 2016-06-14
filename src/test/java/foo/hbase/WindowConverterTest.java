package foo.hbase;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;

import foo.Movie;
import foo.window.MovieRating;
import foo.window.Window;
import foo.window.WindowConverter;

public class WindowConverterTest {
  @Test
  public void name() throws Exception {
    Window w = new Window(new Movie(1),
        new LinkedList<>(Arrays.asList(MovieRating.of(1L, 2.0), MovieRating.of(2L, 4.0))));
    WindowConverter $ = new WindowConverter();
    assertEquals(w, $.fromBytes($.toBytes(w)));
  }
}
