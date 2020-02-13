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
        for (int Ti = 0; Ti < T; Ti++) {
            String s = br.readLine();
            String t = br.readLine();
            boolean[] used = new boolean[s.length()];
            boolean good = false;
            for (int z = 0; z < t.length() && !good; z++) {
                String x = t.substring(0, z);
                String y = t.substring(z);

                // dp[i][j] is minimum length prefix of s that contains
                // non-intersecting subsequences x[0..i) and y[0..j)
                int[][] dp = new int[x.length() + 1][y.length() + 1];
                int si = 0;
                for (int i = 1; i <= x.length(); i++) {
                    char c = x.charAt(i-1);
                    while (si < s.length() && s.charAt(si) != c) si++;
                    if (si == s.length() || dp[i-1][0] == -1) dp[i][0] = -1;
                    else dp[i][0] = si + 1;
                    si++;
                }
                si = 0;
                for (int j = 1; j <= y.length(); j++) {
                    char c = y.charAt(j-1);
                    while (si < s.length() && s.charAt(si) != c) si++;
                    if (si == s.length() || dp[0][j-1] == -1) dp[0][j] = -1;
                    else dp[0][j] = si + 1;
                    si++;
                }
                for (int i = 1; i <= x.length(); i++) {
                    for (int j = 1; j <= y.length(); j++) {
                        dp[i][j] = -1;
                        if (dp[i-1][j] != -1) {
                            for (int k = dp[i-1][j]; k < s.length(); k++) {
                                if (s.charAt(k) == x.charAt(i-1)) {
                                    dp[i][j] = k + 1;
                                    break;
                                }
                            }
                        }
                        if (dp[i][j-1] != -1) {
                            for (int k = dp[i][j-1]; k < s.length(); k++) {
                                if (s.charAt(k) == y.charAt(j-1)) {
                                    if (dp[i][j] == -1 || k+1 < dp[i][j]) {
                                        dp[i][j] = k+1;
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
                if (dp[x.length()][y.length()] != -1) good = true;
            }
            pw.println(good ? "YES" : "NO");
        }
    }
}