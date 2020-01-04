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
        int[] k = new int[m];
        int[] f = new int[m];
        for (int i = 0; i < m; i++) {
            line = readIntLine();
            k[i] = line[0];
            f[i] = line[1];
        }
        int ans = -1;
        for (int x = 1; x <= 999; x++) {
            boolean good = true;
            for (int i = 0; i < m; i++) {
                int expectedFloor = (k[i] - 1) / x + 1;
                if (expectedFloor != f[i]) {
                    good = false;
                    break;
                }
            }
            if (good) {
                if (ans != -1 && ans != (n - 1) / x + 1) {
                    ans = -1;
                    break;
                } else {
                    ans = (n - 1) / x + 1;
                }
            }
        }
        pw.println(ans);
    }
}