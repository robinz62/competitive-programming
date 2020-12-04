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
        int T = ri();
        for (int Ti = 0; Ti < T; Ti++) {
            int n = ri();
            char[][] A = new char[n][];
            for (int i = 0; i < n; i++) A[i] = rs();
            int[][][] rowToPos = new int[n][10][2];
            int[][][] colToPos = new int[n][10][2];
            for (int i = 0; i < n; i++) {
                for (int d = 0; d < 10; d++) {
                    rowToPos[i][d][0] = rowToPos[i][d][1] = colToPos[i][d][0] = colToPos[i][d][1] = -1;
                }
            }
            int[] firstRowWith = new int[10]; Arrays.fill(firstRowWith, -1);
            int[] lastRowWith = new int[10]; Arrays.fill(lastRowWith, -1);
            int[] firstColWith = new int[10]; Arrays.fill(firstColWith, -1);
            int[] lastColWith = new int[10]; Arrays.fill(lastColWith, -1);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    int d = A[i][j] - '0';
                    if (rowToPos[i][d][0] == -1) rowToPos[i][d][0] = j;
                    rowToPos[i][d][1] = j;

                    if (firstRowWith[d] == -1) firstRowWith[d] = i;
                    lastRowWith[d] = i;
                }
            }
            for (int j = 0; j < n; j++) {
                for (int i = 0; i < n; i++) {
                    int d = A[i][j] - '0';
                    if (colToPos[j][d][0] == -1) colToPos[j][d][0] = i;
                    colToPos[j][d][1] = i;

                    if (firstColWith[d] == -1) firstColWith[d] = j;
                    lastColWith[d] = j;
                }
            }

            for (int d = 0; d < 10; d++) {
                int ans = 0;
                // Case 1. We have two of the digit in our row, and we can place the
                // third in the first or last row.
                for (int i = 0; i < n; i++) {
                    if (rowToPos[i][d][0] != -1) {
                        int base = rowToPos[i][d][1] - rowToPos[i][d][0];
                        int height = Math.max(n-1 - i, i);
                        ans = Math.max(ans, base * height);
                    }
                    if (colToPos[i][d][0] != -1) {
                        int base = colToPos[i][d][1] - colToPos[i][d][0];
                        int height = Math.max(n-1 - i, i);
                        ans = Math.max(ans, base * height);
                    }
                }

                // Case 2. Place a second one in the first or last column, and have the
                // third digit be the first or last row with this digit. Same with column case.

                for (int i = 0; i < n; i++) {
                    if (rowToPos[i][d][0] != -1) {
                        int base = Math.max(n-1 - rowToPos[i][d][0], rowToPos[i][d][0]);
                        int height = Math.max(lastRowWith[d] - i, i - firstRowWith[d]);
                        ans = Math.max(ans, base * height);
                    }
                    if (rowToPos[i][d][1] != -1) {
                        int base = Math.max(n-1 - rowToPos[i][d][1], rowToPos[i][d][1]);
                        int height = Math.max(lastRowWith[d] - i, i - firstRowWith[d]);
                        ans = Math.max(ans, base * height);
                    }

                    if (colToPos[i][d][0] != -1) {
                        int base = Math.max(n-1 - colToPos[i][d][0], colToPos[i][d][0]);
                        int height = Math.max(lastColWith[d] - i, i - firstColWith[d]);
                        ans = Math.max(ans, base * height);
                    }
                    if (colToPos[i][d][1] != -1) {
                        int base = Math.max(n-1 - colToPos[i][d][1], colToPos[i][d][1]);
                        int height = Math.max(lastColWith[d] - i, i - firstColWith[d]);
                        ans = Math.max(ans, base * height);
                    }
                }

                pw.print(ans + " ");
            }
            pw.println();
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
        pw.printf("%.16f", d);
    }
}