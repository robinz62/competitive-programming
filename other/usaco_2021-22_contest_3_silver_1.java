import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);

        n = Integer.parseInt(br.readLine());
        int[][] preferences = new int[n][n];
        for (int i = 0; i < n; i++) {
            String[] line = br.readLine().split(" ");
            for (int j = 0; j < n; j++) preferences[i][j] = Integer.parseInt(line[j]) - 1;
        }

        // cow -> gift -> index of that gift for cow
        int[][] map = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                map[i][preferences[i][j]] = j;
            }
        }

        boolean[][] adj = new boolean[n][n];
        for (int cow = 0; cow < n; cow++) {
            int idx = map[cow][cow];
            for (int i = idx-1; i >= 0; i--) {
                adj[cow][preferences[cow][i]] = true;
            }
        }
        
        path = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            dfs(i, i, adj, new boolean[n]);
        }

        for (int cow = 0; cow < n; cow++) {
            int idx = map[cow][cow];

            boolean sickoMode = false;

            // can cow 0 get gift Y?
            for (int i = 0; i < idx; i++) {
                int Y = preferences[cow][i];
                
                // Is there a path from Y to 0?
                // if (dfs(Y, cow, adj, new boolean[n])) {
                //     pw.println(Y+1);
                //     sickoMode = true;
                //     break;
                // }
                if (path[Y][cow]) {
                    pw.println(Y+1);
                    sickoMode = true;
                    break;
                }
            }

            if (!sickoMode) {
                pw.println(cow+1);
            }
        }

        pw.flush();
    }

    static int n;
    static boolean[][] path;
    static void dfs(int u, int src, boolean[][] adj, boolean[] visited) {
        path[src][u] = true;
        visited[u] = true;
        for (int v = 0; v < n; v++) {
            if (visited[v]) continue;
            if (adj[u][v]) dfs(v, src, adj, visited);
        }
    }
}