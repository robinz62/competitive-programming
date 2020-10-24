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
        fact = new long[1001];
        fact[0] = 1;
        for (int i = 1; i <= 1000; i++) fact[i] = fact[i-1] * i % MOD;
        choose = new long[1001][1001];
        for (int i = 0; i <= 1000; i++) choose[i][0] = 1;
        for (int i = 1; i <= 1000; i++) {
            for (int j = 1; j <= i; j++) {
                choose[i][j] = (choose[i-1][j] + choose[i-1][j-1]) % MOD;
            }
        }

        int[] nxpos = ril(3);
        int n = nxpos[0];
        int x = nxpos[1];
        int pos = nxpos[2];

        int[] identity = new int[n];
        for (int i = 0; i < n; i++) identity[i] = i;

        int[] comps = bs(identity, pos);
        comps[0]--;  // -1 b/c x is fixed at pos gives an up for free

        int u = comps[0];
        int d = comps[1];

        long ans = choose(x-1, comps[0]) * choose(n-x, comps[1]) % MOD * fact[u] % MOD * fact[d] % MOD * fact[n - u - d - 1] % MOD;
        pw.println(ans);
    }

    long[][] choose;
    long[] fact;
    long choose(int n, int k) {
        if (n < k) return 0;
        if (n < 0) return 0;
        if (k < 0) return 0;
        return choose[n][k];
    }

    int[] bs(int[] a, int x) {
        int[] comps = new int[2];  // up, down
        int l = 0;
        int r = a.length;
        while (l < r) {
            int m = (l + r) / 2;
            if (a[m] <= x) {
                l = m+1;
                comps[0]++;
            } else {
                r = m;
                comps[1]++;
            }
        }
        if (l > 0 && a[l-1] == x) return comps;
        return null;
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