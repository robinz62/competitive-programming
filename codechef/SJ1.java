import java.util.*;
import java.io.*;

public class SJ1 {
    public static void main(String[] args) throws IOException {
        SJ1 s = new SJ1();
        s.solve();
    }

    Map<Integer, Long> leafToAnswer = new TreeMap<>();
    List<List<Integer>> adjList = new ArrayList<>();
    long[] v = new long[100001];
    long[] m = new long[100001];

    public void solve() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int T = Integer.parseInt(br.readLine());
        for (int t = 0; t < T; t++) {
            leafToAnswer.clear();
            adjList.clear();
            int N = Integer.parseInt(br.readLine());
            for (int i = 0; i <= N; i++) {
                adjList.add(new ArrayList<>());
            }
            for (int i = 0; i < N - 1; i++) {
                String[] line = br.readLine().split(" ");
                int a = Integer.parseInt(line[0]);
                int b = Integer.parseInt(line[1]);
                adjList.get(a).add(b);
                adjList.get(b).add(a);
            }
            String[] line = br.readLine().split(" ");
            for (int i = 0; i < line.length; i++) {
                v[i + 1] = Long.parseLong(line[i]);
            }
            line = br.readLine().split(" ");
            for (int i = 0; i < line.length; i++) {
                m[i + 1] = Long.parseLong(line[i]);
            }

            dfs(1, -1, v[1]);

            StringBuilder sb = new StringBuilder();
            for (Map.Entry<Integer, Long> e : leafToAnswer.entrySet()) {
                sb.append(e.getValue()).append(" ");
            }
            System.out.println(sb.toString());
        }

        br.close();
    }

    public void dfs(int node, int parent, long gcd) {
        gcd = gcd(gcd, v[node]);
        if (parent != -1 && adjList.get(node).size() <= 1) {
            gcd = gcd(gcd, m[node]);
            leafToAnswer.put(node, m[node] - gcd);
            return;
        }
        for (int u : adjList.get(node)) {
            if (u != parent) {
                dfs(u, node, gcd);
            }
        }
    }

    public static long gcd(long u, long v) {
        int shift = 0;
        if (u == 0) return v;
        if (v == 0) return u;
        while (((u | v) & 1) == 0) {
            shift++;
            u >>= 1;
            v >>= 1;
        }
     
        while ((u & 1) == 0) u >>= 1;
        do {
            while ((v & 1) == 0) v >>= 1;
            if (u > v) {
                long t = v; v = u; u = t;
            }
            v -= u;
        } while (v != 0);

        return u << shift;
    }
}