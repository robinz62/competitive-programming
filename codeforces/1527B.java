import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static int MOD = 1000000007;

    int INF = 1000000;

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
        
        for (int[][][] x : dp) for (int[][] y : x) for (int[] z : y) Arrays.fill(z, INF);
        for (int Ti = 0; Ti < T; Ti++) {
            int n = ri();
            char[] s = rs();

            int l = 0;
            int r = n-1;
            int pairedZeros = 0;
            int inbalanced = 0;
            int middleZero = n % 2 == 1 && s[n / 2] == '0' ? 1 : 0;
            while (l < r) {
                if (s[l] == s[r] && s[l] == '0') pairedZeros++;
                else if (s[l] != s[r]) inbalanced++;
                l++;
                r--;
            }

            // IDEAS
            // if inbalanced = 0, then the string is a palindrome
            // dp[x][y][just_reversed] is best (alice-bob) with x paired zeros, y imbalanced, and just_reversed
            int res = go(pairedZeros, inbalanced, middleZero, false);
            pw.println(res < 0 ? "ALICE" : res > 0 ? "BOB" : "DRAW");
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    boolean hasMiddleZero;
    int[][][][] dp = new int[1000][1000][2][2];
    int go(int pairedZeros, int inbalanced, int middleZero, boolean justReversed) {
        if (pairedZeros == 0 && inbalanced == 0 && middleZero == 0) return 0;
        if (dp[pairedZeros][inbalanced][middleZero][justReversed ? 1 : 0] != INF) return dp[pairedZeros][inbalanced][middleZero][justReversed ? 1 : 0];

        int ans = INF;
        // option 1: if possible, reverse
        if (!justReversed && inbalanced != 0) {
            ans = Math.min(ans, -go(pairedZeros, inbalanced, middleZero, true));
        }

        // option 2: if possible, delete an inbalance
        if (inbalanced > 0) {
            ans = Math.min(ans, 1 - go(pairedZeros, inbalanced-1, middleZero, false));
        }

        // option 3: if possible, delete the middle zero
        if (middleZero > 0) {
            ans = Math.min(ans, 1 - go(pairedZeros, inbalanced, 0, false));
        }

        // option 4: if possible, deleted a pairedZero
        if (pairedZeros > 0) {
            ans = Math.min(ans, 1 - go(pairedZeros-1, inbalanced+1, middleZero, false));
        }

        dp[pairedZeros][inbalanced][middleZero][justReversed ? 1 : 0] = ans;
        return ans;
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