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
        int t = readInt();
        for (int ti = 0; ti < t; ti++) {
            int[] nm = readIntLine();
            int n = nm[0];
            int m = nm[1];
            int x = n / (m + 1);
            long zeros = x - 1;
            long excess = n - m * x - zeros;
            long numWith = m + 1;
            long numWithPlusOne = excess;
            numWith -= excess;
            long total = (long) n * (n - 1) / 2 + n;
            total -= (long) numWith * (zeros * (zeros - 1) / 2 + zeros);
            total -= (long) (numWithPlusOne) * ((zeros + 1) * zeros / 2 + (zeros + 1));

            pw.println(total);
        }
    }
}