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
        int[] nm = ril(2);
        int n = nm[0];
        int m = nm[1];
        List<TreeMap<Integer, Integer>> adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new TreeMap<>());
        int[][] edges = new int[m][];
        for (int i = 0; i < m; i++) {
            edges[i] = ril(3);
            edges[i][0]--; edges[i][1]--;
        }
        int[] dp = new int[m];
        dp[m-1] = 1;
        adj.get(edges[m-1][0]).put(edges[m-1][2], 1);
        for (int i = m-2; i >= 0; i--) {
            int u = edges[i][0];
            int v = edges[i][1];
            int w = edges[i][2];
            Integer higher = adj.get(v).higherKey(w);
            if (higher == null) {
                dp[i] = 1;
            } else {
                dp[i] = 1 + adj.get(v).get(higher);
            }

            TreeMap<Integer, Integer> map = adj.get(u);
            Integer floor = map.floorKey(w);
            while (floor != null && map.get(floor) <= dp[i]) {
                map.remove(floor);
                floor = map.floorKey(w);
            }

            Integer ceiling = map.ceilingKey(w);
            if (ceiling == null || dp[i] > map.get(ceiling)) {
                map.put(w, dp[i]);
            }
        }

        int ans = dp[0];
        for (int x : dp) ans = Math.max(x, ans);
        pw.println(ans);
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