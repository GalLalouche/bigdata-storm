//package foo.window;
//
//import foo.User;
//import org.apache.hadoop.hbase.util.Bytes;
//import org.junit.Test;
//
//import java.util.Arrays;
//
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.*;
//
//public class WindowTest {
//  private HBaseApi hbaseApi = mock(HBaseApi.class);
//  private final byte[] userBytes = Bytes.toBytes(42L);
//  private final Window $ = new Window(new User(43), hbaseApi);
//
//  @Test
//  public void noPreviousMovies_addMovie() throws Exception {
//    when(hbaseApi.getMoviesForUser(userBytes)).thenReturn(new byte[0]);
//    $.addMovie(new MovieRating(666, Rating.POSITIVE));
//    verify(hbaseApi).saveMoviesForUser(userBytes, Bytes.toBytes("666,2;"));
//  }
//
//  @Test
//  public void somePreviousMovies_addMovie() throws Exception {
//    when(hbaseApi.getMoviesForUser(userBytes)).thenReturn(Bytes.toBytes("123,2;456,0;"));
//    $.addMovie(new MovieRating(666, Rating.NEGATIVE));
//    verify(hbaseApi).saveMoviesForUser(userBytes, Bytes.toBytes("666,0;123,2;456,0;"));
//  }
//
//  @Test
//  public void fullMovies_addMovie() throws Exception {
//    when(hbaseApi.getMoviesForUser(userBytes)).thenReturn(Bytes.toBytes("123,0;456,0;135,0;246,0;000,0;"));
//    $.addMovie(new MovieRating(666, Rating.NEUTRAL));
//    verify(hbaseApi).saveMoviesForUser(userBytes, Bytes.toBytes("666,1;123,0;456,0;135,0;246,0;"));
//  }
//
//  @Test
//  public void getMovies() throws Exception {
//    when(hbaseApi.getMoviesForUser(userBytes)).thenReturn(Bytes.toBytes("123,0;456,1;"));
//    assertEquals($.getMovies(), Arrays.asList(new MovieRating(123L, Rating.NEGATIVE), new MovieRating(456L, Rating.NEUTRAL)));
//  }
//
//  @Test
//  public void isFull_whenFull() throws Exception {
//    when(hbaseApi.getMoviesForUser(userBytes)).thenReturn(Bytes.toBytes("1,0;2,0;3,0;4,0;5,0;"));
//    assertTrue($.isFull());
//  }
//
//  @Test
//  public void isFull_whenNotFull() throws Exception {
//    when(hbaseApi.getMoviesForUser(userBytes)).thenReturn(Bytes.toBytes("1,0;2,0;3,0;4,0;"));
//    assertFalse($.isFull());
//  }
//}
