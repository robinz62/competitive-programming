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
        List<List<Integer>> adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int i = 0; i < n-1; i++) {
            int[] uv = ril(2);
            adj.get(uv[0]-1).add(uv[1]-1);
        }
        
        // Root is 0
        int max = dfs1(0, adj, true)[0];
        int min = dfs2(0, adj, true)[0];
        pw.println(max + " " + min);
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    // returns [rank, total] of the leaf that will be selected
    int[] dfs1(int u, List<List<Integer>> adj, boolean turn) {
        if (adj.get(u).isEmpty()) return new int[]{1, 1};

        int[][] results = new int[adj.get(u).size()][];
        int i = 0;
        for (int v : adj.get(u)) results[i++] = dfs1(v, adj, !turn);
        int total = 0;
        for (int[] res : results) total += res[1];
        if (turn) {
            // The answer is when selected rank is closest to top, for that subtree
            int ans = 1;
            for (int[] res : results) ans = Math.max(ans, total - res[1] + res[0]);
            return new int[]{ans, total};
        } else {
            int ans = 0;
            for (int[] res : results) ans += res[0] - 1;
            ans++;
            return new int[]{ans, total};
        }
    }

    int[] dfs2(int u, List<List<Integer>> adj, boolean turn) {
        if (adj.get(u).isEmpty()) return new int[]{1, 1};

        int[][] results = new int[adj.get(u).size()][];
        int i = 0;
        for (int v : adj.get(u)) results[i++] = dfs2(v, adj, !turn);
        int total = 0;
        for (int[] res : results) total += res[1];
        if (turn) {
            int ans = 0;
            for (int[] res : results) ans += res[0];
            return new int[]{ans, total};
        } else {
            // The answer is when selected rank is closest to bottom, for that subtree
            int ans = total;
            for (int[] res : results) ans = Math.min(ans, res[0]);
            return new int[]{ans, total};
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