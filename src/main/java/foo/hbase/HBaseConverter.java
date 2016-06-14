package foo.hbase;

public interface HBaseConverter<T> {
  T fromBytes(byte[] bytes);
  byte[] toBytes(T t);
  byte[] extractKey(T t);
}
