package freezingoctosansa;

// m[i] = max{a[i], m[i-1] + a[i]};
public class MaxSubSequence {

  public Integer Sum;
  public Integer Start;
  public Integer Stop;

  public void compute(Integer[] arr) {

    Start = 0;
    Stop = 0;
    Sum = Integer.MIN_VALUE;

    Integer t = 0;
    Integer j = 0;

    for (int i = 0; i < arr.length; i++) {
      t += arr[i];
      if (t > Sum) {
        Sum = t;
        Start = j;
        Stop = i+1;
      } if (t < 0) {
        t = 0;
        j = i+1;
      }
    }
  }
}
