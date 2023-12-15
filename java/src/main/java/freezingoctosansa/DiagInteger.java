package freezingoctosansa;

public class DiagInteger extends java.lang.Number implements java.lang.Comparable<java.lang.Integer> {

  Integer _value;

  public DiagInteger(int x) {
    _value = x;
  }

  @Override
  public int compareTo(Integer integer) {
    return _value.compareTo(integer);
  }

  @Override
  public int intValue() {
    return _value.intValue();
  }

  @Override
  public long longValue() {
    return _value.longValue();
  }

  @Override
  public float floatValue() {
    return _value.floatValue();
  }

  @Override
  public double doubleValue() {
    return _value.doubleValue();
  }

  @Override
  public int hashCode() {
    System.out.println("hashcode for " + _value);
    return _value.hashCode();
  }

  @Override
  public boolean equals(java.lang.Object o) {
    System.out.println("equals for " + _value);
    return _value.equals(o);
  }
}
