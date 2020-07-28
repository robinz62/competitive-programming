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
        List<Set<Integer>> adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new HashSet<>());
        for (int i = 0; i < n - 1; i++) {
            int[] xy = ril(2);
            adj.get(xy[0]-1).add(xy[1]-1);
            adj.get(xy[1]-1).add(xy[0]-1);
        }
        Set<Integer> cands = new HashSet<>();
        for (int i = 0; i < n; i++) cands.add(i);
        while (true) {
            if (cands.size() == 1) {
                pw.println("! " + (cands.iterator().next()+1));
                return;
            }
            int u = -1;
            int v = -1;
            for (int i : cands) {
                if (adj.get(i).size() == 1) {
                    if (u == -1) u = i;
                    else {
                        v = i;
                        break;
                    }
                }
            }
            pw.println("? " + (u+1) + " " + (v+1));
            pw.flush();
            int w = ri()-1;
            if (w == -2) return;
            if (u == w) {
                pw.println("! " + (u+1));
                break;
            }
            if (v == w) {
                pw.println("! " + (v+1));
                break;
            }
            delete = new ArrayList<>();
            delete.add(u);
            delete.add(v);
            cands.remove(u);
            cands.remove(v);
            dfs(adj, cands, u, w);
            dfs(adj, cands, v, w);
            for (int kill : delete) {
                for (int i = 0; i < n; i++) adj.get(i).remove(kill);
            }
        }
    }

    List<Integer> delete;
    void dfs(List<Set<Integer>> adj, Set<Integer> cands, int u, int w) {
        for (int v : adj.get(u)) {
            if (cands.contains(v) && v != w) {
                cands.remove(v);
                delete.add(v);
                dfs(adj, cands, v, w);
            }
        }
    }
}