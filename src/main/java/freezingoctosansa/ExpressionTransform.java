package freezingoctosansa;

// http://www.spoj.pl/problems/ONP/

import java.util.Stack;
import java.util.StringTokenizer;

/***

 Input:
 3
 (a+(b*c))
 ((a+b)*(z+x))
 ((a+t)*((b+(a+c))^(c+d)))

 Output:
 abc*+
 ab+zx+*
 at+bac++cd+^*

 */
public class ExpressionTransform {


  public class Node {
    public String Value;
    public Node Left;
    public Node Right;

    public Node() {}
    public Node(char ch) {
      Value = String.valueOf(ch);
    }
  }

  // (EXP OP EXP)
  public Node parse(String exp) {
    Stack<Node> stack = new Stack<Node>();

    stack.push(new Node());

    int i = 0;

    while(i < exp.length()) {
      Node node = stack.pop();
      char ch = exp.charAt(i);
      if (ch == '(') {
        stack.push(node);
        stack.push(new Node());
      } else if (ch == ')') {
        node.Right = stack.pop();
        node.Left = stack.pop();
        Node parent = stack.pop();
        stack.push(node);
        stack.push(parent);
      } else if (ch == '*' || ch == '-' || ch == '/' || ch == '+' || ch == '^') {
        node.Value = String.valueOf(ch);
        stack.push(node);
      } else {
        stack.push(new Node(ch));
        stack.push(node);
      }
      i++;
    }

    stack.pop();

    return stack.pop();
  }

  public String transform(Node root) {
    Stack<Node> stack = new Stack<Node>();

    stack.push(root);

    StringBuilder sb = new StringBuilder();

    while(!stack.isEmpty()) {
      Node node = stack.pop();
      sb.insert(0, node.Value);
      if (node.Left != null) stack.push(node.Left);
      if (node.Right != null) stack.push(node.Right);
    }

    return sb.toString();
  }

  public static String Transform(String exp) {
    ExpressionTransform et = new ExpressionTransform();
    Node root = et.parse(exp);
    return et.transform(root);
  }
}
