import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    // After writing solution, quick scan for:
    //   int overflow
    //   array out of bounds
    //   special cases e.g. n=1?
    void solve() throws IOException {
        int n = ri();
        
        // Give matrix (flush)
        long[][] A = new long[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i % 2 == 0) continue;
                int exp = i+j-1;
                A[i][j] = 1l << exp;
            }
        }

        for (long[] row : A) {
            for (long x : row) pw.print(x + " ");
            pw.println();
        }
        pw.flush();

        int q = ri();
        for (int qi = 0; qi < q; qi++) {
            long k = rl();
            int i = 0;
            int j = 0;
            long bit = 0;
            pw.println("1 1");
            while (i != n-1 || j != n-1) {
                if (A[i][j] == 0) {
                    if (((1l << bit) & k) > 0) i++;
                    else j++;
                } else {
                    if (((1l << bit) & k) > 0) j++;
                    else i++;
                }
                bit++;
                pw.println((i+1) + " " + (j+1));
            }

            pw.flush();
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
            int temp = A[i];
            A[i] = A[j];
            A[j] = temp;
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