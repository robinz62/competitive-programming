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
        int[] nm = ril(2);
        int n = nm[0];
        int m = nm[1];
        List<List<Integer>> adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            int[] uv = ril(2);
            adj.get(uv[0]-1).add(uv[1]-1);
            adj.get(uv[1]-1).add(uv[0]-1);
        }

        // Do a BFS
        // Answer = # safe edges / total # paths
        boolean[] visited = new boolean[n];
        long[] numToHere = new long[n];
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        List<List<Integer>> ps = new ArrayList<>(n);
        for (int i = 0; i < n; i++) ps.add(new ArrayList<>());
        Deque<Integer> q = new ArrayDeque<>();
        visited[0] = true;
        q.addLast(0);
        dist[0] = 0;
        numToHere[0] = 1;
        while (!q.isEmpty()) {
            int sz = q.size();
            for (int i = 0; i < sz; i++) {
                int u = q.removeFirst();
                for (int v : adj.get(u)) {
                    if (visited[v]) {
                        if (dist[v] == dist[u] + 1) {
                            ps.get(v).add(u);
                            numToHere[v] += numToHere[u];
                        }
                    } else {
                        visited[v] = true;
                        dist[v] = dist[u] + 1;
                        ps.get(v).add(u);
                        numToHere[v] += numToHere[u];
                        q.addLast(v);
                    }
                }
            }
        }

        // Prune out nodes that don't lead to n-1
        boolean[] canReachEnd = new boolean[n];
        long[] numToHereUp = new long[n];
        canReachEnd[n-1] = true;
        numToHereUp[n-1] = 1;
        q.addLast(n-1);
        while (!q.isEmpty()) {
            int v = q.removeFirst();
            for (int u : ps.get(v)) {
                if (!canReachEnd[u]) {
                    canReachEnd[u] = true;
                    q.addLast(u);
                    numToHereUp[u] += numToHereUp[v];
                } else {
                    numToHereUp[u] += numToHereUp[v];
                }
            }
        }

        double ans = 0;
        for (int i = 0; i < n; i++) {
            if (!canReachEnd[i]) continue;
            long ways = numToHere[i] * numToHereUp[i];
            long mult = i == 0 || i == n-1 ? 1 : 2;
            ans = Math.max(ans, (double) ways * mult / numToHere[n-1]);
        }
        
        printDouble(ans);
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