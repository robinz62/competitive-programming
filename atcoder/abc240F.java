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
            int[] nm = ril(2);
            int n = nm[0];
            int m = nm[1];
            int[][] xys = new int[n][];
            for (int i = 0; i < n; i++) xys[i] = ril(2);

            // y[i] is the count of x[i].
            // Values have absolute value <= 4, 9 possible values in C.
            // Total length of |C| is M, 10^9.

            // B is the prefix sum of C
            // A is the prefix sum of B

            // C is a step graph, composed of intervals.
            // B can be described as a series of the same intervals but each interval
            //   is a linear slope

            int[] blen = new int[n];
            long[] bstart = new long[n];
            long[] bend = new long[n];
            bstart[0] = xys[0][0];
            bend[0] = (long) xys[0][0] * xys[0][1];
            blen[0] = xys[0][1]-1;
            for (int i = 1; i < n; i++) {
                blen[i] = xys[i][1];
                bstart[i] = bend[i-1];
                bend[i] = bstart[i] + (long) xys[i][1] * xys[i][0];
            }

            long ans = bstart[0];
            long curr = bstart[0];
            for (int i = 0; i < n; i++) {
                // pw.println(curr);
                if (bstart[i] > 0 && bend[i] <= 0) {
                    // Find right before it dips to below 0.
                    long d = -xys[i][0];
                    long delta = bstart[i] / d;
                    // pw.println("here " + bstart[i] + " " + bend[i] + " " + blen[i]);
                    // pw.println(delta);
                    ans = Math.max(ans, curr + sum(bstart[i] + xys[i][0], bstart[i] + delta * -d, delta));
                }
                // pw.println("adding [" + (bstart[i]+xys[i][0]) + ", " + bend[i] + "] " + (blen[i]) + " is " + sum(bstart[i]+xys[i][0], bend[i], blen[i]));
                curr += sum(bstart[i] + xys[i][0], bend[i], blen[i]);
                ans = Math.max(ans, curr);
            }

            pw.println(ans);
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    long sum(long a, long b, long count) {
        return (a + b) * count / 2;
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