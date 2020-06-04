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
            long[] ab = readLongLine();
            long a = ab[0];
            long b = ab[1];
            int l = Integer.MAX_VALUE;
            for (int i = 0; i <= 63 && (a << i) >= 0; i++) {
                if ((a << i) == b) {
                    l = i;
                    break;
                }
            }
            int r = Integer.MAX_VALUE;
            int i = 0;
            while (true) {
                if (a == b) {
                    r = i;
                    break;
                }
                if (a % 2 == 0) {
                    a = a >> 1;
                    i++;
                } else {
                    break;
                }
            }
            int ans = Math.min(l, r);
            ans = ans == Integer.MAX_VALUE ? -1 : ans % 3 == 0 ? ans / 3 : ans / 3 + 1;
            pw.println(ans);
        }
    }
}