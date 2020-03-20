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
        int[] nk = readIntLine();
        int n = nk[0];
        int k = nk[1];
        int[] p = readIntLine();

        long pvalue = 0;
        for (int i = n; i > n - k; i--) {
            pvalue += i;
        }
        int min = n - k + 1;
        long mod = 998244353l;
        long ans = 1;

        int i = 0;
        while (p[i] < min) i++;
        long count = 1;

        while (i < p.length) {
            if (p[i] >= min) {
                ans = (ans * count) % mod;
                count = 1;
            } else {
                count++;
            }

            i++;
        }

        pw.println(pvalue + " " + ans);
    }
}