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
    //   sorting, or taking max, after MOD
    
    // Again, *extremely* close on the runtime >:(
    // Following code passed with milliseconds to spare only after replacing
    // hashmap collapsed adjacency list with arraylists, which actually contains
    // redundant edges!
    void solve() throws IOException {
        int[] nm = ril(2);
        n = nm[0];
        int m = nm[1];
        adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            int[] uv = ril(2);
            adj.get(uv[0]-1).add(uv[1]-1);
            adj.get(uv[1]-1).add(uv[0]-1);
        }

        bridges();

        // Collapse all nodes (u, v) where (u, v) is an edge but not a bridge
        UnionFind uf = new UnionFind(n);
        for (int u = 0; u < n; u++) {
            for (int v : adj.get(u)) {
                if (!bridges.contains(u * n + v)) {
                    uf.union(u, v);
                }
            }
        }

        // Map<Integer, Set<Integer>> collapsedAdj = new HashMap<>();
        List<List<Integer>> collapsedAdj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) collapsedAdj.add(new ArrayList<>());
        int edges = 0;
        for (int u = 0; u < n; u++) {
            int x = uf.find(u);
            for (int v : adj.get(u)) {
                int y = uf.find(v);
                if (x == y) continue;
                // if (!collapsedAdj.containsKey(x)) collapsedAdj.put(x, new HashSet<>());
                // if (!collapsedAdj.containsKey(y)) collapsedAdj.put(y, new HashSet<>());
                collapsedAdj.get(x).add(y);
                collapsedAdj.get(y).add(x);
                edges++;
            }
        }

        if (edges == 0) {
            pw.println("0");
            return;
        }

        // collapsedAdj should now represent a tree. answer is the diameter.
        dfs2(uf.find(0), collapsedAdj, new boolean[n], 0);
        distOfFarthest = 0;
        dfs2(farthestNode, collapsedAdj, new boolean[n], 0);
        pw.println(distOfFarthest);
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    int farthestNode;
    int distOfFarthest;
    void dfs2(int u, List<List<Integer>> adj, boolean[] visited, int d) {
        visited[u] = true;
        if (d >= distOfFarthest) {
            distOfFarthest = d;
            farthestNode = u;
        }
        for (int v : adj.get(u)) {
            if (visited[v]) continue;
            dfs2(v, adj, visited, d+1);
        }
    }

    int time;
    int n;
    List<List<Integer>> adj;
    boolean[] visited;
    int[] s;
    int[] low;

    Set<Integer> bridges = new HashSet<>();

    void dfs(int u, int p) {
        visited[u] = true;
        s[u] = low[u] = time++;
        for (int v : adj.get(u)) {
            if (v == p) continue;
            if (visited[v]) {
                low[u] = Math.min(low[u], s[v]);
            } else {
                dfs(v, u);
                low[u] = Math.min(low[u], low[v]);
                if (low[v] > s[u]) {
                    // (u, v) is a bridge
                    bridges.add(u * n + v);
                    bridges.add(v * n + u);
                }
            }
        }
    }

    void bridges() {
        time = 0;
        visited = new boolean[n];
        s = new int[n];
        low = new int[n];
        Arrays.fill(s, -1);
        Arrays.fill(low, -1);
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfs(i, -1);
            }
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

    void printDouble(double d) {
        pw.printf("%.16f", d);
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