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
        int n = ri();
        List<List<Integer>> adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int i = 0; i < n - 1; i++) {
            int[] uv = ril(2);
            adj.get(uv[0]-1).add(uv[1]-1);
            adj.get(uv[1]-1).add(uv[0]-1);
        }
        LCA lca = new LCA(adj, 0);  // root arbitrarily
        int q = ri();
        for (int i = 0; i < q; i++) {
            int[] xyabk = ril(5);
            int x = xyabk[0] - 1;
            int y = xyabk[1] - 1;
            int a = xyabk[2] - 1;
            int b = xyabk[3] - 1;
            int k = xyabk[4];

            int distA = distance(a, b, lca);
            int distB = distance(a, x, lca) + 1 + distance(y, b, lca);
            int distC = distance(a, y, lca) + 1 + distance(x, b, lca);
            if (distA <= k && (k - distA) % 2 == 0 ||
                distB <= k && (k - distB) % 2 == 0 ||
                distC <= k && (k - distC) % 2 == 0) {
                pw.println("YES");
                continue;
            }
            int left = k - (distA + distB);
            if (distA <= left && (left - distA) % 2 == 0 ||
                distB <= left && (left - distB) % 2 == 0 ||
                distC <= left && (left - distC) % 2 == 0) {
                pw.println("YES");
                continue;
            }
            left = k - (distA + distC);
            if (distA <= left && (left - distA) % 2 == 0 ||
                distB <= left && (left - distB) % 2 == 0 ||
                distC <= left && (left - distC) % 2 == 0) {
                pw.println("YES");
                continue;
            }
            pw.println("NO");
        }
    }

    int distance(int a, int b, LCA lca) {
        int w = lca.lca(a, b);
        if (a == w) return lca.depth[b] - lca.depth[a];
        if (b == w) return lca.depth[a] - lca.depth[b];
        return lca.depth[a] + lca.depth[b] - 2 * lca.depth[w];
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