import java.io.*;
import java.math.BigInteger;
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
        int MOD = 1000000007;
        int T = readInt();
        for (int t = 0; t < T; t++) {
            int x = readInt();
            StringBuilder sb = new StringBuilder(br.readLine());
            int l = 0;
            long size = sb.length();
            long curr = size;
            while (sb.length() < x + 2 && l != x) {
                l++;
                int sl = sb.charAt(l - 1) - '0';
                for (int i = 1; sb.length() < x + 2 && i < sl; i++) {
                    for (int j = l; sb.length() < x + 2 && j < curr; j++) {
                        sb.append(sb.charAt(j));
                    }
                }
                long len = curr - l;
                curr = curr + len * (sl - 1);
            }

            long[] rProds = new long[x + 2];
            rProds[x + 1] = 1;
            for (int i = x; i > 0; i--) {
                int rn = sb.charAt(i - 1) - '0' - 1;
                rProds[i] = (rProds[i+1] * (rn + 1)) % MOD;
            }

            long positive = (rProds[1] * size) % MOD;
            long negative = 0;
            for (int i = 0; i < x; i++) {
                int rn = sb.charAt(x-i-1) - '0' - 1;
                negative = (negative + rProds[x-i+1] * rn * (x-i)) % MOD;
            }
            long ans = positive + MOD - negative;
            pw.println(ans % MOD);
        }
    }
}