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

    int readInt() throws IOException {
        return Integer.parseInt(br.readLine());
    }

    long readLong() throws IOException {
        return Long.parseLong(br.readLine());
    }

    int[] readIntLine() throws IOException {
        String[] tokens = br.readLine().split(" ");
        int[] A = new int[tokens.length];
        for (int i = 0; i < A.length; i++)
            A[i] = Integer.parseInt(tokens[i]);
        return A;
    }

    long[] readLongLine() throws IOException {
        String[] tokens = br.readLine().split(" ");
        long[] A = new long[tokens.length];
        for (int i = 0; i < A.length; i++)
            A[i] = Long.parseLong(tokens[i]);
        return A;
    }

    void solve() throws IOException {
        int n = readInt();
        Map<Integer, Integer> edgeToIndex = new HashMap<>();
        List<List<Integer>> adj = new ArrayList<>(5000);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int i = 0; i < n - 1; i++) {
            int[] edge = readIntLine();
            edge[0]--;
            edge[1]--;
            int e = edge[0] < edge[1] ? edge[0] * n + edge[1] : edge[1] * n + edge[0];
            edgeToIndex.put(e, i);
            adj.get(edge[0]).add(edge[1]);
            adj.get(edge[1]).add(edge[0]);
        }
        int m = readInt();
        int[][] queries = new int[m][];
        for (int i = 0; i < m; i++) {
            queries[i] = readIntLine();
            queries[i][0]--;
            queries[i][1]--;
        }
        Arrays.sort(queries, (q1, q2) -> Integer.compare(q1[2], q2[2]));

        // need to compute lazily in Java submissions >:(
        int[][] p = new int[n][];
        Map<Integer, Integer> edgeToBeauty = new HashMap<>();
        for (int[] q : queries) {
            int s = q[0];
            int t = q[1];
            if (p[s] == null) {
                p[s] = new int[n];
                p[s][s] = -1;
                dfs(s, s, adj, p);
            }
            while (t != s) {
                int u = p[s][t];
                int e = u < t ? u * n + t : t * n + u;
                edgeToBeauty.put(e, q[2]);
                t = u;
            }
        }

        for (int[] q : queries) {
            int s = q[0];
            int t = q[1];
            int min = Integer.MAX_VALUE;
            while (t != s) {
                int u = p[s][t];
                int e = u < t ? u * n + t : t * n + u;
                min = Math.min(min, edgeToBeauty.get(e));
                t = u;
            }
            if (min != q[2]) {
                pw.println("-1");
                return;
            }
        }

        int[] ans = new int[n - 1];
        for (int e : edgeToBeauty.keySet()) {
            ans[edgeToIndex.get(e)] = edgeToBeauty.get(e);
        }
        for (int i = 0; i < ans.length; i++) {
            if (ans[i] == 0) ans[i] = 1000000;
            pw.print(ans[i]);
            pw.print(" ");
        }
        pw.println();
    }

    void dfs(int s, int u, List<List<Integer>> adj, int[][] p) {
        for (int v : adj.get(u)) {
            if (v == p[s][u]) continue;
            p[s][v] = u;
            dfs(s, v, adj, p);
        }
    }
}