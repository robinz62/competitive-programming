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
            int[] nk = readIntLine();
            int n = nk[0];
            int k = nk[1];
            // count, [list of 1-indexed indices]
            int[][] lines = new int[k][];
            for (int i = 0; i < k; i++) {
                lines[i] = readIntLine();
            }
            pw.print("? " + n);
            for (int i = 1; i <= n; i++) pw.print(" " + i);
            pw.println();
            pw.flush();
            int max = readInt();
            int l = 1;
            int r = n;
            while (l < r) {
                int m = l + (r - l) / 2;
                pw.print("? " + (m-l+1));
                for (int i = l; i <= m; i++) pw.print(" " + i);
                pw.println();
                pw.flush();
                int currMax = readInt();
                if (currMax == max) {
                    r = m;
                } else {
                    l = m + 1;
                }
            }
            // we now have l = r = index of global max
            int specialIdx = -1;
            int specialAns = -1;
            for (int i = 0; i < lines.length; i++) {
                for (int j = 1; j < lines[i].length; j++) {
                    if (lines[i][j] == l) {
                        Set<Integer> query = new HashSet<>();
                        for (int x = 1; x <= n; x++) query.add(x);
                        for (int x = 1; x < lines[i].length; x++) {
                            query.remove(lines[i][x]);
                        }
                        pw.print("? " + query.size());
                        for (int z : query) pw.print(" " + z);
                        pw.println();
                        pw.flush();
                        specialIdx = i;
                        specialAns = readInt();
                        break;
                    }
                }
            }
            int[] P = new int[k];
            Arrays.fill(P, max);
            if (specialIdx != -1) {
                P[specialIdx] = specialAns;
            }
            pw.print("!");
            for (int p : P) pw.print(" " + p);
            pw.println();
            pw.flush();
            br.readLine();  // Correct
        }
    }
}