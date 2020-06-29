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
            long n = nk[0];
            long k = nk[1];
            int[] a = ril();
            Map<Long, Long> residue = new HashMap<>();
            for (int ai : a) {
                if (ai % k != 0) residue.put(ai % k, residue.getOrDefault((long) ai % k, 0l) + 1);
            }
            long maxcount = 0;
            long numofmax = 0;
            for (long i : residue.keySet()) {
                long f = residue.get(i);
                if (f > maxcount || f == maxcount && i < numofmax) {
                    maxcount = f;
                    numofmax = i;
                }
            }
            int x = residue.isEmpty() ? 0 : 1;
            pw.println((maxcount-1) * k + (k - numofmax) + x);
        }
    }
}