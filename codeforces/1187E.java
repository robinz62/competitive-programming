import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static int MOD = 1000000007;

    // After writing solution, quick scan for:
    //   array out of bounds
    //   special cases e.g. n=1?
    //
    // Big numbers arithmetic bugs:
    //   int overflow
    //   sorting, or taking max, after MOD
    void solve() throws IOException {
        n = ri();
        List<List<Integer>> adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int i = 0; i < n-1; i++) {
            int[] uv = ril(2);
            adj.get(uv[0]-1).add(uv[1]-1);
            adj.get(uv[1]-1).add(uv[0]-1);
        }
        visited = new boolean[n];
        size = new long[n];
        ans1 = new long[n];
        visited[0] = true;
        dfs1(adj, 0);

        Arrays.fill(visited, false);
        ans2 = new long[n];
        ans2[0] = ans1[0];
        for (int v : adj.get(0)) {
            dfs2(adj, v, 0);
        }

        long max = 0;
        for (long x : ans2) max = Math.max(max, x);
        pw.println(max);
    }

    int n;
    boolean[] visited;
    long[] size;  // size[i] is the size of subtree at i
    long[] ans1;  // ans1[i] is the answer if 0 is the root

    long[] ans2;  // ans2[i] is the answer if i is the root

    void dfs1(List<List<Integer>> adj, int u) {
        size[u] = 1;
        int ans = 0;
        for (int v : adj.get(u)) {
            if (!visited[v]) {
                visited[v] = true;
                dfs1(adj, v);
                size[u] += size[v];
                ans1[u] += ans1[v];
            }
        }
        ans1[u] += size[u];
    }

    void dfs2(List<List<Integer>> adj, int u, int p) {
        ans2[p] = ans2[p] - ans1[u] - n + n - size[u];
        ans2[u] = ans1[u] - size[u] + n + ans2[p];
        for (int v : adj.get(u)) {
            if (v != p) dfs2(adj, v, u);
        }
        ans2[p] = ans2[p] + ans1[u] + n - n + size[u];
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
}