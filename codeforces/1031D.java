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
    //   sorting, or taking max, after MOD
    void solve() throws IOException {
        int[] nk = ril(2);
        int n = nk[0];
        int k = nk[1];
        char[][] A = new char[n][];
        for (int i = 0; i < n; i++) A[i] = rs();
        if (k >= 2 * n - 1) {
            for (int i = 0; i < 2 * n - 1; i++) pw.print("a");
            pw.println();
            return;
        }

        // dp[i][j] is number of changes left upon arriving at (i, j)
        // The first negative means no more a's.
        int[][] dp = new int[n][n];
        for (int[] row : dp) Arrays.fill(row, Integer.MIN_VALUE);
        dp[0][0] = A[0][0] == 'a' ? k : k-1;
        for (int i = 1; i < n; i++) {
            if (A[i][0] == 'a' && dp[i-1][0] >= 0) dp[i][0] = dp[i-1][0];
            else if (dp[i-1][0] >= 0) dp[i][0] = dp[i-1][0] - 1;
            if (A[0][i] == 'a' && dp[0][i-1] >= 0) dp[0][i] = dp[0][i-1];
            else if (dp[0][i-1] >= 0) dp[0][i] = dp[0][i-1] - 1;
        }
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < n; j++) {
                if (A[i][j] == 'a') {
                    if (dp[i-1][j] >= 0) dp[i][j] = dp[i-1][j];
                    if (dp[i][j-1] >= 0) dp[i][j] = Math.max(dp[i][j], dp[i][j-1]);
                } else {
                    if (dp[i-1][j] >= 0) dp[i][j] = dp[i-1][j] - 1;
                    if (dp[i][j-1] >= 0) dp[i][j] = Math.max(dp[i][j], dp[i][j-1] - 1);
                }
            }
        }

        // All a's.
        if (dp[n-1][n-1] >= 0) {
            for (int i = 0; i < 2 * n - 1; i++) pw.print("a");
            pw.println();
            return;
        }

        // Find the -1 cells that are as far as possible from (0, 0) manhattan
        // These are the candidates for the answer.
        Set<Integer> candidates = new HashSet<>();
        int distOfCandidates = 0;
        char lowest = 'z';
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (dp[i][j] == -1) {
                    if (i + j > distOfCandidates) {
                        distOfCandidates = i + j;
                        candidates.clear();
                        candidates.add(i * n + j);
                        lowest = A[i][j];
                    } else if (i + j == distOfCandidates) {
                        candidates.add(i * n + j);
                        lowest = (char) Math.min(lowest, A[i][j]);
                    }
                }
            }
        }

        for (int i = 0; i < distOfCandidates; i++) pw.print("a");
        while (!candidates.isEmpty()) {
            pw.print(lowest);
            Set<Integer> next = new HashSet<>();
            char nextLowest = 'z';
            for (int candidate : candidates) {
                int r = candidate / n;
                int c = candidate % n;
                if (A[r][c] != lowest) continue;
                if (r+1 < n) {
                    if (A[r+1][c] < nextLowest) {
                        nextLowest = A[r+1][c];
                        next.clear();
                        next.add((r+1) * n + c);
                    } else if (A[r+1][c] == nextLowest) {
                        next.add((r+1) * n + c);
                    }
                }
                if (c+1 < n) {
                    if (A[r][c+1] < nextLowest) {
                        nextLowest = A[r][c+1];
                        next.clear();
                        next.add(r * n + c+1);
                    } else if (A[r][c+1] == nextLowest) {
                        next.add(r * n + c+1);
                    }
                }
            }
            lowest = nextLowest;
            candidates = next;
        }
        pw.println();
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

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