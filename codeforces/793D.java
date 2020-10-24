import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static int MOD = 1000000007;

    // After writing solution, quick scan for:
    //   array out of bounds
    //   special cases e.g. n=1?
    //
    // Big numbers arithmetic bugs:
    //   int overflow
    //   sorting, or taking max, after MOD
    void solve() throws IOException {
        int[] nk = ril(2);
        int n = nk[0];
        int k = nk[1];
        int m = ri();
        adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            int[] uv = ril(3);
            adj.get(uv[0]-1).add(new int[]{uv[1]-1, uv[2]});
        }
        dp = new int[n][n][k+1][2];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                for (int ki = 0; ki <= k; ki++)
                    dp[i][j][ki][0] = dp[i][j][ki][1] = -1;
        int ans = Integer.MAX_VALUE;
        for (int l = 0; l < n; l++) {
            for (int r = l; r < n; r++) {
                ans = Math.min(ans, go(l, r, k, 0));
                ans = Math.min(ans, go(l, r, k, 1));
            }
        }
        pw.println(ans == Integer.MAX_VALUE ? "-1" : ans);
    }

    int[][][][] dp;
    List<List<int[]>> adj;
    int go(int l, int r, int k, int side) {
        if (k == 0 || k == 1) return 0;
        if (dp[l][r][k][side] != -1) return dp[l][r][k][side];
        if (l > r) return Integer.MAX_VALUE;
        if (k > r - l + 1) return Integer.MAX_VALUE;
        dp[l][r][k][side] = Integer.MAX_VALUE;
        if (side == 0) {
            for (int[] e : adj.get(l)) {
                int next = e[0];
                int cost = e[1];
                if (next < l || next > r) continue;
                
                if (go(next, r, k-1, 0) != Integer.MAX_VALUE) dp[l][r][k][0] = Math.min(dp[l][r][k][0], go(next, r, k-1, 0) + cost);
                if (go(l+1, next, k-1, 1) != Integer.MAX_VALUE) dp[l][r][k][0] = Math.min(dp[l][r][k][0], go(l+1, next, k-1, 1) + cost);
            }
        } else {
            for (int[] e : adj.get(r)) {
                int next = e[0];
                int cost = e[1];
                if (next < l || next > r) continue;
                
                if (go(l, next, k-1, 1) != Integer.MAX_VALUE) dp[l][r][k][1] = Math.min(dp[l][r][k][1], go(l, next, k-1, 1) + cost);
                if (go(next, r-1, k-1, 0) != Integer.MAX_VALUE) dp[l][r][k][1] = Math.min(dp[l][r][k][1], go(next, r-1, k-1, 0) + cost);
            }
        }
        return dp[l][r][k][side];
    }

    // Template code below

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pw = new PrintWriter(System.out);

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
        return Integer.parseInt(br.readLine().trim());
    }

    long rl() throws IOException {
        return Long.parseLong(br.readLine().trim());
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

    int[] rkil() throws IOException {
        int sign = 1;
        int c = br.read();
        int x = 0;
        if (c == '-') {
            sign = -1;
            c = br.read();
        }
        while (c >= '0' && c <= '9') {
            x = x * 10 + c - '0';
            c = br.read();
        }
        return ril(x);
    }

    long[] rkll() throws IOException {
        int sign = 1;
        int c = br.read();
        int x = 0;
        if (c == '-') {
            sign = -1;
            c = br.read();
        }
        while (c >= '0' && c <= '9') {
            x = x * 10 + c - '0';
            c = br.read();
        }
        return rll(x);
    }

    char[] rs() throws IOException {
        return br.readLine().toCharArray();
    }

    void sort(int[] A) {
        Random r = new Random();
        for (int i = A.length-1; i > 0; i--) {
            int j = r.nextInt(i+1);
            int temp = A[i];
            A[i] = A[j];
            A[j] = temp;
        }
        Arrays.sort(A);
    }

    void printDouble(double d) {
        pw.printf("%.16f", d);
    }
}