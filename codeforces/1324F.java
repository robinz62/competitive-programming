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

    int[] a;
    boolean[] visited;
    int[] diffs;
    int[] diffs2;
    void solve() throws IOException {
        int n = ri();
        a = ril(n);
        List<List<Integer>> adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int i = 0; i < n - 1; i++) {
            int[] uv = ril(2);
            adj.get(uv[0]-1).add(uv[1]-1);
            adj.get(uv[1]-1).add(uv[0]-1);
        }
        visited = new boolean[n];
        diffs = new int[n];
        diffs2 = new int[n];
        visited[0] = true;
        dfs1(adj, 0);
        Arrays.fill(visited, false);
        visited[0] = true;
        dfs2(adj, 0, 0);
        for (int i = 0; i < n; i++) {
            pw.print(diffs2[i] + " ");
        }
        pw.println();
    }

    void dfs1(List<List<Integer>> adj, int u) {
        diffs[u] = a[u] == 1 ? 1 : -1;
        for (int v : adj.get(u)) {
            if (!visited[v]) {
                visited[v] = true;
                dfs1(adj, v);
                int wb = diffs[v];
                if (wb > 0) diffs[u] += wb;
            }
        }
    }

    void dfs2(List<List<Integer>> adj, int u, int d) {
        diffs2[u] = diffs[u];
        if (d > 0) diffs2[u] += d;
        d = Math.max(0, d) + (a[u] == 1 ? 1 : -1);
        for (int v : adj.get(u)) {
            if (!visited[v]) if (diffs[v] > 0) d += diffs[v];
        }
        for (int v : adj.get(u)) {
            if (!visited[v]) {
                visited[v] = true;
                if (diffs[v] > 0) d -= diffs[v];
                dfs2(adj, v, d);
                if (diffs[v] > 0) d += diffs[v];
            }
        }
    }
}