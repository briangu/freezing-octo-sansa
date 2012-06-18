package freezingoctosansa;

public class PrintStars {

  public static void printStars(int depth) {
    printStars(depth, 0, depth, 1);
    printStars(depth, depth-2, 0, -1);
  }

  public static void printStars(int depth, int i, int stop, int step) {
    while(step > 0 ? i < stop : i >= stop) {
      for (int j = 0; j < (depth - 1 - i); j++) System.out.print(".");
      for (int j = 0; j < (2*i)+1; j++) System.out.print("*");
      System.out.println();
      i += step;
    }
  }
}
