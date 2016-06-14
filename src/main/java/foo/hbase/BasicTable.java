package foo.hbase;

import org.apache.commons.lang.NotImplementedException;

public class BasicTable {
  public byte[] load(byte[] key) {
    throw new NotImplementedException();
  }

  public void save(byte[] key, byte[] data) {
    throw new NotImplementedException();
  }
}
