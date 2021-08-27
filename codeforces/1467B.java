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
        for (int Ti = 0; Ti < T; Ti++) {
            int n = ri();
            int[] a = ril(n);
            boolean[] special = new boolean[n+1];
            int curr = 0;
            for (int i = 1; i < n-1; i++) {
                if (a[i] > a[i-1] && a[i] > a[i+1]) {
                    special[i] = true;
                    curr++;
                }
                if (a[i] < a[i-1] && a[i] < a[i+1]) {
                    special[i] = true;
                    curr++;
                }
            }
            int ans = curr;
            // Try changing each val to equal the val on its left
            for (int i = 1; i < n; i++) {
                int old = (special[i-1]?1:0) + (special[i]?1:0) + (special[i+1]?1:0);
                int now = 0;
                int temp = a[i];
                a[i] = a[i-1];
                if (isSpecial(a, i+1)) now++;
                a[i] = temp;
                ans = Math.min(ans, curr - old + now);
            }

            // For safety maybe also try changing vals to their right
            for (int i = 0; i < n-1; i++) {
                int old = (i-1>=0&&special[i-1]?1:0) + (special[i]?1:0) + (special[i+1]?1:0);
                int now = 0;
                int temp = a[i];
                a[i] = a[i+1];
                if (isSpecial(a, i-1)) now++;
                a[i] = temp;
                ans = Math.min(ans, curr - old + now);
            }
            pw.println(ans);
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    boolean isSpecial(int[] a, int i) {
        if (i <= 0 || i >= a.length-1) return false;
        return a[i] > a[i-1] && a[i] > a[i+1] || a[i] < a[i-1] && a[i] < a[i+1];
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