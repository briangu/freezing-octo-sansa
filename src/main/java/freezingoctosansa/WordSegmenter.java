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

/*
  public String segment2(String text) {
    return segment(text, _index);
  }

  private String segment2(String text, Node index) {
    if (index.IsTermination || text.length() == 0) return text;

    int offset = 0;
    Character ch;

    while(offset < text.length() && index.Children.containsKey(ch = text.charAt(offset))) {
      index = index.Children.get(ch);
      if (index.IsTermination) {
        String suffixSegments = segment(text.substring(offset + 1), _index);
        if (suffixSegments != null) {
          return text.substring(0, offset + 1) + " " + suffixSegments;
        }
      }
      ++offset;
    }

    return null;
  }
*/


  public String segment(String text) {
    return segment(text, _index, 0);
  }

  private String segment(String text, Node index, int start) {
    if (index.IsTermination || start >= text.length()) return start >= text.length() ? "" : text.substring(start);

    int offset = start;
    Character ch;

    while(offset < text.length() && index.Children.containsKey(ch = text.charAt(offset))) {
      index = index.Children.get(ch);
      if (index.IsTermination) {
        String suffixSegments = segment(text, _index, offset + 1);
        if (suffixSegments != null) {
          return text.substring(start, offset + 1) + " " + suffixSegments;
        }
      }
      ++offset;
    }

    return null;
  }
}
