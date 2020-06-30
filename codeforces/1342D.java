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

    void solve() throws IOException {
        int[] nk = ril(2);
        int n = nk[0];
        int k = nk[1];
        int[] m = ril(n);
        int[] c = ril(k);
        TreeMap<Integer, Integer> freq = new TreeMap<>();
        for (int mi : m) freq.put(mi, freq.getOrDefault(mi, 0) + 1);
        TreeMap<Integer, Integer> map = new TreeMap<>();  // number -> the last c value with that number
        for (int i = 0; i < k; i++) {
            map.put(c[i], i+1);
        }
        List<TestCase> ans = new ArrayList<>();
        PriorityQueue<TestCase> pq = new PriorityQueue<>((tc1, tc2) -> -Integer.compare(tc1.cIdx, tc2.cIdx));
        while (!freq.isEmpty()) {
            int mi = freq.lastKey();
            int fi = freq.get(mi);
            if (pq.isEmpty() || pq.peek().cIdx < mi) {
                TestCase tc = new TestCase();
                tc.cIdx = k;
                int support = Math.min(c[tc.cIdx-1] - tc.numTests, fi);
                tc.numTests += support;
                for (int i = 0; i < support; i++) tc.sizes.add(mi);
                if (tc.numTests >= c[tc.cIdx-1]) {
                    Integer newIdx = map.higherKey(tc.numTests);
                    if (newIdx != null) {
                        tc.cIdx = map.get(newIdx);
                        pq.add(tc);
                    }
                } else {
                    pq.add(tc);
                }
                ans.add(tc);
                if (fi == support) freq.remove(mi);
                else freq.put(mi, fi - support);
            } else {
                TestCase tc = pq.remove();
                int support = Math.min(c[tc.cIdx-1] - tc.numTests, fi);
                tc.numTests += support;
                for (int i = 0; i < support; i++) tc.sizes.add(mi);
                if (tc.numTests >= c[tc.cIdx-1]) {
                    Integer newIdx = map.higherKey(tc.numTests);
                    if (newIdx != null) {
                        tc.cIdx = map.get(newIdx);
                        pq.add(tc);
                    }
                } else {
                    pq.add(tc);
                }
                if (fi == support) freq.remove(mi);
                else freq.put(mi, fi - support);
            }
        }
        pw.println(ans.size());
        for (TestCase tc : ans) {
            pw.print(tc.numTests);
            for (int a : tc.sizes) {
                pw.print(" " + a);
            }
            pw.println();
        }
    }
}

class TestCase {
    int cIdx;
    int numTests;
    List<Integer> sizes = new ArrayList<>();
}