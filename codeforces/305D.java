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
        int[] nmk = ril(3);
        int n = nmk[0];
        int m = nmk[1];
        int k = nmk[2];
        long ans = 0;
        int first = Integer.MAX_VALUE;
        int last = Integer.MIN_VALUE;
        boolean[] has = new boolean[n+1];
        int count = 0;
        for (int i = 0; i < m; i++) {
            int[] uv = ril(2);
            int dist = uv[1] - uv[0];
            if (dist != 1 && dist != k+1) {
                pw.println("0");
                return;
            }
            if (dist == 1) continue;
            // dist is k+1
            first = Math.min(first, uv[0]);
            last = Math.max(last, uv[0]);
            has[uv[0]] = true;
            count++;
        }

        long[] pow2 = new long[n+6];
        pow2[0] = 1;
        for (int i = 1; i < pow2.length; i++) pow2[i] = pow2[i-1] * 2 % MOD;

        if (count == 0) {
            // No edges at all
            ans++;

            // Otherwise, place leftmost edge
            int choices = 0;
            for (int i = n-(k+1); i >= 1; i--) {
                ans = (ans + pow2[choices]) % MOD;
                choices = Math.min(choices+1, k);
            }
            pw.println(ans);
            return;
        } else {
            if (last >= first + k+1) {
                pw.println("0");
                return;
            }

            // Case on where we place the first arc
            int totalChoices = Math.min(n - (k+1) - first + 1, k+1);
            for (int i = first; i >= 1 && last - i < k+1; i--) {
                int choices = totalChoices - count;
                if (i != first) choices--;
                ans = (ans + pow2[choices]) % MOD;
                totalChoices = Math.min(totalChoices+1, k+1);
            }
            pw.println(ans);
            return;
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