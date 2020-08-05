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
        int[] nm = ril(2);
        int n = nm[0];
        int m = nm[1];
        char[][] A = new char[n][];
        for (int i = 0; i < n; i++) {
            A[i] = rs();
        }
        boolean[][] visited = new boolean[n][m];
        ids = new int[n][m];
        idsToSize = new ArrayList<>();
        idsToSize.add(0);
        int id = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (!visited[i][j] && A[i][j] == '.') {
                    visited[i][j] = true;
                    ids[i][j] = id;
                    int size = bfs(A, visited, i, j, id);
                    idsToSize.add(size);
                    id++;
                }
            }
        }
        char[][] ans = new char[n][m];
        Set<Integer> x = new HashSet<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (A[i][j] == '.') ans[i][j] = '.';
                else {
                    int sum = 1;
                    x.clear();
                    if (i-1 >= 0) x.add(ids[i-1][j]);
                    if (i+1 < n) x.add(ids[i+1][j]);
                    if (j-1 >= 0) x.add(ids[i][j-1]);
                    if (j+1 < m) x.add(ids[i][j+1]);
                    for (int ii : x) sum += idsToSize.get(ii);
                    ans[i][j] = (char) (sum % 10 + '0');
                }
            }
        }
        for (char[] row : ans) pw.println(new String(row));
    }

    int[][] dirs = new int[][]{{1,0}, {-1,0}, {0,1}, {0,-1}};
    List<Integer> idsToSize;
    int[][] ids;
    int bfs(char[][] A, boolean[][] visited, int i, int j, int id) {
        int size = 1;
        Deque<int[]> q = new ArrayDeque<>();
        q.addLast(new int[]{i, j});
        while (!q.isEmpty()) {
            int[] u = q.removeFirst();
            for (int[] dir : dirs) {
                int r = u[0]+dir[0];
                int c = u[1]+dir[1];
                if (r >= 0 && c >= 0 && r < A.length && c < A[0].length && !visited[r][c] && A[r][c] == '.') {
                    visited[r][c] = true;
                    size++;
                    ids[r][c] = id;
                    q.addLast(new int[]{r, c});
                }
            }
        }
        return size;
    }
}