import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static int MOD = 998244353;

    // After writing solution, quick scan for:
    //   array out of bounds
    //   special cases e.g. n=1?
    //
    // Big numbers arithmetic bugs:
    //   int overflow
    //   sorting, or taking max, after MOD
    void solve() throws IOException {
        int[] nk = ril(2);
        int n = nk[0];
        int k = nk[1];
        int[][] intervals = new int[n][];
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < n; i++) {
            intervals[i] = ril(2);
            set.add(intervals[i][0]);
            set.add(intervals[i][1]);
        }
        List<Integer> coords = new ArrayList<>(set);
        Collections.sort(coords);
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < coords.size(); i++) {
            map.put(coords.get(i), i);
        }

        int[] starts = new int[coords.size()];
        int[] ends = new int[coords.size()];
        for (int[] e : intervals) {
            starts[map.get(e[0])]++;
            ends[map.get(e[1])]++;
        }

        // compute factorial
        fact = new long[n+1];
        fact[0] = 1;
        for (int i = 1; i <= n; i++) {
            fact[i] = fact[i-1] * i % MOD;
        }

        long ans = 0;
        long active = 0;
        for (int i = 0; i < starts.length; i++) {
            if (starts[i] + active < k || starts[i] == 0) {
                active += starts[i];
                active -= ends[i];
                continue;
            }
            // Editorial: this loop can be replaced with C(starts[i]+active, k) - C(active, k)
            for (int x = 1; x <= starts[i]; x++) {
                ans += choose(starts[i], x) * choose(active, k - x);
                ans %= MOD;
            }
            active += starts[i];
            active -= ends[i];
        }
        pw.println(ans);
    }

    long[] fact;

    long choose(long n, long k) {
        if (k < 0 || n < k) return 0;
        return fact[(int)n] * modInverseFermat(fact[(int)k], MOD) % MOD * modInverseFermat(fact[(int)(n-k)], MOD) % MOD;
    }

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

    Map<Long, Long> cache = new HashMap<>();
    public long modInverseFermat(long a, long m) {
        if (cache.containsKey(a)) return cache.get(a);
        long ans = modPow(a, m - 2, m);
        cache.put(a, ans);
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
}