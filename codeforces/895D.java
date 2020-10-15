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
        char[] a = rs();
        char[] b = rs();
        int n = a.length;
        int[] freq = new int[26];
        for (char c : a) freq[c-'a']++;

        fact = new long[n+1];
        invFact = new long[n+1];
        inv = new long[n+1];
        fact[0] = 1;
        for (int i = 1; i <= n; i++) fact[i] = fact[i-1] * i % MOD;
        for (int i = 0; i <= n; i++) invFact[i] = modInverseFermat(fact[i], MOD);
        for (int i = 1; i <= n; i++) inv[i] = modInverseFermat(i, MOD);

        int[] freqcpy = new int[26];
        System.arraycopy(freq, 0, freqcpy, 0, 26);
        long ans = helper(n, freq, b) - helper(n, freqcpy, a) + MOD - 1;
        ans %= MOD;
        pw.println(ans);
    }

    long[] fact;
    long[] invFact;
    long[] inv;
    long helper(int n, int[] freq, char[] tgt) {
        long ans = 0;
        // Have i be the index where the first mismatch occurs
        long den = 1;
        for (int i : freq) if (i > 0) den = (den * invFact[i]) % MOD;
        for (int i = 0; i < n; i++) {
            int c = tgt[i] - 'a';
            long num = fact[n - i - 1];
            for (int d = 0; d < c; d++) {
                if (freq[d] == 0) continue;
                // set d as the current letter, then count permutations
                // of letters after (= n!/k1!/k2!/...)
                den = den * freq[d] % MOD;
                ans = (ans + num * den) % MOD;
                den = den * inv[freq[d]] % MOD;
            }
            if (freq[c] == 0) break;
            den = den * freq[c] % MOD;
            freq[c]--;
            
        }
        return ans;
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