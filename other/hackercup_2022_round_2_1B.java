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
        for (int Ti = 1; Ti <= T; Ti++) {
            int n = ri();
            int[] a = ril(n);  // 10^6 distinct values
            int q = ri();

            int[] b = new int[n];
            for (int i = 0; i < n; i++) b[i] = a[i] * 3 + 5;

            int[] c = new int[n];
            for (int i = 0; i < n; i++) c[i] = a[i] + 7;

            int MOD1 = 1000000007;
            int MOD2 = 1000000009;
            int MOD3 = 999999937;

            SegmentTree st1 = new SegmentTree(a, MOD1);
            SegmentTree st2 = new SegmentTree(b, MOD2);
            SegmentTree st3 = new SegmentTree(c, MOD3);

            SegmentTreeAdd stadd = new SegmentTreeAdd(a);

            int ans = 0;
            for (int qi = 0; qi < q; qi++) {
                int[] query = ril(3);
                if (query[0] == 1) {
                    int x = query[1] - 1;
                    int y = query[2];

                    st1.modify(x, y);
                    st2.modify(x, y * 3 + 5);
                    st3.modify(x, y + 7);
                    stadd.modify(x, y);
                } else if (query[0] == 2) {
                    int l = query[1] - 1;
                    int r = query[2] - 1;

                    if ((r - l + 1) % 2 == 0) continue;
                    if (l == r) {
                        ans++;
                        continue;
                    }

                    int m = (l + r) / 2;

                    // Try deleting middle
                    if (st1.query(l, m) == st1.query(m+1, r+1) && st2.query(l, m) == st2.query(m+1, r+1) && stadd.query(l, m) == stadd.query(m+1, r+1)
                        && st3.query(l, m) == st3.query(m+1, r+1)) {
                        ans++;
                        continue;
                    }

                    // Try deleting on left
                    // Must be true that exactly one letter where left has one more than right
                    // That number must be the difference in sum
                    long left1 = st1.query(l, m+1);
                    long left2 = st2.query(l, m+1);
                    long left3 = st3.query(l, m+1);
                    long right1 = st1.query(m+1, r+1);
                    long right2 = st2.query(m+1, r+1);
                    long right3 = st3.query(m+1, r+1);
                    long leftsum = stadd.query(l, m+1);
                    long rightsum = stadd.query(m+1, r+1);

                    long diff1 = leftsum - rightsum;
                    long diff2 = diff1 * 3 + 5;
                    long diff3 = diff1 + 7;
                    if (diff1 > 0 && diff1 <= 1000100) {
                        left1 = left1 * modInverseFermat(diff1, MOD1) % MOD1;
                        left2 = left2 * modInverseFermat(diff2, MOD2) % MOD2;
                        left3 = left3 * modInverseFermat(diff3, MOD3) % MOD3;
                        if (left1 == right1 && left2 == right2 && left3 == right3) {
                            ans++;
                            continue;
                        }
                    }

                    // Ditto
                    left1 = st1.query(l, m);
                    left2 = st2.query(l, m);
                    left3 = st3.query(l, m);
                    right1 = st1.query(m, r+1);
                    right2 = st2.query(m, r+1);
                    right3 = st3.query(m, r+1);
                    leftsum = stadd.query(l, m);
                    rightsum = stadd.query(m, r+1);

                    diff1 = rightsum - leftsum;
                    diff2 = diff1 * 3 + 5;
                    diff3 = diff1 + 7;
                    if (diff1 > 0 && diff1 <= 1000100) {
                        right1 = right1 * modInverseFermat(diff1, MOD1) % MOD1;
                        right2 = right2 * modInverseFermat(diff2, MOD2) % MOD2;
                        right3 = right3 * modInverseFermat(diff3, MOD3) % MOD3;
                        if (left1 == right1 && left2 == right2 && left3 == right3) {
                            ans++;
                            continue;
                        }
                    }


                } else {
                    pw.println("IMPOSSIBLE");
                }
            }

            printAnswer(Ti, "" + ans);
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    void printAnswer(int i, String ans) {
        pw.println("Case #" + i + ": " + ans);
    }

    public static long modPow(long base, long exponent, long m) {
        long ans = 1;
        base = base % m;
        while (exponent > 0) {
            if ((exponent & 1) == 1) ans = (ans * base) % m;
            exponent >>= 1;
            base = (base * base) % m;
        } 
        return ans;
    }

    // Computes a^(-1) mod m, the modular inverse of a (modulo m). This
    // algorithm is based on Fermat's little theorem. m must be prime. O(log m).
    public static long modInverseFermat(long a, long m) {
        return modPow(a, m - 2, m);
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
    int MOD;

    int n;
    long[] st;

    // Modify identity and combine together.
    long identity = 1l;
    long combine(long a, long b) {
        return a * b % MOD;
    }

    SegmentTree(int[] arr, int mod) {
        MOD = mod;
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

class SegmentTreeAdd {
    int n;
    long[] st;

    // Modify identity and combine together.
    long identity = 0;
    long combine(long a, long b) {
        return a + b;
    }

    SegmentTreeAdd(int[] arr) {
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