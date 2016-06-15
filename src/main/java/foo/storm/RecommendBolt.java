package foo.storm;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import foo.Movie;
import foo.Ranker;
import foo.User;
import foo.hbase.BasicTableFactory;
import foo.hbase.HBaseAPI;
import foo.lift.LiftGetter;
import foo.recommender.PosRecommender;
import foo.window.*;
import org.apache.commons.lang.time.StopWatch;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;

public class RecommendBolt extends BaseRichBolt {
  static private WindowRepository windowRepository;
  static private Ranker ranker;
  private OutputCollector outputCollector;
  Random r = new Random();

  static {
    try {
      windowRepository = new WindowRepository(new HBaseAPI<>(new WindowConverter(), BasicTableFactory.create("windows")));
      ranker = new Ranker(new LiftGetter(), new PosRecommender());
    } catch (IOException e) {
      throw new AssertionError(e);
    }
  }

  private static void getWindowRepository() {
  }

  @Override
  public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
    this.outputCollector = outputCollector;
  }

  /**
   * Adds the next movie the user rated
   */
  private Double handleMovie(MovieRating mr, Window w) {
    if (w.isFull() == false)
      return null;

    Integer recommendations = ranker.recommend(w, mr.m);
    if (recommendations == null || mr.r == Rating.NEUTRAL)
      return null;

    return (mr.r == Rating.POSITIVE ? 1.0 : -1.0) / recommendations;
  }

  @Override
  public void execute(Tuple input) {
    Movie movieId = new Movie(input.getLongByField("movieId"));
    Rating rating = Rating.fromRating(input.getDoubleByField("rating"));
    User user = new User(input.getLongByField("userId"));
    Window w = windowRepository.getWindow(user);
    if (w == null)
      w = new Window(user);
    Double updatedQ = handleMovie(new MovieRating(movieId, rating), w);
    if (updatedQ != null)
      outputCollector.emit(Arrays.<Object>asList(updatedQ));
    w.addMovie(new MovieRating(movieId, rating));
    windowRepository.saveWindow(w);
  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
    outputFieldsDeclarer.declare(new Fields("Q"));

  }
}
