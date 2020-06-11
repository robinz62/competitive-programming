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
            if (2 * b <= a) {
                pw.println(b);
            } else if (2 * a <= b) {
                pw.println(a);
            } else if (a <= b) {
                long d = b - a;
                long ans = d;
                a -= d;
                b -= 2 * d;
                if (a % 3 == 0) ans += 2 * a / 3;
                else if (a % 3 == 1) ans += 2 * (a / 3);
                else ans += 2 * (a / 3) + 1;
                pw.println(ans);
            } else {
                long d = a - b;
                long ans = d;
                a -= 2 * d;
                b -= d;
                if (a % 3 == 0) ans += 2 * a / 3;
                else if (a % 3 == 1) ans += 2 * (a / 3);
                else ans += 2 * (a / 3) + 1;
                pw.println(ans);
            }
        }
    }
}