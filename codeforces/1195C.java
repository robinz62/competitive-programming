import java.io.*;
import java.util.*;
 
public class Main {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pw = new PrintWriter(System.out);
 
    public static void main(String[] args) throws Exception {
        Main m = new Main();
        m.solve();
        m.close();
    }
 
    void close() throws Exception {
        pw.flush();
        pw.close();
        br.close();
    }

    int[] readIntArray() throws Exception {
        String[] tokens = br.readLine().split(" ");
        int[] A = new int[tokens.length];
        for (int i = 0; i < A.length; i++)
            A[i] = Integer.parseInt(tokens[i]);
        return A;
    }
 
    void solve() throws Exception {
        int n = Integer.parseInt(br.readLine());
        int[] h1 = readIntArray();
        int[] h2 = readIntArray();
        // dp[i][j] is max height sum for players in columns [i, n]
        //          where the last person chosen was in row j
        long[][] dp = new long[n][2];
        dp[n - 1][0] = h1[n - 1];
        dp[n - 1][1] = h2[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            dp[i][0] = Math.max(dp[i + 1][0], h1[i] + dp[i + 1][1]);
            dp[i][1] = Math.max(dp[i + 1][1], h2[i] + dp[i + 1][0]);
        }
        pw.println(Math.max(dp[0][0], dp[0][1]));
    }
}