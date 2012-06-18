package freezingoctosansa;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Justified {

  private final Integer ColWidth;

  public Justified(Integer colWidth) {
    ColWidth = colWidth;
  }

  public List<String> justify(String text) {
    List<List<String>> lines = splice(text);
    List<String> justified = addSpaces(lines);
    return justified;
  }

  private List<List<String>> splice(String text) {
    List<List<String>> lines = new ArrayList<List<String>>();

    List<String> line = new ArrayList<String>();
    int len = 0;

    StringTokenizer st = new StringTokenizer(text, " ");
    while (st.hasMoreTokens()) {
      String word = st.nextToken();

      if ((len + word.length()) > ColWidth) {
        if (len == 0) {
          line.add(word.substring(0, ColWidth-1) + "-");
          word = word.substring(ColWidth-1, word.length());
        }
        lines.add(line);
        line = null;
        len = 0;
      }

      if (line == null) line = new ArrayList<String>();
      line.add(word);
      len += word.length() + (len == 0 ? 0 : 1);
    }

    if (line != null && line.size() > 0) {
      lines.add(line);
    }

    return lines;
  }

  private List<String> addSpaces(List<List<String>> lines) {
    List<String> justified = new ArrayList<String>();

    for (List<String> line : lines) {
      int len = 0;
      for (String word : line) {
        len += word.length();
      }
      int spaces = ColWidth - len;
      int regularSpaces = line.size() == 1 ? 0 : spaces / (line.size() - 1);
      int extraSpaces = (spaces - regularSpaces);

      StringBuilder builder = new StringBuilder();

      for (String word : line) {
        for (int i = 0; i < regularSpaces; i++) word = word + " ";
        if (extraSpaces > 0) {
          word = word + " ";
          extraSpaces--;
        }
        builder.append(word);
      }

      justified.add(builder.toString());
    }

    return justified;
  }
}
