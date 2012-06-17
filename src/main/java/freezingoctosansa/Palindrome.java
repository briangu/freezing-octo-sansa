package freezingoctosansa;

import java.util.ArrayList;
import java.util.Collections;
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

  public static boolean isPalindromeString(int n)
  {
    String s = String.valueOf(n);
    int l = s.length();
    int mid = (l % 2 == 0) ? l / 2 : (l - 1) / 2;
    String a = s.substring(0,mid);
    String b = new StringBuffer(s.substring(mid + (l % 2))).reverse().toString();
    return a.equals(b);
  }

  public static boolean isPalindrome(int n)
  {
    int q = n;
    int k = 0;
    int c = 0;
    while (n > 0) {
      c++;
      k *= 10;
      k += n % 10;
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
