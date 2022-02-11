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
        int MAX_N = 200000;
        fact = new long[MAX_N+1];
        invfact = new long[MAX_N+1];
        fact[0] = 1;
        for (int i = 1; i <= MAX_N; i++) fact[i] = fact[i-1] * i % MOD;
        for (int i = 0; i <= MAX_N; i++) invfact[i] = modInverseFermat(fact[i], MOD);

        int T = ri();
        for (int Ti = 0; Ti < T; Ti++) {
            int n = ri();
            int[][] intervals = new int[n][];
            for (int i = 0; i < n; i++) intervals[i] = ril(2);

// int n = 10;
// int[][] intervals = generateTest(n);
// for (int[] i : intervals) pw.println(Arrays.toString(i));
// pw.flush();

            Arrays.sort(intervals, (i1, i2) -> {
                int c = Integer.compare(i1[0], i2[0]);
                return c != 0 ? c : -Integer.compare(i1[1], i2[1]);
            });

            // The placement of N is fixed. intervals will somewhere contain
            // [1..x-1], [x+1..n], and N is at location x.

            pw.println(solve(1, n, intervals, 0, n-1));
            // pw.println(solveSlow(1, n, intervals, 0, n-1));
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    int[][] generateTest(int n) {
        int[] p = new int[n];
        for (int i = 1; i <= n; i++) p[i-1] = i;
        shuffle(p);
        int[][] intervals = new int[n][2];
        for (int i = 0; i < n; i++) {
            int l = i;
            int r = i;
            while (l >= 0 && p[i] >= p[l]) l--;
            while (r < n && p[i] >= p[r]) r++;
            intervals[i][0] = l+1+1;
            intervals[i][1] = r-1+1;
        }
        return intervals;
    }

    long solve(int l, int r, int[][] intervals, int lll, int rrr) {
        if (l > r || lll > rrr) return 0;
        if (l == r) return lll == rrr && intervals[lll][0] == l && intervals[lll][1] == r ? 1 : 0;
        if (lll == rrr) return l == r && intervals[lll][1] == l && intervals[lll][1] == l ? 1 : 0;
        if (intervals[lll][0] != l || intervals[lll][1] != r) return 0;

        // intervals[lll+1] should give the coords for the next split
        if (intervals[lll+1][0] == intervals[lll][0]) {
            int leftstart = intervals[lll+1][0];
            int leftend = intervals[lll+1][1];
            if (leftend + 1 > r) return 0;  // should be impossible

            // Need to find index of last interval that's on the left side. We can
            // do this by finding the first interval on the right side, then minus 1.
            // Binary search for index where the starting point is equal to leftend + 2.

            int mmm = binarySearchLeft(intervals, lll+1, rrr, leftend + 2);
            if (mmm < 0) {
                // the only time this is ok is if right size is 0. We'll assume that it is
                // and if it ends up being wrong, a subproblem somewhere will return ans = 0 i think
                int leftsize = rrr-(lll+1)+1;
                int rightsize = 0;
                int n = leftsize + rightsize;
                int k = leftsize;
                long ans = fact[n] * invfact[k] % MOD * invfact[n - k] % MOD;
                ans = ans * solve(leftstart, r-1, intervals, lll+1, lll+1+leftsize-1) % MOD;
                return ans;
            } else {
                int leftsize = (mmm-1)-(lll+1)+1;
                int rightsize = rrr-mmm+1;
                int n = leftsize + rightsize;
                int k = leftsize;
                long ans = fact[n] * invfact[k] % MOD * invfact[n - k] % MOD;
                ans = ans * solve(leftstart, leftend, intervals, lll+1, lll+1+leftsize-1) % MOD;
                ans = ans * solve(leftend + 2, r, intervals, mmm, rrr) % MOD;
                return ans;
            }
        } else {
            // there is no left range
            long ans = solve(intervals[lll+1][0], r, intervals, lll+1, rrr);
            return ans;
        }
    }

    public static int binarySearchLeft(int[][] A, int l, int r, int k) {
        int lower = -1;
        while (l <= r) {
            int m = l + (r - l) / 2;
            if (A[m][0] == k && (m-1 < 0 || A[m-1][0] < k)) {
                lower = m;
                break;
            }
            if (A[m][0] < k) {
                l = m + 1;
            } else if (A[m][0] >= k) {
                r = m - 1;
            }
        }
        return lower >= 0 ? lower : -l - 1;
    }

    long[] fact;
    long[] invfact;
    long solveSlow(int l, int r, int[][] intervals, int lll, int rrr) {
        if (l == r) return 1;
        if (intervals[lll][0] != l || intervals[lll][1] != r) return 0;

        int leftMax = l-1;
        int mid = 0;
        boolean found = false;
        int lsize = 0;
        int rsize = 0;
        for (int iii = lll+1; iii <= rrr; iii++) {
            int[] interval = intervals[iii];
            if (found) {
                rsize++;
                continue;
            }

            if (interval[0] == leftMax + 2) {
                found = true;
                mid = leftMax + 1;
                rsize++;
            } else {
                lsize++;
                leftMax = Math.max(leftMax, interval[1]);
            }
        }
        if (!found) {
            if (leftMax != r-1) return 0;
            mid = r;
        }

        int n = lsize + rsize;
        int k = Math.min(lsize, rsize);
        long ans = fact[n] * invfact[k] % MOD * invfact[n - k] % MOD;

        if (mid-1 >= l) ans = ans * solve(l, mid-1, intervals, lll+1, lll+1+lsize-1) % MOD;
        if (mid+1 <= r) ans = ans * solve(mid+1, r, intervals, lll+1+lsize, rrr) % MOD;
        return ans;
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

    void shuffle(int[] A) {
        Random r = new Random();
        for (int i = A.length-1; i > 0; i--) {
            int j = r.nextInt(i+1);
            int temp = A[i];
            A[i] = A[j];
            A[j] = temp;
        }
    }

    void printDouble(double d) {
        pw.printf("%.16f", d);
    }
}