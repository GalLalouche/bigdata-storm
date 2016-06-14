package foo.window;

import foo.Movie;

public class MovieRating {
  public final Movie m;
  public final Rating r;

  public static MovieRating of(long l, double r) {
    return new MovieRating(new Movie(l), Rating.fromRating(r));
  }

  public MovieRating(Movie m, Rating r) {
    this.m = m;
    this.r = r;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    MovieRating that = (MovieRating) o;
    return m.id == that.m.id && r == that.r;
  }

  @Override
  public int hashCode() {
    return 31 * m.hashCode() + r.hashCode();
  }

  @Override
  public String toString() {
    return String.format("MovieRating{m=%d,r=%1.1f}", m.id, r);
  }
}
