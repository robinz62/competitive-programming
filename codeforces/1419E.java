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
        int T = ri();
        for (int Ti = 0; Ti < T; Ti++) {
            int n = ri();
            Map<Integer, Integer> pfs = primeFactorize(n);
            List<Integer> factors = factors(n);

            if (factors.size() == 3 && pfs.size() == 2) {
                Collections.sort(factors);
                for (int f : factors) pw.print(f + " ");
                pw.println();
                pw.println("1");
                continue;
            }

            List<Integer> p = new ArrayList<>(pfs.keySet());
            Collections.sort(p);
            List<Integer> q = new ArrayList<>(p.size());
            for (int pi : p) q.add(pfs.get(pi));

            int p1 = p.get(0);
            int pk = p.get(p.size() - 1);
            int reserved = p1 * pk;

            for (int i = 0; i < p.size(); i++) {
                q.set(i, q.get(i)-1);
                backtrack(p, q, reserved, i, p.get(i));
            }
            pw.println(reserved);
            pw.println("0");
        }
    }

    void backtrack(List<Integer> p, List<Integer> q, int reserved, int i, int curr) {
        if (i == p.size()) {
            if (curr != reserved) pw.print(curr + " ");
            return;
        }
        for (int take = 0, contrib = 1; take <= q.get(i); take++, contrib *= p.get(i)) {
            backtrack(p, q, reserved, i+1, curr * contrib);
        }
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

    public static List<Integer> factors(int n) {
        List<Integer> factors = new ArrayList<>();
        int i = 2;
        for (; i * i < n; i++) {
            if (n % i == 0) {
                factors.add(i);
                factors.add(n / i);
            }
        }
        if (i * i == n) factors.add(i);
        factors.add(n);
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