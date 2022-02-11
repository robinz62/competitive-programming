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
    //
    // Interactive problems: don't forget to flush between test cases
    void solve() throws IOException {
        int T = ri();
        for (int Ti = 0; Ti < T; Ti++) {
            int[] nm = ril(2);
            int n = nm[0];
            int m = nm[1];

            grid = new char[n][];
            for (int i = 0; i < n; i++) grid[i] = rs();

            int labr = -1;
            int labc = -1;
            for (int i = 0; labr == -1 && i < n; i++) for (int j = 0; labr == -1 && j < m; j++) if (grid[i][j] == 'L') {
                labr = i;
                labc = j;
            }

            visited = new boolean[n][m];
            constraint = new int[n][m];
            canReach = new boolean[n][m];
            for (int i = 0; i < n; i++) for (int j = 0; j < m; j++) constraint[i][j] = numBad(i, j);

            dfs(labr, labc, true);

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (canReach[i][j]) grid[i][j] = '+';
                }
            }

            for (char[] row : grid) pw.println(new String(row));
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    int[][] dirs = new int[][]{{1,0}, {-1,0}, {0,1}, {0,-1}};

    char[][] grid;
    boolean[][] visited;
    int[][] constraint;  // number of bad neighbors
    boolean[][] canReach;
    void dfs(int r, int c, boolean isLab) {
        visited[r][c] = true;
        for (int[] dir : dirs) {
            int i = r + dir[0];
            int j = c + dir[1];
            if (valid(i, j)) {
                constraint[i][j]--;
                if (constraint[i][j] <= 1 && !visited[i][j]) {
                    canReach[i][j] = true;
                    dfs(i, j, false);
                }
            }
        }
    }

    boolean valid(int r, int c) {
        return r >= 0 && r < grid.length && c >= 0 && c < grid[0].length && grid[r][c] != '#';
    }

    int numBad(int r, int c) {
        if (grid[r][c] == '#') return Integer.MAX_VALUE;
        int count = 4;
        if (r-1 < 0 || grid[r-1][c] == '#') count--;
        if (r+1 >= grid.length || grid[r+1][c] == '#') count--;
        if (c-1 < 0 || grid[r][c-1] == '#') count--;
        if (c+1 >= grid[0].length || grid[r][c+1] == '#') count--;
        return count;
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