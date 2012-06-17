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

    /*
    Input:
    http://stackoverflow.com/questions/11036689/solving-the-integer-knapsack

4 5

1 8
2 4
3 0
2 5
2 3


Output:
13

     */
    int[] w = new int[] { 1, 2, 3, 2, 2 };
    int[] v = new int[] { 8, 4, 0, 5, 3 };
    System.out.println(Knapsack.A(w, v, w.length, 4));

    int[] w2 = new int[] {2,3,4,5};
    int[] v2 = new int[] {3,4,5,6};

    Knapsack knapsack;
    knapsack = new Knapsack(w, v, 4);
    knapsack.pack();
    knapsack.print();

    knapsack = new Knapsack(w2, v2, 5);
    knapsack.pack();
    knapsack.print();

    PrimeGenerator pg = new PrimeGenerator(1, 100);
    pg.generate();
    pg.print();

    Palindrome palindrome = new Palindrome(808);
    palindrome.next();
    System.out.println(palindrome.current);

    palindrome = new Palindrome(2133);
    palindrome.next();
    System.out.println(palindrome.current);
  }

  public static <T> void print(List<T> arr) {
    for(int i = 0; i < arr.size(); i++) {
      System.out.print(arr.get(i));
      System.out.println((i < arr.size() - 1) ? "," : "");
    }
  }

}
