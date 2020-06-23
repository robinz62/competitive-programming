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
            int[] nk = ril();
            int n = nk[0];
            int k = nk[1];
            int[] a = ril();
            int[] w = ril();
            Arrays.sort(a);
            Arrays.sort(w);
            long ans = 0;
            int idxA = n-1;
            for (int i = 0; i < k; i++) {
                ans += a[idxA];
                if (w[i] == 1) ans += a[idxA];
                idxA--;
                w[i]--;
            }
            for (int wi : w) {
                if (wi == 0) continue;
                ans += a[idxA - wi + 1];
                idxA -= wi;
            }
            pw.println(ans);
        }
    }
}