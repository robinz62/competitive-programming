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

    List<List<int[]>> adj;
    void solve() throws IOException {
        int[] nm = ril(2);
        int n = nm[0];
        int m = nm[1];
        int[][] edges = new int[m][4];
        for (int i = 0; i < m; i++) {
            int[] e = ril(3);
            edges[i][0] = e[0]-1;
            edges[i][1] = e[1]-1;
            edges[i][2] = e[2];
            edges[i][3] = i;
        }
        Arrays.sort(edges, (e1, e2) -> Integer.compare(e1[2], e2[2]));
        adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        UnionFind uf = new UnionFind(n);
        long sum = 0;
        boolean[] chosen = new boolean[m];
        for (int i = 0; i < m; i++) {
            int[] e = edges[i];
            int u = e[0];
            int v = e[1];
            if (uf.find(u) != uf.find(v)) {
                uf.union(u, v);
                chosen[e[3]] = true;
                adj.get(u).add(new int[]{v, e[2]});
                adj.get(v).add(new int[]{u, e[2]});
                sum += e[2];
            }
        }

        LCA lca = new LCA(adj, 0);
        long[] ans = new long[m];
        for (int i = 0; i < m; i++) {
            int origIdx = edges[i][3];
            if (chosen[origIdx]) ans[origIdx] = sum;
            else {
                int max = lca.lcaedge(edges[i][0], edges[i][1]);
                ans[origIdx] = sum - max + edges[i][2];
            }
        }
        for (long ai : ans) pw.println(ai);
    }
}

class UnionFind {
    int n;
    int numComponents;
    int[] parent;
    int[] rank;

    UnionFind(int n) {
        this.n = numComponents = n;
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;
    }

    void union(int u, int v) {
        int x = find(u);
        int y = find(v);
        if (x == y) return;
        if (rank[x] < rank[y]) parent[x] = y;
        else if (rank[x] > rank[y]) parent[y] = x;
        else {
            parent[x] = y;
            rank[y]++;
        }
        numComponents--;
    }

    int find(int u) {
        int current = u;
        List<Integer> toUpdate = new ArrayList<>();
        while (parent[current] != current) {
            toUpdate.add(current);
            current = parent[current];
        }
        for (Integer node : toUpdate) parent[node] = current;
        return current;
    }

    int getNumComponents() {
        return numComponents;
    }
}

class LCA {
    int time = 0;
    int[] s;
    int[] f;
    int[][] up;
    int[][] upedge;

    LCA(List<List<int[]>> adj, int root) {
        s = new int[adj.size()];
        f = new int[adj.size()];
        int log2ceil = 31 - Integer.numberOfLeadingZeros(adj.size());
        up = new int[adj.size()][log2ceil + 1];
        upedge = new int[adj.size()][log2ceil + 1];
        boolean[] visited = new boolean[adj.size()];
        visited[root] = true;
        up[root][0] = root;
        upedge[root][0] = Integer.MIN_VALUE;
        dfs(adj, visited, root);

        for (int j = 1; j <= log2ceil; j++) {
            for (int i = 0; i < adj.size(); i++) {
                int x = up[i][j-1];
                up[i][j] = x == root ? root : up[x][j-1];
                upedge[i][j] = Math.max(upedge[i][j-1], upedge[x][j-1]);
            }
        }
    }

    int lca(int u, int v) {
        if (u == v) return u;
        if (isAncestor(u, v)) return u;
        if (isAncestor(v, u)) return v;
        int j = up[0].length-1;
        int curr = u;
        while (j >= 0) {
            if (isAncestor(up[curr][j], v)) j--;
            else curr = up[curr][j];
        }
        return up[curr][0];
    }

    int lcaedge(int u, int v) {
        int lca = lca(u, v);
        // climb u to lca
        int max = Integer.MIN_VALUE;
        int j = up[0].length-1;
        int curr = u;
        while (j >= 0 && curr != 0) {
            if (isAncestor(up[curr][j], lca) || up[curr][j] == lca) j--;
            else {
                max = Math.max(max, upedge[curr][j]);
                curr = up[curr][j];
            }
        }
        if (curr != lca) max = Math.max(max, upedge[curr][0]);
        j = up[0].length-1;
        curr = v;
        while (j >= 0 && curr != 0) {
            if (isAncestor(up[curr][j], lca) || up[curr][j] == lca) j--;
            else {
                max = Math.max(max, upedge[curr][j]);
                curr = up[curr][j];
            }
        }
        if (curr != lca) max = Math.max(max, upedge[curr][0]);
        return max;
    }

    boolean isAncestor(int u, int v) {
        return s[u] < s[v] && f[u] > f[v];
    }

    private void dfs(List<List<int[]>> adj, boolean[] visited, int u) {
        s[u] = time++;
        for (int[] vw : adj.get(u)) {
            if (!visited[vw[0]]) {
                visited[vw[0]] = true;
                up[vw[0]][0] = u;
                upedge[vw[0]][0] = vw[1];
                dfs(adj, visited, vw[0]);
            }
        }
        f[u] = time++;
    }
}