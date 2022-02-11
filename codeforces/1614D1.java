import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static int MOD = 1000000007;

    int MAX = 5000000;

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
        int n = ri();
        int[] a = ril(n);
        Map<Integer, Integer> vals = new HashMap<>();
        for (int ai : a) vals.put(ai, vals.getOrDefault(ai, 0) + 1);

        int[] counts = new int[MAX+1];
        for (var e : vals.entrySet()) {
            int val = e.getKey();
            int f = e.getValue();
            for (int factor : factors(val)) {
                counts[factor] += f;
            }
        }

        int[] sp = smallestPrime(MAX+1);

        // enough time to prime factorize everything
        // If I can somehow fix the path I take down to 1...
        
        // dp[i] is best score where i'm at i. look at my factors, going down by 1 prime

        long[] dp = new long[MAX+1];
        dp[1] = n;
        long ans = 0;
        for (int i = 2; i <= MAX; i++) {
            for (int pf : primeFactors(i, sp)) {
                int down = i / pf;
                int atme = counts[i];
                dp[i] = Math.max(dp[i], dp[down] + (long) atme * (i - down));
            }
            ans = Math.max(ans, dp[i]);
        }
        pw.println(ans);
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    public static int gcd(int a, int b) {
        return a == 0 ? b : gcd(b % a, a);
    }

    public static List<Integer> primeFactors(int n, int[] sp) {
        List<Integer> pfs = new ArrayList<>();
        while (n != 1) {
            pfs.add(sp[n]);
            int me = sp[n];
            while (n != 1 && sp[n] == me) n /= me;
        }
        return pfs;
    }

    public static int[] smallestPrime(int n) {
        int[] sp = new int[n + 1];
        for (int i = 0; i <= n; i++)
            sp[i] = i;
        for (int i = 4; i <= n; i += 2)
            sp[i] = 2;
        for (int i = 3; i * i <= n; i += 2) {
            if (sp[i] == i) {
                for (int j = i * i; j <= n; j += i) {
                    if (sp[j] == j)
                        sp[j] = i;
                }
            }
        }
        return sp;
    }

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

    public static List<Integer> factors(int n) {
        List<Integer> factors = new ArrayList<>();
        int i = 1;
        for (; i * i < n; i++) {
            if (n % i == 0) {
                factors.add(i);
                factors.add(n / i);
            }
        }
        if (i * i == n) factors.add(i);
        return factors;
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