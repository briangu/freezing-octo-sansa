package freezingoctosansa;

import java.util.Stack;

public class MaxStack extends Stack<Integer> {
  Stack<Integer> _maxStack = new Stack<Integer>();

  public Integer max() {
    return _maxStack.peek();
  }

  public Integer push(Integer item) {
    if (_maxStack.isEmpty()) {
      _maxStack.push(item);
    } else {
      if (_maxStack.peek() <= item) _maxStack.push(item);
    }
    super.push(item);
    return item;
  }

  public Integer pop() {
    Integer result = super.pop();
    if (_maxStack.peek() == result) {
      _maxStack.pop();
    }
    return result;
  }
}
