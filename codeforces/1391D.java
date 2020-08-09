import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static int MOD = 1000000007;

    void solve() throws IOException {
        int[] nm = ril(2);
        int n = nm[0];
        int m = nm[1];
        if (n >= 4 && m >= 4) {
            pw.println("-1");
            return;
        }
        if (n == 1 || m == 1) {
            pw.println("0");
            return;
        }

        int[][] A = new int[n][m];
        for (int i = 0; i < n; i++) {
            char[] line = rs();
            for (int j = 0; j < m; j++) A[i][j] = line[j] - '0';
        }

        if (n == 2 || m == 2) {
            if (n != 2) {
                int[][] tmp = new int[m][n];
                for (int i = 0; i < n; i++)
                    for (int j = 0; j < m; j++)
                        tmp[j][i] = A[i][j];
                A = tmp;
                int temp = n;
                n = m;
                m = temp;
            }
            // dp[i][0..3] is min cost to make up to i good where it ends with 0..3
            int[][] dp = new int[m][4];
            for (int i = 0; i < m; i++) Arrays.fill(dp[i], Integer.MAX_VALUE);
            int fstcol = (A[0][0] << 1) | A[1][0];
            for (int i = 0; i < 4; i++) dp[0][i] = Integer.bitCount(fstcol ^ i);
            for (int i = 1; i < m; i++) {
                int col = (A[0][i] << 1) | A[1][i];
                for (int mask = 0; mask < 4; mask++) {
                    for (int mask2 = 0; mask2 < 4; mask2++) {
                        int total = (mask2 << 2) | mask;
                        if (Integer.bitCount(total & 0b1111) % 2 == 0) continue;
                        dp[i][mask] = min(dp[i][mask], Integer.bitCount(mask ^ col) + dp[i-1][mask2]);
                    }
                }
            }
            int ans = Integer.MAX_VALUE;
            for (int x : dp[m-1]) ans = min(ans, x);
            pw.println(ans);
            return;
        }

        if (n != 3) {
            int[][] tmp = new int[m][n];
            for (int i = 0; i < n; i++)
                for (int j = 0; j < m; j++)
                    tmp[j][i] = A[i][j];
            A = tmp;
            int temp = n;
            n = m;
            m = temp;
        }

        // dp[i][0..8] is min cost to make up to i good
        int[][] dp = new int[m][8];
        for (int i = 0; i < m; i++) Arrays.fill(dp[i], Integer.MAX_VALUE);

        int fstcol = (A[0][0] << 2) | (A[1][0] << 1) | A[2][0];
        int sndcol = (A[0][1] << 2) | (A[1][1] << 1) | A[2][1];
        for (int mask = 0; mask < 8; mask++) {
            for (int mask2 = 0; mask2 < 8; mask2++) {
                int total = (mask2 << 3) | mask;
                if (Integer.bitCount(total & 0b110110) % 2 == 0) continue;
                if (Integer.bitCount(total & 0b011011) % 2 == 0) continue;
                int me = (fstcol << 3) | sndcol;
                dp[1][mask] = min(dp[1][mask], Integer.bitCount(total ^ me));
            }
        }

        for (int i = 2; i < m; i++) {
            int col = (A[0][i] << 2) | (A[1][i] << 1) | A[2][i];
            for (int mask = 0; mask < 8; mask++) {
                for (int mask2 = 0; mask2 < 8; mask2++) {
                    int total = (mask2 << 3) | mask;
                    if (Integer.bitCount(total & 0b110110) % 2 == 0) continue;
                    if (Integer.bitCount(total & 0b011011) % 2 == 0) continue;
                    dp[i][mask] = min(dp[i][mask], Integer.bitCount(mask ^ col) + dp[i-1][mask2]);
                }
            }
        }

        int ans = Integer.MAX_VALUE;
        for (int x : dp[m-1]) ans = min(ans, x);
        pw.println(ans);
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
            A[i] ^= A[j];
            A[j] ^= A[i];
            A[i] ^= A[j];
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