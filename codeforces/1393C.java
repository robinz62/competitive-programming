import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static int MOD = 1000000007;

    void solve() throws IOException {
        int t = ri();
        for (int ti = 0; ti < t; ti++) {
            int n = ri();
            int[] a = ril(n);
            int[] freq = new int[n+1];
            Set<Integer> set = new HashSet<>();
            for (int ai : a) {
                set.add(ai);
                freq[ai]++;
            }
            int l = 0;
            int r = n-1;
            int best = 0;
            while (l <= r) {
                int m = l + (r - l) / 2;
                int[] freqcopy = new int[n+1];
                System.arraycopy(freq, 0, freqcopy, 0, n+1);
                if (ok(set, freqcopy, m, n)) {
                    best = Math.max(best, m);
                    l = m+1;
                } else {
                    r = m-1;
                }
            }
            pw.println(best);
        }
    }

    boolean ok(Set<Integer> a, int[] freq, int d, int n) {
        int t = 0;
        Map<Integer, Integer> ts = new HashMap<>();
        PriorityQueue<Integer> pq = new PriorityQueue<>((c1, c2) -> -Integer.compare(freq[c1], freq[c2]));
        for (int ai : a) pq.add(ai);
        int removed = 0;
        while (!pq.isEmpty()) {
            int u = pq.remove();
            removed++;
            ts.put(t, u);
            freq[u]--;
            if (ts.containsKey(t-d) && freq[ts.get(t-d)] > 0) pq.add(ts.get(t-d));
            t++;
        }

        return removed == n;
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
            A[i] ^= A[j];
            A[j] ^= A[i];
            A[i] ^= A[j];
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