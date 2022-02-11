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
    void solve() throws IOException {
        int n = ri();
        List<List<Integer>> adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        int[][] edges = new int[n-1][];
        for (int i = 0; i < n-1; i++) {
            int[] uv = ril(2);
            uv[0]--; uv[1]--;
            edges[i] = uv;
            adj.get(uv[0]).add(uv[1]);
            adj.get(uv[1]).add(uv[0]);
        }

        int ans = 0;
        boolean[] visited = new boolean[n];
        for (int[] ban : edges) {
            int u = ban[0];
            int v = ban[1];
            depthOfBest = -1;
            Arrays.fill(visited, false);
            visited[v] = true;
            visited[u] = true;
            dfs(u, 0, adj, visited);
            depthOfBest = -1;
            Arrays.fill(visited, false);
            visited[v] = true;
            visited[best] = true;
            dfs(best, 0, adj, visited);
            int x = depthOfBest;

            depthOfBest = -1;
            Arrays.fill(visited, false);
            visited[u] = true;
            visited[v] = true;
            dfs(v, 0, adj, visited);
            depthOfBest = -1;
            Arrays.fill(visited, false);
            visited[u] = true;
            visited[best] = true;
            dfs(best, 0, adj, visited);
            int y = depthOfBest;

            ans = Math.max(ans, x * y);
        }
        pw.println(ans);
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    int best;
    int depthOfBest;
    void dfs(int u, int depth, List<List<Integer>> adj, boolean[] visited) {
        if (depth > depthOfBest) {
            best = u;
            depthOfBest = depth;
        }
        for (int v : adj.get(u)) {
            if (visited[v]) continue;
            visited[v] = true;
            dfs(v, depth+1, adj, visited);
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