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

    boolean intersect(int x1, int y1, int x2, int y2) {
        if (x1 >= x2 && y1 <= y2 || x1 <= x2 && y1 >= y2) return false;
        if (y1 < x2 || y2 < x1) return false;
        return true;
    }

    void solve() throws IOException {
        int t = ri();
        for (int ti = 0; ti < t; ti++) {
            int n = ri();
            int[][] segments = new int[n][];
            Set<Integer> set = new HashSet<>();
            for (int i = 0; i < n; i++) {
                segments[i] = ril(2);
                set.add(segments[i][0]);
                set.add(segments[i][1]);
            }
            List<Integer> coords = new ArrayList<>();
            for (int x : set) coords.add(x);
            Collections.sort(coords);
            Map<Integer, Integer> xToI = new HashMap<>();
            for (int i = 0; i < coords.size(); i++) xToI.put(coords.get(i), i);
            List<Set<Integer>> intervals = new ArrayList<>();
            for (int i = 0; i < coords.size(); i++) intervals.add(new HashSet<>());
            for (int[] seg : segments) {
                int l = xToI.get(seg[0]);
                int r = xToI.get(seg[1]);
                intervals.get(l).add(r);
            }

            int[][] dp = new int[coords.size()][coords.size()];
            for (int len = 1; len <= coords.size(); len++) {
                for (int i = 0; i + len - 1 < coords.size(); i++) {
                    int j = i + len - 1;
                    dp[i][j] = 0;
                    int add = intervals.get(i).contains(j) ? 1 : 0;
                    if (i+1<=j) dp[i][j] = dp[i+1][j];
                    for (int r : intervals.get(i)) {
                        if (r >= j) continue;
                        dp[i][j] = Math.max(dp[i][j], dp[i][r] + dp[r+1][j]);
                    }
                    dp[i][j] += add;
                }
            }
            pw.println(dp[0][coords.size()-1]);
        }
    }
}