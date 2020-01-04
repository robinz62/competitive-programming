import java.util.*;
import java.io.*;

public class SUBREM {
    public static void main(String[] args) throws IOException {
        SUBREM s = new SUBREM();
        s.solve();
    }

    public void solve() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int T = Integer.parseInt(br.readLine());
        for (int t = 0; t < T; t++) {
            String[] tokens = br.readLine().split(" ");
            int N = Integer.parseInt(tokens[0]);
            long X = Long.parseLong(tokens[1]);
            long[] A = new long[N + 1];
            tokens = br.readLine().split(" ");
            List<List<Integer>> adjList = new ArrayList<>();
            adjList.add(new ArrayList<>());
            for (int i = 1; i <= N; i++) {
                A[i] = Long.parseLong(tokens[i - 1]);
                adjList.add(new ArrayList<>());
            }
            for (int i = 0; i < N - 1; i++) {
                tokens = br.readLine().split(" ");
                int u = Integer.parseInt(tokens[0]);
                int v = Integer.parseInt(tokens[1]);
                adjList.get(u).add(v);
                adjList.get(v).add(u);
            }

            System.out.println(dfs(adjList, 1, -1, A, X));
        }

        br.close();
    }

    public long dfs(List<List<Integer>> adjList, int root, int parent, long[] A, long X) {
        if (parent != -1 && adjList.get(root).size() == 1) {
            return Math.max(A[root], -X);
        }
        long ans = A[root];
        for (int v : adjList.get(root)) {
            if (v == parent) {
                continue;
            }
            ans += Math.max(dfs(adjList, v, root, A, X), -X);
        }
        return Math.max(ans, -X);
    }
}