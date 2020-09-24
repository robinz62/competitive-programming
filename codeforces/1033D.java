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
        int n = ri();
        long[] a = new long[n];
        for (int i = 0; i < n; i++) a[i] = rl();
        Map<Long, Integer> freq = new HashMap<>();
        for (long ai : a) freq.put(ai, freq.getOrDefault(ai, 0) + 1);


        Map<Long, Integer> knownPfs = new HashMap<>();
        List<Integer> unknownPfs = new ArrayList<>();

        for (long ai : freq.keySet()) {
            long ret = cbrt(ai);
            if (ret != -1) {
                knownPfs.put(ret, knownPfs.getOrDefault(ret, 0) + 3 * freq.get(ai));
                continue;
            }


            long ret1 = sqrt(ai);
            if (ret1 == -1) {
                // p * q
                boolean found = false;
                for (long aj : freq.keySet()) {
                    if (ai == aj) continue;
                    long g = gcd(ai, aj);
                    if (g == 1) continue;
                    long p = g;
                    long q = ai / p;
                    knownPfs.put(p, knownPfs.getOrDefault(p, 0) + freq.get(ai));
                    knownPfs.put(q, knownPfs.getOrDefault(q, 0) + freq.get(ai));
                    found = true;
                    break;
                }
                if (!found) {
                    unknownPfs.add(freq.get(ai));
                    unknownPfs.add(freq.get(ai));
                }
            } else {
                long ret2 = sqrt(ret1);
                if (ret2 == -1) {
                    // p^2
                    knownPfs.put(ret1, knownPfs.getOrDefault(ret1, 0) + freq.get(ai) * 2);
                } else {
                    // p^4
                    knownPfs.put(ret2, knownPfs.getOrDefault(ret2, 0) + freq.get(ai) * 4);
                }
            }
        }

        long ans = 1;
        for (long pf : knownPfs.keySet()) {
            long count = knownPfs.get(pf);
            ans = ans * (count+1) % MOD;
        }
        for (int pf : unknownPfs) {
            ans = ans * (pf + 1) % MOD;
        }
        // pw.println(knownPfs);
        // pw.println(unknownPfs);
        pw.println(ans);
    }

    public long sqrt(long x) {
        if (x == 0) return 0;
        long l = 1;
        long r = x;
        while (true) {
            long m = l + (r - l) / 2;
            if (m > x / m) {
                r = m - 1;
            } else if (m <= x / m) {
                if (m + 1 > x / (m + 1)) {
                    if (m * m == x) return m;
                    return -1;
                } else {
                    l = m + 1;
                }
            }
        }
    }

    long cbrt(long x) {
        double xx = x;
        double attempt = (long) Math.cbrt(xx);
        if (attempt * attempt * attempt == xx) return (long) attempt;
        return -1;
    }

    public static long gcd(long a, long b) {
        return a == 0 ? b : gcd(b % a, a);
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