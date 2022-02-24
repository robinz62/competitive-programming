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
        int[] nm = ril(2);
        int n = nm[0];
        int m = nm[1];
        int[][] a = new int[n][];
        for (int i = 0; i < n; i++) a[i] = ril(m);

        List<int[]> ans = new ArrayList<>(n * m);

        // done[i][j] means all of [i:i+1][j:j+1] is queued to be wiped
        boolean[][] done = new boolean[n][m];

        // queue contains solid 2x2 squares
        Deque<int[]> q = new ArrayDeque<>();
        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < m-1; j++) {
                int ready = readyToGo(a, i, j);
                if (ready > 0) {
                    q.addLast(new int[]{i, j, ready});
                    done[i][j] = true;
                }
            }
        }

        while (!q.isEmpty()) {
            // print(a);
            int[] pos = q.removeFirst();
            int i = pos[0];
            int j = pos[1];
            int color = pos[2];
            ans.add(new int[]{i, j, color});
            a[i][j] = a[i+1][j] = a[i][j+1] = a[i+1][j+1] = 0;
            for (int r = i-1; r <= i+1; r++) {
                for (int c = j-1; c <= j+1; c++) {
                    if (r < 0 || r+1 >= n || c < 0 || c+1 >= m) continue;
                    if (done[r][c]) continue;
                    int ready = readyToGo(a, r, c);
                    if (ready > 0) {
                        q.addLast(new int[]{r, c, ready});
                        done[r][c] = true;
                    }
                }
            }
        }

        boolean allgood = true;
        for (int i = 0; allgood && i < n; i++) for (int j = 0; allgood && j < m; j++) if (a[i][j] != 0) allgood = false;

        if (allgood) {
            Collections.reverse(ans);
            pw.println(ans.size());
            for (int[] insn : ans) {
                pw.println((insn[0]+1) + " " + (insn[1]+1) + " " + insn[2]);
            }
            // debugPaintIt(n, m, ans);
        } else {
            pw.println("-1");
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    void print(int[][] a) {
        for (int[] row : a) {
            for (int x : row) pw.print(x + " ");
            pw.println();
        }
        pw.println();
    }

    void debugPaintIt(int n, int m, List<int[]> ans) {
        int[][] a = new int[n][m];
        for (int[] insn : ans) {
            int i = insn[0];
            int j = insn[1];
            int c = insn[2];
            for (int ii = i; ii <= i+1; ii++) for (int jj = 0; jj <= j+1; jj++) a[ii][jj] = c;
        }
        for (int[] row : a) {
            for (int x : row) pw.print(x + " ");
            pw.println();
        }
    }

    int readyToGo(int[][] a, int i, int j) {
        int color;
        if (a[i][j] != 0) color = a[i][j];
        else if (a[i+1][j] != 0) color = a[i+1][j];
        else if (a[i][j+1] != 0) color = a[i][j+1];
        else if (a[i+1][j+1] != 0) color = a[i+1][j+1];
        else color = 0;
        if (color == 0) return 0;
        boolean allsame = true;
        allsame = allsame && (a[i+1][j] == 0 || a[i+1][j] == color) && (a[i][j+1] == 0 || a[i][j+1] == color) && (a[i+1][j+1] == 0 || a[i+1][j+1] == color);
        if (allsame) return color;
        else return -1;
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