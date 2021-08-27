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
        List<Set<Integer>> adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new HashSet<>());
        int[] deg = new int[n];
        for (int i = 0; i < n-1; i++) {
            int[] uv = ril(2);
            adj.get(uv[0]-1).add(uv[1]-1);
            adj.get(uv[1]-1).add(uv[0]-1);
            deg[uv[0]-1]++;
            deg[uv[1]-1]++;
        }
        boolean[] done = new boolean[n];
        // This used to be a hashset but tle'd... Tight constant constraints >:(
        Deque<Integer> leaves = new ArrayDeque<>();
        for (int i = 0; i < n; i++) if (deg[i] == 1) leaves.addLast(i);
        int ans = 0;
        int[] to = new int[n];
        while (!leaves.isEmpty()) {
            int u = leaves.removeFirst();
            if (done[u]) continue;
            int r = adj.get(u).iterator().next();
            done[r] = true;
            List<Integer> l = new ArrayList<>();
            for (int v : adj.get(r)) {
                adj.get(v).remove(r);
                deg[v]--;
                if (deg[v] == 0) {
                    l.add(v);
                    done[v] = true;
                }
                if (deg[v] == 1) leaves.addLast(v);
            }
            adj.get(r).clear();
            deg[r] = 0;
            int x = l.size();
            ans += 2 * (x - 1) + 2;

            // If just 2, then just swap
            if (l.size() == 1) {
                to[r] = l.get(0);
                to[l.get(0)] = r;
            } else {
                to[r] = l.get(0);
                for (int i = 0; i < x-1; i++) {
                    to[l.get(i)] = l.get(i+1);
                }
                to[l.get(x-1)] = r;
            }
        }
        pw.println(ans);
        for (int i : to) pw.print((i+1) + " ");
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