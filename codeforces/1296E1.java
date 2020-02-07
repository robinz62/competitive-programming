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
        int n = readInt();
        char[] s = br.readLine().toCharArray();
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (s[i] > s[j]) {
                    adj.get(i).add(j);
                    adj.get(j).add(i);
                }
            }
        }
        int[] color = bfs(adj);
        if (color == null) pw.println("NO");
        else {
            pw.println("YES");
            for (int c : color) pw.print(c-1);
            pw.println();
        }
    }

    int[] bfs(List<List<Integer>> adj) {
        int n = adj.size();
        int[] color = new int[n];
        Deque<Integer> q = new ArrayDeque<>();
        for (int s = 0; s < n; s++) {
            if (color[s] != 0) continue;
            color[s] = 1;
            q.addLast(s);
            while (!q.isEmpty()) {
                int u = q.removeFirst();
                for (int v : adj.get(u)) {
                    if (color[v] == color[u]) return null;
                    if (color[v] == 0) {
                        color[v] = 3 - color[u];
                        q.addLast(v);
                    }
                }
            }
        }
        
        return color;
    }
}