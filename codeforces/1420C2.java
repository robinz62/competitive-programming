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
        int T = ri();
        for (int Ti = 0; Ti < T; Ti++) {
            int[] nq = ril(2);
            int n = nq[0];
            int q = nq[1];  // q = 0
            int[] a = ril(n);

            SegmentTree st = new SegmentTree(a);
            pw.println(st.query());

            // long[][] dp = new long[n][2];
            // dp[0][0] = a[0];
            // dp[0][1] = Integer.MIN_VALUE;
            // for (int i = 1; i < n; i++) {
            //     dp[i][0] = a[i];
            //     dp[i][1] = Integer.MIN_VALUE;
            //     if (dp[i-1][0] != Integer.MIN_VALUE) dp[i][0] = Math.max(dp[i][0], dp[i-1][0]);
            //     if (dp[i-1][1] != Integer.MIN_VALUE) dp[i][0] = Math.max(dp[i][0], dp[i-1][1] + a[i]);
            //     if (dp[i-1][1] != Integer.MIN_VALUE) dp[i][1] = Math.max(dp[i][1], dp[i-1][1]);
            //     if (dp[i-1][0] != Integer.MIN_VALUE) dp[i][1] = Math.max(dp[i][1], dp[i-1][0] - a[i]);
            // }

            // long ans = Math.max(dp[n-1][0], dp[n-1][1]);
            // pw.println(ans);

            for (int i = 0; i < q; i++) {
                int[] lr = ril(2);
                int l = lr[0]-1;
                int r = lr[1]-1;
                int val1 = a[l];
                int val2 = a[r];
                a[l] = val2;
                a[r] = val1;
                st.modify(l, val2);
                st.modify(r, val1);
                pw.println(st.query());
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
}

class SegmentTree {
    int n;
    long[] stpm;
    long[] stpp;
    long[] stmp;
    long[] stmm;

    static long X = Long.MIN_VALUE;

    SegmentTree(int[] arr) {
        n = arr.length;
        stpm = new long[n*4];
        stpp = new long[n*4];
        stmp = new long[n*4];
        stmm = new long[n*4];
        Arrays.fill(stpm, X);
        Arrays.fill(stpp, X);
        Arrays.fill(stmp, X);
        Arrays.fill(stmm, X);
        build(arr, 1, 0, n-1);
    }

    void build(int[] arr, int i, int l, int r) {
        if (l == r) {
            stpp[i] = arr[l];
            stmm[i] = -arr[l];
        } else {
            int m = l + (r - l) / 2;
            build(arr, 2*i, l, m);
            build(arr, 2*i+1, m+1, r);

            stpm[i] = Math.max(stpm[i*2]!=X&&stpm[i*2+1]!=X?stpm[i*2]+stpm[i*2+1]:X, stpp[i*2]!=X&&stmm[i*2+1]!=X?stpp[i*2]+stmm[i*2+1]:X);
            stpp[i] = Math.max(stpm[i*2]!=X&&stpp[i*2+1]!=X?stpm[i*2]+stpp[i*2+1]:X, stpp[i*2]!=X&&stmp[i*2+1]!=X?stpp[i*2]+stmp[i*2+1]:X);
            stmp[i] = Math.max(stmp[i*2]!=X&&stmp[i*2+1]!=X?stmp[i*2]+stmp[i*2+1]:X, stmm[i*2]!=X&&stpp[i*2+1]!=X?stmm[i*2]+stpp[i*2+1]:X);
            stmm[i] = Math.max(stmp[i*2]!=X&&stmm[i*2+1]!=X?stmp[i*2]+stmm[i*2+1]:X, stmm[i*2]!=X&&stpm[i*2+1]!=X?stmm[i*2]+stpm[i*2+1]:X);
            
            if (stpm[i*2] != X) stpm[i] = Math.max(stpm[i], stpm[i*2]);
            if (stpm[i*2+1] != X) stpm[i] = Math.max(stpm[i], stpm[i*2+1]);
            if (stpp[i*2] != X) stpp[i] = Math.max(stpp[i], stpp[i*2]);
            if (stpp[i*2+1] != X) stpp[i] = Math.max(stpp[i], stpp[i*2+1]);
            if (stmp[i*2] != X) stmp[i] = Math.max(stmp[i], stmp[i*2]);
            if (stmp[i*2+1] != X) stmp[i] = Math.max(stmp[i], stmp[i*2+1]);
            if (stmm[i*2] != X) stmm[i] = Math.max(stmm[i], stmm[i*2]);
            if (stmm[i*2+1] != X) stmm[i] = Math.max(stmm[i], stmm[i*2+1]);
        }
    }

    void modify(int i, int value) {
        modify(1, 0, n-1, i, value);
    }

    private void modify(int v, int l, int r, int i, int value) {
        if (l == r) {
            stpp[v] = value;
            stmm[v] = -value;
        } else {
            int m = l + (r - l) / 2;
            if (i <= m) modify(2*v, l, m, i, value);
            else modify(2*v+1, m+1, r, i, value);
            stpm[v] = stpp[v] = stmp[v] = stmm[v] = X;

            stpm[v] = Math.max(stpm[v*2]!=X&&stpm[v*2+1]!=X?stpm[v*2]+stpm[v*2+1]:X, stpp[v*2]!=X&&stmm[v*2+1]!=X?stpp[v*2]+stmm[v*2+1]:X);
            stpp[v] = Math.max(stpm[v*2]!=X&&stpp[v*2+1]!=X?stpm[v*2]+stpp[v*2+1]:X, stpp[v*2]!=X&&stmp[v*2+1]!=X?stpp[v*2]+stmp[v*2+1]:X);
            stmp[v] = Math.max(stmp[v*2]!=X&&stmp[v*2+1]!=X?stmp[v*2]+stmp[v*2+1]:X, stmm[v*2]!=X&&stpp[v*2+1]!=X?stmm[v*2]+stpp[v*2+1]:X);
            stmm[v] = Math.max(stmp[v*2]!=X&&stmm[v*2+1]!=X?stmp[v*2]+stmm[v*2+1]:X, stmm[v*2]!=X&&stpm[v*2+1]!=X?stmm[v*2]+stpm[v*2+1]:X);
            
            if (stpm[v*2] != X) stpm[v] = Math.max(stpm[v], stpm[v*2]);
            if (stpm[v*2+1] != X) stpm[v] = Math.max(stpm[v], stpm[v*2+1]);
            if (stpp[v*2] != X) stpp[v] = Math.max(stpp[v], stpp[v*2]);
            if (stpp[v*2+1] != X) stpp[v] = Math.max(stpp[v], stpp[v*2+1]);
            if (stmp[v*2] != X) stmp[v] = Math.max(stmp[v], stmp[v*2]);
            if (stmp[v*2+1] != X) stmp[v] = Math.max(stmp[v], stmp[v*2+1]);
            if (stmm[v*2] != X) stmm[v] = Math.max(stmm[v], stmm[v*2]);
            if (stmm[v*2+1] != X) stmm[v] = Math.max(stmm[v], stmm[v*2+1]);
        }
    }

    long query() {
        return stpp[1];
    }
}