import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static int MOD = 1000000007;

    // After writing solution, quick scan for:
    //   array out of bounds
    //   special cases e.g. n=1?
    //
    // Big numbers arithmetic bugs:
    //   int overflow
    //   sorting, or taking max, after MOD
    void solve() throws IOException {
        int n = ri();
        int[] a = ril(n);
        boolean[] isPrime = sieve(70);
        Map<Integer, Integer> pToIdx = new HashMap<>();
        int idx = 0;
        for (int i = 2; i <= 70; i++) {
            if (isPrime[i]) pToIdx.put(i, idx++);
        }
        for (int i = 0; i < n; i++) {
            Map<Integer, Integer> pfs = primeFactorize(a[i]);
            int b = 0;
            for (int p : pfs.keySet()) {
                if (pfs.get(p) % 2 == 1) {
                    b |= (1 << pToIdx.get(p));
                }
            }
            a[i] = b;
        }
        Map<Integer, Integer> freq = new HashMap<>();
        for (int ai : a) freq.put(ai, freq.getOrDefault(ai, 0) + 1);
        List<Integer> unique = new ArrayList<>(freq.keySet());

        long[] p2 = new long[100001];
        p2[0] = 1;
        for (int i = 1; i < p2.length; i++) p2[i] = p2[i-1] * 2 % MOD;

        // now, how many subsets of a such that xor is 0?
        // 500,000 numbers possible
        Map<Integer, Long> count = new HashMap<>();
        count.put(0, 1l);
        for (int u : unique) {
            // take even number of me or take odd number of me
            int frequ = freq.get(u);
            Map<Integer, Long> next = new HashMap<>();
            for (int f : count.keySet()) {
                // even number of me, result is just f
                long addWays = (long) count.get(f) * p2[frequ-1] % MOD;
                next.put(f, (next.getOrDefault(f, 0l) + addWays) % MOD);
                
                // odd number of me
                int res = u ^ f;
                addWays = (long) (count.get(f) * p2[frequ-1]) % MOD;
                next.put(res, (next.getOrDefault(res, 0l) + addWays) % MOD);
            }
            count = next;
        }
        pw.println(count.get(0)-1);
    }

    public static boolean[] sieve(int n) {
        List<Integer> primes = new ArrayList<>();
        boolean[] prime = new boolean[n + 1];
        Arrays.fill(prime, true);
        for (int i = 2; i <= n; i++) {
            if (prime[i]) {
                primes.add(i);
            }
            for (int j = 0; j < primes.size() && i * primes.get(j) <= n; j++) {
                prime[i * primes.get(j)] = false;
                if (i % primes.get(j) == 0) break;
            }
        }
        return prime;
    }

    public static Map<Integer, Integer> primeFactorize(int n) {
        Map<Integer, Integer> factors = new HashMap<>();
        while (n % 2 == 0) {
            factors.put(2, factors.getOrDefault(2, 0) + 1);
            n /= 2;
        }
        for (int i = 3; i * i <= n; i += 2) {
            while (n % i == 0) {
                factors.put(i, factors.getOrDefault(i, 0) + 1);
                n /= i;
            }
        }
        if (n > 2) {
            factors.put(n, factors.getOrDefault(n, 0) + 1);
        }
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
        pw.printf("%f", d);
    }
}