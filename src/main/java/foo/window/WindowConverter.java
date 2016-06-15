package foo.window;

import foo.User;
import foo.hbase.HBaseConverter;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.LinkedList;

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
    User user = new User(Long.parseLong(split[0]));
    LinkedList<MovieRating> ratings = new LinkedList<>();
    for (int i = 1; i < split.length; i++) {
      if (split[i].isEmpty())
        continue;
      String[] pair = split[i].split(",");
      ratings.add(new MovieRating(Long.parseLong(pair[0]), decodeRating(pair[1])));
    }
    return new Window(user, ratings);
  }

  @Override
  public byte[] toBytes(Window movieRatings) {
    StringBuilder sb = new StringBuilder();
    sb.append(movieRatings.u.id + ";");
    for (MovieRating mr : movieRatings) {
      sb.append(mr.m.id + "," + encodeRating(mr.r) + ";");
    }
    return Bytes.toBytes(sb.toString());
  }

  @Override
  public byte[] extractKey(Window w) {
    return Bytes.toBytes(w.u.id);
  }
}
