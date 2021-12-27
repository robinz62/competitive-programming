import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static int MOD = 998244353;

    // After writing solution, quick scan for:
    //   array out of bounds
    //   special cases e.g. n=1?
    //   npe, particularly in maps
    //
    // Big numbers arithmetic bugs:
    //   int overflow
    //   sorting, or taking max, after MOD
    void solve() throws IOException {
        int[] nk = ril(2);
        int n = nk[0];
        int k = nk[1];
        char[] s = rs();

        if (k == 0) {
            pw.println("1");
            return;
        }
        int count = 0;
        for (char c : s) if (c == '1') count++;
        if (k > count) {
            pw.println("1");
            return;
        }

        long[][] choose = new long[n+1][n+1];
        choose[0][0] = 1;
        for (int i = 1; i <= n; i++) {
            choose[i][0] = 1;
            for (int j = 1; j <= i; j++) {
                choose[i][j] = (choose[i-1][j] + choose[i-1][j-1]) % MOD;
            }
        }

        // dp[i] is the number of unique strings that can be generated from s[0..i].
        // If s[i] == '0', then for every shuffle of s[0..i-1], we could have instead
        // placed one of the k 1s we had at i.
        //  Place one of the 1s at i. 
        //
        //
        //
        // ^^^ this assumes we already had k 1s.
        //
        // If s[i] == '1', the string is
        //   ...1...0100010110011 (k 1s), not including leftmost.
        // Well if we don't have k 1s yet, the answer is 1.
        // If we just got to k 1s, the answer is (i choose k).
        // Else...
        //   We leave the leftmost 1 where it is (since we can't move it).
        //   We only get a new string if the newly found 1 movces (otherwise the string has already
        //   been accounted for).
        //   Say our window size is X. Place a 0 in this rightmost position. Then X-1 choose k.
        long[] dp = new long[n];
        dp[0] = 1;
        int countsofar = s[0] == '1' ? 1 : 0;
        for (int i = 1; i < n; i++) {
            if (s[i] == '0') {
                if (countsofar < k) dp[i] = 1;
                else {
                    int here = 0;
                    int j = i;
                    while (j >= 0 && here <= k) {
                        if (s[j] == '1') {
                            if (here == k) break;
                            here++;
                            j--;
                        } else {
                            j--;
                        }
                    }
                    j++;
                    int X = i - j + 1;
                    dp[i] = dp[i-1];
                    dp[i] += choose[X-1][k-1];
                    dp[i] %= MOD;
                }
            } else if (s[i] == '1') {
                countsofar++;
                if (countsofar < k) dp[i] = 1;
                else if (countsofar == k) {
                    dp[i] = choose[i+1][k];
                } else {
                    int here = 0;
                    int j = i;
                    while (j >= 0 && here <= k) {
                        if (s[j] == '1') {
                            if (here == k) break;
                            here++;
                            j--;
                        } else {
                            j--;
                        }
                    }
                    j++;
                    int X = i - j + 1;
                    dp[i] = dp[i-1];
                    dp[i] += choose[X-1][k];
                    dp[i] %= MOD;
                }
            }
        }
        // pw.println(Arrays.toString(dp));
        pw.println(dp[n-1]);
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