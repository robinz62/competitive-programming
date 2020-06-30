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

    int[] ril(int n) throws IOException {
        int[] nums = new int[n];
        int c = 0;
        for (int i = 0; i < n; i++) {
            int sign = 1;
            c = br.read();
            int x = 0;
            if (c == '-') {
                sign = -1;
                c = br.read();
            }
            while (c >= '0' && c <= '9') {
                x = x * 10 + c - '0';
                c = br.read();
            }
            nums[i] = x * sign;
        }
        while (c != '\n' && c != -1) c = br.read();
        return nums;
    }

    long[] rll(int n) throws IOException {
        long[] nums = new long[n];
        int c = 0;
        for (int i = 0; i < n; i++) {
            int sign = 1;
            c = br.read();
            long x = 0;
            if (c == '-') {
                sign = -1;
                c = br.read();
            }
            while (c >= '0' && c <= '9') {
                x = x * 10 + c - '0';
                c = br.read();
            }
            nums[i] = x * sign;
        }
        while (c != '\n' && c != -1) c = br.read();
        return nums;
    }

    void solve() throws IOException {
        int t = ri();
        for (int ti = 0; ti < t; ti++) {
            int[] nk = ril(2);
            int n = nk[0];
            int k = nk[1];
            char[] s = br.readLine().toCharArray();
            int numOnes = 0;
            for (char c : s) {
                if (c == '1') numOnes++;
            }
            if (numOnes <= 1) {
                pw.println("0");
                continue;
            }
            int[] numToRight = new int[n];
            numToRight[n-1] = 0;
            for (int i = n-2; i >= 0; i--) {
                numToRight[i] = numToRight[i+1] + (s[i+1] == '1' ? 1 : 0);
            }
            int[] numToLeft = new int[n];
            numToLeft[0] = 0;
            for (int i = 1; i < n; i++) {
                numToLeft[i] = numToLeft[i-1] + (s[i-1] == '1' ? 1 : 0);
            }
            // dp[i][j] is #changes for s[0..i] where i is ON
            int[] prefix = new int[n];
            prefix[0] = s[0] - '0';
            for (int i = 1; i < n; i++) prefix[i] = prefix[i-1] + s[i] - '0';
            int[] dp = new int[n];
            dp[0] = s[0] == '0' ? 1 : 0;
            for (int i = 1; i < n; i++) {
                dp[i] = s[i] == '0' ? 1 : 0;
                if (i-k >= 0) dp[i] += dp[i-k];
                dp[i] += prefix[i-1] - (i - k >= 0 ? prefix[i-k] : 0);
                dp[i] = Math.min(dp[i], (s[i] == '0' ? 1 : 0) + numToLeft[i]);
            }
            int best = Integer.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                best = Math.min(best, dp[i] + numToRight[i]);
            }
            pw.println(best);
        }
    }
}