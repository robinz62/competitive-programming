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
    //
    // Interactive problems: don't forget to flush between test cases
    void solve() throws IOException {
        int T = ri();
        for (int Ti = 0; Ti < T; Ti++) {
            int[] nm = ril(2);
            int n = nm[0];
            m = nm[1];
            int[] a = ril(n);
            for (int i = 0; i < n; i++) a[i]--;

            List<List<Integer>> adj = new ArrayList<>(n);
            for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
            for (int i = 0; i < n; i++) {
                if (a[a[i]] != i || a[i] < i) {
                    adj.get(i).add(a[i]);
                    adj.get(a[i]).add(i);
                }
            }

            long[] powM = new long[n+10];
            powM[0] = 1;
            for (int i = 1; i < powM.length; i++) powM[i] = powM[i-1] * (m-1) % MOD;

            parent = new int[n];
            depth = new int[n];
            visited = new boolean[n];
            cycles = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if (visited[i]) continue;
                parent[i] = -1;
                dfs(i, adj);
            }

            boolean[] inCycle = new boolean[n];
            for (List<Integer> cycle : cycles) for (int u : cycle) inCycle[u] = true;

            long ans = 1;
            Arrays.fill(visited, false);
            for (List<Integer> cycle : cycles) {
                if (m == 2) {
                    if (cycle.size() % 2 != 0) {
                        ans = 0;
                        break;
                    }
                    ans = ans * 2 % MOD;
                    for (int c : cycle) {
                        visited[c] = true;
                        for (int v : adj.get(c)) {
                            if (inCycle[v]) continue;
                            long ways = dfs2(v, adj, m-1);
                            ans = ans * ways % MOD;
                        }
                    }
                    continue;
                }

                boolean dir = true;
                long magic = 0;
                for (int idx = cycle.size(); idx > 3; idx--) {
                    long add = m * powM[idx-1] % MOD;
                    if (!dir) add = MOD - add;
                    magic = magic + add;
                    if (magic >= MOD) magic -= MOD;
                    dir = !dir;
                }
                long k3 = m * (m-1) * (m-2);
                if (dir) magic += k3;
                else magic += (MOD - k3);
                magic %= MOD;
                ans = ans * magic % MOD;

                for (int u : cycle) {
                    visited[u] = true;
                    for (int v : adj.get(u)) {
                        if (inCycle[v]) continue;
                        long ways = dfs2(v, adj, m-1);
                        ans = ans * ways % MOD;
                    }
                }
            }

            // remaining are trees not touching cycles
            for (int i = 0; i < n; i++) {
                if (visited[i]) continue;
                long ways = dfs2(i, adj, m);
                ans = ans * ways % MOD;
            }

            pw.println(ans);
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    int m;
    long dfs2(int u, List<List<Integer>> adj, int factor) {
        visited[u] = true;
        long ways = factor;
        for (int v : adj.get(u)) {
            if (visited[v]) continue;
            ways = ways * dfs2(v, adj, m-1) % MOD;
        }
        return ways;
    }

    int[] parent;
    int[] depth;
    boolean[] visited;
    List<List<Integer>> cycles;
    void dfs(int u, List<List<Integer>> adj) {
        visited[u] = true;
        for (int v : adj.get(u)) {
            if (v == parent[u]) continue;
            if (visited[v]) {
                if (depth[v] < depth[u]) {
                    int curr = u;
                    List<Integer> cycle = new ArrayList<>();
                    while (curr != v) {
                        cycle.add(curr);
                        curr = parent[curr];
                    }
                    cycle.add(v);
                    cycles.add(cycle);
                } else continue;
            } else {
                parent[v] = u;
                depth[v] = depth[u] + 1;
                dfs(v, adj);
            }
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