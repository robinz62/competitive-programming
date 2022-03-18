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
            int n = ri();
            adj = new ArrayList<>(n);
            for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
            for (int i = 0; i < n-1; i++) {
                int[] uv = ril(2);
                adj.get(uv[0]-1).add(uv[1]-1);
                adj.get(uv[1]-1).add(uv[0]-1);
            }
            f = ril(n);

            allfreq = new HashMap<>();
            for (int fi : f) allfreq.put(fi, allfreq.getOrDefault(fi, 0) + 1);

            int root = 0;  // arbitrary
            ans = 0;
            for (int child : adj.get(root)) dfs(child, root);

            pw.println("Case #" + (Ti+1) + ": " + ans);
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    int ans;
    int[] f;
    Map<Integer, Integer> allfreq;
    List<List<Integer>> adj;

    Res dfs(int u, int p) {
        Map<Integer, Integer> counts = new HashMap<>();
        int matches = 0;
        counts.put(f[u], 1);
        if (allfreq.get(f[u]) == 1) {
            matches++;
        }
        for (int v : adj.get(u)) {
            if (v == p) continue;
            Res sub = dfs(v, u);
            matches += sub.matches;
            Res r = merge(counts, sub.map);
            counts = r.map;
            matches += r.matches;
        }
        if (matches == counts.size()) ans++;

        return new Res(counts, matches);
    }

    Res merge(Map<Integer, Integer> a, Map<Integer, Integer> b) {
        if (a.size() > b.size()) {
            Map<Integer, Integer> temp = a;
            a = b;
            b = temp;
        }
        int c = 0;
        for (Map.Entry<Integer, Integer> e : a.entrySet()) {
            int k = e.getKey();
            int v = e.getValue();
            boolean candidate = b.containsKey(k);
            b.put(k, b.getOrDefault(k, 0) + v);
            if (candidate && (int) b.get(k) == (int) allfreq.get(k)) c++;
        }
        return new Res(b, c);
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

class Res {
    Map<Integer, Integer> map;
    int matches;
    Res(Map<Integer, Integer> m, int ma) {
        map = m;
        matches = ma;
    }
}