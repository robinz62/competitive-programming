import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static int MOD = 1000000007;

    // After writing solution, quick scan for:
    //   array out of bounds
    //   special cases e.g. n=1?
    //   npe, particularly in maps
    //
    // Big numbers arithmetic bugs:
    //   int overflow
    //   if (x : long) and (y : int), [y = x] does not compile, but [y += x] does
    //   sorting, or taking max, after MOD
    void solve() throws IOException {
        int n = ri();
        t = new int[n];
        List<List<Integer>> adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(null);
        root = -1;
        for (int i = 0; i < n; i++) {
            int[] at = ril(2);
            if (at[0] != 0) {
                if (adj.get(at[0]-1) == null) adj.set(at[0]-1, new ArrayList<>());
                adj.get(at[0]-1).add(i);
            } else root = i;
            t[i] = at[1];
        }

        int sum = 0;
        for (int ti : t) sum += ti;
        if (sum % 3 != 0) {
            pw.println("-1");
            return;
        }
        tgt = sum / 3;

        dp = new int[n];
        has = new int[n];
        Arrays.fill(has, -1);
        if (dfs(root, adj) && ans[0] != root && ans[1] != root) pw.println((ans[0]+1) + " " + (ans[1]+1));
        else pw.println("-1");
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    int tgt;
    int root;
    int[] t;
    int[] dp;
    int[] has;
    int[] ans = new int[2];
    boolean dfs(int u, List<List<Integer>> adj) {
        dp[u] = t[u];
        if (adj.get(u) != null) {
            for (int v : adj.get(u)) {
                if (dfs(v, adj)) return true;
                dp[u] += dp[v];
                if (has[v] != -1) {
                    if (has[u] != -1) {
                        ans[0] = has[u];
                        ans[1] = has[v];
                        return true;
                    } else {
                        has[u] = has[v];
                    }
                }
            }
        }
        if (dp[u] == 2 * tgt && has[u] != -1) {
            ans[0] = has[u];
            ans[1] = u;
            return true;
        }
        if (dp[u] == tgt) has[u] = u;
        return false;
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
        int c = br.read();
        int x = 0;
        while (c >= '0' && c <= '9') {
            x = x * 10 + c - '0';
            c = br.read();
        }
        return ril(x);
    }

    long[] rkll() throws IOException {
        int c = br.read();
        int x = 0;
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