import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static int MOD = 998244353;

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
            int n = ri();
            int[] a = ril(n);

            // 0 means all good, no skips
            // 1 means skipped, in the up state
            // 2 means skipped, in the down state
            long[][] counts = new long[n+10][3];
            if (a[0] == 0) {
                counts[0][0]++;
            } else if (a[0] == 1) {
                counts[1][1]++;
            }

            for (int i = 1; i < n; i++) {
                // Maintaining good state
                counts[a[i]][0] *= 2;  // either don't take or take
                if (counts[a[i]][0] >= MOD) counts[a[i]][0] -= MOD;

                if (a[i]-1 >= 0) {
                    counts[a[i]][0] += counts[a[i]-1][0];
                    if (counts[a[i]][0] >= MOD) counts[a[i]][0] -= MOD;
                }

                // Skip to the up state
                counts[a[i]][1] *= 2;
                if (counts[a[i]][1] >= MOD) counts[a[i]][1] -= MOD;

                if (a[i]-2 >= 0) counts[a[i]][1] += counts[a[i]-2][0];
                if (counts[a[i]][1] >= MOD) counts[a[i]][1] -= MOD;

                if (a[i]-2 >= 0) counts[a[i]][1] += counts[a[i]-2][2];
                if (counts[a[i]][1] >= MOD) counts[a[i]][1] -= MOD;

                // Skip to the down state
                counts[a[i]][2] *= 2;
                if (counts[a[i]][2] >= MOD) counts[a[i]][2] -= MOD;

                counts[a[i]][2] += counts[a[i]+2][1];
                if (counts[a[i]][2] >= MOD) counts[a[i]][2] -= MOD;

                // Totally new
                if (a[i] == 0) {
                    counts[0][0]++;
                    if (counts[0][0] >= MOD) counts[0][0] -= MOD;
                } else if (a[i] == 1) {
                    counts[1][1]++;
                    if (counts[1][1] >= MOD) counts[1][1] -= MOD;
                }
            }

            long ans = 0;
            for (int i = 0; i < counts.length; i++) {
                ans += (long) counts[i][0] + counts[i][1] + counts[i][2];
            }
            pw.println(ans % MOD);
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