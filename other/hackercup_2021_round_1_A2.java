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
            int N = ri();
            char[] s = rs();

            // L: F and X
            // R: F and O

            // FFXFXO

            char prevNonF = s[0];
            long[] dp = new long[N];
            dp[0] = 0;
            int fstreak = s[0] == 'F' ? 1 : 0;
            for (int i = 1; i < N; i++) {
                if (s[i] == 'F') {
                    dp[i] = dp[i-1];
                    fstreak++;
                } else {
                    if (prevNonF == 'F' || prevNonF == s[i]) {
                        prevNonF = s[i];
                        dp[i] = dp[i-1];
                    } else {
                        prevNonF = s[i];
                        dp[i] = dp[i-1] + i - fstreak;
                    }
                    fstreak = 0;
                }
            }
            long ans = 0;
            for (long ai : dp) ans = (ans + ai) % MOD;

            pw.println("Case #" + (Ti+1) + ": " + ans);

            // long goodans = ans;
            // long brutans = a1_brute(s);
            // pw.println(goodans);
            // pw.println(brutans);
            // if (goodans != brutans) pw.println("FAIL" + " " + Ti);
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    long a1_brute(char[] s) {
        long ans = 0;
        for (int i = 0; i < s.length; i++) {
            for (int j = i; j < s.length; j++) {
                boolean left = true;
                int ans1 = 0;
                for (int k = i; k <= j; k++) {
                    char c = s[k];
                    if (left) {
                        if (c == 'O') {
                            ans1++;
                            left = !left;
                        }
                    } else {
                        if (c == 'X') {
                            ans1++;
                            left = !left;
                        }
                    }
                }

                left = false;
                int ans2 = 0;
                for (int k = i; k <= j; k++) {
                    char c = s[k];
                    if (left) {
                        if (c == 'O') {
                            ans2++;
                            left = !left;
                        }
                    } else {
                        if (c == 'X') {
                            ans2++;
                            left = !left;
                        }
                    }
                }
                ans += Math.min(ans1, ans2);
                ans %= MOD;
            }
        }
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