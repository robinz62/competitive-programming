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
        long[] nm = rll(3);
        int n = (int) nm[0];
        long m = nm[1];
        
        List<long[]> add_events = new ArrayList<>();
        List<long[]> remove_events = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            long[] alr = rll(3);
            add_events.add(new long[]{alr[1]-1, alr[0]-1});
            remove_events.add(new long[]{alr[2]+1-1, alr[0]-1});
        }
        Collections.sort(add_events, (a, b) -> Long.compare(a[0], b[0]));
        Collections.sort(remove_events, (a, b) -> Long.compare(a[0], b[0]));

        long[][][] transitions = new long[8][3][];
        for (int i = 0; i < 8; i++) {
            if ((i & 1) > 0) transitions[i][0] = new long[]{1, 1, 0}; else transitions[i][0] = new long[3];
            if ((i & 2) > 0) transitions[i][1] = new long[]{1, 1, 1}; else transitions[i][1] = new long[3];
            if ((i & 4) > 0) transitions[i][2] = new long[]{0, 1, 1}; else transitions[i][2] = new long[3];
        }

        long[][] M = new long[][]{
            {1, 0, 0},
            {0, 1, 0},
            {0, 0, 1},
        };
        
        int add_idx = 0;
        int remove_idx = 0;
        long prev = 1;
        int[] curr = new int[]{0, 0, 0};
        while (add_idx < add_events.size() || remove_idx < remove_events.size()) {
            long me = Long.MAX_VALUE;
            if (add_idx < add_events.size()) me = Math.min(me, add_events.get(add_idx)[0]);
            if (remove_idx < remove_events.size()) me = Math.min(me, remove_events.get(remove_idx)[0]);

            // Compute if length is > 0
            if (me != prev) {
                int mask = 0;
                if (curr[0] == 0) mask |= 1;
                if (curr[1] == 0) mask |= 2;
                if (curr[2] == 0) mask |= 4;
                long power = me - prev;
                M = matrixMult(M, matrixExp(transitions[mask], power));

                // long[][] temp = matrixMult(M, new long[][]{{0},{1},{0}});
                // pw.println(temp[0][0] + " " + temp[1][0] + " " + temp[2][0]);
                // pw.flush();
            }

            while (add_idx < add_events.size() && add_events.get(add_idx)[0] == me) {
                curr[(int) add_events.get(add_idx)[1]]++;
                add_idx++;
            }
            while (remove_idx < remove_events.size() && remove_events.get(remove_idx)[0] == me) {
                curr[(int) remove_events.get(remove_idx)[1]]--;
                remove_idx++;
            }

            prev = me;
            if (me == m-2) break;
        }

        // final segment
        int mask = 0;
        if (curr[0] == 0) mask |= 1;
        if (curr[1] == 0) mask |= 2;
        if (curr[2] == 0) mask |= 4;
        long power = m-1 - prev;
        M = matrixMult(M, matrixExp(transitions[mask], power));

        // long[][] temp = matrixMult(M, new long[][]{{0},{1},{0}});
        // pw.println(temp[0][0] + " " + temp[1][0] + " " + temp[2][0]);
        // pw.flush();

        long[][] res = matrixMult(M, new long[][]{{0}, {1}, {0}});
        long ans = (res[0][0] + res[1][0] + res[2][0]) % MOD;
        pw.println(ans);
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    long[][] matrixMult(long[][] A, long[][] B) {
        int m = A.length;
        int n = A[0].length;
        int p = B[0].length;
        long[][] C = new long[m][p];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < p; j++)
                for (int k = 0; k < n; k++)
                    C[i][j] += A[i][k] * B[k][j] % MOD;
        return C;
    }

    long[][] matrixExp(long[][] A, long k) {
        int m = A.length;
        int n = A[0].length;
        if (k == 1) {
            long[][] res = new long[m][n];
            for (int i = 0; i < m; i++)
                for (int j = 0; j < n; j++)
                    res[i][j] = A[i][j];
            return res;
        }
        long[][] half = matrixExp(A, k / 2);
        long[][] res = matrixMult(half, half);
        if (k % 2 == 0) return res;
        res = matrixMult(res, A);
        return res;
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