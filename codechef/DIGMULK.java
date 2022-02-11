import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static int MOD = 1000000007;

    // After writing solution, quick scan for:
    //   array out of bounds
    //   special cases e.g. n=1?
    //   npe, particularly in maps
    //
    // Big numbers arithmetic bugs:
    //   int overflow
    //   if (x : long) and (y : int), [y = x] does not compile, but [y += x] does
    //   sorting, or taking max, after MOD
    void solve() throws IOException {
        int T = ri();
        for (int Ti = 0; Ti < T; Ti++) {
            int[] nkm = ril(3);
            int n = nkm[0];
            int k = nkm[1];
            int m = nkm[2];
            char[] s = rs();
            long[][] counts = new long[10][1];
            for (char c : s) counts[c - '0'][0]++;

            long[][] mat = new long[10][10];
            for (int d = 0; d < 10; d++) {
                long res = (long) d * k;
                if (res == 0) {
                    mat[0][d]++;
                } else {
                    while (res > 0) {
                        mat[(int) (res % 10)][d]++;
                        res /= 10;
                    }
                }
            }

            mat = matrixExp(mat, m);
            long[][] result = matrixMult(mat, counts);
            long ans = 0;
            for (int d = 0; d < 10; d++) ans += result[d][0];
            pw.println(ans % MOD);
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    long[][] matrixMult(long[][] A, long[][] B) {
        int m = A.length;
        int n = A[0].length;
        int p = B[0].length;
        long[][] C = new long[m][p];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < p; j++)
                for (int k = 0; k < n; k++)
                    C[i][j] = (C[i][j] + A[i][k] * B[k][j] % MOD) % MOD;
        return C;
    }

    long[][] matrixExp(long[][] A, int k) {
        int n = A.length;
        if (k == 0) {
            long[][] res = new long[n][n];
            for (int i = 0; i < n; i++) res[i][i] = 1;
            return res;
        }
        if (k == 1) {
            long[][] res = new long[n][n];
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    res[i][j] = A[i][j];
            return res;
        }
        long[][] half = matrixExp(A, k / 2);
        long[][] res = matrixMult(half, half);
        if (k % 2 == 0) return res;
        res = matrixMult(res, A);
        return res;
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
        int c = br.read();
        int x = 0;
        while (c >= '0' && c <= '9') {
            x = x * 10 + c - '0';
            c = br.read();
        }
        return ril(x);
    }

    long[] rkll() throws IOException {
        int c = br.read();
        int x = 0;
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