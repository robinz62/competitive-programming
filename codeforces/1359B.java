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
            int[] nmxy = readIntLine();
            int n = nmxy[0];
            int m = nmxy[1];
            int x = nmxy[2];
            int y = nmxy[3];
            int cost = 0;
            for (int i = 0; i < n; i++) {
                char[] row = br.readLine().toCharArray();
                List<Integer> streaks = whiteStreaks(row);
                for (int s : streaks) {
                    if (s % 2 == 0) {
                        cost += Math.min(x * s, y * s / 2);
                    } else {
                        cost += Math.min(x * s, s / 2 * y + x);
                    }
                }
            }
            pw.println(cost);
        }
    }

    List<Integer> whiteStreaks(char[] row) {
        List<Integer> l = new ArrayList<>();
        int count = 1;
        for (int i = 1; i < row.length; i++) {
            if (row[i] == row[i-1]) count++;
            else {
                if (row[i-1] == '.') l.add(count);
                count = 1;
            }
        }
        if (row[row.length-1] == '.') l.add(count);
        return l;
    }
}