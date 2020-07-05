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
        int[] nm = ril(2);
        int n = nm[0];
        int m = nm[1];
        List<List<Integer>> adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int i = 0; i < n - 1; i++) {
            int[] uv = ril(2);
            adj.get(uv[0]-1).add(uv[1]-1);
            adj.get(uv[1]-1).add(uv[0]-1);
        }

        visited = new boolean[n];
        depth = new int[n];
        visited[0] = true;
        p = new int[n];
        p[0] = -1;
        dfs(adj, 0);
        
        int log2ceil = 31 - Integer.numberOfLeadingZeros(adj.size());
        int[][] up = new int[adj.size()][log2ceil + 1];
        
        up[0][0] = 0;
        for (int i = 1; i < n; i++) up[i][0] = p[i];
        for (int j = 1; j <= log2ceil; j++) {
            for (int i = 0; i < adj.size(); i++) {
                int x = up[i][j-1];
                up[i][j] = x == 0 ? 0 : up[x][j-1];
            }
        }
        for (int mi = 0; mi < m; mi++) {
            String[] line = br.readLine().split(" ");
            int k = Integer.parseInt(line[0]);
            List<Integer> v = new ArrayList<>(k);
            for (int i = 1; i <= k; i++) v.add(Integer.parseInt(line[i])-1);
            int lowest = 0;
            for (int vi : v) {
                if (depth[vi] > depth[lowest]) lowest = vi;
            }
            boolean good = true;
            for (int u : v) {
                if (u == 0) continue;
                int diff = depth[lowest] - depth[u];
                if (kthAncestor(up, lowest, diff+1) != p[u]) {
                    good = false;
                    break;
                }
            }
            if (good) pw.println("YES");
            else pw.println("NO");
        }
    }
    
    int kthAncestor(int[][] up, int node, int k) {
        int curr = node;
        int j = up[0].length;
        while (curr != -1 && k > 0) {
            if ((1 << j) > k) j--;
            else {
                curr = up[curr][j];
                k -= (1 << j);
            }
        }
        return curr;
    }

    boolean[] visited;
    int[] depth;
    int[] p;
    void dfs(List<List<Integer>> adj, int u) {
        for (int v : adj.get(u)) {
            if (!visited[v]) {
                visited[v] = true;
                depth[v] = depth[u] + 1;
                p[v] = u;
                dfs(adj, v);
            }
        }
    }
}
