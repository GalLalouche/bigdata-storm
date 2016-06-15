package foo.storm;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;

import java.util.Map;

public class QAdder extends BaseRichBolt {
  private static Double Q = 0.0;
  private static int count = 0;
  @Override
  public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
  }

  @Override
  public void execute(Tuple input) {
//    System.out.println("\n\n--------------------\n\n" + input + "\n\n--------------------\n\n");
    double q = input.getDouble(0);
    Q += q;
    count++;
    System.out.println(String.format("New Q: %f. Total Q is now %f. Total updates: %d", q, Q, count));
  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer declarer) {

  }
}
