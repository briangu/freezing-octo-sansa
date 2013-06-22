package freezingoctosansa;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LockFreeLinkedList {

  public class ValueInfo {
    public Integer RemainingSlots;
    public Integer Value;
    public ValueInfo(Integer remainingSlots, Integer value) {
      this.RemainingSlots = remainingSlots;
      this.Value = value;
    }

    public boolean canInsertRight(Integer value) {
      return (value.equals(Value)) || ((value > Value) && (RemainingSlots > 0));
    }

    public boolean canInsertLeft(Integer value) {
      return (value.equals(Value)) || ((value < Value) && (RemainingSlots > 0));
    }
  }

  public class SparseArray {
    public final Integer[] data;
    public Integer min;
    public Integer max;

    public SparseArray(int capacity) {
      data = new Integer[capacity];
    }

    public ValueInfo getMax() {
      if (data.length > 0) {
        int idx = data.length - 1;
        while (idx >= 0) {
          if (data[idx] != 0) {
            return new ValueInfo(data.length - 1 - idx, data[idx]);
          }
          idx--;
        }
      }

      return null;
    }

    public ValueInfo getMin() {
      if (data.length > 0) {
        int idx = 0;
        while (idx < data.length) {
          if (data[idx] != 0) {
            return new ValueInfo(idx, data[idx]);
          }
          idx++;
        }
      }

      return null;
    }

    public boolean canInsert(Integer value) {
      ValueInfo minInfo = getMin();
      if (value.equals(minInfo.Value) || (value < minInfo.Value && minInfo.RemainingSlots > 0)) {
        return true;
      }
      ValueInfo maxInfo = getMax();
      return (value.equals(minInfo.Value) || (value > maxInfo.Value && maxInfo.RemainingSlots > 0));
    }

    public boolean insert(Integer value) {
      ValueInfo minInfo = getMin();
      if (value.equals(minInfo.Value)) {
        return true;
      }
      if (value < minInfo.Value && minInfo.RemainingSlots > 0) {

        return true;
      }
      ValueInfo maxInfo = getMax();
      if (value.equals(maxInfo.Value)) {
        return true;
      }
      if (value > maxInfo.Value && maxInfo.RemainingSlots > 0) {

        return true;
      }
      return false;
    }

    public void read(Set<Integer> results) {
      for (Integer value : data) {
        if (value != 0) {
          results.add(value);
        }
      }
    }
  }

  public class Node {
    public List<Node> Children;
    public List<SparseArray> SparseArrays;

    public Node(List<Node> children, List<SparseArray> sparseArrays) {
      Children = children == null ? Collections.<Node>emptyList() : children;
      SparseArrays = sparseArrays == null ? Collections.<SparseArray>emptyList() : sparseArrays;
    }
  }

  public volatile Node Root;

  public void insert(Integer value) {
    // find node where value is in the range and insert
  }

  public Set<Integer> read() {

    Set<Integer> results = new HashSet<Integer>();

    if (Root != null) {
      read(results, Root);
    }

    return results;
  }

  private void read(Set<Integer> results, Node node) {
    for (Node childNode : Root.Children) {
      read(results, childNode);
    }

    for (SparseArray sparseArray : node.SparseArrays) {
      sparseArray.read(results);
    }
  }
}
