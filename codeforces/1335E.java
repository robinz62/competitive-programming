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

    int[] ril(int n) throws IOException {
        int[] nums = new int[n];
        int c = 0;
        for (int i = 0; i < n; i++) {
            int sign = 1;
            c = br.read();
            int x = 0;
            if (c == '-') {
                sign = -1;
                c = br.read();
            }
            while (c >= '0' && c <= '9') {
                x = x * 10 + c - '0';
                c = br.read();
            }
            nums[i] = x * sign;
        }
        while (c != '\n' && c != -1) c = br.read();
        return nums;
    }

    long[] rll(int n) throws IOException {
        long[] nums = new long[n];
        int c = 0;
        for (int i = 0; i < n; i++) {
            int sign = 1;
            c = br.read();
            long x = 0;
            if (c == '-') {
                sign = -1;
                c = br.read();
            }
            while (c >= '0' && c <= '9') {
                x = x * 10 + c - '0';
                c = br.read();
            }
            nums[i] = x * sign;
        }
        while (c != '\n' && c != -1) c = br.read();
        return nums;
    }

    void solve() throws IOException {
        int t = ri();
        for (int ti = 0; ti < t; ti++) {
            int n = ri();
            int[] a = ril(n);
            int best = 1;
            int[] freq = new int[201];
            for (int ai : a) freq[ai]++;
            for (int i = 1; i <= 200; i++) best = Math.max(best, freq[i]);
            List<List<Integer>> pos = new ArrayList<>(201);
            for (int i = 0; i < 201; i++) pos.add(new ArrayList<>());
            for (int i = 0; i < a.length; i++) {
                pos.get(a[i]).add(i);
            }
            int[][] prefix = new int[n][201];
            prefix[0][a[0]] = 1;
            for (int i = 1; i < n; i++) {
                for (int j = 1; j <= 200; j++) {
                    prefix[i][j] = prefix[i-1][j];
                }
                prefix[i][a[i]]++;
            }
            for (int x = 1; x <= 200; x++) {  // fix value of outer
                List<Integer> bingo = pos.get(x);
                for (int i = 0; i < bingo.size() / 2; i++) {
                    Arrays.fill(freq, 0);
                    int l = bingo.get(i) + 1;
                    int r = bingo.get(bingo.size() - 1 - i) - 1;
                    int inner = 0;
                    for (int ii = 1; ii <= 200; ii++) {
                        inner = Math.max(inner, prefix[r][ii] - (l-1 >= 0 ? prefix[l-1][ii] : 0));
                    }
                    for (int fi : freq) inner = Math.max(inner, fi);

                    best = Math.max(best, (i+1)*2 + inner);
                }
            }
            pw.println(best);
        }
    }
}