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
        int t = ri();
        for (int ti = 0; ti < t; ti++) {
            int n = ri();
            int[] p = ril(2 * n);
            TreeSet<Integer> here = new TreeSet<>();
            for (int i = 1; i <= 2 * n; i++) here.add(i);
            List<Integer> chunks = new ArrayList<>();
            int size = 0;
            for (int i = 2 * n-1; i >= 0; i--) {
                int me = p[i];
                size++;
                if (me == here.last()) {
                    here.remove(me);
                    chunks.add(size);
                    size = 0;
                } else {
                    here.remove(me);
                }
            }
            boolean[][] dp = new boolean[chunks.size()][n + 1];
            dp[0][0] = true;
            if (chunks.get(0) <= n) dp[0][chunks.get(0)] = true;
            for (int i = 1; i < chunks.size(); i++) {
                dp[i][0] = true;
                for (int tgt = 1; tgt <= n; tgt++) {
                    if (tgt - chunks.get(i) >= 0 && dp[i-1][tgt - chunks.get(i)]) {
                        dp[i][tgt] = true;
                    }
                    if (dp[i-1][tgt]) dp[i][tgt] = true;
                }
            }
            pw.println(dp[chunks.size() - 1][n] ? "YES" : "NO");
        }

        // one of the arrays is [biggest number] + suffix
    }
}