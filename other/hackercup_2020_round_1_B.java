import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static int MOD = 1000000007;

    // After writing solution, quick scan for:
    //   int overflow
    //   array out of bounds
    //   special cases e.g. n=1?
    void solve() throws IOException {
        int t = ri();
        for (int ti = 0; ti < t; ti++) {
            int[] NMKS = ril(4);
            int N = NMKS[0], M = NMKS[1], K = NMKS[2], S = NMKS[3];
            long[] PK = rll(K);
            long[] ABCDP = rll(4);
            long AP = ABCDP[0], BP = ABCDP[1], CP = ABCDP[2], DP = ABCDP[3];
            long[] QK = rll(K);
            long[] ABCDQ = rll(4);
            long AQ = ABCDQ[0], BQ = ABCDQ[1], CQ = ABCDQ[2], DQ = ABCDQ[3];

            long[] P = new long[N];
            System.arraycopy(PK, 0, P, 0, K);
            for (int i = K; i < N; i++) P[i] = ((AP * P[i-2] + BP * P[i-1] + CP) % DP) + 1;
            long[] Q = new long[M];
            System.arraycopy(QK, 0, Q, 0, K);
            for (int i = K; i < M; i++) Q[i] = ((AQ * Q[i-2] + BQ * Q[i-1] + CQ) % DQ) + 1;

            Arrays.sort(P);
            Arrays.sort(Q);

            long l = 0;
            long r = 2_000_000_000L;  // surely high enough
            long ans = 2_000_000_001L;
            while (l <= r) {
                long m = l + (r - l) / 2;
                if (ok(P, Q, m)) {
                    r = m - 1;
                    ans = Math.min(ans, m);
                } else {
                    l = m + 1;
                }
            }

            if (ans == 2_000_000_001L) {
                pw.println("BAD");
                return;
            }

            pw.println("Case #" + (ti+1) + ": " + ans);
        }
    }

    boolean ok(long[] P, long[] Q, long t) {
        int j = 0;  // index of stack in Q that needs destruction
        for (int i = 0; i < P.length && j < Q.length; i++) {
            // we want P[i] to kill as many as possible within t time
            // if kill any, must also kill Q[i]

            // go "left then right"
            long distToJ = Math.abs(P[i] - Q[j]);
            if (distToJ > t) continue;
            long pos = Q[j];
            long fuel = t - distToJ;
            int maxIdx1 = binarySearch1(Q, j, Q.length-1, pos, fuel);

            // go "right then left"
            pos = P[i];
            int maxIdx2 = binarySearch2(Q, j, Q.length-1, pos, fuel);

            j = Math.max(maxIdx1, maxIdx2) + 1;
        }
        return j == Q.length;
    }

    // I am standing at position pos with specified fuel.
    // I want to go as far right as possible in Q while I still have fuel.
    // It is guaranteed that Q[l] == pos
    // Returns the max reachable index.
    public static int binarySearch1(long[] Q, int l, int r, long pos, long fuel) {
        int ans = l;
        while (l <= r) {
            int m = l + (r - l) / 2;
            if (Q[m] - pos <= fuel) {
                ans = Math.max(ans, m);
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return ans;
    }

    // I am standing at position pos with specified fuel.
    // I want to go as far right as possible in Q and come back with given fuel.
    // Returns the max reachable index.
    public static int binarySearch2(long[] Q, int l, int r, long pos, long fuel) {
        int ans = -1;
        while (l <= r) {
            int m = l + (r - l) / 2;
            if (2 * (Q[m] - pos) <= fuel) {
                ans = Math.max(ans, m);
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return ans;
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

    int max(int a, int b) {
        return a >= b ? a : b;
    }

    int min(int a, int b) {
        return a <= b ? a : b;
    }
}