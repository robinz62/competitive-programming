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
        int t = readInt();
        for (int ti = 0; ti < t; ti++) {
            int[] nm = readIntLine();
            int n = nm[0];
            int m = nm[1];
            int[][] A = new int[n][];
            for (int i = 0; i < n; i++) A[i] = readIntLine();
            UnionFind uf = new UnionFind(n * m);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    int dist = i + j;
                    for (int dy = 0; dy <= dist; dy++) {
                        int r = n-1 - (dist - dy);
                        int c = m-1 - dy;
                        if (r < i || c < j) continue;
                        pw.flush();
                        uf.union(i*m+j, r*m+c);
                    }
                }
            }
            Map<Integer, List<Integer>> components = new HashMap<>();
            for (int i = 0; i < n * m; i++) {
                int p = uf.find(i);
                if (!components.containsKey(p)) components.put(p, new ArrayList<>());
                components.get(p).add(i);
            }
            int ans = 0;
            for (Integer x : components.keySet()) {
                int count0 = 0;
                int count1 = 0;
                for (int y : components.get(x)) {
                    int r = y / m;
                    int c = y % m;
                    if (A[r][c] == 0) count0++;
                    else count1++;
                }
                ans += Math.min(count0, count1);
            }
            pw.println(ans);
        }
    }
}

class UnionFind {
    int n;
    int numComponents;
    int[] parent;
    int[] rank;

    public UnionFind(int n) {
        this.n = numComponents = n;
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;
    }

    void union(int u, int v) {
        int x = find(u);
        int y = find(v);
        if (x == y) return;
        if (rank[x] < rank[y]) parent[x] = y;
        else if (rank[x] > rank[y]) parent[y] = x;
        else {
            parent[x] = y;
            rank[y]++;
        }
        numComponents--;
    }

    int find(int u) {
        int current = u;
        List<Integer> toUpdate = new ArrayList<>();
        while (parent[current] != current) {
            toUpdate.add(current);
            current = parent[current];
        }
        for (Integer node : toUpdate) parent[node] = current;
        return current;
    }

    int getNumComponents() {
        return numComponents;
    }
}