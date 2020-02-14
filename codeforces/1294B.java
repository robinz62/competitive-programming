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
            boolean[] hasPackage = new boolean[1001];
            int[][] cols = new int[1001][2];  // min, max
            for (int z = 0; z <= 1000; z++) cols[z][0] = Integer.MAX_VALUE;
            int lastX = 0;
            for (int i = 0; i < n; i++) {
                int[] xy = readIntLine();
                int x = xy[0];
                int y = xy[1];
                hasPackage[x] = true;
                lastX = Math.max(lastX, x);
                cols[x][0] = Math.min(cols[x][0], y);
                cols[x][1] = Math.max(cols[x][1], y);
            }
            StringBuilder sb = new StringBuilder();
            int y = 0;
            boolean done = false;
            for (int x = 0; x <= lastX; x++) {
                if (!hasPackage[x]) {
                    sb.append('R');
                } else {
                    if (y > cols[x][0]) {
                        pw.println("NO");
                        done = true;
                        break;
                    }
                    while (y < cols[x][1]) {
                        sb.append('U');
                        y++;
                    }
                    sb.append('R');
                }
            }
            if (!done) {
                pw.println("YES");
                sb.deleteCharAt(sb.length() - 1);
                pw.println(sb.toString());
            }
        }
    }
}