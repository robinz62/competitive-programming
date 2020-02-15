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
        int q = readInt();
        for (int qi = 0; qi < q; qi++) {
            int[] nm = readIntLine();
            int n = nm[0];  // [1, 100]
            int m = nm[1];  // [-10^9, 10^9]
            int[][] preferences = new int[n][];
            for (int i = 0; i < n; i++) {
                preferences[i] = readIntLine();
            }
            int[][] dp = new int[n+1][2];  // min, max
            dp[0][0] = dp[0][1] = m;
            int prevTime = 0;
            boolean good = true;
            for (int i = 1; i <= n; i++) {
                int[] curr = preferences[i-1];
                int timeElapsed = curr[0] - prevTime;
                int reachLo = dp[i-1][0] - timeElapsed;
                int reachHi = dp[i-1][1] + timeElapsed;
                if (reachLo <= curr[2] && curr[1] <= reachHi) {
                    dp[i][0] = reachLo <= curr[1] ? curr[1] : reachLo;
                    dp[i][1] = reachHi >= curr[2] ? curr[2] : reachHi;
                } else {
                    pw.println("NO");
                    good = false;
                    break;
                }


                if (dp[i][0] < curr[1] || dp[i][0] > curr[2] || dp[i][1] < curr[1] || dp[i][1] > curr[2]) pw.println("FATAL");
                prevTime = curr[0];
            }
            if (good) {
                pw.println("YES");
            }
        }
    }
}