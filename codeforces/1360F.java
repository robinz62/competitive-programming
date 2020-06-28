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

    void solve() throws IOException {
        int t = ri();
        for (int ti = 0; ti < t; ti++) {
            int[] nm = ril();
            int n = nm[0];
            int m = nm[1];
            char[][] a = new char[n][];
            for (int i = 0; i < n; i++) {
                a[i] = br.readLine().toCharArray();
            }
            // dp[i][j] is if exists string for letters up to i
            // where difference counts are specified by j (exact)
            boolean[][] dp = new boolean[m][1 << n];
            char[][] choice = new char[m][1 << n];  // corresponding to dp
            for (char c = 'a'; c <= 'z'; c++) {
                int mask = 0;
                for (int i = 0; i < n; i++) {
                    if (a[i][0] != c) mask |= (1 << i);
                }
                dp[0][mask] = true;
                choice[0][mask] = c;
            }

            int[][] p = new int[m][1 << n];

            for (int j = 1; j < m; j++) {
                for (char c = 'a'; c <= 'z'; c++) {
                    for (int mask = 0; mask < (1 << n); mask++) {
                        if (!dp[j-1][mask]) continue;
                        int next = mask;
                        boolean ok = true;
                        for (int i = 0; i < n && ok; i++) {
                            if (a[i][j] != c) {
                                if ((mask & (1 << i)) > 0) {
                                    ok = false;
                                } else {
                                    next |= (1 << i);
                                }
                            }
                        }
                        if (ok) {
                            dp[j][next] = true;
                            choice[j][next] = c;
                            p[j][next] = mask;
                        }
                    }
                }
            }

            boolean ok = false;
            for (int mask = 0; mask < (1 << n); mask++) {
                if (dp[m-1][mask]) {
                    ok = true;
                    int curr = mask;
                    StringBuilder sb = new StringBuilder();
                    for (int j = m-1; j >= 0; j--) {
                        sb.append(choice[j][curr]);
                        curr = p[j][curr];
                    }
                    sb.reverse();
                    pw.println(sb.toString());
                    break;
                }
            }
            if (!ok) {
                pw.println("-1");
            }
        }
    }
}