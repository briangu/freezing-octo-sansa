package freezingoctosansa;

import java.io.*;
import java.util.*;

public class SubgraphFinder {

  public class Row {
    public Integer Left;
    public Integer Right;
    public Row(Integer left, Integer right) {
      Left = left;
      Right = right;
    }
  }

  public class GraphIterator implements Iterator<Row> {

    final InputStream _is;
    final BufferedReader _reader;
    String _currentLine = null;

    public GraphIterator(InputStream is) {
      _is = is;
      _reader = new BufferedReader(new InputStreamReader(is));
    }

    @Override
    public boolean hasNext() {
      try {
        _currentLine = _reader.readLine();
      } catch (IOException e) {
      }
      return _currentLine != null;
    }

    @Override
    public Row next() {
      Row row = null;
      if (_currentLine != null) {
        String[] parts = _currentLine.split(" ");
        if (parts.length != 2) throw new RuntimeException("found illegal row in graph data: " + _currentLine);
        row = new Row(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
        _currentLine = null;
      }
      return row;
    }

    @Override
    public void remove() {
      throw new RuntimeException("not yet implemented");
    }

    public void close() {
      if (_is != null) try {
        _is.close();
      } catch (IOException e) {
      }
    }
  }

  public void findSubGraphs(String filename) {
    GraphIterator iter = openGraphFile(filename);
    try {
      Map<Integer, List<Integer>> rm = buildRowMap(iter);
      Map<Integer, List<Integer>> sg = buildSubGraphs(rm);
      printSubGraphs(sg);
    } finally {
      iter.close();
    }
  }

  private void printSubGraphs(Map<Integer, List<Integer>> sg) {
    for (Integer node : sg.keySet()) {
      System.out.print(node + " ");
      for (Integer neighbor : sg.get(node)) {
        System.out.print(neighbor + " ");
      }
      System.out.println();
    }
  }

  private Map<Integer, List<Integer>> buildSubGraphs(Map<Integer, List<Integer>> rm) {
    Map<Integer, List<Integer>> sg = new HashMap<Integer, List<Integer>>();

    Integer root;

    while((root = getNextKey(rm.keySet())) != null) {

      sg.put(root, new ArrayList<Integer>());

      Stack<Integer> stack = new Stack<Integer>();
      stack.push(root);

      while(!stack.isEmpty()) {
        Integer node = stack.pop();
        List<Integer> neighbors = rm.get(node);
        rm.remove(node);

        for (Integer neighbor : neighbors) {
          if (rm.containsKey(neighbor)) {
            stack.add(neighbor);
            sg.get(root).add(neighbor);
          }
        }
      }
    }

    return sg;
  }

  private Integer getNextKey(Set<Integer> keys) {
    Iterator<Integer> iter = keys.iterator();
    return iter.hasNext() ? iter.next() : null;
  }

  private Map<Integer, List<Integer>> buildRowMap(GraphIterator iter) {
    Map<Integer, List<Integer>> rm = new HashMap<Integer, List<Integer>>();

    while(iter.hasNext()) {
      Row row = iter.next();
      if (!rm.containsKey(row.Left)) {
        rm.put(row.Left, new ArrayList<Integer>());
      }
      rm.get(row.Left).add(row.Right);
    }

    return rm;
  }

  private GraphIterator openGraphFile(String filename) {
    File file = new File(filename);
    if (!file.exists()) throw new IllegalArgumentException("missing file " + filename);
    GraphIterator iter = null;
    try {
      iter = new GraphIterator(new FileInputStream(file));
    } catch (FileNotFoundException e) {
    }
    return iter;
  }
}
