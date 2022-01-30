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
            n = ri();
            List<List<Integer>> adj = new ArrayList<>(n);
            for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
            for (int i = 0; i < n-1; i++) {
                int[] uv = ril(2);
                adj.get(uv[0]-1).add(uv[1]-1);
                adj.get(uv[1]-1).add(uv[0]-1);
            }

            // Rooted at 0
            // We can add a single edge with weight 1 <= x <= n (for all)
            // Without modification, worst d[v] is just height of tree.
            
            // Let's try to solve x = 1 first. i.e. we can add a single edge
            // If we add edge (a, b) with weight W
            // Then d[v] becomes min(d[v], d[a] + W + dist(b, v), d[b] + W + dist(a, v))
            //
            // Dist values can be computed using LCA

            depth = new int[n];
            dfs(0, -1, adj);
            int maxdepth = 0;
            for (int d : depth) maxdepth = Math.max(maxdepth, d);

            // can we binary search answer?
            // if we add edge of weight 1, can we achieve minimum distance of M?
            // we only need to consider all the nodes with depth > M, can they be
            // enhanced by this edge by enough?
            //
            // Question: should the edge we add always have an endpoint at the root?
            // I think so.
            //
            // So adding edge, for all values below M, if we add edge (0, a) with weight x then
            // the d[v] becomes (x + dist(a, v))
            //
            // Is there a quick way to compute, for some set of nodes s in S with depth > M,
            // the worst value of dist(a, s)?

            dist = new int[n][n];
            for (int root = 0; root < n; root++) {
                dfs2(root, root, -1, adj);
            }

            // for each dist[root][v], sort the vertices by depth
            List<int[]> st = new ArrayList<>(n);
            List<Integer> perm = new ArrayList<>(n);
            for (int i = 0; i < n; i++) perm.add(i);
            depthToLeftIdx = new ArrayList<>();
            Collections.sort(perm, (u, v) -> Integer.compare(depth[u], depth[v]));
            for (int i = 0; i < n; i++) {
                int depthHere = depth[perm.get(i)];
                if (depthToLeftIdx.size() <= depthHere) depthToLeftIdx.add(i);
            }
            for (int root = 0; root < n; root++) {
                int[] rehashed = new int[n];
                for (int j = 0; j < n; j++) {
                    int node = perm.get(j);
                    rehashed[j] = dist[root][node];
                }
                for (int i = n-2; i >= 0; i--) rehashed[i] = Math.max(rehashed[i], rehashed[i+1]);
                st.add(rehashed);
            }

            int ans = 0;
            for (int x = 1; x <= n; x++) {
                boolean done = false;
                while (!done) {
                    for (int a = 1; a < n; a++) {
                        if (ok(a, x, ans, st.get(a))) {
                            done = true;
                            pw.print(ans + " ");
                            break;
                        }
                    }
                    if (!done) ans++;
                }
            }
            pw.println();
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    List<Integer> depthToLeftIdx;
    int n;

    boolean ok(int a, int x, int m, int[] st) {
        // Only consider nodes with depth > m
        if (m+1 >= depthToLeftIdx.size()) return true;
        int idx = depthToLeftIdx.get(m+1);
        int weight = x + st[idx];
        return weight <= m;
    }

    int[] depth;
    void dfs(int u, int p, List<List<Integer>> adj) {
        for (int v : adj.get(u)) {
            if (v == p) continue;
            depth[v] = depth[u] + 1;
            dfs(v, u, adj);
        }
    }

    int[][] dist;
    void dfs2(int root, int u, int p, List<List<Integer>> adj) {
        for (int v : adj.get(u)) {
            if (v == p) continue;
            dist[root][v] = dist[root][u] + 1;
            dfs2(root, v, u, adj);
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

class SegmentTree {
    int n;
    int[] st;

    // Modify identity and combine together.
    int identity = 0;
    int combine(int a, int b) {
        return Math.max(a, b);
    }

    SegmentTree(int[] arr) {
        n = arr.length;
        st = new int[n*2];
        for (int i = 0; i < n; i++) {
            st[n + i] = arr[i];
        }
        build();
    }

    void build() {
        for (int i = n - 1; i > 0; i--) {
            st[i] = combine(st[i*2], st[i*2+1]);
        }
    }

    void modify(int i, int value) {
        st[n + i] = value;
        for (i = (n + i) / 2; i > 0; i /= 2) {
            st[i] = combine(st[i*2], st[i*2+1]);
        }
    }

    // Note: input range is half-open [l, r)
    int query(int l, int r) {
        l += n;
        r += n;
        int resl = identity;
        int resr = identity;
        while (l < r) {
            if ((l & 1) > 0) resl = combine(resl, st[l++]);
            if ((r & 1) > 0) resr = combine(st[--r], resr);
            l /= 2;
            r /= 2;
        }
        return combine(resl, resr);
    }
}