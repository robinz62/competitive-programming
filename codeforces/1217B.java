import java.io.*;
import java.util.*;
 
public class Main {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pw = new PrintWriter(System.out);
 
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
 
    int[] readIntLine() throws IOException {
        String[] tokens = br.readLine().split(" ");
        int[] A = new int[tokens.length];
        for (int i = 0; i < A.length; i++) A[i] = Integer.parseInt(tokens[i]);
        return A;
    }
 
    long[] readLongLine() throws IOException {
        String[] tokens = br.readLine().split(" ");
        long[] A = new long[tokens.length];
        for (int i = 0; i < A.length; i++) A[i] = Long.parseLong(tokens[i]);
        return A;
    }
 
    void solve() throws IOException {
        int T = readInt();
        for (int t = 0; t < T; t++) {
            int[] line = readIntLine();
            int n = line[0];
            int x = line[1];
            int bestD = Integer.MIN_VALUE;
            int bestDecrease = Integer.MIN_VALUE;
            for (int i = 0; i < n; i++) {
                line = readIntLine();
                bestDecrease = Math.max(bestDecrease, line[0] - line[1]);
                bestD = Math.max(bestD, line[0]);
            }
            if (bestDecrease <= 0 && x > bestD) {
                pw.println("-1\n");
                continue;
            }
            int moves = x - bestD - 1 >= 0 ? (x - bestD - 1) / bestDecrease + 1 + 1 : 1;
            pw.print(moves);
            pw.print("\n");
        }
    }
}