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
        int t = ri();
        for (int ti = 0; ti < t; ti++) {
            int[] nm = ril(2);
            int n = nm[0];
            int m = nm[1];
            p = ril(n);
            h = ril(n);
            adj = new ArrayList<>(n);
            for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
            for (int i = 0; i < n - 1; i++) {
                int[] xy = ril(2);
                adj.get(xy[0]-1).add(xy[1]-1);
                adj.get(xy[1]-1).add(xy[0]-1);
            }

            solution = new int[n][];  // city -> [happy, unhappy]
            visited = new boolean[n];

            visited[0] = true;
            dfs(0);
            if (solution[0] == null) {
                pw.println("NO");
            } else {
                pw.println("YES");
            }
        }
    }

    int[] p;
    int[] h;
    List<List<Integer>> adj;
    boolean[] visited;
    int[][] solution;

    // returns population of subtree rooted here
    void dfs(int u) {
        int[] here = new int[2];
        for (int v : adj.get(u)) {
            if (visited[v]) continue;  // parent
            visited[v] = true;
            dfs(v);
            if (solution[v] == null) {
                solution[u] = null;
                return;
            }
            here[0] += solution[v][0];
            here[1] += solution[v][1];
        }
        // right now, 'here' and 'pop' represent the cumulative
        // happy/unhappy among my subtrees, adding to pop

        // need to utilize pHere in order to get children H
        // to wantH. we can additionally turn unhappy children
        // into happy ones here. it is always better to keep
        // people unhappy, because we can go unhappy -> happy
        // but not the other way around
        int childrenH = here[0] - here[1];
        if (childrenH == h[u]) {
            if (p[u] % 2 == 0) {
                here[0] += p[u] / 2;
                here[1] += p[u] / 2;
                solution[u] = here;
                return;
            }
            solution[u] = null;
            return;
        }

        // preliminarily, set all to unhappy
        here[1] += p[u];

        // can't lower happiness any more
        if (h[u] < (here[0] - here[1])) {
            solution[u] = null;
            return;
        }

        int raise = h[u] - (here[0] - here[1]);
        // moving 1 unhappy to happy increases h by 2.
        if (raise % 2 != 0) {
            solution[u] = null;
            return;
        }
        int moveOver = raise / 2;
        if (moveOver > here[1]) {
            solution[u] = null;
            return;
        }
        here[0] += moveOver;
        here[1] -= moveOver;
        solution[u] = here;
        return;
    }
}