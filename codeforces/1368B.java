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
        String cf = "codeforces";
        long k = readLong();
        int[] count = new int[10];
        Arrays.fill(count, 1);
        long times = 1;
        boolean done = k == 1;
        int ans = 10;
        while (!done) {
            for (int i = 0; i < 10 && !done; i++) {
                times /= count[i];
                count[i]++;
                times *= count[i];
                ans++;
                if (times >= k) {
                    done = true;
                }
            }
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < count[i]; j++) {
                pw.print(cf.charAt(i));
            }
        }
        pw.println();
    }
}