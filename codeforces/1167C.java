import java.io.*;
import java.util.*;
 
public class Main {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pw = new PrintWriter(System.out);
 
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
 
    int[] readIntLine() throws IOException {
        String[] tokens = br.readLine().split(" ");
        int[] A = new int[tokens.length];
        for (int i = 0; i < A.length; i++) A[i] = Integer.parseInt(tokens[i]);
        return A;
    }
 
    long[] readLongLine() throws IOException {
        String[] tokens = br.readLine().split(" ");
        long[] A = new long[tokens.length];
        for (int i = 0; i < A.length; i++) A[i] = Long.parseLong(tokens[i]);
        return A;
    }
 
    void solve() throws IOException {
        int[] line = readIntLine();
        int n = line[0];
        int m = line[1];
        int[][] groupToUsers = new int[m + 1][];
        Map<Integer, List<Integer>> userToGroups = new HashMap<>();
        for (int i = 1; i <= m; i++) {
            int[] g = readIntLine();
            groupToUsers[i] = new int[g.length - 1];
            for (int j = 1; j < g.length; j++) {
                if (!userToGroups.containsKey(g[j])) userToGroups.put(g[j], new ArrayList<>());
                userToGroups.get(g[j]).add(i);
                groupToUsers[i][j - 1] = g[j];
            }
        }
        UnionFind uf = new UnionFind(m);
        for (int user = 1; user <= n; user++) {
            List<Integer> groups = userToGroups.get(user);
            if (groups == null) continue;
            for (int i = 1; i < groups.size(); i++) {
                uf.union(groups.get(0) - 1, groups.get(i) - 1);
            }
        }
        Map<Integer, Set<Integer>> superGroupToPeople = new HashMap<>();
        for (int i = 1; i <= m; i++) {
            int p = uf.find(i - 1) + 1;
            if (!superGroupToPeople.containsKey(p)) superGroupToPeople.put(p, new HashSet<>());
            Set<Integer> ppl = superGroupToPeople.get(p);
            for (int user : groupToUsers[i]) {
                ppl.add(user);
            }
        }

        for (int i = 1; i <= n; i++) {
            if (!userToGroups.containsKey(i)) {
                pw.print("1 ");
            } else {
                int p = uf.find(userToGroups.get(i).get(0) - 1) + 1;
                pw.print(superGroupToPeople.get(p).size() + " ");
            }
        }
    }
}

class UnionFind {
    private int n;
    private int numComponents;
    private int[] parent;
    private int[] rank;

    public UnionFind(int n) {
        this.n = numComponents = n;
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }

    public void union(int u, int v) {
        int x = find(u);
        int y = find(v);
        if (x == y) return;
        if (rank[x] < rank[y]) {
            parent[x] = y;
        }
        else if (rank[x] > rank[y]) {
            parent[y] = x;
        }
        else {
            parent[x] = y;
            rank[y]++;
        }
        numComponents--;
    }

    public int find(int u) {
        int current = u;
        List<Integer> toUpdate = new ArrayList<>();
        while (parent[current] != current) {
            toUpdate.add(current);
            current = parent[current];
        }
        for (Integer node : toUpdate) parent[node] = current;
        return current;
    }

    public int getNumComponents() {
        return numComponents;
    }
}