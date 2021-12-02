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
    //   sorting, or taking max, after MOD
    void solve() throws IOException {
        int[] nx = ril(2);
        int n = nx[0];
        int x = nx[1];

        choose = new long[n+1][n+1];
        choose[0][0] = 1;
        for (int i = 1; i <= n; i++) {
            choose[i][0] = 1;
            for (int j = 1; j <= i; j++) {
                choose[i][j] = choose[i-1][j] + choose[i-1][j-1];
                if (choose[i][j] >= MOD) choose[i][j] -= MOD;
            }
        }

        dp = new long[n+1][x+1];
        for (long[] row : dp) Arrays.fill(row, -1);
        pw.println(helper(n, x));
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    long[][] choose;
    long[][] dp;
    long helper(int n, int x) {
        if (n == 1) return 0;
        if (n == 0) return 1;
        if (x <= 0) return 0;
        if (dp[n][x] != -1) return dp[n][x];

        dp[n][x] = 0;
        for (int kill = 0; kill <= n; kill++) {
            if (kill == n-1) continue;
            long choose_ways = choose[n][kill];
            long max_health_for_kill = Math.min(n-1, x);
            long kill_ways = modPow(max_health_for_kill, kill, MOD);
            kill_ways = kill_ways * choose_ways % MOD;

            int left = n - kill;
            // everyone left has health in range [n..x)
            // subtract n-1 from that to obtain range [1..x-(n-1)]
            long sub = helper(left, x-(n-1));
            dp[n][x] = (dp[n][x] + kill_ways * sub) % MOD;
        }
        return dp[n][x];
    }

    public static long modPow(long base, long exponent, long m) {
        long ans = 1;
        base = base % m;
        while (exponent > 0) {
            if ((exponent & 1) == 1) ans = (ans * base) % m;
            exponent >>= 1;
            base = (base * base) % m;
        } 
        return ans;
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