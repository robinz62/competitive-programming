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

    void solve() throws IOException {
        int t = ri();
        for (int ti = 0; ti < t; ti++) {
            int[] nm = ril(2);
            int n = nm[0];
            int m = nm[1];
            int[][] ab = new int[m][];
            for (int i = 0; i < m; i++) {
                ab[i] = ril(2);
            }

            // original indices sorted by (a) value
            List<Integer> l = new ArrayList<>(m);
            for (int i = 0; i < m; i++) l.add(i);
            Collections.sort(l, (i1, i2) -> -Integer.compare(ab[i1][0], ab[i2][0]));

            // map from original index to index in sorted
            Map<Integer, Integer> map = new HashMap<>();
            for (int i = 0; i < l.size(); i++) map.put(l.get(i), i);

            // sorted (a) values
            List<Integer> ll = new ArrayList<>(m);
            for (int i = 0; i < m; i++) ll.add(ab[l.get(i)][0]);

            long[] prefix = new long[m];
            prefix[0] = ll.get(0);
            for (int i = 1; i < m; i++) prefix[i] = prefix[i-1] + ll.get(i);

            long best = 0;
            for (int i = 0; i < m; i++) {
                long value = ab[i][0] + (long) (n - 1) * ab[i][1];
                int idx = binarySearchRight(ll, 0, m-1, ab[i][1]);

                if (idx < 0) idx = -idx - 1 - 1;
                int count = Math.min(idx+1, n-1);
                if (count > 0) {
                    value -= (long) count * ab[i][1];
                    value += prefix[count-1];
                    int myIdx = map.get(i);
                    if (myIdx <= count-1 && (count == ll.size() || ll.get(count) < ab[i][0])) {
                        value = value - ab[i][0] + ab[i][1];
                    }
                }
                best = Math.max(best, value);
            }
            pw.println(best);
            br.readLine();
        }
    }

    public static int binarySearchRight(List<Integer> A, int l, int r, int k) {
        int upper = -1;
        while (l <= r) {
            int m = l + (r - l) / 2;
            if (A.get(m) == k && (m+1==A.size() || A.get(m+1) < k)) {
                upper = m;
                break;
            }
            if (A.get(m) >= k) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return upper >= 0 ? upper : -l - 1;
    }
}