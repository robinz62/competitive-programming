import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pw = new PrintWriter(System.out);
    static int MOD = 998244353;

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
        int[] nm = readIntLine();
        int n = nm[0];
        int m = nm[1];
        int[] a = readIntLine();
        int[] b = readIntLine();
        Map<Integer, Integer> idxLast = new HashMap<>();
        for (int i = 0; i < n; i++) idxLast.put(a[i], i);
        int[] prevSmaller = new int[n];
        prevSmaller[0] = -1;
        for (int i = 1; i < n; i++) {
            int j = i-1;
            while (j != -1 && a[j] >= a[i]) j = prevSmaller[j];
            prevSmaller[i] = j;
        }

        int[] nextSmaller = new int[n];
        nextSmaller[n-1] = n;
        for (int i = n-2; i >= 0; i--) {
            int j = i+1;
            while (j != n && a[j] >= a[i]) j = nextSmaller[j];
            nextSmaller[i] = j;
        }

        for (int i = 0; i < m; i++) {
            if (!idxLast.containsKey(b[i])) {
                pw.println("0");
                return;
            }
            if (nextSmaller[idxLast.get(b[i])] != n) {
                pw.println("0");
                return;
            }
        }

        if (prevSmaller[idxLast.get(b[0])] != -1) {
            pw.println("0");
            return;
        }

        for (int i = idxLast.get(b[m-1]) + 1; i < n; i++) {
            if (a[i] < b[m-1]) {
                pw.println("0");
                return;
            }
        }

        long ans = 1;
        for (int i = 0; i < m - 1; i++) {
            int ia = idxLast.get(b[i]);
            int ib = idxLast.get(b[i+1]);
            if (ia > ib) {
                pw.println("0");
                return;
            }
            int l = prevSmaller[ib];
            if (l <= ia) l = ia;
            int r = ib - 1;
            ans *= (r - l + 1);
            ans %= MOD;
        }
        pw.println(ans);
    }
}