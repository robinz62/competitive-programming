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
            int[] rck = ril(3);
            int r = rck[0];
            int c = rck[1];
            int k = rck[2]-1;
            char[][] grid = new char[r][];
            for (int i = 0; i < r; i++) grid[i] = rs();

            // Feel like the solution never has both ups and downs.
            // Seems like it can be solved column by column independently?

            // try moving up with clears along the way if needed
            // we need to decide the total number of up moves we use. can we compute
            // the answer if we move up zero times, then iteratively in O(1) recalculate for the rest?

            // we basically need to decide: if we use X upward moves, does this column need a type 1 removal?

            int ans = Integer.MAX_VALUE;

            int[][] carsAtAndAbove = new int[r][c];
            for (int j = 0; j < c; j++) {
                if (grid[0][j] == 'X') carsAtAndAbove[0][j] = 1;
                for (int i = 1; i < r; i++) {
                    carsAtAndAbove[i][j] = carsAtAndAbove[i-1][j] + (grid[i][j] == 'X' ? 1 : 0);
                }
            }
            int[][] carsAtAndBelow = new int[r][c];
            for (int j = 0; j < c; j++) {
                if (grid[r-1][j] == 'X') carsAtAndBelow[r-1][j] = 1;
                for (int i = r-2; i >= 0; i--) {
                    carsAtAndBelow[i][j] = carsAtAndBelow[i+1][j] + (grid[i][j] == 'X' ? 1 : 0);
                }
            }

            int curr = 0;
            for (int j = 0; j < c; j++) {
                if (grid[k][j] == 'X') curr++;
            }
            ans = Math.min(ans, curr);

            for (int upwards = 1; upwards <= r-1; upwards++) {
                curr = upwards;
                for (int j = 0; j < c; j++) {
                    if (k+upwards < r && grid[k+upwards][j] == 'X') {
                        curr++;
                        continue;
                    }
                    if (carsAtAndAbove[Math.min(k+upwards, r-1)][j] >= k+1) {
                        curr++;
                        continue;
                    }
                }
                ans = Math.min(ans, curr);
            }

            for (int downwards = 1; downwards <= r-1; downwards++) {
                curr = downwards;
                for (int j = 0; j < c; j++) {
                    if (k-downwards >= 0 && grid[k-downwards][j] == 'X') {
                        curr++;
                        continue;
                    }
                    if (carsAtAndBelow[Math.max(k-downwards, 0)][j] >= r-1-k+1) {
                        curr++;
                        continue;
                    }
                }
                ans = Math.min(ans, curr);
            }

            pw.println("Case #" + (Ti+1) + ": " + ans);
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