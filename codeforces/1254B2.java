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
        long[] a = rll(n);
        long total = 0;
        for (long ai : a) total += ai;
        if (total == 1) {
            pw.println("-1");
            return;
        }
        List<Long> pfs = new ArrayList<>(primeFactorize(total).keySet());
        Collections.sort(pfs);

        long ans = Long.MAX_VALUE;
        for (int k = 0; k < pfs.size(); k++) {
            long x = pfs.get(k);
            long trial = 0;

            // Try greedy? Yolo.
            int extrasource = -1;
            long extra = 0;
            for (int i = 0; i < n; i++) {
                if (a[i] == 0) continue;
                if (a[i] + extra >= 0) {
                    long here = a[i] + extra;
                    if (extra > 0)  trial += extra * (i - extrasource);
                    else if (extra < 0) trial += -extra * (i - extrasource);
                    long y = here % x;  // extra here
                    long z = x - y;     // complement
                    if (y <= z) {
                        extra = y;
                        extrasource = i;
                    } else {
                        extra = -z;
                        extrasource = i;
                    }
                } else {
                    long take = a[i];
                    trial += take * (i - extrasource);
                    extra += a[i];
                }
            }
            ans = Math.min(ans, trial);
        }
        pw.println(ans);
    }

    public static Map<Long, Integer> primeFactorize(long n) {
        Map<Long, Integer> factors = new HashMap<>();
        while (n % 2 == 0) {
            factors.put(2l, factors.getOrDefault(2, 0) + 1);
            n /= 2;
        }
        for (long i = 3; i * i <= n; i += 2) {
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
}