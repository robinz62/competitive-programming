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
        int[] nkq = ril(3);
        int n = nkq[0];
        int k = nkq[1];
        int q = nkq[2];

        long[] a = rll(n);

        long[][] dp = new long[n][k+1];
        for (int i = 0; i < n; i++) dp[i][0] = 1;
        for (int ki = 1; ki <= k; ki++) {
            dp[0][ki] = dp[1][ki-1];
            dp[n-1][ki] = dp[n-2][ki-1];
            for (int i = 1; i < n-1; i++) {
                dp[i][ki] = dp[i-1][ki-1] + dp[i+1][ki-1];
                if (dp[i][ki] >= MOD) dp[i][ki] -= MOD;
            }
        }
        long[] count = new long[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= k; j++) {
                count[i] = (count[i] + dp[i][j] * dp[i][k - j] % MOD) % MOD;
            }
        }

        long curr = 0;
        for (int i = 0; i < n; i++) curr = (curr + count[i] * a[i] % MOD) % MOD;

        for (int qi = 0; qi < q; qi++) {
            int[] ix = ril(2);
            int i = ix[0]-1;
            long x = ix[1];

            long add = x * count[i] % MOD;
            long sub = a[i] * count[i] % MOD;
            curr = (curr + add + MOD - sub) % MOD;
            a[i] = x;

            pw.println(curr);
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