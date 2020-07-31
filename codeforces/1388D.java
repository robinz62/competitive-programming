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
        int n = ri();
        a = rll(n);
        int[] b = ril(n);  // edges
        for (int i = 0; i < n; i++) if (b[i] != -1) b[i]--;
        ans = 0;

        adj = new ArrayList<>(n);  // prereq edges
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int i = 0; i < n; i++) {
            if (b[i] != -1) adj.get(b[i]).add(i);
        }

        // do myself before doing negative guys behind me
        // do positive guys pumping into me before myself
        memo = new long[n];
        Arrays.fill(memo, Long.MIN_VALUE);
        edges = new ArrayList<>(n);
        for (int i = 0; i < n; i++) edges.add(new ArrayList<>());

        for (int i = 0; i < n; i++) {
            if (memo[i] == Long.MIN_VALUE) {
                dfs(i);
            }
        }

        pw.println(ans);
        
        topo = new ArrayList<>();
        visited = new boolean[n];
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                visited[i] = true;
                dfs2(i);
            }
        }
        Collections.reverse(topo);
        for (int i : topo) {
            pw.print((i+1) + " ");
        }
    }

    List<Integer> topo;
    boolean[] visited;
    void dfs2(int u) {
        for (int v : edges.get(u)) {
            if (!visited[v]) {
                visited[v] = true;
                dfs2(v);
            }
        }
        topo.add(u);
    }

    long[] a;
    long ans;
    List<List<Integer>> adj;
    long[] memo;

    List<List<Integer>> edges;  // a -> b means do b before a

    void dfs(int u) {
        long myVal = a[u];
        for (int v : adj.get(u)) {
            if (memo[v] == Long.MIN_VALUE) {
                dfs(v);
            }
            if (memo[v] < 0) {
                edges.get(u).add(v);
            } else {
                myVal += memo[v];
                edges.get(v).add(u);
            }
        }
        ans += myVal;
        memo[u] = myVal;
    }
}