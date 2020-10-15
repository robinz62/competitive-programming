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
        int[] nkd = ril(3);
        int n = nkd[0];
        int k = nkd[1];
        int d = nkd[2];
        int[] a = ril(n);
        sort(a);
        SegmentTree dp = new SegmentTree(n+1);
        dp.modify(0, true);
        for (int i = 1; i <= n; i++) {
            int idx = i-1;
            int r = idx - k + 1;
            if (r < 0) continue;
            int l = binarySearchLeft(a, 0, idx-1, a[idx]-d);
            if (l < 0) l = -l-1;
            if (l > r) continue;
            if (dp.query(l, r+1)) dp.modify(i, true);
        }
        pw.println(dp.query(n, n+1) ? "YES" : "NO");
    }

    public static int binarySearchLeft(int[] A, int l, int r, int k) {
        int lower = -1;
        while (l <= r) {
            int m = l + (r - l) / 2;
            if (A[m] == k && (m-1 < 0 || A[m-1] < k)) {
                lower = m;
                break;
            }
            if (A[m] < k) {
                l = m + 1;
            } else if (A[m] >= k) {
                r = m - 1;
            }
        }
        return lower >= 0 ? lower : -l - 1;
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

class SegmentTree {
    int n;
    boolean[] st;

    // Modify identity and combine together.
    boolean identity = false;
    boolean combine(boolean a, boolean b) {
        return a || b;
    }

    SegmentTree(int n) {
        this.n = n;
        st = new boolean[n*2];
    }

    void modify(int i, boolean value) {
        st[n + i] = value;
        for (i = (n + i) / 2; i > 0; i /= 2) {
            st[i] = combine(st[i*2], st[i*2+1]);
        }
    }

    // Note: input range is half-open [l, r)
    boolean query(int l, int r) {
        l += n;
        r += n;
        boolean resl = identity;
        boolean resr = identity;
        while (l < r) {
            if ((l & 1) > 0) resl = combine(resl, st[l++]);
            if ((r & 1) > 0) resr = combine(st[--r], resr);
            l /= 2;
            r /= 2;
        }
        return combine(resl, resr);
    }
}