import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static int MOD = 1000000007;

    // After writing solution, quick scan for:
    //   array out of bounds
    //   special cases e.g. n=1?
    //   npe, particularly in maps
    //
    // Big numbers arithmetic bugs:
    //   int overflow
    //   if (x : long) and (y : int), [y = x] does not compile, but [y += x] does
    //   sorting, or taking max, after MOD
    void solve() throws IOException {
        int T = ri();
        int MAX_N = 1000;

        int[] dist = new int[MAX_N + 1];
        Arrays.fill(dist, -1);
        Deque<Integer> q = new ArrayDeque<>();
        q.addLast(1);
        dist[1] = 0;
        while (!q.isEmpty()) {
            int u = q.removeFirst();
            for (int div = 1; div <= u; div++) {
                int tgt = u + u / div;
                if (tgt <= MAX_N && dist[tgt] == -1) {
                    dist[tgt] = dist[u] + 1;
                    q.addLast(tgt);
                }
            }
        }

        int maxDist = 0;
        for (int x : dist) maxDist = Math.max(maxDist, x);
        
        for (int Ti = 0; Ti < T; Ti++) {
            int[] nk = ril(2);
            int n = nk[0];
            int k = nk[1];

            int[] b = ril(n);
            int[] c = ril(n);

            k = Math.min(k, maxDist * n);

            for (int i = 0; i < n; i++) b[i] = dist[b[i]];
            int[][] dp = new int[n][k+1];
            if (b[0] <= k) dp[0][b[0]] = c[0];
            for (int i = 1; i < n; i++) {
                dp[i][0] = dp[i-1][0] + (b[i] == 0 ? c[i] : 0);
                for (int ki = 1; ki <= k; ki++) {
                    dp[i][ki] = Math.max(dp[i][ki], dp[i-1][ki]);
                    if (b[i] <= ki) {
                        dp[i][ki] = Math.max(dp[i][ki], dp[i-1][ki - b[i]] + c[i]);
                    }
                }
            }
            int ans = 0;
            for (int ki = 0; ki <= k; ki++) ans = Math.max(ans, dp[n-1][ki]);
            pw.println(ans);
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    // Template code below

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

    int ri() throws IOException {
        return Integer.parseInt(br.readLine().trim());
    }

    long rl() throws IOException {
        return Long.parseLong(br.readLine().trim());
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

    int[] rkil() throws IOException {
        int c = br.read();
        int x = 0;
        while (c >= '0' && c <= '9') {
            x = x * 10 + c - '0';
            c = br.read();
        }
        return ril(x);
    }

    long[] rkll() throws IOException {
        int c = br.read();
        int x = 0;
        while (c >= '0' && c <= '9') {
            x = x * 10 + c - '0';
            c = br.read();
        }
        return rll(x);
    }

    char[] rs() throws IOException {
        return br.readLine().toCharArray();
    }

    void sort(int[] A) {
        Random r = new Random();
        for (int i = A.length-1; i > 0; i--) {
            int j = r.nextInt(i+1);
            int temp = A[i];
            A[i] = A[j];
            A[j] = temp;
        }
        Arrays.sort(A);
    }

    void printDouble(double d) {
        pw.printf("%.16f", d);
    }
}