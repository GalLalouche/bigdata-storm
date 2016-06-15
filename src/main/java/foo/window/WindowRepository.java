package foo.window;

import foo.User;
import foo.hbase.GenericHBaseWrapper;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class WindowRepository {
  private final GenericHBaseWrapper<Window> table;

  public WindowRepository(GenericHBaseWrapper<Window> table) {
    this.table = table;
  }

  public void saveWindow(Window w) {
    try {
      table.save(w);
    } catch (IOException e) {
      throw new AssertionError(e);
    }
  }

  public Window getWindow(User u) {
    try {
      return table.load(Bytes.toBytes(u.id));
    } catch (IOException e) {
      throw new AssertionError(e);
    }
  }
}
