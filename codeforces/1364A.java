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
            int[] nx = readIntLine();
            int n = nx[0];
            int x = nx[1];
            int[] a = readIntLine();
            int idxNotZeroMod = -1;
            int sum = 0;
            int best = -1;
            for (int i = 0; i < a.length; i++) {
                sum += a[i];
                int mod = sum % x;
                if (idxNotZeroMod == -1 && mod != 0) idxNotZeroMod = i;
                if (mod == 0) {
                    if (idxNotZeroMod != -1) {
                        best = Math.max(best, i - idxNotZeroMod);
                    }
                } else {
                    best = Math.max(best, i+1);
                }
            }
            pw.println(best);
        }
    }
}