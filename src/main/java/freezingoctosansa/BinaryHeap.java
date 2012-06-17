package freezingoctosansa;


public class BinaryHeap {

  public int _capacity;
  public Integer[] _arr;
  public int _size = 0;

  public BinaryHeap(int h) {
    _capacity = h;
    _arr = new Integer[(int) (Math.pow(2, _capacity) - 1)];
  }

  public int size() {
    return _size;
  }

  public void insert(int x) {
    if (_size == _arr.length) {
      throw new RuntimeException("heap is full");
    }

    _size++;
    _arr[_size - 1] = x;
    siftUp(_size - 1);
  }

  private void siftUp(int i) {
    if (i == 0) return;

    int parentIdx = getParent(i);
    if (_arr[parentIdx] > _arr[i]) {
      int tmp = _arr[parentIdx];
      _arr[parentIdx] = _arr[i];
      _arr[i] = tmp;
      siftUp(parentIdx);
    }
  }

  public int deleteMin() {
    if (_size == 0) throw new RuntimeException("heap is empty");
    int min = _arr[0];
    _arr[0] = _arr[_size - 1];
    _size--;
    siftDown(0);
    return min;
  }

  private void siftDown(int i) {
    int leftIdx = getLeft(i);
    int rightIdx = getRight(i);
    int minIdx;

    if (rightIdx >= _size) {
      if (leftIdx >= _size) {
        return;
      } else {
        minIdx = leftIdx;
      }
    } else {
      if (_arr[leftIdx] <= _arr[rightIdx]) {
        minIdx = leftIdx;
      } else {
        minIdx = rightIdx;
      }
    }

    if (_arr[i] > _arr[minIdx]) {
      int tmp = _arr[minIdx];
      _arr[minIdx] = _arr[i];
      _arr[i] = tmp;
      siftDown(minIdx);
    }
  }

  public void merge(BinaryHeap heap) {
    while(heap.size() > 0) {
      insert(heap.deleteMin());
    }
  }

  public void print() {
    print(0, 0, "*");
  }

  private void swap(int i, int j)
  {
    int tmp = _arr[j];
    _arr[j] = _arr[i];
    _arr[i] = tmp;
  }

  private void print(int i, int level, String prefix) {
    if (i >= _size) return;
    for (int j = 0; j < level; j++) System.out.print(" ");
    System.out.println(prefix + _arr[i]);
    print(getLeft(i), level + 1, "<");
    print(getRight(i), level + 1, ">");
  }

  private int getLeft(int i) {
    return (2 * i + 1);
  }

  private int getRight(int i) {
    return (2 * i + 2);
  }

  private int getParent(int i) {
    return ((i-1)/2);
  }
}
