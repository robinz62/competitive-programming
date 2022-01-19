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
        int T = ri();
        for (int Ti = 0; Ti < T; Ti++) {
            int n = ri();
            List<List<Integer>> adj = new ArrayList<>(n);
            for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
            edges = new HashMap<>();
            for (int i = 0; i < n-1; i++) {
                int[] uv = ril(2);
                uv[0]--; uv[1]--;
                adj.get(uv[0]).add(uv[1]);
                adj.get(uv[1]).add(uv[0]);
                if (!edges.containsKey(uv[0])) edges.put(uv[0], new HashMap<>());
                edges.get(uv[0]).put(uv[1], i);
                if (!edges.containsKey(uv[1])) edges.put(uv[1], new HashMap<>());
                edges.get(uv[1]).put(uv[0], i);
            }

            // Must print 1 <= ai <= 10^5
            // Degree of 3 is rip
            boolean bad = false;
            for (int i = 0; !bad && i < n; i++) {
                if (adj.get(i).size() >= 3) {
                    bad = true;
                }
            }
            if (bad) {
                pw.println("-1");
                continue;
            }

            int start = -1;
            for (int i = 0; i < n; i++) {
                if (adj.get(i).size() == 1) {
                    start = i;
                    break;
                }
            }

            int[] solution = new int[n-1];
            boolean[] visited = new boolean[n];
            visited[start] = true;
            dfs(start, adj, 2, solution, visited);
            for (int x : solution) pw.print(x + " ");
            pw.println();
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    Map<Integer, Map<Integer, Integer>> edges;
    void dfs(int u, List<List<Integer>> adj, int curr, int[] solution, boolean[] visited) {
        int next = curr == 2 ? 3 : 2;
        for (int v : adj.get(u)) {
            if (visited[v]) continue;
            visited[v] = true;
            int e = edges.get(u).get(v);
            solution[e] = curr;
            dfs(v, adj, next, solution, visited);
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