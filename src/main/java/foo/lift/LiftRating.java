package foo.lift;

public enum LiftRating {
  POS, NEG;

  public static LiftRating isPositive(boolean isPositive) {
    return isPositive ? POS : NEG;
  }
}
