import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    // static int MOD = 1000000007;
    // static int MOD2 = 1000000009;

    // After writing solution, quick scan for:
    //   array out of bounds
    //   special cases e.g. n=1?
    //   npe, particularly in maps
    //
    // Big numbers arithmetic bugs:
    //   int overflow
    //   sorting, or taking max, after MOD
    void solve() throws IOException {
        int q = ri();
        List<Long> primes = Arrays.asList(
            999999751l, 999999757l, 999999761l, 999999797l, 999999883l,
            999999893l, 999999929l, 999999937l, 1000000007l, 1000000009l,
            1000000021l, 1000000033l, 1000000087l, 1000000093l, 1000000097l
        );
        Collections.shuffle(primes);
        long MOD = primes.get(0);
        long MOD2 = primes.get(1);
        for (int qi = 0; qi < q; qi++) {
            int[] nk = ril(2);
            int n = nk[0];
            int k = nk[1];
            char[] s = rs();

            int[] t = new int[n];
            for (int i = 0; i < n; i++) t[i] = (s[i] == '0' ? '1' : '0') - '0'+1;
            long p = 3;
            long invp = modInverseFermat(p, MOD);
            long invp2 = modInverseFermat(p, MOD2);
            long val = 0;
            long val2 = 0;
            long ppow = 1;
            long ppow2 = 1;
            for (int i = 0; i < k; i++) {
                val = (val + ppow * t[i]) % MOD;
                ppow = ppow * p % MOD;
                val2 = (val2 + ppow2 * t[i]) % MOD2;
                ppow2 = ppow2 * p % MOD2;
            }
            Set<Long> banned = new HashSet<>();
            banned.add(val * 1000000000 + val2);
            ppow = ppow * invp % MOD;  // undo last mult for new strings
            ppow2 = ppow2 * invp2 % MOD2;
            for (int i = k; i < n; i++) {
                // delete first char
                val = (val - t[i-k] + MOD) % MOD;
                val = val * invp % MOD;
                val2 = (val2 - t[i-k] + MOD2) % MOD2;
                val2 = val2 * invp2 % MOD2;

                // add new char
                val = (val + ppow * t[i]) % MOD;
                val2 = (val2 + ppow2 * t[i]) % MOD2;
                banned.add(val * 1000000000 + val2);
            }

            long[] pows = new long[k];
            long[] pows2 = new long[k];
            pows[0] = 1;
            pows2[0] = 1;
            for (int i = 1; i < k; i++) pows[i] = pows[i-1] * p % MOD;
            for (int i = 1; i < k; i++) pows2[i] = pows2[i-1] * p % MOD2;

            // Try all substrings of size k until find one not banned
            // How to recompute hash quickly?
            int[] curr = new int[k];
            val = 0;
            val2 = 0;
            for (int i = 0; i < k; i++) {
                val = (val + pows[i]) % MOD;
                val2 = (val2 + pows2[i]) % MOD2;
            }
            if (!banned.contains(val * 1000000000 + val2)) {
                StringBuilder ans = new StringBuilder();
                for (int i : curr) ans.append(i);
                pw.println("YES");
                pw.println(ans);
                continue;
            }
            boolean done = false;

            int upper = k <= 30 ? (1 << k) - 1 : 1 << 30;
            for (int i = 0; i < upper; i++) {
                int idx = k-1;
                while (curr[idx] == 1) {
                    curr[idx] = 0;
                    val = (val - pows[idx] * 2 + MOD + MOD) % MOD;
                    val = (val + pows[idx]) % MOD;
                    val2 = (val2 - pows2[idx] * 2 + MOD2 + MOD2) % MOD2;
                    val2 = (val2 + pows2[idx]) % MOD2;
                    idx--;
                }
                curr[idx] = 1;
                val = (val - pows[idx] + MOD) % MOD;
                val = (val + pows[idx] * 2) % MOD;
                val2 = (val2 - pows2[idx] + MOD2) % MOD2;
                val2 = (val2 + pows2[idx] * 2) % MOD2;
                if (!banned.contains(val * 1000000000 + val2)) {
                    StringBuilder ans = new StringBuilder();
                    for (int x : curr) ans.append(x);
                    pw.println("YES");
                    pw.println(ans);
                    done = true;
                    break;
                }
            }
            if (!done) pw.println("NO");
        }
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

    public static long modInverseFermat(long a, long m) {
        return modPow(a, m - 2, m);
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

    void shuffle(long[] A) {
        Random r = new Random();
        for (int i = A.length-1; i > 0; i--) {
            int j = r.nextInt(i+1);
            long temp = A[i];
            A[i] = A[j];
            A[j] = temp;
        }
    }

    void printDouble(double d) {
        pw.printf("%.16f", d);
    }
}