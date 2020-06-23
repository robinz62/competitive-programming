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

    int ri() throws IOException {
        return Integer.parseInt(br.readLine());
    }

    long rl() throws IOException {
        return Long.parseLong(br.readLine());
    }

    int[] ril() throws IOException {
        String[] tokens = br.readLine().split(" ");
        int[] A = new int[tokens.length];
        for (int i = 0; i < A.length; i++)
            A[i] = Integer.parseInt(tokens[i]);
        return A;
    }

    long[] rll() throws IOException {
        String[] tokens = br.readLine().split(" ");
        long[] A = new long[tokens.length];
        for (int i = 0; i < A.length; i++)
            A[i] = Long.parseLong(tokens[i]);
        return A;
    }

    void solve() throws IOException {
        int t = ri();
        for (int ti = 0; ti < t; ti++) {
            int[] nk = ril();
            int n = nk[0];
            int k = nk[1];
            char[] s = br.readLine().toCharArray();
            int[] freq = new int[26];
            for (char c : s) freq[c-'a']++;

            for (int i = n; i >= 1; i--) {
                int g = gcd(i, k);
                int segments = i / g;
                int count = 0;
                for (int f : freq) {
                    count += f / segments;
                }
                if (count >= g) {
                    pw.println(i);
                    break;
                }
            }
        }
    }

    public static int gcd(int a, int b) {
        return a == 0 ? b : gcd(b % a, a);
    }
}