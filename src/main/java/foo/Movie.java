package foo;

public class Movie {
  public final long id;

  public Movie(long id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    return id == ((Movie) o).id;

  }

  @Override
  public int hashCode() {
    return (int) (id ^ (id >>> 32));
  }
}
