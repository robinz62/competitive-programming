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
            int n = readInt();
            int[] a = readIntLine();
            int minDiff = 0;
            boolean[] nextToMissing = new boolean[n];
            int numMissing = 0;
            for (int i = 0; i < n; i++) if (a[i] == -1) numMissing++;
            if (numMissing == n) {
                pw.println("0 0");
                continue;
            }
            for (int i = 0; i < n - 1; i++) {
                if (a[i] == -1 && a[i+1] != -1) {
                    nextToMissing[i+1] = true;
                } else if (a[i] != -1 && a[i+1] == -1) {
                    nextToMissing[i] = true;
                } else if (a[i] != -1 && a[i+1] != -1) {
                    minDiff = Math.max(minDiff, Math.abs(a[i+1] - a[i]));
                }
            }
            List<Integer> vals = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if (nextToMissing[i]) vals.add(a[i]);
            }
            Collections.sort(vals);
            int selection = -1;
            if (vals.size() == 1) selection = vals.get(0);
            else if (vals.size() >= 2) {
                selection = (vals.get(vals.size() - 1) + vals.get(0)) / 2;
                minDiff = Math.max(minDiff, vals.get(vals.size() - 1) - selection);
                minDiff = Math.max(minDiff, selection - vals.get(0));
            }
            pw.println(minDiff + " " + selection);
        }
    }
}