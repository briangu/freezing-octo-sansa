package freezingoctosansa;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class DAG {

  public static class Node {
    public final List<Node> Next;
    public final String Value;

    public Node(String value) {
      Next = new ArrayList<Node>();
      Value = value;
    }
    public Node(List<Node> next, String value) {
      Next = next;
      Value = value;
    }

    private class PathNode extends Node {

      public final String Path;

      public PathNode(Node node, String path) {
        super(node.Next, node.Value);
        Path = path;
      }
    }

    public void print() {

      Stack<PathNode> stack = new Stack<PathNode>();

      stack.push(new PathNode(this, ""));

      while(!stack.isEmpty()) {

        PathNode node = stack.pop();

        String path = node.Path.length() == 0 ? node.Value.toString() : node.Path + ":" + node.Value.toString();

        if (node.Next.size() == 0) {
          System.out.println(path);
        } else {
          for (Node next : node.Next) {
            stack.push(new PathNode(next, path));
          }
        }
      }
    }
  }
}
