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
            int[] inputs = readIntLine();
            int a = inputs[0];
            int b = inputs[1];
            if (a > b) {
                int temp = a;
                a = b;
                b = temp;
            }
            int c = inputs[2];
            int r = inputs[3];
            int dist = b - a;
            if (c < a) {
                int covered = Math.max(Math.min(c + r - a, dist), 0);
                pw.println(dist - covered);
            } else if (c > b) {
                int covered = Math.max(Math.min(b - (c - r), dist), 0);
                pw.println(dist - covered);
            } else {
                int covered = Math.min(r, c - a) + Math.min(r, b - c);
                pw.println(dist - covered);
            }
        }
    }
}