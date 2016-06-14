package foo.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class BasicTableFactory {
  private static final Connection connection;

  static {
    Configuration config = HBaseConfiguration.create();
    config.set("hbase.zookeeper.property.clientPort", "2181");
    config.set("hbase.zookeeper.quorum", "sandbox.hortonworks.com");
    config.set("zookeeper.znode.parent", "/hbase-unsecure");
    try {
      connection = ConnectionFactory.createConnection(config);
    } catch (IOException e) {
      throw new AssertionError(e);
    }
  }

  static public BasicTable create(String tableName) throws IOException {
    Table table = connection.getTable(TableName.valueOf(tableName));
    return new BasicTable(table, Bytes.toBytes("family"));
  }
}
