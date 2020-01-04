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
        int q = readInt();
        for (int i = 0; i < q; i++) {
            int[] line = readIntLine();
            int n = line[0];
            int r = line[1];
            int[] a = readIntLine();
            Arrays.sort(a);
            int required = 0;
            int ct = a.length - 1;
            for (int j = a.length - 1; j >= 0; j--) {
                if (j != a.length - 1 && a[j] == a[j+1]) continue;
                int myRequired = Math.min((a[j] - 1) / r + 1, a.length - ct);
                ct--;
                required = Math.max(required, myRequired);
            }
            pw.println(required);
        }
    }
}