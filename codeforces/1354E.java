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

    int[] ril() throws IOException {
        String[] tokens = br.readLine().split(" ");
        int[] A = new int[tokens.length];
        for (int i = 0; i < A.length; i++)
            A[i] = Integer.parseInt(tokens[i]);
        return A;
    }

    long[] rll() throws IOException {
        String[] tokens = br.readLine().split(" ");
        long[] A = new long[tokens.length];
        for (int i = 0; i < A.length; i++)
            A[i] = Long.parseLong(tokens[i]);
        return A;
    }

    boolean[] visited;
    void solve() throws IOException {
        int[] nm = ril();
        int n = nm[0];
        int m = nm[1];
        int[] ni = ril();
        int n1 = ni[0];
        int n2 = ni[1];
        int n3 = ni[2];
        List<List<Integer>> adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            int[] uv = ril();
            adj.get(uv[0]-1).add(uv[1]-1);
            adj.get(uv[1]-1).add(uv[0]-1);
        }

        visited = new boolean[n];
        List<Set<Integer>> redsList = new ArrayList<>();
        List<Set<Integer>> bluesList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                reds = new HashSet<>();
                blues = new HashSet<>();
                visited[i] = true;
                reds.add(i);
                int ret = dfs(adj, i, true);
                if (ret == -1) {
                    pw.println("NO");
                    return;
                }
                redsList.add(reds);
                bluesList.add(blues);
            }
        }

        // dp[i][j] is true if using pairs [0..i] we can have
        // sum of j
        boolean[][] dp = new boolean[redsList.size()][n+1];
        dp[0][redsList.get(0).size()] = dp[0][bluesList.get(0).size()] = true;
        for (int i = 1; i < redsList.size(); i++) {
            int numR = redsList.get(i).size();
            int numB = bluesList.get(i).size();
            for (int j = 0; j <= n2; j++) {
                if (j-numR >= 0 && dp[i-1][j-numR]) {
                    dp[i][j] = true;
                    continue;
                }
                if (j-numB >= 0 && dp[i-1][j-numB]) {
                    dp[i][j] = true;
                    continue;
                }
            }
        }

        if (!dp[redsList.size()-1][n2]) {
            pw.println("NO");
            return;
        }
        pw.println("YES");
        int[] color = new int[n];
        int j = n2;
        for (int i = redsList.size() - 1; i > 0; i--) {
            int numR = redsList.get(i).size();
            int numB = bluesList.get(i).size();
            if (j-numR >= 0 && dp[i-1][j-numR]) {
                for (int x : redsList.get(i)) color[x] = 2;
                j -= numR;
            } else {
                for (int x : bluesList.get(i)) color[x] = 2;
                j -= numB;
            }
        }
        if (j == redsList.get(0).size()) {
            for (int x : redsList.get(0)) color[x] = 2;
        } else {
            for (int x : bluesList.get(0)) color[x] = 2;
        }
        for (int i = 0; i < color.length; i++) {
            if (color[i] == 2) continue;
            if (n1 > 0) {
                color[i] = 1;
                n1--;
            } else {
                color[i] = 3;
                n3--;
            }
        }
        for (int c : color) pw.print(c);
        pw.println();
    }

    Set<Integer> reds;
    Set<Integer> blues;
    int dfs(List<List<Integer>> adj, int u, boolean red) {
        for (int v : adj.get(u)) {
            if (!visited[v]) {
                visited[v] = true;
                if (red) blues.add(v);
                else reds.add(v);
                dfs(adj, v, !red);
            } else {
                if (reds.contains(v) && red || blues.contains(v) && !red) return -1;
            }
        }
        return 0;
    }
}