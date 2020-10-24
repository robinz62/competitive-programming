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
        int[][] a = new int[n][];
        for (int i = 0; i < n; i++) a[i] = ril(m);

        // B[i][rem] is best sum from row i with remainder rem
        int[][] B = new int[n][k];

        // dp[i][j][k] for a row is maximum sum for elements [0..i] using <= j elements such that mod is k
        for (int i = 0; i < n; i++) {
            int[][][] dp = new int[m][m/2+1][k];
            for (int x = 0; x < m; x++) for (int y = 0; y < m/2+1; y++) for (int z = 0; z < k; z++) dp[x][y][z] = Integer.MIN_VALUE;
            for (int use = 0; use < m/2+1; use++) dp[0][use][0] = 0;
            for (int use = 1; use < m/2+1; use++) dp[0][use][a[i][0]%k] = a[i][0];
            for (int j = 1; j < m; j++) {
                for (int rem = 0; rem < k; rem++) dp[j][0][rem] = dp[j-1][0][rem];
                for (int use = 1; use < m/2+1; use++) {
                    for (int rem = 0; rem < k; rem++) {
                        int me = a[i][j];
                        // don't use
                        dp[j][use][rem] = Math.max(dp[j-1][use][rem], dp[j][use-1][rem]);
                        // use
                        if (dp[j-1][use-1][(rem-me%k+k)%k] != Integer.MIN_VALUE)
                            dp[j][use][rem] = Math.max(dp[j][use][rem], dp[j-1][use-1][(rem - me%k + k) % k] + me);
                    }
                }
            }
            for (int rem = 0; rem < k; rem++) {
                B[i][rem] = dp[m-1][m/2][rem];
            }
        }

        int[][] dp = new int[n][k];
        dp[0] = B[0];
        for (int i = 1; i < n; i++) {
            Arrays.fill(dp[i], Integer.MIN_VALUE);
            for (int rem = 0; rem < k; rem++) {
                for (int prevrem = 0; prevrem < k; prevrem++) {
                    int r = (rem + prevrem) % k;
                    if (dp[i-1][prevrem] != Integer.MIN_VALUE && B[i][rem] != Integer.MIN_VALUE) dp[i][r] = Math.max(dp[i][r], dp[i-1][prevrem] + B[i][rem]);
                }
            }
        }
        pw.println(dp[n-1][0]);
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
        pw.printf("%f", d);
    }
}