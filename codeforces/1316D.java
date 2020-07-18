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
        n = ri();

        // if (r, c) -> (r, c), then this cell is X
        // if there is a path (a, b) -> (x, y)
        // then there must be a cell adjacent to a blocked cell
        // we can immediately determine that cell's direction
        // continuing inductively (one step at a time), we can
        // determine the letters for all cells along a path
        //
        // another observation: the (-1, -1) paths must form some
        // sort of connected component with each other with size > 1
        // all you have to do is have two cells point to each other,
        // then have all cells in the same CC funnel into one of them
        // (bfs or dfs work)

        int[][] A = new int[n][n];       // destination
        char[][] grid = new char[n][n];  // letter
        
        Deque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            int[] xys = ril(2 * n);
            for (int j = 0; j < xys.length; j++) xys[j]--;
            for (int j = 0; j < n; j++) {
                int r = xys[2*j];
                int c = xys[2*j+1];
                if (r == -2 && c == -2) {
                    A[i][j] = -1;
                } else {
                    A[i][j] = r*n + c;
                }
                if (i*n + j == r*n + c) {
                    q.addLast(i*n + j);
                    grid[i][j] = 'X';
                }
            }
        }

        // Do BFS from all blocked states; solves non-infinite cases
        while (!q.isEmpty()) {
            int rc = q.removeFirst();
            int r = rc / n;
            int c = rc % n;
            if (r-1>=0 && grid[r-1][c] == 0 && A[r-1][c] == A[r][c]) {
                grid[r-1][c] = 'D';
                q.addLast(rc - n);
            }
            if (r+1<n && grid[r+1][c] == 0 && A[r+1][c] == A[r][c]) {
                grid[r+1][c] = 'U';
                q.addLast(rc + n);
            }
            if (c-1>=0 && grid[r][c-1] == 0 && A[r][c-1] == A[r][c]) {
                grid[r][c-1] = 'R';
                q.addLast(rc - 1);
            }
            if (c+1<n && grid[r][c+1] == 0 && A[r][c+1] == A[r][c]) {
                grid[r][c+1] = 'L';
                q.addLast(rc + 1);
            }
        }

        // Find CCs of infinite cases.
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (A[i][j] >= 0 && grid[i][j] == 0) {
                    pw.println("INVALID");
                    return;
                }
                if (A[i][j] == -1 && grid[i][j] == 0) {
                    boolean ok = false;
                    if (i+1<n && A[i+1][j] == -1) {
                        ok = true;
                        grid[i][j] = 'D';
                        grid[i+1][j] = 'U';
                        dfs(i+1, j, A, grid);
                    }
                    if (j+1<n && A[i][j+1] == -1 && grid[i][j+1] == 0) {
                        ok = true;
                        grid[i][j] = 'R';
                        grid[i][j+1] = 'L';
                        dfs(i, j+1, A, grid);
                    }
                    if (!ok) {
                        pw.println("INVALID");
                        return;
                    }
                }
            }
        }

        pw.println("VALID");
        for (char[] row : grid) {
            pw.println(new String(row));
        }
    }

    int n;

    void dfs(int r, int c, int[][] A, char[][] grid) {
        if (r-1>=0 && A[r-1][c] == -1 && grid[r-1][c] == 0) {
            grid[r-1][c] = 'D';
            dfs(r-1, c, A, grid);
        }
        if (r+1<n && A[r+1][c] == -1 && grid[r+1][c] == 0) {
            grid[r+1][c] = 'U';
            dfs(r+1, c, A, grid);
        }
        if (c-1>=0 && A[r][c-1] == -1 && grid[r][c-1] == 0) {
            grid[r][c-1] = 'R';
            dfs(r, c-1, A, grid);
        }
        if (c+1<n && A[r][c+1] == -1 && grid[r][c+1] == 0) {
            grid[r][c+1] = 'L';
            dfs(r, c+1, A, grid);
        }
    }
}