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
        int n = readInt();
        int[] a = readIntLine();  // sorted
        int[] b = new int[n];

        TreeSet<Integer> allowable = new TreeSet<>();
        Map<Integer, Integer> banCount = new HashMap<>();
        for (int i = 0; i <= n; i++) allowable.add(i);
        for (int ai : a) {
            banCount.put(ai, banCount.getOrDefault(ai, 0) + 1);
            allowable.remove(ai);
        }
        // Choose the smallest allowable number that needs fulfillment
        for (int i = 0; i < n; i++) {
            if (allowable.isEmpty()) {
                b[i] = n + 1;
            } else {
                b[i] = allowable.first();
                allowable.remove(b[i]);
            }
            banCount.put(a[i], banCount.get(a[i]) - 1);
            if (banCount.get(a[i]) == 0) {
                allowable.add(a[i]);
                banCount.remove(a[i]);
            }
        }

        TreeSet<Integer> unused = new TreeSet<>();
        for (int i = 0; i <= n; i++) unused.add(i);
        boolean ok = true;
        for (int i = 0; i < n; i++) {
            unused.remove(b[i]);
            int mex = unused.first();
            if (a[i] != mex) {
                ok = false;
                break;
            }
        }
        if (!ok) {
            pw.println("-1");
        } else {
            for (int bi : b) {
                pw.print(bi + " ");
            }
        }
    }
}