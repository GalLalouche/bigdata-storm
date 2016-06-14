package foo.window;

public enum Rating {
  NEGATIVE, NEUTRAL, POSITIVE;

  public static Rating fromRating(Double d) {
    if (d <= 2.5)
      return NEGATIVE;
    if (d <= 3.0)
      return NEUTRAL;
    return POSITIVE;
  }
}
