package freezingoctosansa;

import java.util.List;
import java.util.Random;
import java.util.Stack;

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
  }

  public static <T> void print(List<T> arr) {
    for(int i = 0; i < arr.size(); i++) {
      System.out.print(arr.get(i));
      System.out.println((i < arr.size() - 1) ? "," : "");
    }
  }

}
