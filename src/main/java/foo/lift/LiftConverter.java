package foo.lift;


import foo.hbase.StringConverter;
import org.apache.hadoop.hbase.util.Pair;

import java.util.LinkedList;
import java.util.List;

public class LiftConverter extends StringConverter<Pair<Long, Double>, List<Pair<Long, Double>>> {
  @Override
  protected List<Pair<Long, Double>> build(LinkedList<Pair<Long, Double>> list) {
    return list;
  }

  @Override
  protected Pair<Long, Double> decode(String str) {
    String[] pairSplit = str.split(",");
    return Pair.newPair(Long.parseLong(pairSplit[0]), Double.parseDouble(pairSplit[1]));
  }

  @Override
  protected String encode(Pair<Long, Double> p) {
    return p.getFirst() + "," + p.getSecond();
  }
}
