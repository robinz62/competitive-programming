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
            int n = ri();  // 10^9
            char[] num = Integer.toString(n).toCharArray();

            // dp[i][d][0/1] is number of ways to get the digit d in the ith position with/without a carry
            int k = num.length;

            // careful counting duplicates...
            int[][] dp = new int[10][2];
            dp[0][0] = 1;
            dp[1][0] = 2;
            dp[2][0] = 3;
            dp[3][0] = 4;
            dp[4][0] = 5;
            dp[5][0] = 6;
            dp[6][0] = 7;
            dp[7][0] = 8;
            dp[8][0] = 9;
            dp[9][0] = 10;
            dp[0][1] = 9;
            dp[1][1] = 8;
            dp[2][1] = 7;
            dp[3][1] = 6;
            dp[4][1] = 5;
            dp[5][1] = 4;
            dp[6][1] = 3;
            dp[7][1] = 2;
            dp[8][1] = 1;
            dp[9][1] = 0;

            long ans = 0;
            for (int carrymask = 0; carrymask < (1 << k); carrymask++) {
                if ((carrymask & (1 << (k-1))) > 0) continue;
                if ((carrymask & (1 << (k-2))) > 0) continue;
                long curr = 1;
                for (int i = k-1; i >= 0; i--) {
                    boolean carryHere = (carrymask & (1 << i)) > 0;
                    boolean generatesCarry = i-2 < k && (carrymask & (1 << (i-2))) > 0;
                    int d = num[i] - '0';
                    if (carryHere) d--;
                    if (generatesCarry) d += 10;
                    if (d < 0) {
                        curr = 0;
                        break;
                    }

                    int dighere = d % 10;
                    int arg = d / 10;
                    curr *= dp[dighere][arg];
                }
                ans += curr;
            }
            ans -= 2;  // can't have either number be 0.
            pw.println(ans);
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