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
    //
    // Interactive problems: don't forget to flush between test cases
    void solve() throws IOException {
        int[] np = ril(2);
        int n = np[0];
        int p = np[1];  // interesting: p <= 2 * 10^5
        int[] a = ril(n);

        sort(a);

        // It would be good to know the "minimal set" i.e. remove redundancies in initial
        // input, to prevent overcounting.

        // We can make original number
        // with any number of trailing 1s
        // and any number of 0s wherever, as long as they are consecutive 0s.

        Set<Integer> bases = new HashSet<>();
        for (int ai : a) {
            int orig = ai;
            // Try chopping all suffixes of ai that have consecutive even 0s.
            // If the prefix is present in base, kill it.
            if (bases.contains(ai)) continue;
            if (p < 60 && orig >= (1l << p)) continue;

            boolean ok = true;
            while (ai > 0) {
                if (ai % 2 == 1) {
                    ai /= 2;
                    if (bases.contains(ai)) {
                        ok = false;
                        break;
                    }
                    continue;
                } else {
                    // need 2 adjacent.
                    ai /= 2;
                    if (ai % 2 == 0) {
                        ai /= 2;
                    } else {
                        break;
                    }
                    if (bases.contains(ai)) {
                        ok = false;
                        break;
                    }
                }
            }

            if (ok) bases.add(orig);
        }

        long[] dp = new long[p+10];
        dp[0] = 1;
        dp[1] = 1;
        dp[2] = 2;
        for (int i = 3; i < dp.length; i++) {
            dp[i] = dp[i-1] + dp[i-2];
            if (dp[i] >= MOD) dp[i] -= MOD;
        }
        for (int i = 1; i < dp.length; i++) {
            dp[i] = dp[i-1] + dp[i];
            if (dp[i] >= MOD) dp[i] -= MOD;
        }

        // pw.println(bases);

        long ans = 0;
        for (int base : bases) {
            int len = Integer.numberOfTrailingZeros(Integer.highestOneBit(base)) + 1;
            
            int upto = p - len;

            // pw.println(base + " " + len + " " + p);
            // pw.flush();

            // Fix the length which is 0 <= i <= upto
            // How many ways to add 1s?
            ans = ans + dp[upto];
            if (ans >= MOD) ans -= MOD;
        }
        pw.println(ans);
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