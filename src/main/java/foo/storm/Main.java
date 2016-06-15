package foo.storm;


import backtype.storm.Config;
import backtype.storm.ILocalCluster;
import backtype.storm.LocalCluster;
import backtype.storm.generated.StormTopology;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import foo.hbase.BasicTable;
import foo.hbase.BasicTableFactory;
import foo.hbase.GenericHBaseWrapper;
import foo.lift.LiftGetter;
import foo.window.WindowConverter;
import foo.window.WindowRepository;

public class Main {

  public static final int PARALLELISM_HINT = 80;

  public static void main(String[] args) throws Exception {
    Config config = new Config();
    config.setDebug(false);
    BasicTable windows = BasicTableFactory.create("windows");
    windows.clear();
    WindowRepository windowRepository = new WindowRepository(new GenericHBaseWrapper<>(new WindowConverter(), windows));
    LiftGetter liftGetter = new LiftGetter();
    ILocalCluster cluster = new LocalCluster();
    try {
      TopologyBuilder b = new TopologyBuilder();
      b.setSpout("movies", new MoviesSpout());
      b.setBolt("split", new SplittingBolt(), PARALLELISM_HINT).shuffleGrouping("movies");
      b.setBolt("recommend", new RecommendBolt(), PARALLELISM_HINT).fieldsGrouping("split", new Fields("userId"));
      b.setBolt("QAdder", new QAdder()).globalGrouping("recommend");
      StormTopology topology = b.createTopology();
      cluster.submitTopology("Movies", config, topology);
      cluster.activate("Movies");
      Thread.sleep(1_000_000_000);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      cluster.shutdown();
      System.exit(0);
    }
  }
}
