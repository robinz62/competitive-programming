import java.io.*;
import java.util.*;
 
public class Main {
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

    int readInt() throws IOException {
        return Integer.parseInt(br.readLine());
    }
 
    int[] readIntLine() throws IOException {
        String[] tokens = br.readLine().split(" ");
        int[] A = new int[tokens.length];
        for (int i = 0; i < A.length; i++) A[i] = Integer.parseInt(tokens[i]);
        return A;
    }
 
    long[] readLongLine() throws IOException {
        String[] tokens = br.readLine().split(" ");
        long[] A = new long[tokens.length];
        for (int i = 0; i < A.length; i++) A[i] = Long.parseLong(tokens[i]);
        return A;
    }
 
    void solve() throws IOException {
        int T = readInt();
        for (int t = 0; t < T; t++) {
            long[] line = readLongLine();
            long N = line[0];
            long M = line[1];
            if (M < N - 1 || M > N * (N - 1) / 2 + N) {
                pw.print("-1\n");
                continue;
            }
            if (N == 1) {
                if (M == 0) pw.print("0\n");
                else if (M == 1) pw.print("1\n");
                continue;
            }
            if (N == 2) {
                if (M == 1) pw.print("1\n");
                else pw.print("2\n");
                continue;
            }
            if (M == N + 1) {
                pw.print("2\n");
                continue;
            }
            if (M == N - 1 || M == N) {
                pw.print("2\n");
                continue;
            }
            if (M <= 2 * N) {
                pw.print("3\n");
                continue;
            }
            M -= N;  // don't forget to add 1 at the end
            long l = 2;  // 2-regular graph
            long r = N - 1; // (N - 1)-regular graph
            while (l <= r) {
                long m = l + (r - l) / 2;
                long a = maxEdgesWithMaxDegree(N, m);
                long b = maxEdgesWithMaxDegree(N, m + 1);
                if (a < M && b >= M) {
                    pw.print(m + 1 + 1);
                    pw.print('\n');
                    break;
                }
                if (b < M) {
                    l = m + 1;
                } else {
                    r = m - 1;
                }
            }
        }
    }

    // returns the maximum possible number of edges in a graph with n vertices
    // where the maximum degree of any vertex is k
    long maxEdgesWithMaxDegree(long n, long k) {
        if (k >= n - 1) {
            return n * (n - 1) / 2;
        }
        if (n * k % 2 == 0) {
            // it is possible to construct a k-regular graph
            return n * k / 2;
        }
        // n is odd, k is odd
        long base = n * (k - 1) / 2;  // first start with all vertices having
                                      // degree (k - 1)

        // we can add an additional ??? n / 2 ??? edges
        base += n / 2;
        return base;
    }
}