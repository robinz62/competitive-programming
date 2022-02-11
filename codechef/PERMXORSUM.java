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
    //   if (x : long) and (y : int), [y = x] does not compile, but [y += x] does
    //   sorting, or taking max, after MOD
    void solve() throws IOException {
        // long[] dp = new long[1000001];
        // dp[0] = dp[1] = 0;
        // dp[2] = 6;
        // for (int i = 3; i < dp.length; i++) {
        //     int flipped = ~i;
        //     int mask = (Integer.highestOneBit(i) << 1) - 1;
        //     flipped = flipped & mask;
        //     if (flipped == 0) {
        //         dp[i] = dp[i-1];
        //     } else {
        //         long count = i - flipped + 1;
        //         dp[i] = count * mask + dp[flipped-1];
        //     }
        // }

        int T = ri();
        for (int Ti = 0; Ti < T; Ti++) {
            int n = ri();

            // pw.println(dp[n]);
            pw.println(dp(n));
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    Map<Integer, Long> memo = new HashMap<>();
    long dp(int i) {
        if (i == 0 || i == 1) return 0;
        if (i == 2) return 6;
        if (memo.containsKey(i)) return memo.get(i);
        int flipped = ~i;
        int mask = (Integer.highestOneBit(i) << 1) - 1;
        flipped = flipped & mask;
        if (flipped == 0) {
            long ans = dp(i-1);
            memo.put(i, ans);
            return ans;
        } else {
            long count = i - flipped + 1;
            long ans = count * mask + dp(flipped-1);
            memo.put(i, ans);
            return ans;
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
        int c = br.read();
        int x = 0;
        while (c >= '0' && c <= '9') {
            x = x * 10 + c - '0';
            c = br.read();
        }
        return ril(x);
    }

    long[] rkll() throws IOException {
        int c = br.read();
        int x = 0;
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