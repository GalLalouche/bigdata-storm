package foo.storm;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IBasicBolt;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;

import java.util.Map;

public class QAdder extends BaseRichBolt {
  @Override
  public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {

  }

  @Override
  public void execute(Tuple input) {

  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer declarer) {

  }
}
