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

    void solve() throws IOException {
        int n = ri();
        c = ril(n);
        adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int i = 0; i < n - 1; i++) {
            int[] xy = ril(2);
            adj.get(xy[0]-1).add(xy[1]-1);
            adj.get(xy[1]-1).add(xy[0]-1);
        }
        answers = new long[n];
        dfs(0, -1);
        for (long ai : answers) pw.print(ai + " ");
        pw.println();
    }

    List<List<Integer>> adj;
    long[] answers;
    int[] c;
    
    Thing dfs(int u, int p) {
        Thing me = new Thing();
        me.map1 = new HashMap<>();
        me.map2 = new HashMap<>();
        me.map3 = new TreeMap<>();
        me.map1.put(c[u], 1);
        me.map2.put(1, new HashSet<>());
        me.map2.get(1).add(c[u]);
        me.map3.put(1, (long) c[u]);
        for (int v : adj.get(u)) {
            if (v == p) continue;
            me = merge(dfs(v, u), me);
        }
        answers[u] = me.map3.lastEntry().getValue();
        return me;
    }

    Thing merge(Thing a, Thing b) {
        Thing t;
        if (a.map1.size() >= b.map1.size()) {
            t = a;
            for (Map.Entry<Integer, Integer> e : b.map1.entrySet()) {
                int c = e.getKey();
                int f = e.getValue();
                if (!t.map1.containsKey(c)) {
                    t.map1.put(c, f);
                    if (!t.map2.containsKey(f)) t.map2.put(f, new HashSet<>());
                    t.map2.get(f).add(c);
                    t.map3.put(f, t.map3.getOrDefault(f, 0l) + c);
                } else {
                    int prevF = t.map1.get(c);
                    t.map1.put(c, prevF + f);
                    t.map2.get(prevF).remove(c);
                    if (!t.map2.containsKey(prevF + f)) t.map2.put(prevF + f, new HashSet<>());
                    t.map2.get(prevF + f).add(c);
                    t.map3.put(prevF, t.map3.get(prevF) - c);
                    t.map3.put(prevF + f, t.map3.getOrDefault(prevF + f, 0l) + c);
                }
            }
        } else {
            t = b;
            for (Map.Entry<Integer, Integer> e : a.map1.entrySet()) {
                int c = e.getKey();
                int f = e.getValue();
                if (!t.map1.containsKey(c)) {
                    t.map1.put(c, f);
                    if (!t.map2.containsKey(f)) t.map2.put(f, new HashSet<>());
                    t.map2.get(f).add(c);
                    t.map3.put(f, t.map3.getOrDefault(f, 0l) + c);
                } else {
                    int prevF = t.map1.get(c);
                    t.map1.put(c, prevF + f);
                    t.map2.get(prevF).remove(c);
                    if (!t.map2.containsKey(prevF + f)) t.map2.put(prevF + f, new HashSet<>());
                    t.map2.get(prevF + f).add(c);
                    t.map3.put(prevF, t.map3.get(prevF) - c);
                    t.map3.put(prevF + f, t.map3.getOrDefault(prevF + f, 0l) + c);
                }
            }
        }

        return t;
    }
}

class Thing {
    Map<Integer, Integer> map1;
    Map<Integer, Set<Integer>> map2;
    TreeMap<Integer, Long> map3;
}