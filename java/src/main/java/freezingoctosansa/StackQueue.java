package freezingoctosansa;

import java.util.Stack;

public class StackQueue<T> {

  Stack<T> _stackIn = new Stack<T>();
  Stack<T> _stackOut = new Stack<T>();

  public void enqueue(T item) {
    _stackIn.push(item);
  }

  public T dequeue() {
    if (_stackOut.empty()) {
      while(!_stackIn.empty()) {
        _stackOut.push(_stackIn.pop());
      }
    }
    return _stackOut.empty() ? null : _stackOut.pop();
  }

  public boolean isEmpty() {
    return _stackIn.isEmpty() && _stackOut.isEmpty();
  }
}
