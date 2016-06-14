package foo.window;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Window implements Iterable<MovieRating> {
  static final int MAX_SIZE = 5;
  private final LinkedList<MovieRating> movies;

  public Window() {
    this(new LinkedList<MovieRating>());
  }

  public Window(LinkedList<MovieRating> movies) {
    this.movies = movies;
  }

  public void addMovie(MovieRating m) {
    if (isFull())
      movies.removeLast();
    movies.addFirst(m);
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
