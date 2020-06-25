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
            char[] s = br.readLine().toCharArray();
            int sum = 0;
            int[] prefix = new int[s.length];
            Map<Integer, Integer> firstOccurrence = new HashMap<>();
            for (int i = 0; i < s.length; i++) {
                sum += s[i] == '+' ? 1 : -1;
                prefix[i] = sum;
                if (!firstOccurrence.containsKey(sum)) firstOccurrence.put(sum, i);
            }
            long ans = 0;
            for (int k : firstOccurrence.keySet()) {
                if (k >= 0) continue;
                int i = firstOccurrence.get(k);
                ans += i+1;
            }
            ans += s.length;
            pw.println(ans);
        }
    }
}