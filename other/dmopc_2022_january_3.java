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
        int T = ri();
        boolean[] prime = sieve(100000);

        List<Integer> primes = new ArrayList<>();
        for (int i = 2; i <= 100000; i++) if (prime[i]) primes.add(i);

        boolean[] dp = new boolean[100001];
        dp[1] = dp[2] = false;
        dp[3] = true;
        for (int i = 4; i <= 100000; i++) {
            // Do any of my prime jumps to the left result in a losing state?
            boolean win = false;
            for (int p : primes) {
                if (i - p < 1) break;
                if (!dp[i - p]) {
                    win = true;
                    break;
                }
            }
            dp[i] = win;
        }

        for (int Ti = 0; Ti < T; Ti++) {
            int n = ri();
            if (n == 1) {
                pw.println("-1");
                continue;
            }
            if (n == 2) {
                pw.println("-1");
                continue;
            }

            if (dp[n]) {
                pw.print("1");
                for (int i = 2; i <= n; i++) pw.print(" " + i);
                pw.println();
            } else {
                if (dp[n-1]) {
                    pw.print("1");
                    for (int i = 2; i <= n-2; i++) pw.print(" " + i);
                    pw.print(" " + n);
                    pw.print(" " + (n-1));
                    pw.println();
                } else {
                    pw.print("1");
                    for (int i = 2; i <= n-3; i++) pw.print(" " + i);
                    pw.print(" " + n + " " + (n-2) + " " + (n-1));
                    pw.println();
                }
            }
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    public static boolean[] sieve(int n) {
        List<Integer> primes = new ArrayList<>();
        boolean[] prime = new boolean[n + 1];
        Arrays.fill(prime, true);
        prime[0] = prime[1] = false;
        for (int i = 2; i <= n; i++) {
            if (prime[i]) primes.add(i);
            for (int j = 0; j < primes.size() && i * primes.get(j) <= n; j++) {
                prime[i * primes.get(j)] = false;
                if (i % primes.get(j) == 0) break;
            }
        }
        return prime;
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