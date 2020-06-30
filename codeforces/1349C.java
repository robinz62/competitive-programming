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
        int[] nmt = ril(3);
        int n = nmt[0];
        int m = nmt[1];
        int t = nmt[2];
        char[][] A = new char[n][];
        for (int i = 0; i < n; i++) {
            A[i] = br.readLine().toCharArray();
        }

        // change[i][j] is the first iteration in which A[i][j] starts flipping
        int[][] change = new int[n][m];
        for (int i = 0; i < n; i++) Arrays.fill(change[i], -1);
        Deque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (hasAdjSame(A, i, j)) {
                    change[i][j] = 0;
                    q.addLast(i*m+j);
                }
            }
        }
        int[][] dirs = new int[][]{{-1,0},{1,0},{0,1},{0,-1}};
        while (!q.isEmpty()) {
            int u = q.removeFirst();
            int i = u / m;
            int j = u % m;
            for (int[] dir : dirs) {
                int x = i+dir[0];
                int y = j+dir[1];
                if (x >= 0 && x < n && y >= 0 && y < m && change[x][y] < 0) {
                    change[x][y] = change[i][j] + 1;
                    q.addLast(x*m+y);
                }
            }
        }

        for (int ti = 0; ti < t; ti++) {
            long[] ijp = rll(3);
            int i = (int) ijp[0] - 1;
            int j = (int) ijp[1] - 1;
            long p = ijp[2];
            long c = change[i][j];
            if (p < c || c == -1) {
                pw.println(A[i][j]);
            } else {
                long flips = (p - c) % 2;
                if (flips == 0) pw.println(A[i][j]);
                else pw.println(1 - (A[i][j] - '0'));
            }
        }
    }

    boolean hasAdjSame(char[][] A, int i, int j) {
        if (i-1 >= 0 && A[i-1][j] == A[i][j]) return true;
        if (j-1 >= 0 && A[i][j-1] == A[i][j]) return true;
        if (i+1 < A.length && A[i+1][j] == A[i][j]) return true;
        if (j+1 < A[0].length && A[i][j+1] == A[i][j]) return true;
        return false;
    }
}