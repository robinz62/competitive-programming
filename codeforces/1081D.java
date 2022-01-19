import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static int MOD = 1000000007;

    // After writing solution, quick scan for:
    //   array out of bounds
    //   special cases e.g. n=1?
    //   npe, particularly in maps
    //
    // Big numbers arithmetic bugs:
    //   int overflow
    //   sorting, or taking max, after MOD
    void solve() throws IOException {
        int[] nmk = ril(3);
        int n = nmk[0];
        int m = nmk[1];
        int k = nmk[2];

        int[] x = ril(k);
        for (int i = 0; i < k; i++) x[i]--;

        UnionFind uf = new UnionFind(n);
        for (int xi : x) uf.isSpecial[xi] = true;

        int[][] edges = new int[m][];
        for (int i = 0; i < m; i++) {
            int[] uvw = ril(3);
            uvw[0]--; uvw[1]--;
            edges[i] = uvw;
        }
        Arrays.sort(edges, (e1, e2) -> Integer.compare(e1[2], e2[2]));

        for (int[] uvw : edges) {
            uf.union(uvw[0], uvw[1], uvw[2]);
        }

        for (int xi : x) {
            int leader = uf.find(xi);
            pw.print(uf.ans[leader] + " ");
        }
        pw.println();
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    // Template code below

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

    int ri() throws IOException {
        return Integer.parseInt(br.readLine().trim());
    }

    long rl() throws IOException {
        return Long.parseLong(br.readLine().trim());
    }

    int[] ril(int n) throws IOException {
        int[] nums = new int[n];
        int c = 0;
        for (int i = 0; i < n; i++) {
            int sign = 1;
            c = br.read();
            int x = 0;
            if (c == '-') {
                sign = -1;
                c = br.read();
            }
            while (c >= '0' && c <= '9') {
                x = x * 10 + c - '0';
                c = br.read();
            }
            nums[i] = x * sign;
        }
        while (c != '\n' && c != -1) c = br.read();
        return nums;
    }

    long[] rll(int n) throws IOException {
        long[] nums = new long[n];
        int c = 0;
        for (int i = 0; i < n; i++) {
            int sign = 1;
            c = br.read();
            long x = 0;
            if (c == '-') {
                sign = -1;
                c = br.read();
            }
            while (c >= '0' && c <= '9') {
                x = x * 10 + c - '0';
                c = br.read();
            }
            nums[i] = x * sign;
        }
        while (c != '\n' && c != -1) c = br.read();
        return nums;
    }

    int[] rkil() throws IOException {
        int sign = 1;
        int c = br.read();
        int x = 0;
        if (c == '-') {
            sign = -1;
            c = br.read();
        }
        while (c >= '0' && c <= '9') {
            x = x * 10 + c - '0';
            c = br.read();
        }
        return ril(x);
    }

    long[] rkll() throws IOException {
        int sign = 1;
        int c = br.read();
        int x = 0;
        if (c == '-') {
            sign = -1;
            c = br.read();
        }
        while (c >= '0' && c <= '9') {
            x = x * 10 + c - '0';
            c = br.read();
        }
        return rll(x);
    }

    char[] rs() throws IOException {
        return br.readLine().toCharArray();
    }

    void sort(int[] A) {
        Random r = new Random();
        for (int i = A.length-1; i > 0; i--) {
            int j = r.nextInt(i+1);
            int temp = A[i];
            A[i] = A[j];
            A[j] = temp;
        }
        Arrays.sort(A);
    }

    void printDouble(double d) {
        pw.printf("%.16f", d);
    }
}

class UnionFind {
    int n;
    int[] parent;
    int[] rank;
    int[] ans;
    boolean[] isSpecial;

    UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];
        ans = new int[n];
        isSpecial = new boolean[n];
        for (int i = 0; i < n; i++) parent[i] = i;
    }

    void union(int u, int v, int w) {
        int x = find(u);
        int y = find(v);
        if (x == y) return;
        if (rank[x] < rank[y]) parent[x] = y;
        else if (rank[x] > rank[y]) parent[y] = x;
        else {
            parent[x] = y;
            rank[y]++;
        }
        if (isSpecial[x] && isSpecial[y]) {
            ans[x] = ans[y] = w;
        } else if (isSpecial[x] || isSpecial[y]) {
            isSpecial[x] = isSpecial[y] = true;
            int max = Math.max(ans[x], ans[y]);
            ans[x] = ans[y] = max;
        }
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
}