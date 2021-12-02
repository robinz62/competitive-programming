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
        int n = ri();
        int[] ain = ril(n);
        int[] bin = ril(n);
        int[] a = new int[n+1];
        int[] b = new int[n+1];
        System.arraycopy(ain, 0, a, 1, n);
        System.arraycopy(bin, 0, b, 1, n);

        // Naive graph has O(n) nodes and O(n^2) edges.
        // Idea: when iterating edges, iterate over unvisted nodes only

        TreeSet<Integer> unvisitedPreSlip = new TreeSet<>();
        Set<Integer> visitedPostSlip = new HashSet<>();
        for (int i = 1; i < n; i++) unvisitedPreSlip.add(i);
        visitedPostSlip.add(n);

        Deque<Integer> q = new ArrayDeque<>();
        // The order is [from[i] -> abcd[i] -> i]
        int[] from = new int[n+1];
        int[] dist = new int[n+1];
        int[] abcd = new int[n+1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        from[n] = -1;
        dist[n] = 0;
        q.addLast(n);
        while (!q.isEmpty()) {
            int u = q.removeFirst();

            if (u - a[u] == 0) {
                dist[0] = dist[u] + 1;
                from[0] = u;
                break;
            }
            
            // We can reach the range [u - a[u], u].
            Integer v = unvisitedPreSlip.ceiling(u - a[u]);
            while (v != null && v < u) {
                int postSlip = v + b[v];
                if (dist[u] + 1 < dist[postSlip]) {
                    dist[postSlip] = dist[u] + 1;
                    from[postSlip] = u;
                    abcd[postSlip] = v;
                    if (!visitedPostSlip.contains(postSlip)) {
                        visitedPostSlip.add(postSlip);
                        q.addLast(postSlip);
                    }
                }
                unvisitedPreSlip.remove(v);

                v = unvisitedPreSlip.ceiling(v);
            }
        }

        if (dist[0] == Integer.MAX_VALUE) {
            pw.println("-1");
        } else {
            pw.println(dist[0]);
            List<Integer> ans = new ArrayList<>();
            int curr = 0;
            while (curr != n) {
                ans.add(abcd[curr]);
                curr = from[curr];
            }
            Collections.reverse(ans);
            for (int node : ans) pw.print(node + " ");
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