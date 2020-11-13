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
        int[] nx = ril(2);
        int n = nx[0];
        int x = nx[1];

        String[] tokens = br.readLine().split(" ");
        double[] p = new double[x+1];
        for (int i = 0; i < p.length; i++) {
            p[i] = Double.parseDouble(tokens[i]);
        }

        int SIZE = 1 << 7;

        double[][] matrix = new double[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if ((i^j) < p.length) matrix[i][j] = p[i ^ j];
            }
        }
        double[][] pMat = new double[SIZE][1];
        for (int i = 0; i < p.length; i++) pMat[i][0] = p[i]; 

        // Lose if xor is 0
        double[][] mat = matrixExp(matrix, n-1);
        double[][] ans = new double[SIZE][1];
        matrixMult(mat, pMat, ans);
        printDouble(1 - ans[0][0]);
    }

    double[][] matrixExp(double[][] A, int k) {
        int m = A.length;
        int n = A[0].length;
        if (k == 1) {
            double[][] res = new double[m][n];
            for (int i = 0; i < m; i++)
                for (int j = 0; j < n; j++)
                    res[i][j] = A[i][j];
            return res;
        }
        double[][] half = matrixExp(A, k / 2);
        double[][] res = new double[m][n];
        matrixMult(half, half, res);
        if (k % 2 == 0) return res;
        double[][] res2 = new double[m][n];
        matrixMult(res, A, res2);
        return res2;
    }

    void matrixMult(double[][] A, double[][] B, double[][] C) {
        int n = A.length;
        int m = B.length;
        int p = B[0].length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < p; j++) {
                C[i][j] = 0;
                for (int k = 0; k < m; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
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
        pw.printf("%.16f\n", d);
    }
}