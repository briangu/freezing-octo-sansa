package freezingoctosansa;

import java.util.*;

public class Main {

  public static void main(String[] args) {

    Random random = new Random();

    Stack<Integer> arr = new Stack<Integer>();
    for (int i = 0; i < 32; i++) {
      arr.push(random.nextInt(100));
    }

    print(arr);
    StackSort.sort(arr);
    print(arr);

    BinaryHeap bh = new BinaryHeap(10);
    BinaryHeap bh2 = new BinaryHeap(10);
    bh.print();
    for (int i = 0; i < 5; i++) {
      bh.insert(random.nextInt(100));
      bh2.insert(random.nextInt(100));
      bh.print();
      System.out.println("__________");
    }
    bh.print();
    bh.deleteMin();
    bh.print();

    bh2.print();
    bh.merge(bh2);
    bh.print();

    MaxSubSequence mss = new MaxSubSequence();
    mss.compute(new Integer[]{1, 2, -5, 4, 7, -2});
    System.out.println(String.format("(Sum,Start,Stop) = (%d,%d,%d)", mss.Sum, mss.Start, mss.Stop));

    DAG.Node nodeD = new DAG.Node("D");
    DAG.Node nodeC = new DAG.Node(Arrays.asList(nodeD),"C");
    DAG.Node nodeB = new DAG.Node(Arrays.asList(nodeD),"B");
    DAG.Node nodeA = new DAG.Node(Arrays.asList(nodeC, nodeB),"A");
    nodeA.print();

    int[] w = new int[] { 1, 3, 5, 9, 1 };
    int[] v = new int[] { 200, 150, 2, 100, 90 };
    System.out.println(Knapsack.A(w, v, 1, 10));


  }

  public static <T> void print(List<T> arr) {
    for(int i = 0; i < arr.size(); i++) {
      System.out.print(arr.get(i));
      System.out.println((i < arr.size() - 1) ? "," : "");
    }
  }

}
