package foo.window;

import foo.Movie;
import foo.hbase.StringConverter;

import java.util.LinkedList;

public class WindowConverter extends StringConverter<MovieRating, Window> {
  private static Rating decodeRating(String s) {
    int i = Integer.parseInt(s);
    switch (i) {
      case 0:
        return Rating.NEGATIVE;
      case 1:
        return Rating.NEUTRAL;
      case 2:
        return Rating.POSITIVE;
      default:
        throw new AssertionError("Invalid flag: " + i);
    }
  }

  @Override
  protected Window build(LinkedList<MovieRating> list) {
    return new Window(list);
  }

  @Override
  protected MovieRating decode(String str) {
    String[] movieSplit = str.split(",");
    return new MovieRating(new Movie(Long.parseLong(movieSplit[0])), decodeRating(movieSplit[1]));
  }

  @Override
  protected String encode(MovieRating movieRating) {
    return movieRating.m.id + "," + encodeRating(movieRating.r);
  }

  private int encodeRating(Rating r) {
    switch (r) {
      case NEGATIVE:
        return 0;
      case NEUTRAL:
        return 1;
      case POSITIVE:
        return 2;
      default:
        throw new AssertionError("Invalid rating: " + r);
    }
  }
}
