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

    void solve() throws IOException {
        int[] nm = ril(2);
        int n = nm[0];
        int m = nm[1];
        char[][] A = new char[n][];
        for (int i = 0; i < n; i++) {
            A[i] = br.readLine().toCharArray();
        }
        List<Integer> rowsWithNoBlack = new ArrayList<>();
        List<Integer> colsWithNoBlack = new ArrayList<>();
        // if any rows or columns are B W B, then bad
        for (int i = 0; i < n; i++) {
            int bsegments = A[i][0] == '#' ? 1 : 0;
            for (int j = 1; j < m; j++) {
                if (A[i][j] == '#' && A[i][j-1] == '.') bsegments++;
            }
            if (bsegments == 0) rowsWithNoBlack.add(i);
            else if (bsegments > 1) {
                pw.println("-1");
                return;
            }
        }
        for (int j = 0; j < m; j++) {
            int bsegments = A[0][j] == '#' ? 1 : 0;
            for (int i = 1; i < n; i++) {
                if (A[i][j] == '#' && A[i-1][j] == '.') bsegments++;
            }
            if (bsegments == 0) colsWithNoBlack.add(j);
            else if (bsegments > 1) {
                pw.println("-1");
                return;
            }
        }
        if (rowsWithNoBlack.size() > 0 && colsWithNoBlack.isEmpty()
            || colsWithNoBlack.size() > 0 && rowsWithNoBlack.isEmpty()) {
            pw.println("-1");
            return;
        }
        boolean[][] visited = new boolean[n][m];
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (!visited[i][j] && A[i][j] == '#') {
                    visited[i][j] = true;
                    dfs(A, visited, i, j);
                    count++;
                }
            }
        }
        pw.println(count);
    }

    int[][] dirs = new int[][]{{1,0}, {-1,0}, {0,1}, {0,-1}};
    void dfs(char[][] A, boolean[][] visited, int i, int j) {
        for (int[] dir : dirs) {
            int r = i+dir[0];
            int c = j+dir[1];
            if (r >= 0 && c >= 0 && r < A.length && c < A[0].length && !visited[r][c] && A[i][j] == '#') {
                visited[r][c] = true;
                dfs(A, visited, r, c);
            }
        }
    }
}