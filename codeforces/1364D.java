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

    List<List<Integer>> adj;
    boolean[] visited;
    int[] depth;
    int[] p;

    void solve() throws IOException {
        int[] nmk = readIntLine();
        int n = nmk[0];
        int m = nmk[1];
        k = nmk[2];
        adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            int[] uv = readIntLine();
            adj.get(uv[0]-1).add(uv[1]-1);
            adj.get(uv[1]-1).add(uv[0]-1);
        }
        visited = new boolean[n];
        depth = new int[n];
        depth[0] = 0;
        visited[0] = true;
        p = new int[n];
        p[0] = -1;
        dfs(0);
        if (!solved) {
            int countEven = 0;
            int countOdd = 0;
            for (int i = 0; i < levels.size(); i++) {
                if (i % 2 == 0) countEven += levels.get(i).size();
                else countOdd += levels.get(i).size();
            }
            k = k % 2 == 0 ? k / 2 : k / 2 + 1;
            if (countEven >= k) {
                pw.println("1");
                int count = 0;
                for (int i = 0; i < levels.size() && count < k; i += 2) {
                    for (int c : levels.get(i)) {
                        pw.print((c+1) + " ");
                        count++;
                        if (count == k) break;
                    }
                }
                return;
            } else {
                pw.println("1");
                int count = 0;
                for (int i = 1; i < levels.size() && count < k; i += 2) {
                    for (int c : levels.get(i)) {
                        pw.print((c+1) + " ");
                        count++;
                        if (count == k) break;
                    }
                }
            }
        }
    }

    boolean solved = false;
    int k;
    List<List<Integer>> levels = new ArrayList<>();

    void dfs(int u) {
        if (depth[u] >= levels.size()) {
            levels.add(new ArrayList<>());
        }
        levels.get(depth[u]).add(u);

        int maxDepthAncestor = -1;
        for (int v : adj.get(u)) {
            if (visited[v] && v != p[u]) {
                if (maxDepthAncestor == -1 || depth[v] > depth[maxDepthAncestor]) {
                    maxDepthAncestor = v;
                }
            } else if (!visited[v]) {
                p[v] = u;
                visited[v] = true;
                depth[v] = depth[u] + 1;
                dfs(v);
                if (solved) return;
            }
        }
        if (solved) return;
        if (maxDepthAncestor != -1) {
            int numNodes = depth[u] - depth[maxDepthAncestor] + 1;
            if (numNodes <= k) {
                pw.println("2");
                pw.println(numNodes);
                int curr = u;
                while (curr != maxDepthAncestor) {
                    pw.print((curr+1) + " ");
                    curr = p[curr];
                }
                pw.print((curr+1));
                solved = true;
            } else {
                int want = k % 2 == 0 ? k / 2 : k / 2 + 1;
                pw.println("1");
                int curr = u;
                while (want > 0) {
                    pw.print((curr+1) + " ");
                    curr = p[p[curr]];
                    want--;
                }
                solved = true;
            }
        }
    }
}