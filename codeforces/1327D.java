import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pw = new PrintWriter(System.out);
    static int MOD = 1000000007;

    public static void main(String[] args) throws IOException {
        Main m = new Main();
        m.solve();
        m.close();
    }

    void close() throws IOException {
        pw.flush();
        pw.close();
        br.close();
    }

    int readInt() throws IOException {
        return Integer.parseInt(br.readLine());
    }

    long readLong() throws IOException {
        return Long.parseLong(br.readLine());
    }

    int[] readIntLine() throws IOException {
        String[] tokens = br.readLine().split(" ");
        int[] A = new int[tokens.length];
        for (int i = 0; i < A.length; i++)
            A[i] = Integer.parseInt(tokens[i]);
        return A;
    }

    long[] readLongLine() throws IOException {
        String[] tokens = br.readLine().split(" ");
        long[] A = new long[tokens.length];
        for (int i = 0; i < A.length; i++)
            A[i] = Long.parseLong(tokens[i]);
        return A;
    }

    void solve() throws IOException {
        int T = readInt();
        boolean flag = false;
        for (int Ti = 0; Ti < T; Ti++) {
            int n = readInt();
            int[] p = readIntLine();
            for (int i = 0; i < p.length; i++) p[i]--;
            int[] c = readIntLine();

            List<List<Integer>> cycles = new ArrayList<>();
            boolean[] visited = new boolean[n];
            for (int src = 0; src < n; src++) {
                if (!visited[src]) {
                    List<Integer> cycle = new ArrayList<>();
                    visited[src] = true;
                    cycle.add(c[src]);
                    int j = p[src];
                    while (j != src) {
                        visited[j] = true;
                        cycle.add(c[j]);
                        j = p[j];
                    }
                    cycles.add(cycle);
                }
            }
            int best = Integer.MAX_VALUE;
            for (List<Integer> cycle : cycles) {
                List<Integer> factors = factors(cycle.size());
                Collections.sort(factors);
                for (int f : factors) {
                    boolean good = false;
                    for (int i = 0; i < f; i++) {
                        good = true;
                        for (int j = i; good && j < cycle.size(); j += f) {
                            if (!cycle.get(j).equals(cycle.get(i))) good = false;
                        }
                        if (good) {
                            best = Math.min(best, f);
                            break;
                        }
                    }
                    if (good) break;
                }
            }
            pw.println(best);
        }
    }

    public static List<Integer> factors(int n) {
        List<Integer> factors = new ArrayList<>();
        int i = 1;
        for (; i * i < n; i++) {
            if (n % i == 0) {
                factors.add(i);
                factors.add(n / i);
            }
        }
        if (i * i == n) factors.add(i);
        return factors;
    }
}