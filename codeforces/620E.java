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
        int[] nm = ril(2);
        int n = nm[0];
        int m = nm[1];
        c = ril(n);  // 1 <= ci <= 60
        adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int i = 0; i < n-1; i++) {
            int[] xy = ril(2);
            adj.get(xy[0]-1).add(xy[1]-1);
            adj.get(xy[1]-1).add(xy[0]-1);
        }
        euler = new int[2 * n];
        s = new int[n];
        f = new int[n];
        dfs(0, -1);
        long[] starr = new long[2 * n];
        for (int i = 0; i < euler.length; i++) {
            starr[i] = 1l << c[euler[i]];
        }

        SegmentTree st = new SegmentTree(starr);

        for (int i = 0; i < m; i++) {
            String[] line = br.readLine().split(" ");
            if (Integer.parseInt(line[0]) == 1) {
                int v = Integer.parseInt(line[1]) - 1;
                int c = Integer.parseInt(line[2]);
                int l = s[v];
                int r = f[v];
                st.modify(l, r, 1l << c);
            } else {
                int v = Integer.parseInt(line[1]) - 1;
                pw.println(Long.bitCount(st.query(s[v], f[v])));
            }
        }
    }

    List<List<Integer>> adj;
    int[] c;
    int[] euler;
    int[] s;
    int[] f;
    int idx = 0;
    void dfs(int u, int p) {
        euler[idx] = u;
        s[u] = idx;
        idx++;
        for (int v : adj.get(u)) {
            if (v != p) dfs(v, u);
        }
        euler[idx] = u;
        f[u] = idx;
        idx++;
    }
}

class SegmentTree {
    int n;
    long[] st;
    boolean[] hasLazy;
    long[] lazy;

    // Modify identity and combine together.
    long identity = 0;
    long combine(long a, long b) {
        return a | b;
    }

    // Range assignment overrides the existing value.
    long lazyFunction(long currVal, long lazyVal) {
        return lazyVal;
    }

    SegmentTree(long[] arr) {
        n = arr.length;
        st = new long[4*n];
        hasLazy = new boolean[4*n];
        lazy = new long[4*n];
        build(arr, 1, 0, n-1);
    }

    private void build(long[] arr, int i, int l, int r) {
        if (l == r) st[i] = arr[l];
        else {
            int m = l + (r - l) / 2;
            build(arr, 2*i, l, m);
            build(arr, 2*i+1, m+1, r);
            st[i] = combine(st[2*i], st[2*i+1]);
        }
    }

    private void push(int v) {
        if (!hasLazy[v]) return;
        st[2*v] = lazyFunction(st[2*v], lazy[v]);
        lazy[2*v] = lazyFunction(lazy[2*v], lazy[v]);
        st[2*v+1] = lazyFunction(st[2*v+1], lazy[v]);
        lazy[2*v+1] = lazyFunction(lazy[2*v+1], lazy[v]);
        hasLazy[2*v] = hasLazy[2*v+1] = true;
        hasLazy[v] = false;
    }

    void modify(int l, int r, long value) {
        modify(1, 0, n-1, l, r, value);
    }

    private void modify(int v, int l, int r, int ql, int qr, long value) {
        if (ql > qr) return;
        if (l == ql && r == qr) {
            st[v] = lazyFunction(st[v], value);
            lazy[v] = lazyFunction(lazy[v], value);
            hasLazy[v] = true;
        } else {
            push(v);
            int m = l + (r - l) / 2;
            modify(2*v, l, m, ql, Math.min(qr, m), value);
            modify(2*v+1, m+1, r, Math.max(ql, m+1), qr, value);
            st[v] = combine(st[2*v], st[2*v+1]);
        }
    }

    // Note: input range is closed [l, r]
    long query(int l, int r) {
        return query(1, 0, n-1, l, r);
    }

    private long query(int v, int l, int r, int ql, int qr) {
        if (ql > qr) return identity;
        if (l == ql && r == qr) return st[v];
        push(v);
        int m = l + (r - l) / 2;
        long resl = query(2*v, l, m, ql, Math.min(qr, m));
        long resr = query(2*v+1, m+1, r, Math.max(ql, m+1), qr);
        return combine(resl, resr);
    }
}