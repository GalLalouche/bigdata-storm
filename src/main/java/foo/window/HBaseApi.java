package foo.window;

interface HBaseApi {
  byte[] getMoviesForUser(byte[] bytes);

  void saveMoviesForUser(byte[] userBytes, byte[] bytes);
}
