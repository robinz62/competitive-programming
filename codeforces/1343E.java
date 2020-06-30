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

    void solve() throws IOException {
        int t = ri();
        for (int ti = 0; ti < t; ti++) {
            int[] nmabc = ril(5);
            int n = nmabc[0];
            int m = nmabc[1];
            int a = nmabc[2]-1;
            int b = nmabc[3]-1;
            int c = nmabc[4]-1;
            int[] p = ril(m);
            Arrays.sort(p);
            List<List<Integer>> adj = new ArrayList<>(n);
            for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
            for (int i = 0; i < m; i++) {
                int[] uv = ril(2);
                adj.get(uv[0]-1).add(uv[1]-1);
                adj.get(uv[1]-1).add(uv[0]-1);
            }

            long[] prefix = new long[m];
            prefix[0] = p[0];
            for (int i = 1; i < m; i++) {
                prefix[i] = prefix[i-1] + p[i];
            }
            
            int[] distFromA = bfs(adj, a);
            int[] distFromB = bfs(adj, b);
            int[] distFromC = bfs(adj, c);
            long best = Long.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                int overlap = distFromB[i];
                int notoverlap = distFromA[i] + distFromC[i];
                if (overlap + notoverlap - 1 >= prefix.length) continue;
                long overlapcost = (overlap-1 >= 0 ? prefix[overlap-1] : 0) * 2;
                long notoverlapcost = overlap+notoverlap-1 >= 0 ? prefix[overlap+notoverlap-1] - overlapcost / 2 : 0;
                long cost = overlapcost + notoverlapcost;
                best = Math.min(best, cost);
            }
            pw.println(best);
        }
    }

    int[] bfs(List<List<Integer>> adj, int r) {
        int n = adj.size();
        boolean[] visited = new boolean[n];
        int[] dist = new int[n];
        Deque<Integer> q = new ArrayDeque<>();
        q.addLast(r);
        visited[r] = true;
        dist[r] = 0;
        while (!q.isEmpty()) {
            int u = q.removeFirst();
            for (int v : adj.get(u)) {
                if (!visited[v]) {
                    visited[v] = true;
                    dist[v] = dist[u] + 1;
                    q.addLast(v);
                }
            }
        }
        return dist;
    }
}