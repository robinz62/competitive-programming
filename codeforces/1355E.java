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

    long N;
    long A;
    long R;
    long M;
    void solve() throws IOException {
        int[] NARM = ril();
        N = NARM[0];
        A = NARM[1];
        R = NARM[2];
        M = NARM[3];
        int[] h = ril();
        int maxh = 0;
        for (int hi : h) maxh = Math.max(maxh, hi);

        long l = 0;
        long r = maxh;
        while (l <= r) {
            long m = l + (r - l) / 2;
            long costl = m-1 >= 0 ? cost(h, m-1) : Long.MAX_VALUE;
            long costm = cost(h, m);
            long costr = m+1 <= maxh ? cost(h, m+1) : Long.MAX_VALUE;
            if (costm <= costl && costm <= costr) {
                pw.println(costm);
                break;
            } else if (costl < costm || costm < costr) {
                r = m-1;
            } else {
                l = m+1;
            }
        }
    }

    long cost(int[] h, long tgt) {
        long excess = 0;
        long deficit = 0;
        for (int hi : h) {
            if (hi > tgt) excess += hi - tgt;
            else if (hi < tgt) deficit += tgt - hi;
        }
        if (A + R <= M) return R * excess + A * deficit;
        long moves = Math.min(excess, deficit);
        return M * moves + Math.max(excess - moves, 0) * R + Math.max(deficit - moves, 0) * A;
    }
}