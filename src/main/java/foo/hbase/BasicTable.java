package foo.hbase;

import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;

import java.io.IOException;

public class BasicTable {
  private static final byte[] EMPTY_QUALIFIER = new byte[0];
  private final Table table;
  private final byte[] columnFamily;

  public BasicTable(Table table, byte[] columnFamily) {
    this.table = table;
    this.columnFamily = columnFamily;
  }

  public byte[] load(byte[] key) throws IOException {
    return table
        .get(new Get(key))
        .getValue(columnFamily, EMPTY_QUALIFIER);
  }

  public void save(byte[] key, byte[] data) throws IOException {
    table.put(new Put(key)
        .addColumn(columnFamily, EMPTY_QUALIFIER, data));
  }
}
