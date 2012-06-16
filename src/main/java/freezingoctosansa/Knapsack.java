package freezingoctosansa;

/*
def A(w, v, i,j):
    if i == 0 or j == 0: return 0
    if w[i-1] > j:  return A(w, v, i-1, j)
    if w[i-1] <= j: return max(A(w,v, i-1, j), v[i-1] + A(w,v, i-1, j - w[i-1]))
 */
public class Knapsack {

  /***
   *
   * @param w Weights
   * @param v Values
   * @param i evaluation position
   * @param j remaining weight
   * @return
   */
  public static int A(int[] w, int[] v, int i, int j) {
    if (i ==0 || j == 0) return 0;
    if (w[i-1] > j) return A(w, v, i-1, j);
    //if (w[i-1] <= j)
    return Math.max(A(w, v, i-1, j), v[i-1] + A(w, v, i-1, j - w[i-1]));
  }

}
