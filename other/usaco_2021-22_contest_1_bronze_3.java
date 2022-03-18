import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);

        int T = Integer.parseInt(br.readLine());
        for (int Ti = 0; Ti < T; Ti++) {
            String[] line = br.readLine().split(" ");
            int n = Integer.parseInt(line[0]);
            int k = Integer.parseInt(line[1]);
            
            char[][] grid = new char[n][];
            for (int i = 0; i < n; i++) grid[i] = br.readLine().toCharArray();

            // dp[i][j][k][l] is number of ways to get to grid[i][j] using exactly k turns and our
            // new direction is l.
            // l is 0 for right, 1 for down
            long[][][][] dp = new long[n][n][k+1][2];
            dp[0][0][0][0] = dp[0][0][0][1] = 1;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i == 0 && j == 0) continue;
                    if (grid[i][j] == 'H') continue;

                    for (int ki = 0; ki <= k; ki++) {
                        // Don't turn from previous
                        if (j-1 >= 0) dp[i][j][ki][0] += dp[i][j-1][ki][0];
                        if (i-1 >= 0) dp[i][j][ki][1] += dp[i-1][j][ki][1];

                        // Turn from previous direction, only if we have turns
                        if (ki != 0) {
                            if (i-1 >= 0) dp[i][j][ki][0] += dp[i-1][j][ki-1][1];
                            if (j-1 >= 0) dp[i][j][ki][1] += dp[i][j-1][ki-1][0];
                        }
                    }
                }
            }

            long ans = 0;
            for (int ki = 0; ki <= k; ki++) ans += dp[n-1][n-2][ki][0] + dp[n-2][n-1][ki][1];
            pw.println(ans);
        }

        pw.flush();
    }

    static int sign(int x) {
        return x > 0 ? 1 : x < 0 ? -1 : 0;
    }
}