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

    void solve() throws IOException {
        int[] nmk = readIntLine();
        int n = nmk[0];
        int m = nmk[1];
        int k = nmk[2];
        int[] a = readIntLine();  // the k special fields
        for (int i = 0; i < a.length; i++) a[i]--;
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            int[] e = readIntLine();
            adj.get(e[0] - 1).add(e[1] - 1);
            adj.get(e[1] - 1).add(e[0] - 1);
        }
        int src = 0;
        int[] distFromSrc = new int[n];
        int d = 0;
        boolean[] visited = new boolean[n];
        Deque<Integer> q = new ArrayDeque<>();
        visited[src] = true;
        q.addLast(src);
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                int u = q.removeFirst();
                distFromSrc[u] = d;
                for (int v : adj.get(u)) {
                    if (!visited[v]) {
                        visited[v] = true;
                        q.addLast(v);
                    }
                }
            }
            d++;
        }

        int tgt = n - 1;
        int[] distFromTgt = new int[n];
        d = 0;
        Arrays.fill(visited, false);
        visited[tgt] = true;
        q.addLast(tgt);
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                int u = q.removeFirst();
                distFromTgt[u] = d;
                for (int v : adj.get(u)) {
                    if (!visited[v]) {
                        visited[v] = true;
                        q.addLast(v);
                    }
                }
            }
            d++;
        }

        List<Integer> special = new ArrayList<>();
        for (int ki : a) special.add(ki);
        Collections.sort(special, (k1, k2) -> Integer.compare(distFromSrc[k1], distFromSrc[k2]));

        int best = 0;
        for (int i = 0; i < special.size() - 1; i++) {
            int ki = special.get(i);
            int kj = special.get(i + 1);
            int shortestPath = Math.min(distFromSrc[tgt], distFromSrc[ki] + 1 + distFromTgt[kj]);
            best = Math.max(best, shortestPath);
        }

        pw.println(best);
    }
}