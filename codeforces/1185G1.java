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
    //   sorting, or taking max, after MOD
    void solve() throws IOException {
        int[] nT = ril(2);
        int n = nT[0];
        int T = nT[1];
        int[][] tg = new int[n][];
        for (int i = 0; i < n; i++) tg[i] = ril(2);

        // dp[t][m][g] is the number of sequences possible to be formed from
        // songs indicated by mask m, having total time t, and whose last song
        // is genre g.
        int[][][] dp = new int[T+1][1 << n][3];
        for (int ti = 1; ti <= T; ti++) {
            for (int mask = 1; mask <= (1 << n); mask++) {
                // Iterate over the possible last song. It's genre will determine
                // what the third dimension should be
                for (int j = 0; j < n; j++) {
                    if (((1 << j) & mask) == 0) continue;
                    int time = tg[j][0];
                    int genre = tg[j][1]-1;
                    if (ti - time < 0) continue;
                    long base = dp[ti][mask][genre];
                    long add = 0;
                    if (ti - time == 0) {
                        // Base case: there is always one way regardless of what songs are available.
                        // The empty playlist
                        add = 1;
                    } else {
                        for (int g = 0; g < 3; g++) add += dp[ti-time][mask ^ (1 << j)][g];
                        add -= dp[ti-time][mask ^ (1 << j)][genre];
                    }
                    dp[ti][mask][genre] = (int) ((base + add) % MOD);
                }
            }
        }
        long ans = 0;
        for (int g = 0; g < 3; g++) ans += dp[T][(1 << n)-1][g];
        pw.println(ans % MOD);
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
        int sign = 1;
        int c = br.read();
        int x = 0;
        if (c == '-') {
            sign = -1;
            c = br.read();
        }
        while (c >= '0' && c <= '9') {
            x = x * 10 + c - '0';
            c = br.read();
        }
        return ril(x);
    }

    long[] rkll() throws IOException {
        int sign = 1;
        int c = br.read();
        int x = 0;
        if (c == '-') {
            sign = -1;
            c = br.read();
        }
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