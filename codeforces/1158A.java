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
        int[] line = readIntLine();
        int n = line[0];
        int m = line[1];
        int[] b = readIntLine();
        int[] g = readIntLine();
        Arrays.sort(b);
        Arrays.sort(g);
        long totalGiven = 0;
        for (int bi : b) {
            totalGiven += (long) bi * g.length;
        }
        int supplemented = 0;
        for (int gj : g) {
            if (b[b.length - 1] > gj) {
                pw.println("-1");
                return;
            } else if (b[b.length - 1] < gj) {
                totalGiven += (long) gj - b[b.length - 1];
                supplemented++;
            }
        }
        if (supplemented == g.length) {
            totalGiven -= g[0] - b[b.length - 1];
            totalGiven += g[0] - b[b.length - 2];
        }
        pw.println(totalGiven);
    }
}