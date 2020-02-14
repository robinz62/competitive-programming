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
        int[] nm = readIntLine();
        int n = nm[0];
        int m = nm[1];
        int[][] a = new int[n][];
        for (int i = 0; i < n; i++) {
            a[i] = readIntLine();
            for (int j = 0; j < m; j++) a[i][j]--;
        }
        int moves = 0;
        int[] matches = new int[n];
        for (int j = 0; j < m; j++) {
            Arrays.fill(matches, 0);
            for (int i = 0; i < n; i++) {
                if (a[i][j] % m != j) continue;
                int q = a[i][j] / m;
                if (q > n - 1) continue;
                int base = (i - q + n) % n;
                matches[base]++;
            }
            int minMoves = n;
            for (int i = 0; i < matches.length; i++) {
                minMoves = Math.min(minMoves, n - matches[i] + i);
            }
            moves += minMoves;
        }
        pw.println(moves);
    }
}