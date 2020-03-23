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
            int[][] lists = new int[n][];
            for (int i = 0; i < n; i++) {
                int[] line = readIntLine();
                lists[i] = new int[line[0]];
                for (int j = 1; j < line.length; j++) {
                    lists[i][j-1] = line[j];
                }
            }

            Set<Integer> taken = new HashSet<>();
            int lastUnmatched = -1;
            for (int i = 0; i < n; i++) {
                boolean matched = false;
                for (int j = 0; !matched && j < lists[i].length; j++) {
                    if (!taken.contains(lists[i][j])) {
                        taken.add(lists[i][j]);
                        matched = true;
                    }
                }
                if (!matched) {
                    lastUnmatched = i;
                }
            }

            if (lastUnmatched == -1) {
                pw.println("OPTIMAL");
            } else {
                pw.println("IMPROVE");
                for (int i = 1; i <= n; i++) {
                    if (!taken.contains(i)) {
                        pw.println((lastUnmatched+1) + " " + (i));
                        break;
                    }
                }
            }
        }
    }
}