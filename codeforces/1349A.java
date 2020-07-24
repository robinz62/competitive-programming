import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pw = new PrintWriter(System.out);
    static int MOD = 1000000007;

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
        return Integer.parseInt(br.readLine());
    }

    long rl() throws IOException {
        return Long.parseLong(br.readLine());
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

    char[] rs() throws IOException {
        return br.readLine().toCharArray();
    }

    void solve() throws IOException {
        int n = ri();
        int[] a = ril(n);
        int[] sp = smallestPrime(200001);
        Map<Integer, List<Integer>> ps = new HashMap<>();
        for (int ai : a) {
            Map<Integer, Integer> f = primeFactorize(ai, sp);
            for (int p : f.keySet()) {
                if (!ps.containsKey(p)) ps.put(p, new ArrayList<>());
                ps.get(p).add(f.get(p));
            }
        }
        long ans = 1;
        for (int p : ps.keySet()) {
            List<Integer> l = ps.get(p);
            Collections.sort(l);
            int idx = 1 - (n - l.size());
            if (idx < 0) continue;
            for (int i = 0; i < l.get(idx); i++) ans *= p;
        }
        pw.println(ans);
    }

    public static Map<Integer, Integer> primeFactorize(int n, int[] sp) {
        Map<Integer, Integer> factors = new HashMap<>();
        while (n != 1) {
            factors.put(sp[n], factors.getOrDefault(sp[n], 0) + 1);
            n /= sp[n];
        }
        return factors;
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
}