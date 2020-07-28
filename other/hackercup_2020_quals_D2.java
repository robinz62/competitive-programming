import java.io.*;
import java.math.BigInteger;
import java.util.*;

// This solution must be run with increased stack size i.e.
// java -Xss512m Main < in > out
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

    int M;
    long[] C;

    void solve() throws IOException {
        int T = ri();
        for (int Ti = 0; Ti < T; Ti++) {
            int[] NMAB = ril(4);
            int N = NMAB[0];
            M = NMAB[1];
            int A = NMAB[2] - 1;
            int B = NMAB[3] - 1;
            int[] P = new int[N];
            C = new long[N];
            for (int i = 0; i < N; i++) {
                int[] PC = ril(2);
                P[i] = PC[0] - 1;
                C[i] = PC[1] == 0 ? Long.MAX_VALUE : PC[1];
            }
            C[A] = C[B] = 0;
            List<List<Integer>> adj = new ArrayList<>(N);
            for (int i = 0; i < N; i++) adj.add(new ArrayList<>());
            for (int i = 1; i < N; i++) {
                adj.get(i).add(P[i]);
                adj.get(P[i]).add(i);
            }

            // initial dfs to get path from A to B
            boolean[] visited = new boolean[N];
            int[] parentA = new int[N];  // DFS starting at A
            parentA[A] = A;
            visited[A] = true;
            dfs(A, adj, visited, parentA);

            // path from A to B (does not include A)
            List<Integer> path = new ArrayList<>();
            int curr = B;
            while (curr != A) {
                path.add(curr);
                curr = parentA[curr];
            }
            Collections.reverse(path);

            // maintains weird values. st[i] is (min cost to get to i) + Ci
            // st[i] is weird value of node at distance i
            // initialize st[0] = 0. fill rest as we go
            // N is an upper bound on the size (max dist0)
            SegmentTree st = new SegmentTree(new long[N]);
            Arrays.fill(visited, false);
            visited[A] = true;
            for (int pn : path) visited[pn] = true;  // make sure dfs2 doesn't use visit nodes
            for (int i = 0; i < path.size(); i++) {
                int pn = path.get(i);
                int dist = i+1;
                // phase 1
                // first compute for non-pathnode paths in a dfs (backtracking) fashion
                // (1 path at a time).
                // i assume it is optimal to not allow retracing paths within the dfs2 (not 100% sure)
                // i also assume it is never optimal to have passed through pathnode X and went back
                // to pathnode X-1 for refueling purposes.

                st.modify(dist, Long.MAX_VALUE);
                capture = new ArrayList<>();
                if (pn != B) {
                    for (int u : adj.get(pn)) {
                        if (visited[u]) continue;
                        visited[u] = true;
                        dfs2(u, adj, visited, st, dist, 1);
                    }
                }

                // compute for pathnode. we can do this before the flipping,
                // because it is never optimal to pass through the path node
                // to offshoots, then come back and refuel at the path node.
                long min = st.query(Math.max(0, dist - M), dist);
                if (min != Long.MAX_VALUE) min += C[pn];
                if (C[pn] == Long.MAX_VALUE) min = Long.MAX_VALUE;
                st.modify(dist, min);
                
                // flipping
                for (long[] offNode : capture) {
                    int deltaD = (int) offNode[1];
                    long weirdVal = offNode[2];
                    int trueDist = dist - deltaD;
                    if (trueDist < 0) continue;
                    if (weirdVal < st.query(trueDist, trueDist+1)) {
                        st.modify(trueDist, weirdVal);
                    }
                }
            }

            int lastDist = path.size();
            long ans = st.query(lastDist, lastDist+1);
            ans = ans == Long.MAX_VALUE ? -1 : ans - C[B];
            pw.println("Case #" + (Ti+1) + ": " + ans);
        }
    }

    void dfs(int u, List<List<Integer>> adj, boolean[] visited, int[] p) {
        for (int v : adj.get(u)) {
            if (!visited[v]) {
                visited[v] = true;
                p[v] = u;
                dfs(v, adj, visited, p);
            }
        }
    }

    // might not actually need node id, but good for debugging
    List<long[]> capture;  // pair is [node id, dist from pn, weird value]

    // dist is distance from the pathnode
    void dfs2(int u, List<List<Integer>> adj, boolean[] visited, SegmentTree st, int pndist, int dist) {
        long min = st.query(Math.max(0, pndist+dist-M), pndist+dist);
        if (min != Long.MAX_VALUE) min += C[u];
        if (C[u] == Long.MAX_VALUE) min = Long.MAX_VALUE;
        capture.add(new long[]{u, dist, min});
        st.modify(pndist+dist, min);  // setting as Long.MAX_VALUE should also work.
                                      // the main point is that it is never optimal to refuel, then go even deeper
        for (int v : adj.get(u)) {
            if (visited[v]) continue;
            visited[v] = true;
            dfs2(v, adj, visited, st, pndist, dist+1);
        }
    }
}

class SegmentTree {
    int n;
    long[] st;

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = n; i < 2 * n; i++) sb.append(st[i] + " ");
        sb.append("]");
        return sb.toString();
    }

    // Modify identity and combine together.
    long identity = Long.MAX_VALUE;
    long combine(long a, long b) {
        return Math.min(a, b);
    }

    SegmentTree(long[] arr) {
        n = arr.length;
        st = new long[n*2];
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

    void modify(int i, long value) {
        st[n + i] = value;
        for (i = (n + i) / 2; i > 0; i /= 2) {
            st[i] = combine(st[i*2], st[i*2+1]);
        }
    }

    // Note: input range is half-open [l, r)
    long query(int l, int r) {
        l += n;
        r += n;
        long resl = identity;
        long resr = identity;
        while (l < r) {
            if ((l & 1) > 0) resl = combine(resl, st[l++]);
            if ((r & 1) > 0) resr = combine(st[--r], resr);
            l /= 2;
            r /= 2;
        }
        return combine(resl, resr);
    }
}