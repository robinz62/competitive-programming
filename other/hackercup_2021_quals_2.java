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
            int n = ri();
            char[][] board = new char[n][];
            for (int i = 0; i < n; i++) board[i] = rs();

            int[] rowsx = new int[n];
            int[] colsx = new int[n];
            boolean[] rowpossible = new boolean[n];
            boolean[] colpossible = new boolean[n];
            Arrays.fill(rowpossible, true);
            Arrays.fill(colpossible, true);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    char c = board[i][j];
                    if (c == '.') continue;
                    else if (c == 'X') {
                        rowsx[i]++;
                        colsx[j]++;
                    } else if (c == 'O') {
                        rowpossible[i] = colpossible[j] = false;
                    }
                }
            }

            int ans = Integer.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                if (rowpossible[i]) ans = Math.min(ans, n-rowsx[i]);
                if (colpossible[i]) ans = Math.min(ans, n-colsx[i]);
            }

            if (ans == Integer.MAX_VALUE) {
                pw.println("Case #" + (Ti+1) + ": Impossible");
                continue;
            }

            if (ans == 1) {
                int count = 0;
                for (int i = 0; i < n; i++) {
                    if (rowpossible[i] && rowsx[i] == n-ans) count++;
                    if (colpossible[i] && colsx[i] == n-ans) count++;
                }
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (rowpossible[i] && colpossible[j] && rowsx[i] == n-ans && colsx[j] == n-ans && board[i][j] == '.') count--;
                    }
                }
                pw.println("Case #" + (Ti+1) + ": " + ans + " " + count);
                continue;
            }

            int count = 0;
            for (int i = 0; i < n; i++) {
                if (rowpossible[i] && rowsx[i] == n-ans) count++;
                if (colpossible[i] && colsx[i] == n-ans) count++;
            }
            pw.println("Case #" + (Ti+1) + ": " + ans + " " + count);
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