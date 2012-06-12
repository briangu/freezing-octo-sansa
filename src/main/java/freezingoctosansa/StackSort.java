package freezingoctosansa;

import java.util.Stack;

public class StackSort {

  public static void sort(Stack<Integer> stackA) {

    Stack<Integer> stackB = new Stack<Integer>();

    while (!stackA.empty()) {

      Integer a = stackA.pop();

      while (!stackB.isEmpty() && stackB.peek() < a) {
        stackA.push(stackB.pop());
      }

      stackB.push(a);
    }

    while(!stackB.empty()) stackA.push(stackB.pop());
  }
}
