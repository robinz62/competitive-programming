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
        int T = ri();
        long[] pow10 = new long[18];
        long[] pow11 = new long[18];
        pow10[0] = 1l;
        pow11[0] = 1l;
        for (int i = 1; i <= 17; i++) pow11[i] = pow11[i-1] * 11l;
        for (int i = 1; i <= 17; i++) pow10[i] = pow10[i-1] * 10l;
        for (int Ti = 0; Ti < T; Ti++) {
            int[] sn = ril(2);
            long s = sn[0];
            int n = sn[1];  // at most 100 numbers
            if (n == 1) {
                pw.println(s);
                continue;
            }
            long[] ans = new long[n];
            int ins = 0;
            while (s > 0) {
                for (int p = 17; p >= 0; p--) {
                    if (p > s) continue;
                    if (s - pow10[p] < 0) continue;
                    long val = pow10[p];

                    // how many can we take
                    int take = 1;
                    while (s - val * take >= 0) take++;
                    take--;

                    s -= take * val;

                    // distribute take among the numbers
                    for (int i = 0; take > 0 && i < n; i++) {
                        long get = Math.min(9, take);
                        ans[i] += val * get;
                        take -= get;
                    }

                    // is it possible that there isn't enough room? don't think so?
                }
            }
            int zeroidx = 0;
            while (zeroidx < n && ans[zeroidx] != 0) zeroidx++;

            // we need to distribute the least useful numbers to any numbers that are still 0.
            while (zeroidx < n) {
                // pw.println(Arrays.toString(ans));
                int idxMinTakable = -1;
                int powToTake = Integer.MAX_VALUE;

                int idxSplitable = -1;
                int powSplitable = Integer.MAX_VALUE;

                boolean found = false;
                for (int i = 0; i < n; i++) {
                    if (ans[i] == 0 || ans[i] == 1) continue;
                    int powNonZero = 0;  // power to take

                    while (ans[i] % pow10[powNonZero+1] / pow10[powNonZero] == 0) powNonZero++;

                    if (ans[i] == pow10[powNonZero]) {
                        // can't take, but can split
                        if (powNonZero < powSplitable) {
                            idxSplitable = i;
                            powSplitable = powNonZero;
                        }
                    } else {
                        // take it
                        if (powNonZero < powToTake) {
                            idxMinTakable = i;
                            powToTake = powNonZero;
                        }
                    }
                }

                if (powToTake != Integer.MAX_VALUE) {
                    ans[idxMinTakable] -= pow10[powToTake];
                    ans[zeroidx++] += pow10[powToTake];
                } else {
                    long val = pow10[powSplitable];
                    long smaller = val / 10;
                    ans[idxSplitable] = smaller * 9l;
                    ans[zeroidx++] = smaller;
                }
            }
            
            for (long x : ans) pw.print(x + " ");
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