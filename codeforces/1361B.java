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

    int readInt() throws IOException {
        return Integer.parseInt(br.readLine());
    }

    long readLong() throws IOException {
        return Long.parseLong(br.readLine());
    }

    int[] readIntLine() throws IOException {
        String[] tokens = br.readLine().split(" ");
        int[] A = new int[tokens.length];
        for (int i = 0; i < A.length; i++)
            A[i] = Integer.parseInt(tokens[i]);
        return A;
    }

    long[] readLongLine() throws IOException {
        String[] tokens = br.readLine().split(" ");
        long[] A = new long[tokens.length];
        for (int i = 0; i < A.length; i++)
            A[i] = Long.parseLong(tokens[i]);
        return A;
    }

    void solve() throws IOException {
        long MOD = 1_000_000_007;
        int t = readInt();
        for (int ti = 0; ti < t; ti++) {
            int[] np = readIntLine();
            int n = np[0];
            int p = np[1];
            int[] k = readIntLine();  // length n, ki <= 10^6
            if (p == 1) {
                pw.println(n % 2);
                continue;
            }

            Arrays.sort(k);
            int lbit = k[k.length-1];
            TreeMap<Integer, Integer> r = new TreeMap<>();
            for (int i = k.length - 2; i >= 0; i--) {
                int dig = k[i];
                if (lbit != -1) {
                    int x = dig;
                    r.put(x, r.getOrDefault(x, 0) + 1);
                    while (r.get(x) == p) {
                        r.remove(x);
                        x++;
                        r.put(x, r.getOrDefault(x, 0) + 1);
                    }
                    if (r.size() == 1 && r.getOrDefault(lbit, -1) != -1) {
                        lbit = -1;
                        r.clear();
                    }
                } else {
                    lbit = dig;
                }
            }
            long ans = 0;
            if (lbit != -1) ans = modPow(p, lbit, MOD) % MOD;
            for (int x : r.keySet()) {
                ans = (ans + (MOD - modPow(p, x, MOD)) * r.get(x)) % MOD;
            }
            pw.println(ans);
        }
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
}