import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static int MOD = 1000000007;

    // After writing solution, quick scan for:
    //   array out of bounds
    //   special cases e.g. n=1?
    //
    // Big numbers arithmetic bugs:
    //   int overflow
    //   sorting, or taking max, after MOD
    void solve() throws IOException {
        int T = ri();
        for (int Ti = 0; Ti < T; Ti++) {
            int[] nm = ril(2);
            int n = nm[0];
            int m = nm[1];
            char[][] A = new char[n][];
            for (int i = 0; i < n; i++) A[i] = rs();
            
            List<int[]> ops = new ArrayList<>();
            if (n % 2 == 0 && m % 2 == 0) {
                for (int i = 0; i < n; i += 2) {
                    for (int j = 0; j < m; j += 2) {
                        solve(ops, A, i, j);
                    }
                }
                pw.println(ops.size());
                for (int[] op : ops) {
                    for (int z = 0; z < 6; z++) op[z]++;
                    for (int x : op) {
                        pw.print(x + " ");
                    }
                    pw.println();
                }
            } else if (n % 2 == 1 && m % 2 == 0) {
                for (int i = 0; i < n-2; i++) {
                    for (int j = 0; j < m; j++) {
                        if (A[i][j] != '0') {
                            int c1 = j+1 < m ? j+1 : j-1;
                            ops.add(new int[]{i, j, i+1, j, i+1, c1});
                            A[i+1][j] = flip(A[i+1][j]);
                            A[i+1][c1] = flip(A[i+1][c1]);
                        }
                    }
                }
                int i = n-2;
                for (int j = 0; j < m; j += 2) {
                    solve(ops, A, i, j);
                }
                pw.println(ops.size());
                for (int[] op : ops) {
                    for (int z = 0; z < 6; z++) op[z]++;
                    for (int x : op) {
                        pw.print(x + " ");
                    }
                    pw.println();
                }
            } else if (n % 2 == 0 && m % 2 == 1) {
                char[][] At = new char[m][n];
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < m; j++) {
                        At[j][i] = A[i][j];
                    }
                }
                
                // Copy Paste
                for (int i = 0; i < m-2; i++) {
                    for (int j = 0; j < n; j++) {
                        if (At[i][j] != '0') {
                            int c1 = j+1 < n ? j+1 : j-1;
                            ops.add(new int[]{i, j, i+1, j, i+1, c1});
                            At[i+1][j] = flip(At[i+1][j]);
                            At[i+1][c1] = flip(At[i+1][c1]);
                        }
                    }
                }
                int i = m-2;
                for (int j = 0; j < n; j += 2) {
                    solve(ops, At, i, j);
                }
                // End Copy Paste

                pw.println(ops.size());
                for (int[] op : ops) {
                    for (int z = 0; z < 6; z++) op[z]++;
                    pw.println(op[1] + " " + op[0] + " " + op[3] + " " + op[2] + " " + op[5] + " " + op[4]);
                }
            } else {
                for (int j = 0; j < m-2; j++) {
                    for (int i = 0; i < n; i++) {
                        if (A[i][j] != '0') {
                            int r1 = i+1 < n ? i+1 : i-1;
                            ops.add(new int[]{i, j, i, j+1, r1, j+1});
                            A[i][j+1] = flip(A[i][j+1]);
                            A[r1][j+1] = flip(A[r1][j+1]);
                        }
                    }
                }

                // We are left with a ODDx2 to solve
                char[][] left = new char[n][2];
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < 2; j++) {
                        left[i][j] = A[i][m-2+j];
                    }
                }

                List<int[]> ops2 = new ArrayList<>();
                for (int i = 0; i < n-2; i++) {
                    for (int j = 0; j < 2; j++) {
                        if (left[i][j] != '0') {
                            int c1 = j+1 < 2 ? j+1 : j-1;
                            ops2.add(new int[]{i, j, i+1, j, i+1, c1});
                            left[i+1][j] = flip(left[i+1][j]);
                            left[i+1][c1] = flip(left[i+1][c1]);
                        }
                    }
                }
                int i = n-2;
                for (int j = 0; j < 2; j += 2) {
                    solve(ops2, left, i, j);
                }
                pw.println(ops.size() + ops2.size());
                for (int[] op : ops) {
                    for (int z = 0; z < 6; z++) op[z]++;
                    for (int x : op) {
                        pw.print(x + " ");
                    }
                    pw.println();
                }
                for (int[] op : ops2) {
                    for (int z = 0; z < 6; z++) op[z]++;
                    op[1] += m-2;
                    op[3] += m-2;
                    op[5] += m-2;
                    for (int x : op) {
                        pw.print(x + " ");
                    }
                    pw.println();
                }
            }
        }
    }

    void solve(List<int[]> ops, char[][] A, int i, int j) {
        List<int[]> z = new ArrayList<>();
        List<int[]> o = new ArrayList<>();
        for (int a = i; a <= i+1; a++) {
            for (int b = j; b <= j+1; b++) {
                if (A[a][b] == '0') z.add(new int[]{a, b});
                else o.add(new int[]{a, b});
            }
        }
        if (z.size() == 4) return;
        if (z.size() == 3) {
            ops.add(new int[]{z.get(0)[0], z.get(0)[1], z.get(1)[0], z.get(1)[1], o.get(0)[0], o.get(0)[1]});
            ops.add(new int[]{o.get(0)[0], o.get(0)[1], z.get(2)[0], z.get(2)[1], z.get(0)[0], z.get(0)[1]});
            ops.add(new int[]{o.get(0)[0], o.get(0)[1], z.get(1)[0], z.get(1)[1], z.get(2)[0], z.get(2)[1]});
        } else if (z.size() == 2) {
            ops.add(new int[]{z.get(0)[0], z.get(0)[1], z.get(1)[0], z.get(1)[1], o.get(0)[0], o.get(0)[1]});
            ops.add(new int[]{z.get(0)[0], z.get(0)[1], z.get(1)[0], z.get(1)[1], o.get(1)[0], o.get(1)[1]});
        } else if (z.size() == 1) {
            ops.add(new int[]{o.get(0)[0], o.get(0)[1], o.get(1)[0], o.get(1)[1], o.get(2)[0], o.get(2)[1]});
        } else {
            ops.add(new int[]{o.get(0)[0], o.get(0)[1], o.get(1)[0], o.get(1)[1], o.get(2)[0], o.get(2)[1]});
            ops.add(new int[]{o.get(1)[0], o.get(1)[1], o.get(2)[0], o.get(2)[1], o.get(3)[0], o.get(3)[1]});
            ops.add(new int[]{o.get(0)[0], o.get(0)[1], o.get(3)[0], o.get(3)[1], o.get(1)[0], o.get(1)[1]});
            ops.add(new int[]{o.get(0)[0], o.get(0)[1], o.get(2)[0], o.get(2)[1], o.get(3)[0], o.get(3)[1]});
        }
    }

    void apply(char[][] A, List<int[]> ops) {
        for (int[] op : ops) {
            for (int i = 0; i < 6; i += 2) {
                A[op[i]-1][op[i+1]-1] = flip(A[op[i]-1][op[i+1]-1]);
            }
        }
    }

    char flip(char c) {
        return c == '0' ? '1' : '0';
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