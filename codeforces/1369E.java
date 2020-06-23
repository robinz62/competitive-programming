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

    void solve() throws IOException {
        int[] nm = ril();
        int n = nm[0];
        int m = nm[1];
        int[] w = ril();
        int[][] xy = new int[m][];
        List<Set<Integer>> adj = new ArrayList<>(n);  // food -> people
        for (int i = 0; i < n; i++) adj.add(new HashSet<>());
        for (int i = 0; i < m; i++) {
            xy[i] = ril();
            xy[i][0]--;
            xy[i][1]--;
            adj.get(xy[i][0]).add(i);
            adj.get(xy[i][1]).add(i);
        }
        List<Integer> ans = new ArrayList<>();
        Deque<Integer> foodsQ = new ArrayDeque<>();
        boolean[] visited = new boolean[n];
        for (int i = 0; i < n; i++) {
            if (adj.get(i).size() <= w[i]) {
                visited[i] = true;
                foodsQ.addLast(i);
            }
        }
        while (!foodsQ.isEmpty()) {
            int i = foodsQ.removeFirst();
            List<Integer> ppl = new ArrayList<>(adj.get(i));
            for (int p : ppl) {
                ans.add(p);
                adj.get(xy[p][0]).remove(p);
                adj.get(xy[p][1]).remove(p);
                if (!visited[xy[p][0]] && adj.get(xy[p][0]).size() <= w[xy[p][0]]) {
                    visited[xy[p][0]] = true;
                    foodsQ.addLast(xy[p][0]);
                }
                if (!visited[xy[p][1]] && adj.get(xy[p][1]).size() <= w[xy[p][1]]) {
                    visited[xy[p][1]] = true;
                    foodsQ.addLast(xy[p][1]);
                }
            }
        }
        if (ans.size() != m) {
            pw.println("DEAD");
            return;
        }
        Collections.reverse(ans);
        for (int p : ans) {
            if (w[xy[p][0]] == 0 && w[xy[p][1]] == 0) {
                pw.println("DEAD");
                return;
            }
            w[xy[p][0]] = Math.max(w[xy[p][0]] - 1, 0);
            w[xy[p][1]] = Math.max(w[xy[p][1]] - 1, 0);
        }
        pw.println("ALIVE");
        for (int p : ans) pw.print((p+1) + " ");
    }
}