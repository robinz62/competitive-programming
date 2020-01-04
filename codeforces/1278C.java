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
            int n = readInt();
            int[] jars = readIntLine();
            int[] L = new int[n];
            int[] R = new int[n];
            System.arraycopy(jars, 0, L, 0, n);
            System.arraycopy(jars, n, R, 0, n);
            Map<Integer, Integer> diff = new HashMap<>();  // 1s minus 2s
            diff.put(0, 0);
            int curr = 0;
            for (int i = 0; i < n; i++) {
                if (L[i] == 1) curr++;
                else curr--;
                diff.put(curr, i+1);
            }
            int minEat = 2 * n - diff.get(0);
            int rDiff = 0;
            for (int i = n - 1; i >= 0; i--) {
                if (R[i] == 1) rDiff++;
                else rDiff--;
                Integer match = diff.get(-rDiff);
                if (match != null) {
                    minEat = Math.min(minEat, i + n - match);
                }
            }
            pw.print(minEat + "\n");
        }
    }
}