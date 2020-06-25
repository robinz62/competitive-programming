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

    int ri() throws IOException {
        return Integer.parseInt(br.readLine());
    }

    long rl() throws IOException {
        return Long.parseLong(br.readLine());
    }

    int[] ril() throws IOException {
        String[] tokens = br.readLine().split(" ");
        int[] A = new int[tokens.length];
        for (int i = 0; i < A.length; i++)
            A[i] = Integer.parseInt(tokens[i]);
        return A;
    }

    long[] rll() throws IOException {
        String[] tokens = br.readLine().split(" ");
        long[] A = new long[tokens.length];
        for (int i = 0; i < A.length; i++)
            A[i] = Long.parseLong(tokens[i]);
        return A;
    }

    void solve() throws IOException {
        int t = ri();
        for (int ti = 0; ti < t; ti++) {
            int[] nmab = ril();
            int n = nmab[0];
            int m = nmab[1];
            int a = nmab[2];
            int b = nmab[3];
            int[][] A = new int[n][m];
            if (a * n != b * m) {
                pw.println("NO");
                continue;
            }
            int total = a * n;
            int placed = 0;
            int i = 0;
            int j = 0;
            while (placed < total) {
                while (A[i][j] == 1) j = (j + 1) % m;
                A[i][j] = 1;
                placed++;
                i = (i+1) % n;
                j = (j+1) % m;
            }
            pw.println("YES");
            for (int[] row : A) {
                for (int x : row) {
                    pw.print(x);
                }
                pw.println();
            }
        }
    }
}