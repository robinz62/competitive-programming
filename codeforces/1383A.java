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

    char[] rs() throws IOException {
        return br.readLine().toCharArray();
    }

    void solve() throws IOException {
        int t = ri();
        for (int ti = 0; ti < t; ti++) {
            int n = ri();
            char[] A = rs();
            char[] B = rs();
            boolean done = false;
            for (int i = 0; i < n; i++) {
                if (A[i] > B[i]) {
                    pw.println("-1");
                    done = true;
                    break;
                }
            }
            if (done) continue;
            boolean[][] adj = new boolean[20][20];
            int ans = 0;
            for (int i = 0; i < n; i++) {
                int u = A[i] - 'a';
                int v = B[i] - 'a';
                if (u != v) adj[u][v] = true;
            }
            for (int i = 0; i < 20; i++) {
                int minNext = Integer.MAX_VALUE;
                for (int j = 0; j < 20; j++) {
                    if (adj[i][j]) minNext = Math.min(minNext, j);
                }
                if (minNext == Integer.MAX_VALUE) continue;
                ans++;
                for (int j = i; j < 20; j++) {
                    if (adj[i][j] && minNext != j) adj[minNext][j] = true;
                }
            }
            pw.println(ans);
        }
    }
}