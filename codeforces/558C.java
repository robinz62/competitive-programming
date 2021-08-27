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

    // Tight constraints nightmare...
    // Using a hash set to keep track of visited for each run of bfs took too
    // much overhead to pass the tests. Needed to replace that with a persistent
    // visited array used in a janky way, where visited[i] = j means i was
    // visited during the jth bfs.
    // Very dumb...
    //
    // Takeaway is that to be completely safe, when doing many runs of bfs, try
    // to re-use the visited array (even though it shouldn't be necessary).
    void solve() throws IOException {
        int n = ri();
        int[] a = ril(n);
        limit = a[0];
        for (int ai : a) limit = Math.max(limit, ai);
        int[] count = new int[limit + 1];
        int[] dist = new int[limit + 1];
        int[] visited = new int[limit + 1];
        Arrays.fill(visited, -1);
        Deque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            int d = 1;
            int x = a[i];
            visited[x] = i;
            count[x]++;
            q.addLast(x);
            while (!q.isEmpty()) {
                int sz = q.size();
                for (int p = 0; p < sz; p++) {
                    int u = q.removeFirst();
                    if (u * 2 <= limit && visited[u * 2] != i) {
                        count[u*2]++;
                        dist[u*2] += d;
                        visited[u * 2] = i;
                        q.addLast(u * 2);
                    }
                    if (visited[u / 2] != i) {
                        count[u/2]++;
                        dist[u/2] += d;
                        visited[u / 2] = i;
                        q.addLast(u / 2);
                    }
                }
                d++;
            }
        }

        int ans = Integer.MAX_VALUE;
        for (int i = 0; i <= limit; i++) {
            if (count[i] == n) ans = Math.min(ans, dist[i]);
        }
        pw.println(ans);
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    int limit;

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