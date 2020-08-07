import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static int MOD = 1000000007;

    void solve() throws IOException {
        int[] nm = ril(2);
        int n = nm[0];
        int m = nm[1];
        char[][] A = new char[n][];
        for (int i = 0; i < n; i++) A[i] = rs();
        long[][] dp = new long[n][m];
        for (int i = 0; i < n; i++) dp[i][0] = 1;
        for (int j = 1; j < m; j++) dp[0][j] = 1;

        long sum = n + m - 1;
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                char c = A[i][j];
                if (i-2 >= 0 && j-1 >= 0 && j+1 < m && A[i-1][j-1] == c && A[i-1][j] == c && A[i-1][j+1] == c && A[i-2][j] == c) {
                    dp[i][j] = 1 + Math.min(Math.min(dp[i-1][j-1], dp[i-2][j]), Math.min(dp[i-1][j], dp[i-1][j+1]));
                } else {
                    dp[i][j] = 1;
                }
                sum += dp[i][j];
            }
        }
        pw.println(sum);
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