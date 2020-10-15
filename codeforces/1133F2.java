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
        int[] nmD = ril(3);
        int n = nmD[0];
        int m = nmD[1];
        int D = nmD[2];
        int[][] edges = new int[m][];
        for (int i = 0; i < m; i++) {
            edges[i] = ril(2);
            edges[i][0]--;
            edges[i][1]--;
        }

        UnionFind uf = new UnionFind(n);
        List<int[]> tree = new ArrayList<>(n-2);
        List<Integer> vneighbors = new ArrayList<>();
        for (int[] e : edges) {
            if (e[0] == 0 || e[1] == 0) {
                if (e[0] == 0) vneighbors.add(e[1]);
                else vneighbors.add(e[0]);
                continue;
            }
            if (uf.find(e[0]) != uf.find(e[1])) {
                uf.union(e[0], e[1]);
                tree.add(e);
            }
        }

        if (vneighbors.size() < D) {
            pw.println("NO");
            return;
        }

        List<Set<Integer>> adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new HashSet<>());
        for (int[] e : tree) {
            adj.get(e[0]).add(e[1]);
            adj.get(e[1]).add(e[0]);
        }
        p = new int[n];
        visited = new boolean[n];
        List<Integer> roots = new ArrayList<>();
        List<Integer> others = new ArrayList<>();
        for (int w : vneighbors) {
            if (!visited[w]) {
                p[w] = -1;
                roots.add(w);
                dfs(w, -1, adj);
            } else {
                others.add(w);
            }
        }
        if (roots.size() > D) {
            pw.println("NO");
            return;
        }
        for (int w : roots) {
            adj.get(0).add(w);
            adj.get(w).add(0);
        }

        for (int i = 0; i < D-roots.size(); i++) {
            int w = others.get(i);
            adj.get(0).add(w);
            adj.get(w).add(0);
            adj.get(w).remove(p[w]);
            adj.get(p[w]).remove(w);
        }

        List<int[]> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j : adj.get(i)) {
                if (i < j) ans.add(new int[]{i+1, j+1});
            }
        }
        if (ans.size() != n-1) pw.println("NO");
        else {
            pw.println("YES");
            for (int[] e : ans) pw.println(e[0] + " " + e[1]);
        }
    }

    int[] p;
    boolean[] visited;
    void dfs(int u, int par, List<Set<Integer>> adj) {
        visited[u] = true;
        for (int v : adj.get(u)) {
            if (v == par) continue;
            p[v] = u;
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