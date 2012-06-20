package freezingoctosansa;

import java.util.ArrayList;
import java.util.List;

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
   * @param i evaluation position (starts with w.length)
   * @param j remaining weight
   * @return
   */
  public static int Value(int[] w, int[] v, int i, int j) {
    if (i ==0 || j == 0) return 0;
    return (w[i - 1] > j)
      ? Value(w, v, i - 1, j)
      : Math.max(Value(w, v, i - 1, j), v[i - 1] + Value(w, v, i - 1, j - w[i - 1]));
  }

  int[][] B;
  int Value;
  Integer[] ItemIdx;
  int Capacity;
  int[] Weights;
  int[] Values;

  public Knapsack(int[] w, int[] v, int C)
  {
    Weights = w;
    Values = v;
    Capacity = C;
  }

  // http://www.cse.unl.edu/~goddard/Courses/CSCE310J/Lectures/Lecture8-DynamicProgramming.pdf
  public int pack()
  {
    B = new int[Weights.length+1][Capacity+1];

    for (int i = 1; i <= Weights.length; i++) {
      for (int w = 0; w <= Capacity; w++) {
        B[i][w] = Math.max(B[i-1][w], (Weights[i-1] <= w) ? Values[i-1] + B[i-1][w - Weights[i-1]] : B[i-1][w]);
      }
    }

    Value = B[Weights.length][Capacity];
    findItems();

    return Value;
  }

  private void findItems()
  {
    int i = Weights.length;
    int k = Capacity;

    List<Integer> idx = new ArrayList<Integer>();

    while (i > 0 && k > 0) {
      if (B[i][k] != B[i-1][k]) {
        idx.add(i);
        k -= Weights[i-1];
      }
      i--;
    }

    ItemIdx = idx.toArray(new Integer[0]);
  }

  public void print()
  {
    for (int i = 0; i <= Weights.length; i++) {
      for (int w = 0; w <= Capacity; w++) {
        System.out.print(String.format("%2d ", B[i][w]));
      }
      System.out.println();
    }

    System.out.println(String.format("Value = %d", Value));

    System.out.println("Items (w,v):");
    for (int i = 0; i < ItemIdx.length; i++) {
      System.out.println(String.format("(%d,%d)", Weights[ItemIdx[i]-1], Values[ItemIdx[i]-1]));
    }
  }
}
