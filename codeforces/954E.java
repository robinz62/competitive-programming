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
        int[] nT = ril(2);
        int n = nT[0];
        int T = nT[1];
        int[][] at = new int[n][2];
        int[] a = ril(n);
        for (int i = 0; i < n; i++) at[i][0] = a[i];
        int[] t = ril(n);
        for (int i = 0; i < n; i++) at[i][1] = t[i];

        // Sort by temperature
        Arrays.sort(at, (at1, at2) -> Integer.compare(at1[1], at2[1]));
        long less = 0;
        long more = 0;
        for (int[] ati : at) {
            if (ati[1] < T) less += (long) ati[0] * (T - ati[1]);
            else if (ati[1] > T) more += (long) ati[0] * (ati[1] - T);
        }
        double ans = 0;
        if (less < more) {
            // take all of less, then take however much of the lower values of
            // T you can (since higher values cancel faster)
            long num = 0;
            long den = 0;
            for (int i = 0; i < n; i++) {
                double addNum = (long) at[i][0] * at[i][1];
                double addDen = (long) at[i][0];
                if ((double) (num+addNum) / (den+addDen) > T) {
                    ans += ((double) den * T - num) / ((double) at[i][1] - T);
                    break;
                }
                num += addNum;
                den += addDen;
                ans += at[i][0];
            }
        } else {
            long num = 0;
            long den = 0;
            for (int i = n-1; i >= 0; i--) {
                double addNum = (long) at[i][0] * at[i][1];
                double addDen = (long) at[i][0];
                if ((double) (num+addNum) / (den+addDen) < T) {
                    ans += ((double) den * T - num) / ((double) at[i][1] - T);
                    break;
                }
                num += addNum;
                den += addDen;
                ans += at[i][0];
            }
        }
        printDouble(ans);
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

    void printDouble(double d) {
        pw.printf("%.16f", d);
    }
}