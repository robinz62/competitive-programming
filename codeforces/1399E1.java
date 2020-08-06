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

    List<List<Integer>> adj;
    List<Map<Integer, Integer>> eToW;
    List<Map<Integer, Integer>> eToC;
    void solve() throws IOException {
        int t = ri();
        for (int ti = 0; ti < t; ti++) {
            long[] nS = rll(2);
            int n = (int) nS[0];
            long S = nS[1];
            adj = new ArrayList<>(n);
            eToW = new ArrayList<>();
            eToC = new ArrayList<>();
            for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
            for (int i = 0; i < n; i++) {
                eToW.add(new HashMap<>());
                eToC.add(new HashMap<>());
            }
            int[][] edges = new int[n-1][];
            for (int i = 0; i < n-1; i++) {
                int[] uvw = edges[i] = ril(3);
                uvw[0]--;
                uvw[1]--;
                adj.get(uvw[0]).add(uvw[1]);
                adj.get(uvw[1]).add(uvw[0]);
                eToW.get(uvw[0]).put(uvw[1], uvw[2]);
                eToW.get(uvw[1]).put(uvw[0], uvw[2]);
                eToC.get(uvw[0]).put(uvw[1], 0);
                eToC.get(uvw[1]).put(uvw[0], 0);
            }
            dfs(0, -1);
            long sum = 0;
            // [weight, count]
            PriorityQueue<long[]> vals = new PriorityQueue<>((p1, p2) -> {
                long sub1 = (p1[0] * p1[1]) - (p1[0] / 2 * p1[1]);
                long sub2 = (p2[0] * p2[1]) - (p2[0] / 2 * p2[1]);
                return -Long.compare(sub1, sub2);
            });
            for (int[] e : edges) {
                int u = e[0];
                int v = e[1];
                long contrib = (long) eToW.get(u).get(v) * eToC.get(u).get(v);
                sum += contrib;
                vals.add(new long[]{eToW.get(u).get(v), eToC.get(u).get(v)});
            }
            int moves = 0;
            while (sum > S) {
                long[] top = vals.remove();
                long prev = top[0] * top[1];
                long next = top[0] / 2 * top[1];
                sum = sum - prev + next;
                top[0] /= 2;
                vals.add(top);
                moves++;
            }
            pw.println(moves);
        }
    }

    // returns number of leaves under u
    int dfs(int u, int p) {
        int count = adj.get(u).size() == 1 ? 1 : 0;
        for (int v : adj.get(u)) {
            if (v != p) count += dfs(v, u);
        }
        if (p != -1) {
            eToC.get(u).put(p, eToC.get(u).get(p) + count);
            eToC.get(p).put(u, eToC.get(p).get(u) + count);
        }
        return count;
    }
}