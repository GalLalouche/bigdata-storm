package foo;

import foo.window.Rating;

public class UserRating {
  public final Movie m;
  public final Rating r;
  public final User u;

  public UserRating(Movie m, Rating r, User u) {
    this.m = m;
    this.r = r;
    this.u = u;
  }
}
