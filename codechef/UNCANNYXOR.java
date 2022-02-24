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
    //   if (x : long) and (y : int), [y = x] does not compile, but [y += x] does
    //   sorting, or taking max, after MOD
    //
    // Interactive problems: don't forget to flush between test cases
    void solve() throws IOException {
        int T = ri();
        for (int Ti = 0; Ti < T; Ti++) {
            int n = ri();  // huge

            // Only need to solve up to first time it's undefined.
            // f basically counts the number of times the operation is applied until we get 0.
            
            // if i is a palindrome, then the f(i) = 1
            // answer is always 1, 2, or 3.

            // Alternate 2 and 3 except palindromes which sometimes take the place of 1.

            long ans = 0;
            long half = modPow(2, n-1, MOD);
            ans = (ans + half * 2l % MOD + half * 3l % MOD) % MOD;
            ans = (ans + (MOD - 2)) % MOD;  // subtract 2 for 0

            // how many palindromes with up to n bits?
            long count = 0;
            if (n % 2 == 0) {
                count = modPow(2, n / 2, MOD);
                count = (count + MOD - 1) % MOD;
                count = count * 2l % MOD;
            } else {
                count = modPow(2, (n + 1) / 2, MOD);
                long sub = modPow(2, (n + 1) / 2 - 1, MOD);
                count = (count + MOD - 1) % MOD;
                count = count * 2l % MOD;
                count = (count + MOD - sub) % MOD;
            }

            long sub = 2l * count % MOD;
            ans = (ans + (MOD - sub)) % MOD;
            pw.println(ans);
        }

        // for (int i = 1; i <= 1000000; i++) {
        //     pw.println(Integer.toString(i, 2) + " " + f(i));
        // }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    public static long modPow(long base, long exponent, long m) {
        long ans = 1;
        base = base % m;
        while (exponent > 0) {
            if ((exponent & 1) == 1) ans = (ans * base) % m;
            exponent >>= 1;
            base = (base * base) % m;
        } 
        return ans;
    }

    int f(int i) {
        if (i == 0) return 0;
        return f(i ^ reverse(i)) + 1;
    }

    int reverse(int x) {
        return Integer.parseInt(new StringBuilder(Integer.toString(x, 2)).reverse().toString(), 2);
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