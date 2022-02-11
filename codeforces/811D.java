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
    void solve() throws IOException {
        int[] nm = ril(2);
        int n = nm[0];
        int m = nm[1];

        char[][] grid = new char[n][];
        for (int i = 0; i < n; i++) grid[i] = rs();

        int r = 0;
        int c = 0;

        if (n == 1) {
            boolean reversed;
            pw.println("L"); pw.flush();
            int[] xy = ril(2); xy[0]--; xy[1]--;
            if (xy[0] == r && xy[1] == c) reversed = false;
            else reversed = true;
            r = xy[0];
            c = xy[1];
            while (grid[r][c] != 'F') {
                pw.println(reversed ? "L" : "R"); pw.flush();
                xy = ril(2); xy[0]--; xy[1]--;
                r = xy[0];
                c = xy[1];
            }
            return;
        }

        if (m == 1) {
            boolean reversed;
            pw.println("U"); pw.flush();
            int[] xy = ril(2); xy[0]--; xy[1]--;
            if (xy[0] == r && xy[1] == c) reversed = false;
            else reversed = true;
            r = xy[0];
            c = xy[1];
            while (grid[r][c] != 'F') {
                pw.println(reversed ? "U" : "D"); pw.flush();
                xy = ril(2); xy[0]--; xy[1]--;
                r = xy[0];
                c = xy[1];
            }
            return;
        }

        boolean hReverse = false;
        boolean vReverse = false;

        if (grid[0][1] != '*' && grid[1][0] != '*') {  // Test at start
            pw.println("U"); pw.flush();
            int[] xy = ril(2); xy[0]--; xy[1]--;
            if (grid[xy[0]][xy[1]] == 'F') return;
            if (xy[0] == r && xy[1] == c) {
                vReverse = false;
            } else {
                vReverse = true;
                pw.println("D"); pw.flush();
                ril(2); xy[0]--; xy[1]--;
            }
            // Back at (0, 0)
            pw.println("L"); pw.flush();
            xy = ril(2); xy[0]--; xy[1]--;
            if (grid[xy[0]][xy[1]] == 'F') return;
            if (xy[0] == r && xy[1] == c) {
                hReverse = false;
            } else {
                hReverse = true;
                r = xy[0];
                c = xy[1];
            }
        } else if (grid[0][1] != '*') {                // Go right until we can test
            pw.println("L"); pw.flush();
            int[] xy = ril(2); xy[0]--; xy[1]--;
            if (grid[xy[0]][xy[1]] == 'F') return;
            if (xy[0] == r && xy[1] == c) {
                hReverse = false;
            } else {
                hReverse = true;
                r = xy[0];
                c = xy[1];
            }
            while (grid[r+1][c] == '*') {
                pw.println(hReverse ? "L" : "R"); pw.flush();
                xy = ril(2); xy[0]--; xy[1]--;
                if (grid[xy[0]][xy[1]] == 'F') return;
                r = xy[0];
                c = xy[1];
            }
            pw.println("U"); pw.flush();
            xy = ril(2); xy[0]--; xy[1]--;
            if (grid[xy[0]][xy[1]] == 'F') return;
            if (xy[0] == r && xy[1] == c) {
                vReverse = false;
            } else {
                vReverse = true;
                r = xy[0];
                c = xy[1];
            }
        } else {                                       // Go down until we can test 
            pw.println("U"); pw.flush();
            int[] xy = ril(2); xy[0]--; xy[1]--;
            if (grid[xy[0]][xy[1]] == 'F') return;
            if (xy[0] == r && xy[1] == c) {
                vReverse = false;
            } else {
                vReverse = true;
                r = xy[0];
                c = xy[1];
            }
            while (grid[r][c+1] == '*') {
                pw.println(vReverse ? "U" : "D"); pw.flush();
                xy = ril(2); xy[0]--; xy[1]--;
                if (grid[xy[0]][xy[1]] == 'F') return;
                r = xy[0];
                c = xy[1];
            }
            pw.println("L"); pw.flush();
            xy = ril(2); xy[0]--; xy[1]--;
            if (grid[xy[0]][xy[1]] == 'F') return;
            if (xy[0] == r && xy[1] == c) {
                hReverse = false;
            } else {
                hReverse = true;
                r = xy[0];
                c = xy[1];
            }
        }

        // BFS to solution from current point.

        boolean[][] visited = new boolean[n][m];
        int[][] parent = new int[n][m];
        Deque<int[]> q = new ArrayDeque<>();
        visited[r][c] = true;
        parent[r][c] = -1;
        q.addLast(new int[]{r, c});
        int endr = 0;
        int endc = 0;
        while (!q.isEmpty()) {
            int[] u = q.removeFirst();
            int ur = u[0];
            int uc = u[1];
            int me = ur * m + uc;
            if (grid[ur][uc] == 'F') {
                endr = ur;
                endc = uc;
                break;
            }
            if (ok(ur-1, uc, grid, visited)) {
                visited[ur-1][uc] = true;
                parent[ur-1][uc] = me;
                q.addLast(new int[]{ur-1, uc});
            }
            if (ok(ur+1, uc, grid, visited)) {
                visited[ur+1][uc] = true;
                parent[ur+1][uc] = me;
                q.addLast(new int[]{ur+1, uc});
            }
            if (ok(ur, uc-1, grid, visited)) {
                visited[ur][uc-1] = true;
                parent[ur][uc-1] = me;
                q.addLast(new int[]{ur, uc-1});
            }
            if (ok(ur, uc+1, grid, visited)) {
                visited[ur][uc+1] = true;
                parent[ur][uc+1] = me;
                q.addLast(new int[]{ur, uc+1});
            }
        }

        List<String> path = new ArrayList<>();
        while (endr != r || endc != c) {
            int parentr = parent[endr][endc] / m;
            int parentc = parent[endr][endc] % m;
            if (parentr == endr - 1) path.add("D");
            else if (parentr == endr + 1) path.add("U");
            else if (parentc == endc - 1) path.add("R");
            else if (parentc == endc + 1) path.add("L");
            endr = parentr;
            endc = parentc;
        }
        Collections.reverse(path);
        for (String s : path) {
            if (s.equals("D")) {
                if (!vReverse) pw.println("D");
                else pw.println("U");
            } else if (s.equals("U")) {
                if (!vReverse) pw.println("U");
                else pw.println("D");
            } else if (s.equals("L")) {
                if (!hReverse) pw.println("L");
                else pw.println("R");
            } else if (s.equals("R")) {
                if (!hReverse) pw.println("R");
                else pw.println("L");
            }
            pw.flush();
            ril(2);
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    boolean ok(int r, int c, char[][] grid, boolean[][] visited) {
        return r >= 0 && r < grid.length && c >= 0 && c < grid[0].length && !visited[r][c] && grid[r][c] != '*';
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