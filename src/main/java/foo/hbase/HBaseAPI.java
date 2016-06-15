package foo.hbase;

import foo.window.Window;
import org.apache.hadoop.hbase.util.Pair;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

public class HBaseAPI<T>{
  private final HBaseConverter<T> converter;
  private final BasicTable table;

  public HBaseAPI(HBaseConverter<T> converter, BasicTable table) {
    this.converter = converter;
    this.table = table;
  }

  public void save(Iterable<T> ts) throws IOException {
    Collection<Pair<byte[], byte[]>> pairs = new LinkedList<>();
    for (T t : ts) {
      pairs.add(Pair.newPair(converter.extractKey(t), converter.toBytes(t)));
    }
    table.save(pairs);
  }

  public T load(byte[] key) throws IOException {
    byte[] load = table.load(key);
    if (load == null)
      return null;
    return converter.fromBytes(load);
  }

  public void save(T t) throws IOException {
    save(Arrays.asList(t));
  }
}
