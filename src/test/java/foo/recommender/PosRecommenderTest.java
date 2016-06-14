package foo.recommender;

import foo.Movie;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PosRecommenderTest extends RecommenderTest {
  private final Recommender $ = new PosRecommender();

  @Test
  public void name() throws Exception {
    List<Movie> result = $.recommend(Arrays.asList(createLift(true, 1, 51, 2, 75), createLift(false, 3, 100)));
    assertEquals(Arrays.asList(new Movie(2), new Movie(1), new Movie(3)), result);
  }
}
