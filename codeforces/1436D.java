import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static int MOD = 1000000007;

    // After writing solution, quick scan for:
    //   array out of bounds
    //   special cases e.g. n=1?
    //
    // Big numbers arithmetic bugs:
    //   int overflow
    //   sorting, or taking max, after MOD
    void solve() throws IOException {
        int n = ri();
        int[] p = ril(n-1);  // p[i] is parent of i+1 (0 has no parent)
        a = rll(n);

        adj = new ArrayList<>(n);  // directed
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int i = 1; i < n; i++) {
            adj.get(p[i-1]-1).add(i);
        }

        TreeMap<Long, Long> ans = dfs(0);
        pw.println(ans.lastKey());
    }

    // Each subtree tries to balance its own leaves as best it can

    long[] a;
    List<List<Integer>> adj;
    TreeMap<Long, Long> dfs(int u) {
        TreeMap<Long, Long> ans = new TreeMap<>();
        if (adj.get(u).isEmpty()) { // leaf
            ans.put(a[u], 1l);
            return ans;
        }

        for (int v : adj.get(u)) {
            ans = merge(ans, dfs(v));
        }
        
        long maxHere = ans.lastKey();
        long countMax = ans.get(maxHere);
        while (ans.size() > 1 && a[u] != 0) {  // while not just the max
            long val = ans.firstKey();
            long diff = maxHere - val;
            if (a[u] >= diff) {
                ans.put(val, ans.get(val) - 1);
                if (ans.get(val) == 0) ans.remove(val);

                a[u] -= diff;
                
                countMax++;
            } else {
                ans.put(val, ans.get(val) - 1);
                if (ans.get(val) == 0) ans.remove(val);

                ans.put(val+a[u], ans.getOrDefault(val+a[u], 0l) + 1);
                
                a[u] -= a[u];
            }
        }
        ans.put(maxHere, countMax);
        if (a[u] > 0) {
            long addAmount = a[u] / countMax;
            long getsExtra = a[u] % countMax;
            long gets = countMax - getsExtra;
            ans.clear();
            ans.put(maxHere + addAmount, gets);
            if (getsExtra > 0) ans.put(maxHere + addAmount + 1, getsExtra);
        }
        return ans;
    }

    // amount -> num w that amount
    TreeMap<Long, Long> merge(TreeMap<Long, Long> a, TreeMap<Long, Long> b) {
        if (a.size() > b.size()) {
            TreeMap<Long, Long> temp = a;
            a = b;
            b = temp;
        }

        // Merge into b
        for (Map.Entry<Long, Long> e : a.entrySet()) {
            b.put(e.getKey(), b.getOrDefault(e.getKey(), 0l) + e.getValue());
        }
        return b;
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