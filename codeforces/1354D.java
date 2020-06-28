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

    int readInt() throws IOException {
        int x = 0;
        int c = 0;
        while (!(c == '-' || c >= '0' && c <= '9')) c = br.read();
        boolean neg = false;
        if (c == '-') {
            neg = true;
            c = br.read();
        }
        while (c >= '0' && c <= '9') {
            x *= 10;
            x += c - '0';
            c = br.read();
        }
        return x * (neg ? -1 : 1);
    }

    void solve() throws IOException {
        int[] nq = ril();
        int n = nq[0];
        int q = nq[1];
        int[] a = new int[n];
        int[] k = new int[q];
        for (int i = 0; i < n; i++) a[i] = readInt();
        for (int i = 0; i < q; i++) k[i] = readInt();
        int l = 1;
        int r = n;
        int min = Integer.MAX_VALUE;
        int m = l + (r - l) / 2;
        int lte = 0;
        while (l <= r) {
            m = l + (r - l) / 2;
            lte = 0;
            for (int i = 0; i < n; i++) {
                if (a[i] <= m) lte++;
            }
            for (int i = 0; i < q; i++) {
                if (k[i] > 0) {
                    if (k[i] <= m) lte++;
                } else {
                    if (-k[i] <= lte) lte--;
                }
            }
            if (lte > 0) {
                min = Math.min(min, m);
                r = m - 1;
            } else {
                l = m + 1;
            }
        }
        pw.println(min == Integer.MAX_VALUE ? 0 : min);
    }
}