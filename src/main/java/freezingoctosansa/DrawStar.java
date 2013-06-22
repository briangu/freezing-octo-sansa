package freezingoctosansa;

public class DrawStar {

/*
  public static void drawStar(int n) {
    if(n < 1) {
      return;
    }

    char[] line = new char[2 * n - 1];
    int middle = n - 1;
    for (int i = 0; i < middle; i++) {
      line[i] = ' ';
    }
    line[middle] = '*';

    System.out.println(new String(line));

    if(n == 1) {
      return;
    }

    for (int i = 1; i < n; i++) {
      line[middle + i] = '*';
      line[middle - i] = '*';
      System.out.println(new String(line));
    }

    for (int i = n - 1; i > 0; i--) {
      line[middle + i] = ' ';
      line[middle - i] = ' ';
      System.out.println(new String(line));
    }

  }
*/
/*
static void drawStar(int n){
  int row = 2 * n - 1;
  for(int i = 0 ; i < row ; i ++){
    int space = Math.abs(n - 1 - i);
    int star = i < n ? 2 * i + 1 : 2 * (row - 1 - i) + 1;
    printSpace(space);
    printStars(star);
    printSpace(space);
    System.out.println();
  }
}

  static void printSpace(int n){
    for(int i = 0 ; i < n ; i ++){
      System.out.print(" ");
    }
  }

  static void printStars(int n){
    for(int i = 0 ; i < n ; i ++){
      System.out.print("*");
    }
  }
*/
static void drawStar(int n) {
  if (n <= 0) return;
  for (int i=0; i<2*n-1; i++) {
    StringBuilder sb = new StringBuilder();
    int x = i < n ? i : 2 * n - 2 - i;
    for (int j=0; j<n-x-1; j++) sb.append(" ");
    for (int j=0; j<2*x+1; j++) sb.append("*");
    System.out.println(sb.toString());
  }
}


  public static void main(String[] args) {
    drawStar(1);
    drawStar(2);
    drawStar(3);
  }
}
