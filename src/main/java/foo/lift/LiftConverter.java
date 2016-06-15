package foo.lift;


import foo.Movie;
import foo.hbase.HBaseConverter;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.util.Pair;

import java.util.Collection;
import java.util.LinkedList;

public class LiftConverter implements HBaseConverter<Lift> {
  @Override
  public Lift fromBytes(byte[] bytes) {
    String[] split = Bytes.toString(bytes).split(";");
    String[] firstSplit = split[0].split(",");
    Movie movie = new Movie(Long.parseLong(firstSplit[0]));
    LiftRating r = firstSplit[1].equals("0") ? LiftRating.NEG : LiftRating.POS;
    Collection<Pair<Long, Double>> list = new LinkedList<>();
    for (int i = 1; i < split.length; i++) {
      if (split[i].isEmpty())
        continue;
      String[] pair = split[i].split(",");
      list.add(Pair.newPair(Long.parseLong(pair[0]), Double.parseDouble(pair[1])));
    }
    return new Lift(movie, r, list);
  }

  @Override
  public byte[] toBytes(Lift lift) {
    StringBuilder sb = new StringBuilder();
    sb.append(lift.m.id + "," + (lift.rating == LiftRating.NEG ? 0 : 1));
    for (Pair<Long, Double> mr : lift) {
      sb.append(mr.getFirst() + "," + mr.getSecond() + ";");
    }
    return Bytes.toBytes(sb.toString());
  }

  @Override
  public byte[] extractKey(Lift lift) {
    return Bytes.toBytes(lift.m.id + ", " + lift.rating.name().toLowerCase());
  }
}
