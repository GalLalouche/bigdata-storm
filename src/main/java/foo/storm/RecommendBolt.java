package foo.storm;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;
import foo.Movie;
import foo.window.MovieRating;
import foo.User;

import java.util.Map;

public class RecommendBolt extends BaseRichBolt {
  @Override
  public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {

  }

  @Override
  public void execute(Tuple input) {
//    Movie movieId = new Movie(input.getLongByField("movieId"));
//    Rating rating = new Rating(input.getDoubleByField("rating"));
//    User user = new User(input.getLongByField("userId"));
//
//    MovieRating mr = new MovieRating(movieId, rating, user);
//    System.out.println(mr);
  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

  }
}
