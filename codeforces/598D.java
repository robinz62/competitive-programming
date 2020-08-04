import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main implements Runnable {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pw = new PrintWriter(System.out);
    static int MOD = 1000000007;

    public static void main(String[] args) throws IOException {
        new Thread(null, new Main(), "Main", 1 << 27).start();
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

    public void run() {
        try {
            int[] nmk = ril(3);
            int n = nmk[0];
            int m = nmk[1];
            int k = nmk[2];
            A = new char[n][];
            p = new int[n][m];
            visited = new boolean[n][m];
            for (int i = 0; i < n; i++) {
                A[i] = rs();
            }
            List<Integer> map = new ArrayList<>();
            int id = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (!visited[i][j] && A[i][j] == '.') {
                        map.add(dfs(i, j, id));
                        id++;
                    }
                }
            }
            for (int i = 0; i < k; i++) {
                int[] xy = ril(2);
                pw.println(map.get(p[xy[0]-1][xy[1]-1]));
                pw.flush();
            }
        } catch (Exception e) {}
    }

    char[][] A;
    int[][] p;
    boolean[][] visited;
    int dfs(int r, int c, int id) {
        if (r < 0 || c < 0 || r >= A.length || c >= A[0].length || A[r][c] == '*' || visited[r][c]) return 0;
        int sum = 0;
        visited[r][c] = true;
        p[r][c] = id;
        if (r-1 >= 0 && A[r-1][c] == '*') sum++;
        if (r+1 < A.length && A[r+1][c] == '*') sum++;
        if (c-1 >= 0 && A[r][c-1] == '*') sum++;
        if (c+1 < A[0].length && A[r][c+1] == '*') sum++;
        sum += dfs(r-1, c, id);
        sum += dfs(r+1, c, id);
        sum += dfs(r, c-1, id);
        sum += dfs(r, c+1, id);
        return sum;
    }
}