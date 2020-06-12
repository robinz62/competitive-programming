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
        int n = readInt();
        int[] a = readIntLine();
        int[] X = new int[n];
        int[] Y = new int[n];
        int[] sp = smallestPrime(10_000_000);
        for (int i = 0; i < n; i++) {
            Map<Integer, Integer> factors = primeFactorize(a[i], sp);
            if (factors.size() == 1) {
                X[i] = Y[i] = -1;
            } else {
                Y[i] = 1;
                for (int f : factors.keySet()) {
                    if (X[i] == 0) X[i] = f;
                    else Y[i] *= f;
                }
            }
        }
        for (int x : X) pw.print(x + " ");
        pw.println();
        for (int y : Y) pw.print(y + " ");
        pw.println();
    }

    public Map<Integer, Integer> primeFactorize(int n, int[] sp) {
        Map<Integer, Integer> factors = new HashMap<>();
        while (n != 1) {
            factors.put(sp[n], factors.getOrDefault(sp[n], 0) + 1);
            n /= sp[n];
        }
        return factors;
    }

    public int[] smallestPrime(int n) {
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