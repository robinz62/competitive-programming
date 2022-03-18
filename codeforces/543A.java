import java.io.*;

public class A543 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] tokens = br.readLine().split(" ");
        int n = Integer.parseInt(tokens[0]);
        int m = Integer.parseInt(tokens[1]);
        int b = Integer.parseInt(tokens[2]);
        int mod = Integer.parseInt(tokens[3]);
        int[] a = new int[n];
        String[] nums = br.readLine().split(" ");
        for (int i = 0; i < n; ++i) {
            a[i] = Integer.parseInt(nums[i]);
        }

        int[][][] dp = new int[2][m + 1][b + 1];
        for (int k = 0; k <= b; ++k) {
            dp[0][0][k] = dp[1][0][k] = 1;
        }

        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j <= m; ++j) {
                for (int k = 0; k <= b; ++k) {
                    dp[i % 2][j][k] = dp[(i + 1) % 2][j][k];
                    if (k - a[i - 1] >= 0) {
                        dp[i % 2][j][k] += dp[i % 2][j - 1][k - a[i - 1]];
                    }
                    dp[i % 2][j][k] %= mod;
                }
            }
        }

        System.out.println(dp[n % 2][m][b]);
    }
}