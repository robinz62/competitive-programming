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

    int[] ril() throws IOException {
        String[] tokens = br.readLine().split(" ");
        int[] A = new int[tokens.length];
        for (int i = 0; i < A.length; i++)
            A[i] = Integer.parseInt(tokens[i]);
        return A;
    }

    long[] rll() throws IOException {
        String[] tokens = br.readLine().split(" ");
        long[] A = new long[tokens.length];
        for (int i = 0; i < A.length; i++)
            A[i] = Long.parseLong(tokens[i]);
        return A;
    }

    void solve() throws IOException {
        int t = ri();
        for (int ti = 0; ti < t; ti++) {
            int[] nm = ril();
            n = nm[0];
            m = nm[1];
            char[][] A = new char[n][];
            for (int i = 0; i < n; i++) {
                A[i] = br.readLine().toCharArray();
            }
            int numG = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (A[i][j] == 'G') numG++;
                    else if (A[i][j] == 'B') {
                        if (i-1 >= 0 && A[i-1][j] == '.') A[i-1][j] = '#';
                        if (j-1 >= 0 && A[i][j-1] == '.') A[i][j-1] = '#';
                        if (i+1 < n && A[i+1][j] == '.') A[i+1][j] = '#';
                        if (j+1 < m && A[i][j+1] == '.') A[i][j+1] = '#';
                    }
                }
            }
            int foundg = 0;
            if (A[n-1][m-1] != '#') {
                boolean[][] visited = new boolean[n][m];
                visited[n-1][m-1] = true;
                foundg = dfs(A, visited, n-1, m-1);
            }
            if (foundg != numG) {
                pw.println("No");
            } else {
                pw.println("Yes");
            }
        }
    }

    int n;
    int m;
    int dfs(char[][] A, boolean[][] visited, int i, int j) {
        int ret = 0;
        if (A[i][j] == 'G') ret++;
        else if (A[i][j] == 'B') return -1;
        if (i-1 >= 0 && A[i-1][j] != '#' && !visited[i-1][j]) {
            visited[i-1][j] = true;
            int c = dfs(A, visited, i-1, j);
            if (c == -1) return -1;
            ret += c;
        }
        if (i+1 < n && A[i+1][j] != '#' && !visited[i+1][j]) {
            visited[i+1][j] = true;
            int c = dfs(A, visited, i+1, j);
            if (c == -1) return -1;
            ret += c;
        }
        if (j-1 >= 0 && A[i][j-1] != '#' && !visited[i][j-1]) {
            visited[i][j-1] = true;
            int c = dfs(A, visited, i, j-1);
            if (c == -1) return -1;
            ret += c;
        }
        if (j+1 < m && A[i][j+1] != '#' && !visited[i][j+1]) {
            visited[i][j+1] = true;
            int c = dfs(A, visited, i, j+1);
            if (c == -1) return -1;
            ret += c;
        }
        return ret;
    }
}