import java.io.*;
import java.util.*;
 
public class Main {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pw = new PrintWriter(System.out);
 
    public static void main(String[] args) throws Exception {
        Main m = new Main();
        m.solve();
        m.close();
    }
 
    void close() throws Exception {
        pw.flush();
        pw.close();
        br.close();
    }

    int[] readIntArray() throws Exception {
        String[] tokens = br.readLine().split(" ");
        int[] A = new int[tokens.length];
        for (int i = 0; i < A.length; i++)
            A[i] = Integer.parseInt(tokens[i]);
        return A;
    }
 
    void solve() throws Exception {
        int[] line = readIntArray();
        int n = line[0];
        int k = line[1];
        int[] A = readIntArray();
        int numDiffs = n - k;
        int[] diffs = new int[n - 1];
        for (int i = 0; i < diffs.length; i++) {
            diffs[i] = A[i + 1] - A[i];
        }
        Arrays.sort(diffs);
        long ans = 0;
        for (int i = 0; i < numDiffs; i++) ans += diffs[i];
        pw.println(ans);
    }
}