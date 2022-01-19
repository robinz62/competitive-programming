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
        int T = ri();
        for (int Ti = 0; Ti < T; Ti++) {
            int[] nq = ril(2);
            int n = nq[0];
            int q = nq[1];
            a = ril(n);
            List<List<int[]>> adj = new ArrayList<>();
            List<List<Integer>> adjForLca = new ArrayList<>();
            for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
            for (int i = 0; i < n; i++) adjForLca.add(new ArrayList<>());
            for (int i = 0; i < n-1; i++) {
                int[] uvw = ril(3);
                adj.get(uvw[0]-1).add(new int[]{uvw[1]-1, uvw[2]});
                adj.get(uvw[1]-1).add(new int[]{uvw[0]-1, uvw[2]});
                adjForLca.get(uvw[0]-1).add(uvw[1]-1);
                adjForLca.get(uvw[1]-1).add(uvw[0]-1);
            }

            LCA lca_query = new LCA(adjForLca, 0);

            dp = new long[n];
            sumW = new long[n];
            sumA = new long[n];
            depth = new int[n];
            dp[0] = sumW[0] = 0;
            sumA[0] = a[0];
            dfs(0, -1, adj);
            for (int qi = 0; qi < q; qi++) {
                int[] xy = ril(2);
                int x = xy[0]-1;
                int y = xy[1]-1;

                int lca = lca_query.lca(x, y);

                long xToLca = dp[x] - dp[lca] - sumA[lca] * (sumW[x] - sumW[lca]) + sumW[lca] * (sumA[x] - sumA[lca]) + a[lca] * (sumW[x] - sumW[lca]);
                long lcaToY = - (dp[y] - dp[lca] - sumA[lca] * (sumW[y] - sumW[lca]) + sumW[lca] * (sumA[y] - sumA[lca]) + a[lca] * (sumW[y] - sumW[lca]));

                long add1 = (sumA[y] - sumA[lca]) * (sumW[x] - sumW[lca]);
                long add2 = - ( (sumA[x] - sumA[lca]) * (sumW[y] - sumW[lca]) );

                long ans = xToLca + lcaToY + atLca + add1 + add2;
                pw.println(ans);
            }
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    int[] a;
    long[] dp;    // dp[node] is the score of the path node -> 0
    long[] sumW;  // sumW[node] is the sum of edges from 0 -> node
    long[] sumA;  // sumA[node] is the sum of nodes from 0 -> node
    int[] depth;
    void dfs(int u, int p, List<List<int[]>> adj) {
        for (int[] vw : adj.get(u)) {
            int v = vw[0];
            int w = vw[1];
            if (v == p) continue;
            sumW[v] = sumW[u] + w;
            sumA[v] = sumA[u] + a[v];
            dp[v] = dp[u] + sumA[u] * w - a[v] * sumW[v];
            depth[v] = depth[u] + 1;
            dfs(v, u, adj);
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

class LCA {
    int n;
    int m;
    int[] first;
    int[] depth;
    int[] st;

    LCA(List<List<Integer>> adj, int root) {
        n = adj.size();
        first = new int[n];
        depth = new int[n];
        boolean[] visited = new boolean[n];
        List<Integer> euler = new ArrayList<>(2 * n);
        dfs(adj, root, visited, euler, 0);
        build(euler);
    }

    int lca(int u, int v) {
        int l = first[u];
        int r = first[v];
        if (l > r) {
            l = first[v];
            r = first[u];
        }
        r++;
        l += m;
        r += m;
        int minNode = st[l];
        while (l < r) {
            if ((l & 1) > 0) {
                minNode = depth[st[l]] < depth[minNode] ? st[l] : minNode;
                l++;
            }
            if ((r & 1) > 0) {
                r--;
                minNode = depth[st[r]] < depth[minNode] ? st[r] : minNode;
            }
            l /= 2;
            r /= 2;
        }
        return minNode;
    }

    private void dfs(List<List<Integer>> adj, int u, boolean[] visited,
            List<Integer> euler, int currDepth) {
        visited[u] = true;
        depth[u] = currDepth;
        first[u] = euler.size();
        euler.add(u);
        for (int v : adj.get(u)) {
            if (!visited[v]) {
                dfs(adj, v, visited, euler, currDepth + 1);
                euler.add(u);
            }
        }
    }

    private void build(List<Integer> euler) {
        m = euler.size();
        st = new int[m * 2];
        for (int i = 0; i < m; i++) {
            st[m+i] = euler.get(i);
        }
        for (int i = m - 1; i > 0; i--) {
            st[i] = depth[st[i*2]] < depth[st[i*2+1]] ? st[i*2] : st[i*2+1];
        }
    }
}