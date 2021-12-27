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
        int T = ri();
        for (int Ti = 0; Ti < T; Ti++) {
            int[] nm = ril(2);
            int n = nm[0];  // tiny
            int m = nm[1];
            int[] x = ril(n);

            char[][] results = new char[n][];
            for (int i = 0; i < n; i++) results[i] = rs();

            // Find a permutation of the m=10^4 questions.
            // There are few students

            // We can't take any question individually, since we only think about the sum
            // of points for each student...

            // What about for an individual student?
            // Let's say his score was x[i]. We would be surprised if the sum of his points
            // was super low or super high...
            //
            // There is sacrifice: if we make i's score super high, then we may inadvertantly
            // make other students' scores not surprising at all.
            // We must link the students somehow. In a graph?

            // Connect two students with an edge j if they both do question j.
            // Multi edges are clearly allowed.

            // 2^10 = 1024. Can we brute force on which students should be low vs high?
            // 0 means low, 1 means high
            // Give the lowest scores to the problems that the "bad kids" solve.
            // Among them, the lowest score should be given to the problem that a lot of the
            // bad kids solve.

            long maxSurprise = -1;
            int[] bestAssignment = null;

            List<Integer> order = new ArrayList<>(m);
            for (int i = 0; i < m; i++) order.add(i);

            for (int mask = 0; mask < (1 << n); mask++) {
                int[] badkidsolves = new int[m];
                for (int i = 0; i < n; i++) {
                    if (((1 << i) & mask) > 0) continue;
                    for (int j = 0; j < m; j++) {
                        if (results[i][j] == '1') badkidsolves[j]++;
                    }
                }

                int[] goodkidsolves = new int[m];
                for (int i = 0; i < n; i++) {
                    if (((1 << i) & mask) == 0) continue;
                    for (int j = 0; j < m; j++) {
                        if (results[i][j] == '1') goodkidsolves[j]++;
                    }
                }

                int[] solvesPoints = new int[m];
                for (int i = 0; i < m; i++) {
                    // It's "bad" if the bad kids get points at all
                    solvesPoints[i] = goodkidsolves[i] - badkidsolves[i];
                }

                Collections.sort(order, (i1, i2) -> -Integer.compare(solvesPoints[i1], solvesPoints[i2]));

                int[] assignment = new int[m];
                int pts = m;
                for (int z : order) {
                    assignment[z] = pts--;
                }

                long surprise = 0;
                for (int i = 0; i < n; i++) {
                    long score = 0;
                    for (int j = 0; j < m; j++) if (results[i][j] == '1') score += assignment[j];
                    surprise += Math.abs(score - x[i]);
                }

                if (surprise > maxSurprise) {
                    maxSurprise = surprise;
                    bestAssignment = assignment;
                }
            }

            for (int z : bestAssignment) {
                pw.print(z + " ");
            }
            pw.println();
        }
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