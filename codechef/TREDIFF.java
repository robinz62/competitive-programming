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

    int readInt() throws IOException {
        return Integer.parseInt(br.readLine());
    }

    long readLong() throws IOException {
        return Long.parseLong(br.readLine());
    }

    int[] readIntLine() throws IOException {
        String[] tokens = br.readLine().split(" ");
        int[] A = new int[tokens.length];
        for (int i = 0; i < A.length; i++)
            A[i] = Integer.parseInt(tokens[i]);
        return A;
    }

    long[] readLongLine() throws IOException {
        String[] tokens = br.readLine().split(" ");
        long[] A = new long[tokens.length];
        for (int i = 0; i < A.length; i++)
            A[i] = Long.parseLong(tokens[i]);
        return A;
    }

    void solve() throws IOException {
        int T = readInt();
        for (int t = 0; t < T; t++) {
            int[] NQ = readIntLine();
            int N = NQ[0];
            int Q = NQ[1];
            int[] A = readIntLine();
            List<List<Integer>> adj = new ArrayList<>(N);
            for (int i = 0; i < N; i++) adj.add(new ArrayList<>());
            for (int i = 0; i < N - 1; i++) {
                int[] uv = readIntLine();
                adj.get(uv[0]-1).add(uv[1]-1);
                adj.get(uv[1]-1).add(uv[0]-1);
            }
            int root = (int) (Math.random() * N);
            Map<Integer, int[]> counts = new HashMap<>();
            int[] p = new int[N];
            p[root] = -1;
            boolean[] visited = new boolean[N];
            visited[root] = true;
            counts.put(-1, new int[101]);
            dfsCounts(root, adj, visited, new int[101], A, p, counts);
            LCA lca = new LCA(adj, root);
            for (int q = 0; q < Q; q++) {
                int[] ab = readIntLine();
                int a = ab[0]-1;
                int b = ab[1]-1;

                int c = lca.lca(a, b);
                int minDiff = Math.abs(A[a] - A[b]);
                if (c == a || c == b) {
                    int[] countsB = counts.get(c == a ? b : a);
                    int[] countsSub = counts.get(p[c]);
                    int count = -500;
                    for (int i = 1; i <= 100; i++) {
                        int countI = countsB[i] - countsSub[i];
                        if (countI > 1) {
                            minDiff = 0;
                            break;
                        }
                        if (countI == 1) {
                            minDiff = Math.min(minDiff, Math.abs(count+1));
                            count = 0;
                        } else {
                            count++;
                        }
                    }
                } else {
                    int[] countsA = counts.get(a);
                    int[] countsB = counts.get(b);
                    int[] countsC = counts.get(p[c]);
                    int count = -500;
                    for (int i = 1; i <= 100; i++) {
                        int countI = countsA[i] + countsB[i] - 2 * countsC[i];
                        if (i == A[c]) countI--;
                        if (countI > 1) {
                            minDiff = 0;
                            break;
                        }
                        if (countI == 1) {
                            minDiff = Math.min(minDiff, Math.abs(count+1));
                            count = 0;
                        } else {
                            count++;
                        }
                    }
                }
                pw.println(minDiff);
            }
        }
    }

    void dfsCounts(Integer u, List<List<Integer>> adj, boolean[] visited, int[] curr,
                   int[] A, int[] p, Map<Integer, int[]> counts) {
        curr[A[u]]++;
        counts.put(u, Arrays.copyOf(curr, curr.length));
        for (Integer v : adj.get(u)) {
            if (!visited[v]) {
                visited[v] = true;
                p[v] = u;
                dfsCounts(v, adj, visited, curr, A, p, counts);
            }
        }
        curr[A[u]]--;
    }
}

class LCA {
    int[] height, first;
    List<Integer> euler = new ArrayList<>();
    int[] segtree;
    boolean[] visited;
    int n;

    LCA(List<List<Integer>> adj, int root) {
        n = adj.size();
        height = new int[n];
        first = new int[n];
        euler = new ArrayList<>(2 * n);
        visited = new boolean[n];
        dfs(adj, root, 0);
        int m = euler.size();
        segtree = new int[m * 4];
        build(1, 0, m - 1);
    }

    void dfs(List<List<Integer>> adj, int node, int h) {
        visited[node] = true;
        height[node] = h;
        first[node] = euler.size();
        euler.add(node);
        for (int to : adj.get(node)) {
            if (!visited[to]) {
                dfs(adj, to, h + 1);
                euler.add(node);
            }
        }
    }

    void build(int node, int b, int e) {
        if (b == e) {
            segtree[node] = euler.get(b);
        } else {
            int mid = (b + e) / 2;
            build(node << 1, b, mid);
            build(node << 1 | 1, mid + 1, e);
            int l = segtree[node << 1], r = segtree[node << 1 | 1];
            segtree[node] = (height[l] < height[r]) ? l : r;
        }
    }

    int query(int node, int b, int e, int L, int R) {
        if (b > R || e < L)
            return -1;
        if (b >= L && e <= R)
            return segtree[node];
        int mid = (b + e) >> 1;

        int left = query(node << 1, b, mid, L, R);
        int right = query(node << 1 | 1, mid + 1, e, L, R);
        if (left == -1) return right;
        if (right == -1) return left;
        return height[left] < height[right] ? left : right;
    }

    int lca(int u, int v) {
        int left = first[u], right = first[v];
        if (left > right) {
            int temp = left;
            left = right;
            right = temp;
        }
        return query(1, 0, euler.size() - 1, left, right);
    }
};