package foo.storm;


import backtype.storm.Config;
import backtype.storm.ILocalCluster;
import backtype.storm.LocalCluster;
import backtype.storm.generated.StormTopology;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

public class Main {

  public static final int PARALLELISM_HINT = 8;

  public static void main(String[] args) throws Exception {
    Config config = new Config();
    config.setDebug(false);

    ILocalCluster cluster = new LocalCluster();
    try {
      TopologyBuilder b = new TopologyBuilder();
      b.setSpout("movies", new MoviesSpout());
      b.setBolt("split", new SplittingBolt(), PARALLELISM_HINT).shuffleGrouping("movies");
      b.setBolt("recommend", new RecommendBolt(), PARALLELISM_HINT).fieldsGrouping("split", new Fields("userId"));
//    b.setBolt("QAdder", new QAdder(), 8).shuffleGrouping("recommend");
//    b.setBolt("QAdderFinal", new QAdder(), 1).globalGrouping("QAdder");
      StormTopology topology = b.createTopology();
      cluster.submitTopology("Movies", config, topology);

      Thread.sleep(10_000);

    } finally {
      cluster.shutdown();
      System.exit(0);
    }
  }
}
