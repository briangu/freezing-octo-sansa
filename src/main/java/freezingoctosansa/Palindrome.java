package freezingoctosansa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Palindrome {

  int current;

  public Palindrome(int start)
  {
    current = start;
  }

  public int next()
  {
    while(!isPalindrome(++current)) {}
    return current;
  }

  public static boolean isPalindrome(int n)
  {
    int q = n;
    int k = 0;
    int c = 0;
    while (n > 0) {
      c++;
      k = (k * 10) + n % 10;
      n /= 10;
    }
    n = q;
    c = ((c & 1) == 0) ? c / 2 : ((c - 1) / 2) + 1;
    for (int i = 0; i < c; i++) {
      n /= 10;
      k /= 10;
    }
    return k == n;
  }
}
