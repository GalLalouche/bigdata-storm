package foo.storm;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import foo.Movie;
import foo.window.MovieRating;
import foo.User;

import java.util.Arrays;
import java.util.Map;

public class JoinerBolt extends BaseRichBolt {
  private OutputCollector collector;

  @Override
  public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
    this.collector = collector;
  }

  @Override
  public void execute(Tuple input) {
//    Movie movieId = new Movie(input.getLongByField("movieId"));
//    Rating rating = new Rating(input.getDoubleByField("rating"));
//    User user = new User(input.getLongByField("userId"));
//    collector.emit(Arrays.<Object>asList(new MovieRating(movieId, rating, user)));
  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    declarer.declare(new Fields("movieRating"));
  }
}
