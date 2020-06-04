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

    int readInt() throws IOException {
        return Integer.parseInt(br.readLine());
    }

    long readLong() throws IOException {
        return Long.parseLong(br.readLine());
    }

    int[] readIntLine() throws IOException {
        String[] tokens = br.readLine().split(" ");
        int[] A = new int[tokens.length];
        for (int i = 0; i < A.length; i++)
            A[i] = Integer.parseInt(tokens[i]);
        return A;
    }

    long[] readLongLine() throws IOException {
        String[] tokens = br.readLine().split(" ");
        long[] A = new long[tokens.length];
        for (int i = 0; i < A.length; i++)
            A[i] = Long.parseLong(tokens[i]);
        return A;
    }

    void solve() throws IOException {
        int[] nm = readIntLine();
        int n = nm[0];
        int m = nm[1];
        List<List<Integer>> adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            int[] ab = readIntLine();
            adj.get(ab[0]-1).add(ab[1]-1);
            adj.get(ab[1]-1).add(ab[0]-1);
        }
        int[] t = readIntLine();
        for (int i = 0; i < t.length; i++) t[i]--;
        // blogs in the order they should be written
        List<Integer> blogs = new ArrayList<>();
        for (int i = 0; i < n; i++) blogs.add(i);
        Collections.sort(blogs, (b1, b2) -> Integer.compare(t[b1], t[b2]));
        boolean[] visited = new boolean[n];
        for (int b : blogs) {
            int desiredT = t[b];
            boolean[] topicCoveredByNeighbor = new boolean[desiredT+1];
            for (int bv : adj.get(b)) {
                if (visited[bv]) {
                    topicCoveredByNeighbor[t[bv]] = true;
                }
            }
            boolean good = true;
            if (topicCoveredByNeighbor[desiredT]) good = false;
            for (int i = 0; i < desiredT && good; i++) {
                if (!topicCoveredByNeighbor[i]) {
                    good = false;
                }
            }
            if (!good) {
                pw.println("-1");
                return;
            }
            visited[b] = true;
        }
        for (int b : blogs) {
            pw.print((b+1) + " ");
        }
    }
}