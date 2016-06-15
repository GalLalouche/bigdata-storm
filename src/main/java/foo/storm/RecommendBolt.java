package foo.storm;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

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
import foo.hbase.GenericHBaseWrapper;
import foo.lift.LiftGetter;
import foo.recommender.PosRecommender;
import foo.window.MovieRating;
import foo.window.Rating;
import foo.window.Window;
import foo.window.WindowConverter;
import foo.window.WindowRepository;

public class RecommendBolt extends BaseRichBolt {
  private static WindowRepository windowRepository;
  private static Ranker ranker;
  private OutputCollector outputCollector;

  static {
    try {
      windowRepository = new WindowRepository(new GenericHBaseWrapper<>(new WindowConverter(), BasicTableFactory.create("windows")));
      ranker = new Ranker(new LiftGetter(), new PosRecommender());
    } catch (IOException e) {
      throw new AssertionError(e);
    }
  }

  @Override
  public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
    this.outputCollector = outputCollector;
  }

  private Double handleMovie(MovieRating mr, Window w) {
    if (w.isFull() == false)
      return null;

    Integer recommendations = ranker.recommend(w, mr.m);
    if (recommendations == null || mr.r == Rating.NEUTRAL)
      return 0.0;

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
    Window updatedW = w.addMovie(new MovieRating(movieId, rating));
    windowRepository.saveWindow(updatedW);
  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
    outputFieldsDeclarer.declare(new Fields("Q"));

  }
}
