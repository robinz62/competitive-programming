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
    //   sorting, or taking max, after MOD
    void solve() throws IOException {
        int T = ri();
        for (int Ti = 0; Ti < T; Ti++) {
            int[] nm = ril(2);
            int n = nm[0];
            int m = nm[1];
            char[][] grid = new char[n][];
            for (int i = 0; i < n; i++) grid[i] = rs();
            boolean ok = true;
            for (int i = 0; ok && i < n; i++) {
                boolean expectRed = i % 2 == 0;
                if (expectRed && grid[i][0] == 'W' || !expectRed && grid[i][0] == 'R') ok = false;
                expectRed = !expectRed;
                for (int j = 1; ok && j < m; j++) {
                    if (expectRed && grid[i][j] == 'W' || !expectRed && grid[i][j] == 'R') ok = false;
                    expectRed = !expectRed;
                }
            }
            if (ok) {
                pw.println("YES");
                for (int i = 0; ok && i < n; i++) {
                    boolean expectRed = i % 2 == 0;
                    if (expectRed) pw.print('R'); else pw.print('W');
                    expectRed = !expectRed;
                    for (int j = 1; ok && j < m; j++) {
                        if (expectRed) pw.print('R'); else pw.print('W');
                        expectRed = !expectRed;
                    }
                    pw.println();
                }
                continue;
            }

            ok = true;
            for (int i = 0; ok && i < n; i++) {
                boolean expectRed = i % 2 == 1;
                if (expectRed && grid[i][0] == 'W' || !expectRed && grid[i][0] == 'R') ok = false;
                expectRed = !expectRed;
                for (int j = 1; ok && j < m; j++) {
                    if (expectRed && grid[i][j] == 'W' || !expectRed && grid[i][j] == 'R') ok = false;
                    expectRed = !expectRed;
                }
            }

            if (ok) {
                pw.println("YES");
                for (int i = 0; ok && i < n; i++) {
                    boolean expectRed = i % 2 == 1;
                    if (expectRed) pw.print('R'); else pw.print('W');
                    expectRed = !expectRed;
                    for (int j = 1; ok && j < m; j++) {
                        if (expectRed) pw.print('R'); else pw.print('W');
                        expectRed = !expectRed;
                    }
                    pw.println();
                }
            } else {
                pw.println("NO");
            }
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

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