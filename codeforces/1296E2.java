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
        char[] s = br.readLine().toCharArray();
        int[] dp = new int[n];  // dp[i] is longest decreasing subsequence ending at i
        int[] max = new int[26];  // max[c] is len of longest decreasing subsequence using character c
        Arrays.fill(dp, 1);
        for (int i = 0; i < s.length; i++) {
            for (int c = s[i] - 'a' + 1; c < 26; c++) {
                dp[i] = Math.max(dp[i], max[c] + 1);
            }
            max[s[i] - 'a'] = Math.max(max[s[i] - 'a'], dp[i]);
        }
        int k = 0;
        for (int m : max) k = Math.max(k, m);
        pw.println(k);
        for (int i = 0; i < dp.length; i++) {
            pw.print(dp[i]);
            pw.print(" ");
        }
        pw.println();
    }
}