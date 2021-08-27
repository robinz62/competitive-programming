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
        int n = ri();
        adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

        int[] r = ril(n);
        for (int i = 0; i < n; i++) r[i]--;
        for (int i = 0; i < n; i++) {
            if (r[i] >= 0) adj.get(r[i]).add(i);
        }
        
        int[][] up;
        int log2ceil = 31 - Integer.numberOfLeadingZeros(n);
        up = new int[n][log2ceil + 1];
        for (int i = 0; i < n; i++) up[i][0] = r[i];
        for (int j = 1; j <= log2ceil; j++) {
            for (int i = 0; i < n; i++) {
                int x = up[i][j-1];
                up[i][j] = x == -1 ? -1 : up[x][j-1];
            }
        }

        int m = ri();
        ans = new int[m];
        vToPI = new HashMap<>();
        for (int i = 0; i < m; i++) {
            int[] vp = ril(2);
            int v = vp[0]-1;
            int p = vp[1];
            int parent = ancestor(v, p, up);
            if (parent == -1) {
                ans[i] = 0;
            } else {
                if (!vToPI.containsKey(parent)) vToPI.put(parent, new ArrayList<>());
                vToPI.get(parent).add(new int[]{p, i});
            }
        }

        for (int i = 0; i < n; i++) {
            if (r[i] == -1) dfs(i, 0);
        }

        for (int ansi : ans) pw.print(ansi + " ");
        pw.println();
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    int ancestor(int u, int d, int[][] up) {
        int i = up[u].length - 1;
        while (d != 0) {
            if ((1 << i) > d) i--;
            else {
                u = up[u][i];
                d -= (1 << i);
            }
            if (u == -1) return -1;
        }
        return u;
    }

    int[] ans;
    List<List<Integer>> adj;
    Map<Integer, List<int[]>> vToPI;
    Map<Integer, Integer> dfs(int u, int d) {
        Map<Integer, Integer> all = new HashMap<>();
        for (int v : adj.get(u)) {
            Map<Integer, Integer> sub = dfs(v, d+1);
            all = merge(all, sub);
        }
        all.put(d, 1);
        if (vToPI.containsKey(u)) {
            for (int[] query : vToPI.get(u)) {
                int depth = d + query[0];
                int i = query[1];
                ans[i] = all.get(depth) - 1;
            }
        }
        return all;
    }

    Map<Integer, Integer> merge(Map<Integer, Integer> t1, Map<Integer, Integer> t2) {
        if (t1.size() > t2.size()) {
            Map<Integer, Integer> temp = t1;
            t1 = t2;
            t2 = temp;
        }
        for (Map.Entry<Integer, Integer> e : t1.entrySet()) {
            int d = e.getKey();
            int v = e.getValue();
            t2.put(d, t2.getOrDefault(d, 0) + v);
        }
        return t2;
    }

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
