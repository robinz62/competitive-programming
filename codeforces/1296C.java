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
        for (int t = 0; t < T; t++) {
            int n = readInt();
            char[] s = br.readLine().toCharArray();
            Map<Pair, Integer> prefixDeltas = new HashMap<>();  // delta -> last index
            prefixDeltas.put(new Pair(0, 0), -1);
            int x = 0;
            int y = 0;
            int[] idx = new int[2];
            int ans = Integer.MAX_VALUE;
            for (int i = 0; i < s.length; i++) {
                char c = s[i];
                if (c == 'L') x--;
                else if (c == 'R') x++;
                else if (c == 'U') y++;
                else if (c == 'D') y--;
                Pair p = new Pair(x, y);
                if (prefixDeltas.containsKey(p)) {
                    if (i - prefixDeltas.get(p) < ans) {
                        idx[0] = prefixDeltas.get(p) + 1;
                        idx[1] = i;
                        ans = i - prefixDeltas.get(p);
                    }
                }
                prefixDeltas.put(p, i);
            }
            if (ans == Integer.MAX_VALUE) pw.println("-1");
            else pw.println((idx[0]+1) + " " + (idx[1]+1));
        }
        
    }

    class Pair {
        int x;
        int y;
        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public int hashCode() {
            return x * 1749287 + y;
        }
        public boolean equals(Object o) {
            if (!(o instanceof Pair)) return false;
            Pair p = (Pair) o;
            return x == p.x && y == p.y;
        }
    }
}