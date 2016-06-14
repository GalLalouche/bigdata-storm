package foo.storm;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

public class MoviesSpout extends BaseRichSpout {
  private SpoutOutputCollector collector;
  private Scanner scanner;

  @Override
  public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
    try {
      this.scanner = new Scanner(new File("C:/ratings.dat")).useDelimiter("\n");
    } catch (FileNotFoundException e) {
      throw new AssertionError(e);
    }
    this.collector = spoutOutputCollector;
  }

  @Override
  public void nextTuple() {
    while (scanner.hasNext())
    collector.emit(Arrays.<Object>asList(scanner.next()));
  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
    outputFieldsDeclarer.declare(new Fields("line"));
  }
}
