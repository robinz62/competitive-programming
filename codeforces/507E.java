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
    //   if (x : long) and (y : int), [y = x] does not compile, but [y += x] does
    //   sorting, or taking max, after MOD
    void solve() throws IOException {
        int[] nm = ril(2);
        int n = nm[0];
        int m = nm[1];
        List<List<int[]>> adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        int[][] edges = new int[m][3];
        for (int i = 0; i < m; i++) {
            int[] uvw = ril(3);
            int u = uvw[0]-1;
            int v = uvw[1]-1;
            int w = uvw[2];
            adj.get(u).add(new int[]{v, w});
            adj.get(v).add(new int[]{u, w});
            edges[i][0] = Math.min(u, v);
            edges[i][1] = Math.max(u, v);
            edges[i][2] = w;
        }

        boolean[] visited = new boolean[n];
        int[] dist = new int[n];
        int[] numOk = new int[n];
        int[] p = new int[n];
        Deque<Integer> q = new ArrayDeque<>();
        visited[0] = true;
        q.addLast(0);
        while (!q.isEmpty()) {
            int u = q.removeFirst();
            for (int[] vw : adj.get(u)) {
                int v = vw[0];
                int w = vw[1];
                if (v == p[u]) continue;
                if (visited[v]) {
                    if (dist[u] + 1 <= dist[v] && numOk[u] + w > numOk[v]) {
                        numOk[v] = numOk[u] + w;
                        p[v] = u;
                    }
                } else {
                    visited[v] = true;
                    dist[v] = dist[u] + 1;
                    numOk[v] = numOk[u] + w;
                    p[v] = u;
                    q.addLast(v);
                }
            }
        }
        
        Map<Integer, Map<Integer, Integer>> ans = new HashMap<>();
        int me = n-1;
        while (me != 0) {
            int u = Math.min(me, p[me]);
            int v = Math.max(me, p[me]);
            if (!ans.containsKey(u)) ans.put(u, new HashMap<>());
            ans.get(u).put(v, 1);
            me = p[me];
        }
        List<int[]> output = new ArrayList<>();
        for (int[] e : edges) {
            int u = e[0];
            int v = e[1];
            int original = e[2];
            if (ans.containsKey(u) && ans.get(u).containsKey(v)) {
                if (original != 1) {
                    output.add(new int[]{u, v, 1});
                }
            } else {
                if (original != 0) {
                    output.add(new int[]{u, v, 0});
                }
            }
        }
        pw.println(output.size());
        for (int[] e : output) pw.println((e[0]+1) + " " + (e[1]+1) + " " + e[2]);
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
        int c = br.read();
        int x = 0;
        while (c >= '0' && c <= '9') {
            x = x * 10 + c - '0';
            c = br.read();
        }
        return ril(x);
    }

    long[] rkll() throws IOException {
        int c = br.read();
        int x = 0;
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