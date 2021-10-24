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
    void solve() throws IOException {
        long[] nk = rll(2);
        int n = (int) nk[0];
        long k = nk[1];

        long[][] projects = new long[n][];
        for (int i = 0; i < n; i++) projects[i] = rll(3);

        // sort by L.
        List<Integer> sorted = new ArrayList<>();
        for (int i = 0; i < n; i++) sorted.add(i);
        Collections.sort(sorted, (i1, i2) -> Long.compare(projects[i1][0], projects[i2][0]));

        List<Integer> sortedR = new ArrayList<>();
        for (int i = 0; i < n; i++) sortedR.add(i);
        Collections.sort(sortedR, (i1, i2) -> Long.compare(projects[i1][1], projects[i2][1]));

        long ans = 0;
        long[] ansLR = new long[2];

        // profits[r] is total profit up to r minus the distance needed.
        long[] profits = new long[200001];
        long sumProfit = 0;
        for (int i : sortedR) {
            long[] p = projects[i];
            sumProfit += p[2];
            profits[(int) p[1]] = sumProfit;
        }

        for (int i = 1; i <= 200000; i++) profits[i] -= k * i;
        SegmentTree st = new SegmentTree(profits);

        // Start off with L = 1
        long[] ret = st.query(1, 200000);
        if (ret[0] > 0) {
            ans = ret[0];
            ansLR[0] = 1;
            ansLR[1] = ret[1];
        }

        int killIdx = 0;
        for (int i : sorted) {
            int L = (int) projects[i][0];
            long offset = k * (L-1);
            while (killIdx < sorted.size() && projects[sorted.get(killIdx)][0] < L) {
                long[] tokill = projects[sorted.get(killIdx++)];
                st.modify((int) tokill[1], 200000, -tokill[2]);
            }
            ret = st.query(L, 200000);

            if (ret[0] + offset > ans) {
                ans = ret[0] + offset;
                ansLR[0] = L;
                ansLR[1] = ret[1];
            }
        }

        if (ans == 0) pw.println("0");
        else {
            int L = (int) ansLR[0];
            int R = (int) ansLR[1];
            List<Integer> choose = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                long[] p = projects[i];
                if (p[0] >= L && p[1] <= R) {
                    choose.add(i+1);
                }
            }
            pw.println(ans + " " + L + " " + R + " " + choose.size());
            for (int idx : choose) pw.print(idx + " ");
            pw.println();
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

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

class SegmentTree {
    int n;
    long[] st;
    int[] idx;
    boolean[] hasLazy;
    long[] lazy;

    long[] ret = new long[2];

    // Adjust identity, combine, and lazyFunction together to suit needs.
    // Default code below is range minimum query with range addition.
    long identity = Long.MIN_VALUE;
    void combine(long a, long b, int ia, int ib) {
        if (a >= b) {
            ret[0] = a;
            ret[1] = ia;
        } else {
            ret[0] = b;
            ret[1] = ib;
        }
    }
    long lazyFunction(long currVal, long lazyVal) {
        return currVal + lazyVal;
    }

    SegmentTree(long[] arr) {
        n = arr.length;
        st = new long[4*n];
        idx = new int[4*n];
        hasLazy = new boolean[4*n];
        lazy = new long[4*n];
        build(arr, 1, 0, n-1);
    }

    private void build(long[] arr, int i, int l, int r) {
        if (l == r) {
            st[i] = arr[l];
            idx[i] = l;
        } else {
            int m = l + (r - l) / 2;
            build(arr, 2*i, l, m);
            build(arr, 2*i+1, m+1, r);
            combine(st[2*i], st[2*i+1], idx[2*i], idx[2*i+1]);
            st[i] = ret[0];
            idx[i] = (int) ret[1];
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
        lazy[v] = 0;
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
            combine(st[2*v], st[2*v+1], idx[2*v], idx[2*v+1]);
            st[v] = ret[0];
            idx[v] = (int) ret[1];
        }
    }

    // Note: input range is closed [l, r]
    long[] query(int l, int r) {
        return query(1, 0, n-1, l, r);
    }

    private long[] query(int v, int l, int r, int ql, int qr) {
        if (ql > qr) {
            ret[0] = identity;
            ret[1] = -1;
            return ret;
        }
        if (l == ql && r == qr) {
            ret[0] = st[v];
            ret[1] = idx[v];
            return ret;
        }
        push(v);
        int m = l + (r - l) / 2;
        query(2*v, l, m, ql, Math.min(qr, m));
        long resl = ret[0];
        int idxl = (int) ret[1];
        query(2*v+1, m+1, r, Math.max(ql, m+1), qr);
        long resr = ret[0];
        int idxr = (int) ret[1];
        combine(resl, resr, idxl, idxr);
        return ret;
    }
}