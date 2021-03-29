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
        List<List<Integer>> adj = new ArrayList<>(n);
        List<List<Integer>> adjr = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int i = 0; i < n; i++) adjr.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            int[] uv = ril(2);
            adj.get(uv[0]-1).add(uv[1]-1);
            adjr.get(uv[1]-1).add(uv[0]-1);
        }

        int[][] d = new int[n][n];
        for (int[] row : d) Arrays.fill(row, Integer.MIN_VALUE);
        int[] visited = new int[n];
        Arrays.fill(visited, -1);
        Deque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            q.addLast(i);
            visited[i] = i;
            d[i][i] = 0;
            while (!q.isEmpty()) {
                int u = q.removeFirst();
                for (int v : adj.get(u)) {
                    if (visited[v] == i) continue;
                    visited[v] = i;
                    d[i][v] = d[i][u] + 1;
                    q.addLast(v);
                }
            }
        }
        List<Integer> temp = new ArrayList<>(n);
        for (int i = 0; i < n; i++) temp.add(i);
        int[][][] far = new int[n][3][2];
        for (int i = 0; i < n; i++) {
            final int u = i;
            Collections.sort(temp, (i1, i2) -> -Integer.compare(d[u][i1], d[u][i2]));
            far[i][0][0] = d[i][temp.get(0)];
            far[i][1][0] = d[i][temp.get(1)];
            far[i][2][0] = d[i][temp.get(2)];
            far[i][0][1] = temp.get(0);
            far[i][1][1] = temp.get(1);
            far[i][2][1] = temp.get(2);
        }

        int[][] dr = new int[n][n];
        for (int[] row : dr) Arrays.fill(row, Integer.MIN_VALUE);
        Arrays.fill(visited, -1);
        for (int i = 0; i < n; i++) {
            q.addLast(i);
            visited[i] = i;
            dr[i][i] = 0;
            while (!q.isEmpty()) {
                int u = q.removeFirst();
                for (int v : adjr.get(u)) {
                    if (visited[v] == i) continue;
                    visited[v] = i;
                    dr[i][v] = dr[i][u] + 1;
                    q.addLast(v);
                }
            }
        }
        int[][][] farr = new int[n][3][2];
        for (int i = 0; i < n; i++) {
            final int u = i;
            Collections.sort(temp, (i1, i2) -> -Integer.compare(dr[u][i1], dr[u][i2]));
            farr[i][0][0] = dr[i][temp.get(0)];
            farr[i][1][0] = dr[i][temp.get(1)];
            farr[i][2][0] = dr[i][temp.get(2)];
            farr[i][0][1] = temp.get(0);
            farr[i][1][1] = temp.get(1);
            farr[i][2][1] = temp.get(2);
        }

        int distBest = 0;
        int[] ans = new int[4];
        for (int b = 0; b < n; b++) {
            for (int c = 0; c < n; c++) {
                if (b == c) continue;
                if (d[b][c] == Integer.MIN_VALUE) continue;
                for (int ai = 0; ai < 3; ai++) {
                    int a = farr[b][ai][1];
                    if (a == b || a == c) continue;
                    if (d[a][b] == Integer.MIN_VALUE) continue;
                    for (int di = 0; di < 3; di++) {
                        int dd = far[c][di][1];
                        if (dd == a || dd == b || dd == c) continue;
                        if (d[c][dd] == Integer.MIN_VALUE) continue;
                        int dist = farr[b][ai][0] + d[b][c] + far[c][di][0];
                        if (dist > distBest) {
                            distBest = dist;
                            ans[0] = a;
                            ans[1] = b;
                            ans[2] = c;
                            ans[3] = dd;
                        }
                    }
                }
            }
        }
        for (int x : ans) pw.print((x+1) + " ");
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