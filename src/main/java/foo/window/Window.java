package foo.window;

import java.util.Iterator;
import java.util.LinkedList;

import foo.User;

public class Window implements Iterable<MovieRating> {
  static final int MAX_SIZE = 5;
  private final LinkedList<MovieRating> movies;
  public final User u;

  public Window(User u) {
    this(u, new LinkedList<MovieRating>());
  }

  Window(User u, LinkedList<MovieRating> movies) {
    this.u = u;
    this.movies = movies;
  }

  public Window addMovie(MovieRating m) {
    LinkedList<MovieRating> list = new LinkedList<>();
    list.addAll(movies);
    if (isFull())
      list.removeLast();
    list.addFirst(m);
    return new Window(u, list);
  }

  public boolean isFull() {
    return movies.size() == MAX_SIZE;
  }

  @Override
  public Iterator<MovieRating> iterator() {
    return movies.iterator();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    return movies.equals(((Window) o).movies);
  }

  @Override
  public int hashCode() {
    return movies != null ? movies.hashCode() : 0;
  }
}
