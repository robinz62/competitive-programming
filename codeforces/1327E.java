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
        long MOD = 998244353l;
        int n = readInt();
        if (n == 1) {
            pw.println("10");
            return;
        }
        if (n == 2) {
            pw.println("180 10");
            return;
        }
        for (int i = 1; i < n; i++) {
            int numPositionsSandwich = n - i + 1 - 2;

            BigInteger x = BigInteger.valueOf(10l);
            x = x.modPow(BigInteger.valueOf(n - i), BigInteger.valueOf(MOD));
            x = x.multiply(BigInteger.valueOf(18)).mod(BigInteger.valueOf(MOD));

            BigInteger y = BigInteger.valueOf(10l);
            y = y.modPow(BigInteger.valueOf(n - i - 1), BigInteger.valueOf(MOD));
            y = y.multiply(BigInteger.valueOf(81 * numPositionsSandwich)).mod(BigInteger.valueOf(MOD));

            long sum = (x.longValue() + y.longValue()) % MOD;
            pw.println(sum);
        }
        pw.println(10);
    }
}