package foo.hbase;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Pair;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class BasicTable {
  private static final byte[] EMPTY_QUALIFIER = new byte[0];
  private final Table table;
  private final Admin admin;
  private final byte[] columnFamily;

  public BasicTable(Table table, Admin admin, byte[] columnFamily) {
    this.table = table;
    this.admin = admin;
    this.columnFamily = columnFamily;
  }

  public byte[] load(byte[] key) throws IOException {
    return table
        .get(new Get(key))
        .getValue(columnFamily, EMPTY_QUALIFIER);
  }

  public void save(byte[] key, byte[] data) throws IOException {
    table.put(createPut(key, data));
  }

  private Put createPut(byte[] key, byte[] data) {
    return new Put(key)
        .addColumn(columnFamily, EMPTY_QUALIFIER, data);
  }

  public void save(Iterable<Pair<byte[], byte[]>> keyDataPairs) throws IOException {
    List<Put> puts = new LinkedList<>();
    for (Pair<byte[], byte[]> pair : keyDataPairs)
      puts.add(createPut(pair.getFirst(), pair.getSecond()));
    table.put(puts);
  }

  public void clear() throws IOException {
    try {
      if (admin.isTableEnabled(table.getName()))
        admin.disableTable(table.getName());
      if (admin.tableExists(table.getName()))
        admin.deleteTable(table.getName());
      HTableDescriptor desc = new HTableDescriptor(table.getName());
      desc.addFamily(new HColumnDescriptor(columnFamily));
      admin.createTable(desc);
    } catch (IOException e) {
      throw new AssertionError(e);
    }
  }
}
