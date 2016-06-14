package foo.window;

import org.apache.hadoop.hbase.util.Bytes;

import java.util.LinkedList;

import foo.Movie;
import foo.hbase.HBaseConverter;

public class WindowConverter implements HBaseConverter<Window> {
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

  @Override
  public Window fromBytes(byte[] bytes) {
    String[] split = Bytes.toString(bytes).split(";");
    Movie movie = new Movie(Long.parseLong(split[0]));
    LinkedList<MovieRating> ratings = new LinkedList<>();
    for (int i = 1; i < split.length; i++) {
      String[] pair = split[i].split(",");
      ratings.add(new MovieRating(Long.parseLong(pair[0]), decodeRating(pair[1])));
    }
    return new Window(movie, ratings);
  }

  @Override
  public byte[] toBytes(Window movieRatings) {
    StringBuilder sb = new StringBuilder();
    sb.append(movieRatings.m.id + ";");
    for (MovieRating mr : movieRatings) {
      sb.append(mr.m.id + "," + encodeRating(mr.r) + ";");
    }
    return Bytes.toBytes(sb.toString());
  }

  @Override
  public byte[] extractKey(Window movieRatings) {
    return Bytes.toBytes(movieRatings.m.id);
  }
}
