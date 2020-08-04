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
    boolean[] visited;
    int[] p;
    void solve() throws IOException {
        int n = ri();
        adj = new ArrayList<>(n);
        visited = new boolean[n];
        p = new int[n];
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int i = 0; i < n-1; i++) {
            int[] ab = ril(2);
            adj.get(ab[0]-1).add(ab[1]-1);
            adj.get(ab[1]-1).add(ab[0]-1);
        }
        dfs(1, 0);
        int a = x;
        Arrays.fill(visited, false);
        p[a] = -1;
        x = -1;
        d = -1;
        visited[a] = true;
        dfs(a, 0);
        int b = x;
        int ans = d;
        Arrays.fill(visited, false);
        int curr = b;
        List<Integer> diam = new ArrayList<>();
        while (curr != -1) {
            diam.add(curr);
            visited[curr] = true;
            curr = p[curr];
        }
        int c = -1;
        int dd = -1;
        for (int k : diam) {
            if (k == a || k == b) continue;
            x = -1;
            d = -1;
            dfs(k, 0);
            if (d > dd) {
                c = x;
                dd = d;
            }
        }
        ans += dd;
        pw.println(ans);
        pw.println((a+1) + " " + (b+1) + " " + (c+1));
    }

    int x = 1;
    int d = 0;
    void dfs(int u, int dist) {
        if (dist > d) {
            x = u;
            d = dist;
        }
        for (int v : adj.get(u)) {
            if (visited[v]) continue;
            p[v] = u;
            visited[v] = true;
            dfs(v, dist+1);
        }
    }
}