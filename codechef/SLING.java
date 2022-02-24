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
    //
    // Interactive problems: don't forget to flush between test cases
    void solve() throws IOException {
        int T = ri();
        for (int Ti = 0; Ti < T; Ti++) {
            int[] nms = ril(3);
            int n = nms[0];
            int m = nms[1];
            int s = nms[2];

            int[][] a = new int[n][];
            for (int i = 0; i < n; i++) a[i] = ril(m);
            List<int[]> sorted = new ArrayList<>(n * m);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    sorted.add(new int[]{i, j});
                }
            }
            Collections.sort(sorted, (p1, p2) -> Integer.compare(a[p1[0]][p1[1]], a[p2[0]][p2[1]]));

            // TLE SOLUTION
            // I think this commented solution is correct but has large constants (4x) and also has
            // an extra log asymptotic factor, making it too slow.
            // Good learning tho.
            // 
            // long[][] rotated = rotateMatrix45Clockwise(new long[n][m]);
            // int rotatedn = rotated.length;
            // int rotatedm = rotated[0].length;

            // SegmentTree2D st = new SegmentTree2D(rotated);
            // long ans = 0;
            // for (int[] point : sorted) {
            //     int[] rotatedPt = rotatePoint45Clockwise(point, n, m);
            //     int r = rotatedPt[0];
            //     int c = rotatedPt[1];

            //     long max = 0;
            //     max = Math.max(max, st.query(0, r - s - 1, 0, rotatedm-1));
            //     max = Math.max(max, st.query(r + s + 1, rotatedn-1, 0, rotatedm-1));
            //     max = Math.max(max, st.query(r - s, r + s, 0, c - s - 1));
            //     max = Math.max(max, st.query(r - s, r + s, c + s + 1, rotatedm-1));
            //     st.modify(r, c, max + a[point[0]][point[1]]);
            //     ans = Math.max(ans, max + a[point[0]][point[1]]);
            // }

            int diag1Offset = -m+1;
            SegmentTree diag1 = new SegmentTree(new long[n + m - 1]);  // Down-right: i - j
            SegmentTree diag2 = new SegmentTree(new long[n + m - 1]);  // Up-right: i + j

            long ans = 0;
            for (int[] point : sorted) {
                int i = point[0];
                int j = point[1];
                int me = a[i][j];

                long here = 0;
                int id = i - s - j - 1 - diag1Offset;
                if (id >= 0) here = Math.max(here, diag1.query(0, id));
                id = i + s - j + 1 - diag1Offset;
                if (id <= n + m - 2) here = Math.max(here, diag1.query(id, n + m - 2));
                id = i - s + j - 1;
                if (id >= 0) here = Math.max(here, diag2.query(0, id));
                id = i + s + j + 1;
                if (id <= n + m - 2) here = Math.max(here, diag2.query(id, n + m - 2));

                ans = Math.max(ans, here + me);
                if (here + me > diag1.query(i - j - diag1Offset, i - j - diag1Offset)) diag1.modify(i - j - diag1Offset, here + me);
                if (here + me > diag2.query(i + j, i + j)) diag2.modify(i + j, here + me);
            }

            pw.println(ans);
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    long[][] rotateMatrix45Clockwise(long[][] mat) {
        int m = mat.length;
        int n = mat[0].length;
        long[][] rotated = new long[m + n - 1][m + n - 1];
        int offset = -m + 1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                rotated[i + j][-i + j - offset] = mat[i][j];
            }
        }
        return rotated;
    }

    int[] rotatePoint45Clockwise(int[] point, int m, int n) {
        return new int[]{point[0] + point[1], -point[0] + point[1] + m - 1};
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

class SegmentTree2D {
    int n;
    int m;
    long[][] st;

    // Modify identity and combine together.
    long identity = 0;
    long combine(long a, long b) {
        return Math.max(a, b);
    }

    SegmentTree2D(long[][] mat) {
        n = mat.length;
        m = mat[0].length;
        st = new long[4*n][4*m];
        buildRows(mat, 1, 0, n-1);
    }

    private void buildCols(long[][] mat, int i, int lx, int rx, int j, int ly, int ry) {
        if (ly == ry) {
            if (lx == rx) st[i][j] = mat[lx][ly];
            else st[i][j] = combine(st[2*i][j], st[2*i+1][j]);
        } else {
            int my = ly + (ry - ly) / 2;
            buildCols(mat, i, lx, rx, 2*j, ly, my);
            buildCols(mat, i, lx, rx, 2*j+1, my+1, ry);
            st[i][j] = combine(st[i][2*j], st[i][2*j+1]);
        }
    }

    private void buildRows(long[][] mat, int i, int lx, int rx) {
        if (lx != rx) {
            int mx = lx + (rx - lx) / 2;
            buildRows(mat, 2*i, lx, mx);
            buildRows(mat, 2*i+1, mx+1, rx);
        }
        buildCols(mat, i, lx, rx, 1, 0, m-1);
    }

    private void modifyCols(int r, int c, long val, int i, int lx, int rx, int j, int ly, int ry) {
        if (ly == ry) {
            if (lx == rx) st[i][j] = val;
            else st[i][j] = combine(st[2*i][j], st[2*i+1][j]);
        } else {
            int my = ly + (ry - ly) / 2;
            if (c <= my) modifyCols(r, c, val, i, lx, rx, 2*j, ly, my);
            else modifyCols(r, c, val, i, lx, rx, 2*j+1, my+1, ry);
            st[i][j] = combine(st[i][2*j], st[i][2*j+1]);
        }
    }

    private void modifyRows(int r, int c, long val, int i, int lx, int rx) {
        if (lx != rx) {
            int mx = lx + (rx - lx) / 2;
            if (r <= mx) modifyRows(r, c, val, 2*i, lx, mx);
            else modifyRows(r, c, val, 2*i+1, mx+1, rx);
        }
        modifyCols(r, c, val, i, lx, rx, 1, 0, m-1);
    }

    void modify(int r, int c, long val) {
        modifyRows(r, c, val, 1, 0, n-1);
    }

    private long queryCols(int qly, int qry, int i, int j, int ly, int ry) {
        if (qly > qry) return identity;
        if (ly == qly && ry == qry) return st[i][j];
        int my = ly + (ry - ly) / 2;
        long resl = queryCols(qly, Math.min(qry, my), i, 2*j, ly, my);
        long resr = queryCols(Math.max(qly, my+1), qry, i, 2*j+1, my+1, ry);
        return combine(resl, resr);
    }

    private long queryRows(int qlx, int qrx, int qly, int qry, int i, int lx, int rx) {
        if (qlx > qrx) return identity;
        if (lx == qlx && rx == qrx) return queryCols(qly, qry, i, 1, 0, m-1);
        int mx = lx + (rx - lx) / 2;
        long resl = queryRows(qlx, Math.min(qrx, mx), qly, qry, 2*i, lx, mx);
        long resr = queryRows(Math.max(qlx, mx+1), qrx, qly, qry, 2*i+1, mx+1, rx);
        return combine(resl, resr);
    }

    // Note: input range is closed
    long query(int lx, int rx, int ly, int ry) {
        lx = Math.max(lx, 0);
        rx = Math.min(rx, n-1);
        ly = Math.max(ly, 0);
        ry = Math.min(ry, m-1);
        return queryRows(lx, rx, ly, ry, 1, 0, n-1);
    }
}

class SegmentTree {
    int n;
    long[] st;

    // Modify identity and combine together.
    long identity = 0;
    long combine(long a, long b) {
        return Math.max(a, b);
    }

    SegmentTree(long[] arr) {
        n = arr.length;
        st = new long[n*2];
        for (int i = 0; i < n; i++) {
            st[n + i] = arr[i];
        }
        build();
    }

    private void build() {
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

    // Closed.
    long query(int l, int r) {
        r++;
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