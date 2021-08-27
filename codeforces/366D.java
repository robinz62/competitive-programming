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
        int[] nm = ril(2);
        int n = nm[0];
        int m = nm[1];
        List<List<int[]>> adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        Set<Integer> ls = new HashSet<>();
        int maxr = 0;
        for (int i = 0; i < m; i++) {
            int[] ablr = ril(4);
            adj.get(ablr[0]-1).add(new int[]{ablr[1]-1, ablr[2], ablr[3]});
            adj.get(ablr[1]-1).add(new int[]{ablr[0]-1, ablr[2], ablr[3]});
            ls.add(ablr[2]);
            maxr = Math.max(maxr, ablr[3]);
        }

        int best = 0;
        for (int l : ls) {
            int rl = l;
            int rr = maxr;
            while (rl <= rr) {
                int rm = rl + (rr - rl) / 2;
                if (possible(adj, l, rm)) {
                    best = Math.max(best, rm - l + 1);
                    rl = rm + 1;
                } else {
                    rr = rm - 1;
                    if (rr - l + 1 <= best) break;
                }
            }
        }
        pw.println(best == 0 ? "Nice work, Dima!" : best);
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    boolean possible(List<List<int[]>> adj, int L, int R) {
        int n = adj.size();
        boolean[] visited = new boolean[n];
        Deque<Integer> q = new ArrayDeque<>();
        q.addLast(0);
        visited[0] = true;
        while (!q.isEmpty()) {
            int u = q.removeFirst();
            if (u == n-1) return true;
            for (int[] vlr : adj.get(u)) {
                int v = vlr[0];
                int l = vlr[1];
                int r = vlr[2];
                if (visited[v]) continue;
                if (l > L || r < R) continue;
                visited[v] = true;
                q.addLast(v);
            }
        }
        return false;
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