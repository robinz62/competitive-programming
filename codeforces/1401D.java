import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static int MOD = 1_000_000_007;

    // After writing solution, quick scan for:
    //   array out of bounds
    //   special cases e.g. n=1?
    //
    // Big numbers arithmetic bugs:
    //   int overflow
    //   sorting, or taking max, after MOD
    void solve() throws IOException {
        int t = ri();
        for (int ti = 0; ti < t; ti++) {
            n = ri();
            adj = new ArrayList<>(n);
            visited = new boolean[n];
            size = new int[n];
            for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
            for (int i = 0; i < n-1; i++) {
                int[] uv = ril(2);
                adj.get(uv[0]-1).add(uv[1]-1);
                adj.get(uv[1]-1).add(uv[0]-1);
            }

            int m = ri();
            long[] p = rll(m);
            Arrays.sort(p);
            List<Long> factors = new ArrayList<>();
            long biggest = p[p.length-1];
            int i = p.length-2;
            for (; i+1>=n-2+1; i--) {
                biggest = biggest * p[i] % MOD;
            }
            while (i >= 0) factors.add(p[i--]);
            while (factors.size() < n-2) factors.add(1l);

            visited[0] = true;
            edgeFreq = new ArrayList<>();
            dfs1(0);
            Collections.sort(factors);
            factors.add(biggest);
            Collections.sort(edgeFreq);
            long ans = 0;
            for (i = 0; i < n-1; i++) {
                ans += edgeFreq.get(i) % MOD * factors.get(i);
                ans %= MOD;
            }
            pw.println(ans);
        }
    }

    int n;
    List<List<Integer>> adj;
    boolean[] visited;
    int[] size;  // size of subtree

    List<Long> edgeFreq;

    void dfs1(int u) {
        size[u] = 1;
        for (int v : adj.get(u)) {
            if (!visited[v]) {
                visited[v] = true;
                dfs1(v);
                long freq = (long) size[v] * (n - size[v]);
                edgeFreq.add(freq);
                size[u] += size[v];
            }
        }
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
        return Integer.parseInt(br.readLine());
    }

    long rl() throws IOException {
        return Long.parseLong(br.readLine());
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

    int max(int a, int b) {
        return a >= b ? a : b;
    }

    int min(int a, int b) {
        return a <= b ? a : b;
    }
}