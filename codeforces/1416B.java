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
            int n = ri();
            long[] aa = rll(n);
            long[] a = new long[n+1];
            System.arraycopy(aa, 0, a, 1, n);  // 1-indexed
// long[] debug = new long[n+1];
// System.arraycopy(a, 0, debug, 0, n+1);
            
            if (n == 1) {
                pw.println("0");
                continue;
            }

            long sum = 0;
            for (int i = 1; i <= n; i++) sum += a[i];
            if (sum % n != 0) {
                pw.println("-1");
                continue;
            }

            List<long[]> ans = new ArrayList<>();
            long target = sum / n;

            // bankroll a[1]
            for (int j = 2; j <= n; j++) {
                if (a[j] % j != 0) {
                    long need = j - (a[j] % j);
                    ans.add(new long[]{1, j, need});
                    a[1] -= need;
                    a[j] += need;
                }
                long cycles = a[j] / j;
                ans.add(new long[]{j, 1, cycles});
                a[j] -= cycles * j;
                a[1] += cycles * j;
            }

            // all a[j] are now = 0
            for (int j = 2; j <= n; j++) {
                long need = target - a[j];
                ans.add(new long[]{1, j, need});
                a[1] -= need;
                a[j] += need;
            }

            pw.println(ans.size());
            for (long[] arr : ans) pw.println(arr[0] + " " + arr[1] + " " + arr[2]);
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