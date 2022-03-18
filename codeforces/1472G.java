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
        int T = ri();
        for (int Ti = 0; Ti < T; Ti++) {
            rs();
            int[] nm = ril(2);
            int n = nm[0];
            int m = nm[1];
            List<List<Integer>> adj = new ArrayList<>(n);
            for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
            for (int i = 0; i < m; i++) {
                int[] uv = ril(2);
                adj.get(uv[0]-1).add(uv[1]-1);
            }

            int[] d = new int[n];
            Deque<Integer> q = new ArrayDeque<>();
            boolean[] visited = new boolean[n];
            visited[0] = true;
            q.addLast(0);
            int dist = 0;
            while (!q.isEmpty()) {
                int sz = q.size();
                for (int i = 0; i < sz; i++) {
                    int u = q.removeFirst();
                    d[u] = dist;
                    for (int v : adj.get(u)) {
                        if (visited[v]) continue;
                        visited[v] = true;
                        q.addLast(v);
                    }
                }
                dist++;
            }

            // We can consider the edges of operation 1 to form a DAG. For each node of this DAG,
            // we should look at its outgoing edges of type 2 to see the best one it can reach.
            // Then use DAG dp to compute best for each.

            int[] op2 = new int[n];
            for (int i = 0; i < n; i++) {
                op2[i] = d[i];  // corresponds to doing nothing
                for (int v : adj.get(i)) op2[i] = Math.min(op2[i], d[v]);
            }

            ans = new int[n];
            dfs(0, adj, d, new boolean[n], op2);

            for (int ai : ans) pw.print(ai + " ");
            pw.println();
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    int[] ans;
    void dfs(int u, List<List<Integer>> adj, int[] d, boolean[] visited, int[] op2) {
        visited[u] = true;
        ans[u] = op2[u];
        for (int v : adj.get(u)){ 
            if (d[v] <= d[u]) continue;
            if (!visited[v]) dfs(v, adj, d, visited, op2);
            ans[u] = Math.min(ans[u], ans[v]);
        }
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