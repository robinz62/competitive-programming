import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static int MOD = 1000000007;

    // After writing solution, quick scan for:
    //   int overflow
    //   array out of bounds
    //   special cases e.g. n=1?
    void solve() throws IOException {
        int t = ri();
        for (int ti = 0; ti < t; ti++) {
            int n = ri();
            int[] a = ril(n);
            int min = a[0];
            for (int ai : a) min = Math.min(min, ai);
            boolean[] good = new boolean[n];
            for (int i = 0; i < n; i++) good[i] = gcd(a[i], min) == min;
            int[] sorted = new int[n];
            System.arraycopy(a, 0, sorted, 0, n);
            Arrays.sort(sorted);
            boolean[] needsSwap = new boolean[n];
            for (int i = 0; i < n; i++) needsSwap[i] = a[i] != sorted[i];
            boolean done = false;
            for (int i = 0; !done && i < n; i++) {
                if (needsSwap[i]) {
                    if (!good[i]) {
                        pw.println("NO");
                        done = true;
                    }
                }
            }
            if (!done) pw.println("YES");
        }
    }

    public static int gcd(int a, int b) {
        return a == 0 ? b : gcd(b % a, a);
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
        return Integer.parseInt(br.readLine());
    }

    long rl() throws IOException {
        return Long.parseLong(br.readLine());
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

    int max(int a, int b) {
        return a >= b ? a : b;
    }

    int min(int a, int b) {
        return a <= b ? a : b;
    }
}