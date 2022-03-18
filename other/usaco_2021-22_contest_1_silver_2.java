import java.util.*;
import java.io.*;
import java.math.BigInteger;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);

        int T = Integer.parseInt(br.readLine());
        for (int Ti = 0; Ti < T; Ti++) {
            String[] line = br.readLine().split(" ");
            int n = Integer.parseInt(line[0]);
            int m = Integer.parseInt(line[1]);
            List<List<Integer>> adj = new ArrayList<>(n);
            for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
            for (int i = 0; i < m; i++) {
                line = br.readLine().split(" ");
                int u = Integer.parseInt(line[0]);
                int v = Integer.parseInt(line[1]);
                adj.get(u-1).add(v-1);
                adj.get(v-1).add(u-1);
            }

            // Compute the connected components.
            // Linking two connected components costs: the min difference between any 2 items
            //   (easily computable in loglinear time, not sure if better possible)
            //    ^ iterate through the smaller set for lower_bound and upper_bound in the other.

            int[] id = new int[n];  // id[i] is the cc of i.
            Arrays.fill(id, -1);
            for (int i = 0; i < n; i++) {
                if (id[i] == -1) dfs(i, id, adj, i);
            }

            Map<Integer, TreeSet<Integer>> ccs = new HashMap<>();
            for (int i = 0; i < n; i++) {
                if (!ccs.containsKey(id[i])) ccs.put(id[i], new TreeSet<>());
                ccs.get(id[i]).add(i);
            }

            // The answer is (besides already solved):
            // 1) directly connect components containing 1 and N
            // 2) find the intermediate component to go through, say C. N^2 solution is just naively try them all.
            if (id[0] == id[n-1]) {
                pw.println(0);
                continue;
            }

            long ans = Long.MAX_VALUE;

            int idstart = id[0];
            int idend = id[n-1];
            TreeSet<Integer> startcc = ccs.get(idstart);
            TreeSet<Integer> endcc = ccs.get(idend);
            ans = dist(startcc, endcc);
            
            // INEFFICIENT
            for (int cc : ccs.keySet()) {
                if (cc == idstart || cc == idend) continue;
                TreeSet<Integer> me = ccs.get(cc);
                ans = Math.min(ans, dist(startcc, me) + dist(me, endcc));
            }

            pw.println(ans);
        }
        pw.flush();
    }

    static long dist(TreeSet<Integer> t1, TreeSet<Integer> t2) {
        if (t1.size() > t2.size()) {
            TreeSet<Integer> temp = t1;
            t1 = t2;
            t2 = temp;
        }
        long ans = Long.MAX_VALUE;
        for (int u : t1) {
            Integer lower = t2.lower(u);
            if (lower != null) ans = Math.min(ans, (long) (u - lower) * (u - lower));
            Integer upper = t2.higher(u);
            if (upper != null) ans = Math.min(ans, (long) (upper - u) * (upper - u));
        }
        return ans;
    }

    static void dfs(int u, int[] id, List<List<Integer>> adj, int x) {
        id[u] = x;
        for (int v : adj.get(u)) {
            if (id[v] != -1) continue;
            dfs(v, id, adj, x);
        }
    }
}