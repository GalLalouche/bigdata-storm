package foo.hbase;

import org.apache.hadoop.hbase.util.Bytes;

import java.util.LinkedList;

public abstract class StringConverter<T, S extends Iterable<T>> implements HBaseConverter<S> {
  @Override
  public S fromBytes(byte[] bytes) {
    String[] split = Bytes.toString(bytes).split(";");
    LinkedList<T> $ = new LinkedList<>();
    for (String str : split)
      if (str.isEmpty() == false)
        $.add(decode(str));

    return build($);
  }

  protected abstract S build(LinkedList<T> $);

  protected abstract T decode(String str);

  @Override
  public byte[] toBytes(S ts) {
    StringBuilder sb = new StringBuilder();
    for (T t : ts)
      sb.append(encode(t) + ";");
    return Bytes.toBytes(sb.toString());
  }

  protected abstract String encode(T t);
}
