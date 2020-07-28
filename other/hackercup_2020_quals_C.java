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

    char[] rs() throws IOException {
        return br.readLine().toCharArray();
    }

    void solve() throws IOException {
        int T = ri();
        for (int Ti = 0; Ti < T; Ti++) {
            int N = ri();
            long[][] PH = new long[N][];
            for (int i = 0; i < N; i++) {
                PH[i] = rll(2);
            }
            Arrays.sort(PH, (t1, t2) -> Long.compare(t1[0], t2[0]));
            // dp[x] is length of longest interval ending at x
            Map<Long, Long> dp = new HashMap<>();
            long ans = 0;
            for (long[] ph : PH) {
                long max = dp.getOrDefault(ph[0] + ph[1], 0l);
                max = Math.max(max, dp.getOrDefault(ph[0], 0l) + ph[1]); // fall right
                dp.put(ph[0] + ph[1], max);
                ans = Math.max(ans, max);
                max = dp.getOrDefault(ph[0], 0l);
                max = Math.max(max, ph[1] + dp.getOrDefault(ph[0] - ph[1], 0l));  // fall left
                dp.put(ph[0], max);
                ans = Math.max(ans, max);
            }

            pw.println("Case #" + (Ti+1) + ": " + ans);
        }
    }
}