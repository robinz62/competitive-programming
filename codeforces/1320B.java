import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pw = new PrintWriter(System.out);
    static int MOD = 998244353;

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
        for (int i = 0; i < m; i++) {
            int[] uv = ril(2);
            adj.get(uv[1]-1).add(uv[0]-1);
        }
        int k = ri();
        int[] p = ril(k);
        for (int i = 0; i < k; i++) p[i]--;

        Deque<Integer> q = new ArrayDeque<>();
        boolean[] visited = new boolean[n];
        int[] dist = new int[n];
        int[] optFromHere = new int[n];
        int t = p[k-1];
        visited[t] = true;
        dist[t] = 0;
        optFromHere[t] = 0;
        q.addLast(t);
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                int u = q.removeFirst();
                for (int v : adj.get(u)) {
                    if (!visited[v]) {
                        visited[v] = true;
                        dist[v] = dist[u] + 1;
                        optFromHere[v]++;
                        q.addLast(v);
                    } else {
                        if (dist[u] == dist[v] - 1) optFromHere[v]++;
                    }
                }
            }
        }

        int ansMin = 0;
        int ansMax = 0;
        for (int i = 0; i < k-1; i++) {
            int a = p[i];
            int b = p[i+1];
            if (dist[b] != dist[a] - 1) ansMin++;

            // for ansMax, rebuild if take non-optimal path
            // or take optimal path, but there is also another optimal
            // path to take from node a
            if (dist[b] != dist[a] - 1) ansMax++;
            else if (optFromHere[a] > 1) ansMax++;
        }

        pw.println(ansMin + " " + ansMax);
    }
}