package freezingoctosansa;

import java.util.*;


/*
Input:
2
1 10
3 5

Output:
2
3
5
7

3
5
 */
public class PrimeGenerator {

  public final Integer Start;
  public final Integer Stop;
  public Set<Integer> Primes;

  public PrimeGenerator(int start, int stop) {
    Start = start;
    Stop = stop;
  }

  public Set<Integer> generate() {
    return Primes = generate(Start, Stop);
  }

  public void print() {
    List<Integer> sorted = new ArrayList<Integer>(Primes);
    Collections.sort(sorted);
    for (Integer i : sorted) {
      System.out.println(i);
    }
  }

  public static Set<Integer> generate(int start, int stop) {
    Set<Integer> candidates = new HashSet<Integer>();

    for (int i = start; i <= stop; i++) {
      if ((i & 1) == 1) {
        candidates.add(i);
      }
    }

    for (int i = Math.max(2,start); i <= Math.sqrt(stop); i++) {
      if (candidates.contains(i)) {
        int pow = (int) Math.pow(i,2);
        for (int j = pow, k = 0; j < stop; k++, j = pow + k*i) {
          candidates.remove(j);
        }
      }
    }

    return candidates;
  }
}
