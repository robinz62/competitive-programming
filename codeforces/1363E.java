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

    int readInt() throws IOException {
        return Integer.parseInt(br.readLine());
    }

    long readLong() throws IOException {
        return Long.parseLong(br.readLine());
    }

    int[] readIntLine() throws IOException {
        String[] tokens = br.readLine().split(" ");
        int[] A = new int[tokens.length];
        for (int i = 0; i < A.length; i++)
            A[i] = Integer.parseInt(tokens[i]);
        return A;
    }

    long[] readLongLine() throws IOException {
        String[] tokens = br.readLine().split(" ");
        long[] A = new long[tokens.length];
        for (int i = 0; i < A.length; i++)
            A[i] = Long.parseLong(tokens[i]);
        return A;
    }

    int[][] abc;
    List<List<Integer>> adj;

    void solve() throws IOException {
        int n = readInt();
        adj = new ArrayList<>(n);
        abc = new int[n][];
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
            abc[i] = readIntLine();
        }
        for (int i = 0; i < n - 1; i++) {
            int[] uv = readIntLine();
            adj.get(uv[0]-1).add(uv[1]-1);
            adj.get(uv[1]-1).add(uv[0]-1);
        }
        int[] res = dfs(0, -1, Integer.MAX_VALUE);
        if (res[0] != 0 || res[1] != 0) pw.println("-1");
        else pw.println(cost);
    }

    long cost = 0;

    // returns [#0->1, #1->0]
    int[] dfs(int u, int p, int minCost) {
        minCost = Math.min(minCost, abc[u][0]);
        int[] delta = new int[2];
        if (abc[u][1] == 0 && abc[u][2] == 1) delta[0]++;
        if (abc[u][1] == 1 && abc[u][2] == 0) delta[1]++;
        for (int v : adj.get(u)) {
            if (v == p) continue;
            int[] sub = dfs(v, u, minCost);
            delta[0] += sub[0];
            delta[1] += sub[1];
        }
        int min = Math.min(delta[0], delta[1]);
        cost += (long) minCost * (min * 2);
        delta[0] -= min;
        delta[1] -= min;
        return delta;
    }
}