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
        int T = readInt();
        for (int t = 0; t < T; t++) {
            int[] ngb = readIntLine();
            int n = ngb[0];
            int g = ngb[1];
            int b = ngb[2];
            long required = n % 2 == 0 ? n / 2 : n / 2 + 1;
            long fullCycles = required % g == 0 ? required / g - 1 : required / g;
            long extraGood = required - g * fullCycles;
            long days = fullCycles * (g + b) + extraGood;
            if (days >= n) {
                pw.println(days);
            } else {
                pw.println(n);
            }
        }
    }
}