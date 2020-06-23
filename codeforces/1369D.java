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

    int ri() throws IOException {
        return Integer.parseInt(br.readLine());
    }

    long rl() throws IOException {
        return Long.parseLong(br.readLine());
    }

    int[] ril() throws IOException {
        String[] tokens = br.readLine().split(" ");
        int[] A = new int[tokens.length];
        for (int i = 0; i < A.length; i++)
            A[i] = Integer.parseInt(tokens[i]);
        return A;
    }

    long[] rll() throws IOException {
        String[] tokens = br.readLine().split(" ");
        long[] A = new long[tokens.length];
        for (int i = 0; i < A.length; i++)
            A[i] = Long.parseLong(tokens[i]);
        return A;
    }

    // Post-contest note
    // In this solution I took the max of 2 modded values max(x % M, y % M).
    // In general, max(x, y) % M != max(x % M, y % M), but it happens to work
    // out in this case because |x - y| was always small (<= 4) and for the
    // range of inputs, there was never an error.
    // A "safe" solution does some additional checks of the level % 3.
    void solve() throws IOException {
        int t = ri();
        int maxN = 2_000_000;
        int[][] dp = new int[maxN+1][3];
        for (int i = 3; i <= maxN; i++) {
            dp[i][0] = (((dp[i-2][2] + dp[i-2][2]) % MOD) + dp[i-1][2]) % MOD;
            dp[i][1] = (((dp[i-2][0] + dp[i-2][0]) % MOD) + dp[i-1][0] + 4) % MOD;
            dp[i][2] = Math.max(dp[i][0], dp[i][1]);
        }
        for (int ti = 0; ti < t; ti++) {
            int n = ri();
            pw.println(dp[n][2]);
        }
    }
}