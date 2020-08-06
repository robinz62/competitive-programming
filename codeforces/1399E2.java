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
    List<Map<Integer, Integer>> eToI;
    void solve() throws IOException {
        int t = ri();
        for (int ti = 0; ti < t; ti++) {
            long[] nS = rll(2);
            int n = (int) nS[0];
            long S = nS[1];
            adj = new ArrayList<>(n);
            eToI = new ArrayList<>();
            for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
            for (int i = 0; i < n; i++) eToI.add(new HashMap<>());
            int[][] edges = new int[n-1][];
            int[] c = new int[n-1];
            for (int i = 0; i < n-1; i++) {
                int[] uvwc = edges[i] = ril(4);
                uvwc[0]--;
                uvwc[1]--;
                adj.get(uvwc[0]).add(uvwc[1]);
                adj.get(uvwc[1]).add(uvwc[0]);
                eToI.get(uvwc[0]).put(uvwc[1], i);
                eToI.get(uvwc[1]).put(uvwc[0], i);
            }

            count = new int[n-1];
            dfs(0, -1);

            PriorityQueue<long[]> pq1 = new PriorityQueue<>((p1, p2) -> {
                long sub1 = (p1[0] * p1[1]) - (p1[0] / 2 * p1[1]);
                long sub2 = (p2[0] * p2[1]) - (p2[0] / 2 * p2[1]);
                return -Long.compare(sub1, sub2);
            });
            PriorityQueue<long[]> pq2 = new PriorityQueue<>((p1, p2) -> {
                long sub1 = (p1[0] * p1[1]) - (p1[0] / 2 * p1[1]);
                long sub2 = (p2[0] * p2[1]) - (p2[0] / 2 * p2[1]);
                return -Long.compare(sub1, sub2);
            });

            long sum = 0;
            for (int i = 0; i < n-1; i++) {
                if (edges[i][3] == 1) {
                    pq1.add(new long[]{edges[i][2], count[i]});
                } else {
                    pq2.add(new long[]{edges[i][2], count[i]});
                }
                sum += (long) count[i] * edges[i][2];
            }

            int moves = 0;
            while (sum > S) {
                // short circuit: is removing single or removing double immediately enough
                if (!pq1.isEmpty()) {
                    long[] e = pq1.peek();
                    long reduction = e[0]*e[1] - e[0]/2*e[1];
                    if (sum - reduction <= S) {
                        moves++;
                        break;
                    }
                }
                if (!pq2.isEmpty()) {
                    long[] e = pq2.peek();
                    long reduction = e[0]*e[1] - e[0]/2*e[1];
                    if (sum - reduction <= S) {
                        moves += 2;
                        break;
                    }
                }

                // obvious cases: one of pqs is empty
                if (pq1.isEmpty()) {
                    long[] d1 = pq2.remove();
                    long doublereduction = d1[0]*d1[1] - d1[0]/2*d1[1];
                    sum -= doublereduction;
                    d1[0] /= 2;
                    pq2.add(d1);
                    moves += 2;
                    continue;
                }
                if (pq2.isEmpty()) {
                    long[] s1 = pq1.remove();
                    long singlereduction = s1[0]*s1[1] - s1[0]/2*s1[1];
                    sum -= singlereduction;
                    s1[0] /= 2;
                    pq1.add(s1);
                    moves++;
                    continue;
                }

                long[] s1 = pq1.remove();
                long[] d1 = pq2.peek();
                long singlereduction = s1[0]*s1[1] - s1[0]/2*s1[1];
                if (pq1.isEmpty()) {
                    long next = s1[0] / 2;
                    singlereduction += next*s1[1] - next/2*s1[1];
                } else {
                    long next = s1[0] / 2;
                    long redsame = next*s1[1] - next/2*s1[1];

                    long[] s2 = pq1.peek();
                    long reddiff = s2[0]*s2[1] - s2[0]/2*s2[1];

                    singlereduction += Math.max(redsame, reddiff);
                }
                long doublereduction = d1[0]*d1[1] - d1[0]/2*d1[1];
                if (sum - singlereduction <= S || singlereduction >= doublereduction) {
                    sum -= s1[0]*s1[1] - s1[0]/2*s1[1];
                    moves++;
                    s1[0] /= 2;
                    pq1.add(s1);
                } else {
                    pq2.remove();
                    pq1.add(s1);
                    sum -= doublereduction;
                    moves += 2;
                    d1[0] /= 2;
                    pq2.add(d1);
                }
            }
            pw.println(moves);
        }
    }

    int[] count;
    int dfs(int u, int p) {
        int c = adj.get(u).size() == 1 ? 1 : 0;
        for (int v : adj.get(u)) {
            if (v != p) {
                c += dfs(v, u);
            }
        }
        if (p != -1) count[eToI.get(p).get(u)] = c;
        return c;
    }
}