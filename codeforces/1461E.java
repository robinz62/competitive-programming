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
        long[] klrtxy = rll(6);
        long k = klrtxy[0];  // 10^18
        long l = klrtxy[1];  // 10^18
        long r = klrtxy[2];  // 10^18
        long t = klrtxy[3];  // 10^18
        long x = klrtxy[4];  // 10^6
        long y = klrtxy[5];  // 10^18

        // Normalize to acceptable range [0, r]
        r -= l;
        k -= l;
        l -= l;

        // No refilling necessary at all
        if (k / x >= t) {
            pw.println("Yes");
            return;
        }

        // Immediately rekt
        if (k + y > r && k - x < 0) {
            pw.println("No");
            return;
        }

        // Perfectly balanced
        if (x == y) {
            if (k + y <= r || k - x >= 0) pw.println("Yes");
            else pw.println("No");
            return;
        }

        // Bleeding out. In this case, we don't need to worry about adding too much,
        // since we will only achieve less than previous day, which was already ok.
        if (x > y) {
            long firstRefillDay = (k+x+y-r)%x==0 ? (k+x+y-r)/x : (k+x+y-r)/x + 1;
            // First refill day is too late
            if (k - firstRefillDay * x + x < 0) {
                pw.println("No");
                return;
            }

            t -= firstRefillDay - 1;
            k = k - firstRefillDay * x + x;
            // Starting now, we do a +y then -x. 
            long deathDay = k/(x-y)+1;
            long ok = deathDay-1;
            if (ok >= t) pw.println("Yes");
            else pw.println("No");
            return;
        }

        // y > x. We worry about refilling too much. The danger is
        // if we hit a level between [0, x-1] and we cannot refill
        // NOTICE: x <= 10^6; we can probably take advantage of that...

        // If I can refill at x-1, then I'm always ok
        if (x-1 + y <= r) {
            pw.println("Yes");
            return;
        }

        boolean[] visited = new boolean[(int) x];
        while (true) {
            long daysLast = k / x;
            k -= daysLast * x;
            t -= daysLast;
            if (t <= 0) {
                pw.println("Yes");
                return;
            }
            if (visited[(int) k]) {
                pw.println("Yes");
                return;
            }
            visited[(int) k] = true;
            if (k + y > r) {
                pw.println("No");
                return;
            }
            k += y;
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

    void printDouble(double d) {
        pw.printf("%.16f", d);
    }
}