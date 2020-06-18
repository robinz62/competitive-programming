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
        int n = readInt();
        int[] a = readIntLine();
        long ans = 0;

        int[] counts = new int[20];
        for (int ai : a) {
            for (int i = 0; i < 20; i++) {
                if ((ai & (1 << i)) > 0) counts[i]++;
            }
        }

        for (int i = 0; i < n; i++) {
            long num = 0;
            for (int b = 0; b < 20; b++) {
                if (counts[b] > 0) {
                    num |= (1 << b);
                    counts[b]--;
                }
            }
            ans += num * num;
            if (num == 0) break;
        }

        pw.println(ans);
    }
}