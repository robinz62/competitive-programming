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
        int T = ri();
        for (int Ti = 0; Ti < T; Ti++) {
            int[] nm = ril(2);
            int n = nm[0];
            int m = nm[1];
            List<List<Integer>> adj = new ArrayList<>(n);
            for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
            for (int i = 0; i < m; i++) {
                int[] uv = ril(2);
                adj.get(uv[0]-1).add(uv[1]-1);
                adj.get(uv[1]-1).add(uv[0]-1);
            }

            int l = 0;
            int r = n-1;
            int best = n-1;
            int[] ans = new int[n];
            while (l <= r) {
                int mid = l + (r - l) / 2;
                if (ok(n, adj, mid)) {
                    best = mid;
                    System.arraycopy(temp, 0, ans, 0, n);
                    r = mid-1;
                } else {
                    l = mid+1;
                }
            }

            pw.println(best);
            for (int ai : ans) pw.print((ai+1) + " ");
            pw.println();
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    int[] temp;
    boolean ok(int n, List<List<Integer>> adj, int k) {
        temp = new int[n];
        Arrays.fill(temp, -1);

        int[] deg = new int[n];
        Deque<Integer> goodToGo = new ArrayDeque<>();
        Set<Integer> tooBig = new HashSet<>();
        for (int i = 0; i < n; i++) {
            int sz = adj.get(i).size();
            deg[i] = sz;
            if (sz <= k) goodToGo.addLast(i);
            else tooBig.add(i);
        }

        for (int val = n-1; val >= 0; val--) {
            if (goodToGo.isEmpty()) return false;
            int node = goodToGo.removeFirst();
            temp[node] = val;

            for (int v : adj.get(node)) {
                deg[v]--;
                if (deg[v] == k) {
                    tooBig.remove(v);
                    goodToGo.addLast(v);
                }
            }

            Integer sz = degToNodes.floorKey(k);
            if (sz == null) return false;
            int node = degToNodes.get(sz).iterator().next();
            degToNodes.get(sz).remove(node);
            if (degToNodes.get(sz).isEmpty()) degToNodes.remove(sz);
            
            temp[node] = val;
            for (int v : adj.get(node)) {
                if (temp[v] != -1) continue;
                degToNodes.get(deg[v]).remove(v);
                if (degToNodes.get(deg[v]).isEmpty()) degToNodes.remove(deg[v]);
                deg[v]--;
                if (!degToNodes.containsKey(deg[v])) degToNodes.put(deg[v], new HashSet<>());
                degToNodes.get(deg[v]).add(v);
            }
        }

        return true;
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