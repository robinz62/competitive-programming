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
    //
    // Interactive problems: don't forget to flush between test cases
    void solve() throws IOException {
        int[] nq = ril(2);
        int n = nq[0];
        int q = nq[1];
        x = ril(n);
        List<List<Integer>> adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int i = 0; i < n-1; i++) {
            int[] uv = ril(2);
            adj.get(uv[0]-1).add(uv[1]-1);
            adj.get(uv[1]-1).add(uv[0]-1);
        }

        int[][] queries = new int[q][3];
        for (int i = 0; i < q; i++) {
            int[] query = ril(2);
            queries[i][0] = query[0]-1;
            queries[i][1] = query[1]-1;
            queries[i][2] = i;
        }

        queriesByNode = new HashMap<>();
        ans = new int[q];
        for (int[] query : queries) {
            int u = query[0];
            if (!queriesByNode.containsKey(u)) queriesByNode.put(u, new ArrayList<>());
            queriesByNode.get(u).add(query);
        }

        for (List<int[]> list : queriesByNode.values()) Collections.sort(list, (q1, q2) -> Integer.compare(q1[1], q2[1]));
        dfs(0, -1, adj);
        for (int ansi : ans) pw.println(ansi);
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    int[] x;
    Map<Integer, List<int[]>> queriesByNode;
    int[] ans;
    TreeMap<Integer, Integer> dfs(int u, int p, List<List<Integer>> adj) {
        TreeMap<Integer, Integer> here = new TreeMap<Integer, Integer>(Collections.reverseOrder());
        here.put(x[u], 1);
        for (int v : adj.get(u)) {
            if (v == p) continue;
            here = merge(here, dfs(v, u, adj));
        }
        if (queriesByNode.containsKey(u)) {
            List<int[]> queries = queriesByNode.get(u);
            int qIdx = 0;
            int subbed = 0;
            for (var e : here.entrySet()) {
                int val = e.getKey();
                int count = e.getValue();
                subbed += count;
                while (qIdx < queries.size() && subbed > queries.get(qIdx)[1]) {
                    ans[queries.get(qIdx)[2]] = val;
                    qIdx++;
                }
                if (qIdx == queries.size()) break;
            }
        }
        return here;
    }

    TreeMap<Integer, Integer> merge(TreeMap<Integer, Integer> t1, TreeMap<Integer, Integer> t2) {
        if (t1.size() > t2.size()) {
            TreeMap<Integer, Integer> temp = t1;
            t1 = t2;
            t2 = temp;
        }
        for (var e : t1.entrySet()) {
            int val = e.getKey();
            int count = e.getValue();
            t2.put(val, t2.getOrDefault(val, 0) + count);
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