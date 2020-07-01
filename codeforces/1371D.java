import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pw = new PrintWriter(System.out);
    static int MOD = 1000000007;

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

    void solve() throws IOException {
        int t = ri();
        for (int ti = 0; ti < t; ti++) {
            int[] nk = ril(2);
            int n = nk[0];
            int k = nk[1];
            int[][] A = new int[n][n];
            int r = 0;
            int c = 0;
            for (int i = 0; i < k; i++) {
                A[r][c] = 1;
                r = (r + 1) % n;
                c = (c + 1) % n;
                if (r == 0) c++;
            }
            int maxr = Integer.MIN_VALUE;
            int minr = Integer.MAX_VALUE;
            int maxc = Integer.MIN_VALUE;
            int minc = Integer.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                int sum = 0;
                for (int j = 0; j < n; j++) {
                    sum += A[i][j];
                }
                maxr = Math.max(maxr, sum);
                minr = Math.min(minr, sum);
            }
            for (int j = 0; j < n; j++) {
                int sum = 0;
                for (int i = 0; i < n; i++) {
                    sum += A[i][j];
                }
                maxc = Math.max(maxc, sum);
                minc = Math.min(minc, sum);
            }
            int dr = maxr - minr;
            int dc = maxc - minc;
            pw.println(dr * dr + dc * dc);
            for (int[] row : A) {
                for (int x : row) {
                    pw.print(x);
                }
                pw.println();
            }
        }
    }
}