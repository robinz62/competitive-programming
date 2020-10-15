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
        int n = ri();
        List<List<Integer>> adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int i = 0; i < n-1; i++) {
            int[] uv = ril(2);
            adj.get(uv[0]-1).add(uv[1]-1);
            adj.get(uv[1]-1).add(uv[0]-1);
        }
        // 0 -> edge to parent, none to me
        // 1 -> edge to parent, edge to me
        // 2 -> none to parent, edge to me
        // 3 -> none to parent, none to me
        dp = new int[n][4];
        int ans = 0;
        for (int v : adj.get(0)) {
            for (int w : adj.get(v)) {
                if (w == 0) continue;
                dfs(adj, w, v);
                ans += Math.min(dp[w][0], dp[w][1]);
            }
        }
        pw.println(ans);
    }

    int[][] dp;
    void dfs(List<List<Integer>> adj, int u, int p) {
        for (int v : adj.get(u)) if (v != p) dfs(adj, v, u);
        dp[u][1]++;
        dp[u][2]++;
        for (int v : adj.get(u)) {
            if (v == p) continue;
            dp[u][0] += Math.min(dp[v][2], dp[v][3]);
            dp[u][1] += Math.min(dp[v][0], dp[v][1]);
            dp[u][2] += Math.min(dp[v][0], dp[v][1]);
        }

        boolean haveEdge = false;
        int bestDelta = Integer.MAX_VALUE;
        for (int v : adj.get(u)) {
            if (v == p) continue;
            // 2 vs 3, need at least one 2
            if (dp[v][2] <= dp[v][3]) {
                haveEdge = true;
                dp[u][3] += dp[v][2];
            } else {
                bestDelta = Math.min(bestDelta, dp[v][2] - dp[v][3]);
                dp[u][3] += dp[v][3];
            }
        }
        if (!haveEdge) {
            dp[u][3] += bestDelta;
        }
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
}