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
        char[] s = br.readLine().toCharArray();
        long[] freq = new long[26];
        long[][] freq2 = new long[26][26];
        for (int i = 0; i < s.length; i++) {
            int c = s[i] - 'a';
            for (int p = 0; p < 26; p++) {
                freq2[p][c] += freq[p];
            }
            freq[c]++;
        }
        long max = 0;
        for (long f : freq) max = Math.max(max, f);
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < 26; j++) {
                max = Math.max(max, freq2[i][j]);
            }
        }
        pw.println(max);
    }
}