import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static int MOD = 1000000007;

    // After writing solution, quick scan for:
    //   array out of bounds
    //   special cases e.g. n=1?
    //
    // Big numbers arithmetic bugs:
    //   int overflow
    //   sorting, or taking max, after MOD
    void solve() throws IOException {
        int T = ri();
        for (int Ti = 0; Ti < T; Ti++) {
            obtainable = new HashSet<>();
            int[] nq = ril(2);
            int n = nq[0];
            int q = nq[1];
            int[] arr = ril(n);
            Map<Integer, Integer> freq = new HashMap<>();
            for (int ai : arr) freq.put(ai, freq.getOrDefault(ai, 0) + 1);
            List<int[]> a = new ArrayList<>();
            for (Map.Entry<Integer, Integer> e : freq.entrySet()) {
                a.add(new int[]{e.getKey(), e.getValue()});
            }
            int k = a.size();
            Collections.sort(a, (p1, p2) -> Integer.compare(p1[0], p2[0]));
            long[] prefix = new long[k];
            prefix[0] = (long) a.get(0)[0] * a.get(0)[1];
            for (int i = 1; i < k; i++) {
                prefix[i] = prefix[i-1] + (long) a.get(i)[0] * a.get(i)[1];
            }

            // Observations
            //   I'm like pretty sure array order never matters
            //   You can always obtain all of a single number
            //   Equal values can never be separated, might as well maintain (val, count)x
            generate(a, 0, k-1, prefix);

            for (int qi = 0; qi < q; qi++) {
                long s = rl();
                pw.println(obtainable.contains(s) ? "Yes" : "No");
            }
        }
    }

    Set<Long> obtainable;
    void generate(List<int[]> a, int l, int r, long[] prefix) {
        if (l > r || r < 0 || l >= a.size()) return;
        long sum = prefix[r] - (l-1 >= 0 ? prefix[l-1] : 0);
        obtainable.add(sum);
        if (l == r) return;
        int min = a.get(l)[0];
        int max = a.get(r)[0];
        int midVal = (min + max) / 2;
        int midIdx = Collections.binarySearch(a, new int[]{midVal, 0}, (p1, p2) -> Integer.compare(p1[0], p2[0]));
        if (midIdx < 0) midIdx = -midIdx-1 - 1;
        generate(a, l, midIdx, prefix);
        generate(a, midIdx+1, r, prefix);
    }

    List<List<Integer>> slice(List<Integer> a) {
        int min = a.get(0);
        int max = a.get(0);
        for (int i : a) {
            min = Math.min(min, i);
            max = Math.max(max, i);
        }
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        int mid = (max + min) / 2;
        for (int ai : a) {
            if (ai <= mid) left.add(ai);
            else right.add(ai);
        }
        return Arrays.asList(left, right);
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