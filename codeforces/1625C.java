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
        int[] nlk = ril(3);
        int n = nlk[0];
        int l = nlk[1];
        int k = nlk[2];

        int[] a = ril(n);
        int[] d = ril(n);

        Set<Integer> speeds = new HashSet<>();
        for (int di : d) speeds.add(di);
        List<Integer> vs = new ArrayList<>(speeds);
        Collections.sort(vs);
        Map<Integer, Integer> vToId = new HashMap<>();
        for (int i = 0; i < vs.size(); i++) vToId.put(vs.get(i), i);

        // Remap the d array
        for (int i = 0; i < n; i++) {
            d[i] = vToId.get(d[i]);
        }

        // dp[i][k][v] is the minimum time to traverse d[0] through d[i] where we remove at exactly k stop signs,
        // and the speed we end up with is v
        // Don't forget to traverse the final leg afterwards.
        long[][][] dp = new long[2][k+1][vs.size()];
        for (long[][] mat : dp) {
            for (long[] row : mat) {
                Arrays.fill(row, Long.MAX_VALUE);
            }
        }

        // Base case: can't remove first sign
        dp[0][0][d[0]] = 0;

        for (int i = 1; i < n; i++) {
            for (int ki = 0; ki <= k; ki++) {
                for (int v = 0; v < vs.size(); v++) {
                    dp[i%2][ki][v] = Long.MAX_VALUE;
                }
            }

            int ii = i % 2;
            int jj = ii ^ 1;
            dp[ii][0][d[i]] = dp[jj][0][d[i-1]] + (long) (a[i] - a[i-1]) * vs.get(d[i-1]);

            for (int ki = 1; ki <= k; ki++) {
                // Don't remove my sign
                for (int prevV = 0; prevV < vs.size(); prevV++) {
                    if (dp[jj][ki][prevV] == Long.MAX_VALUE) continue;
                    dp[ii][ki][d[i]] = Math.min(dp[ii][ki][d[i]], dp[jj][ki][prevV] + (long) (a[i] - a[i-1]) * vs.get(prevV));
                }

                // Remove my sign
                for (int prevV = 0; prevV < vs.size(); prevV++) {
                    if (dp[jj][ki-1][prevV] == Long.MAX_VALUE) continue;
                    dp[ii][ki][prevV] = Math.min(dp[ii][ki][prevV], dp[jj][ki-1][prevV] + (long) (a[i] - a[i-1]) * vs.get(prevV));
                }
            }
        }

        long ans = Long.MAX_VALUE;
        for (int ki = 0; ki <= k; ki++) {
            for (int finalV = 0; finalV < vs.size(); finalV++) {
                if (dp[(n-1)%2][ki][finalV] == Long.MAX_VALUE) continue;
                long dist = dp[(n-1)%2][ki][finalV] + (long) (l - a[n-1]) * vs.get(finalV);
                ans = Math.min(ans, dist);
            }
        }

        pw.println(ans);
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    long ansForI(long[][][] dp, int k, int i) {
        long[][] dp2 = dp[i];
        long ans = Long.MAX_VALUE;
        for (int ki = 0; ki <= k; ki++) {
            for (long x : dp2[ki]) {
                ans = Math.min(ans, x);
            }
        }
        return ans;
    }

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