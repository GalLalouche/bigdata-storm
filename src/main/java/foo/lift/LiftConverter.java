package foo.lift;


import foo.hbase.HBaseConverter;

public class LiftConverter implements HBaseConverter<Lift> {
  @Override
  public Lift fromBytes(byte[] bytes) {
    return null;
  }

  @Override
  public byte[] toBytes(Lift lift) {
    return new byte[0];
  }

  @Override
  public byte[] extractKey(Lift lift) {
    return new byte[0];
  }
}
