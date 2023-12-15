package freezingoctosansa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WordSegmenter {

  private final Set<String> _dictionary;

  private class Node {
    public final Character Character;
    public final Map<Character, Node> Children = new HashMap<Character, Node>();
    public Boolean IsTermination = false;
    public Node() { this(null); }
    public Node(Character character) {
      Character = character;
    }
  }

  private final Node _index;

  public WordSegmenter(Set<String> dictionary) {
    _dictionary = dictionary;
    _index = buildIndex(dictionary);
  }

  private Node buildIndex(Set<String> dictionary) {
    Node root = new Node();

    for (String word : dictionary) {
      Map<Character, Node> index = root.Children;

      for (int i = 0; i < word.length(); i++) {
        Character ch = word.charAt(i);
        if (!index.containsKey(ch)) {
          index.put(ch, new Node(ch));
        }

        Node node = index.get(ch);
        if (i == (word.length() - 1)) {
          node.IsTermination = true;
        }
        index = node.Children;
      }
    }

    return root;
  }

  public String segment(String text) {
    return segment(text, 0);
  }

  private String segment(String text, int start) {
    if (start >= text.length()) return "";

    int offset = start;
    Node index = _index;
    Character ch;

    while(offset < text.length() && index.Children.containsKey(ch = text.charAt(offset))) {
      index = index.Children.get(ch);
      if (index.IsTermination) {
        String suffixSegments = segment(text, offset + 1);
        if (suffixSegments != null) {
          return text.substring(start, offset + 1) + " " + suffixSegments;
        }
      }
      ++offset;
    }

    return null;
  }
}
