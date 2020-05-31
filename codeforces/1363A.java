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
            int[] nx = readIntLine();
            int n = nx[0];
            int x = nx[1];
            int[] a = readIntLine();
            int numE = 0;
            int numO = 0;
            for (int ai : a) {
                if (ai % 2 == 0) numE++;
                else numO++;
            }
            if (numO == 0) {
                pw.println("No");
                continue;
            }
            if (numO % 2 == 0) numO--;
            if (numO >= x) {
                if (x % 2 == 0) x = 1;
                else x = 0;
            } else {
                x -= numO;
            }
            if (numE >= x) {
                pw.println("Yes");
            } else {
                pw.println("No");
            }
        }
    }
}