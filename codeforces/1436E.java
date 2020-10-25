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
        int n = ri();
        int[] a = ril(n);
        Map<Integer, List<Integer>> numToIdx = new HashMap<>();
        for (int i = 0; i < n; i++) {
            if (!numToIdx.containsKey(a[i])) numToIdx.put(a[i], new ArrayList<>());
            numToIdx.get(a[i]).add(i);
        }

        // 5 ... 5
        // Is 5 included in the list of MEXes? YES iff all numbers < 5 are found
        // within the ... range.

        boolean[] isPresent = new boolean[n+3];
        Arrays.fill(isPresent, false);
        // Query [l, r, x] returns the minimum index of all numbers < x
        // For x to be a mex, the answer must be >= l
        List<int[]> queries = new ArrayList<>();
        for (int num : numToIdx.keySet()) {
            if (num == 1) continue;
            if (num != 1) isPresent[1] = true;
            List<Integer> indices = numToIdx.get(num);
            if (indices.get(0) > 0) queries.add(new int[]{0, indices.get(0)-1, num});
            for (int i = 0; i < indices.size() - 1; i++) {
                int i1 = indices.get(i);
                int i2 = indices.get(i+1);
                if (i2-i1-1>0) queries.add(new int[]{i1, i2, num});
            }
            int lastIdx = indices.get(indices.size() - 1);
            if (lastIdx != n-1) queries.add(new int[]{lastIdx, n-1, num});
        }
        Collections.sort(queries, (q1, q2) -> Integer.compare(q1[1], q2[1]));

        int[] arr = new int[n+1];
        Arrays.fill(arr, Integer.MIN_VALUE);
        SegmentTree st = new SegmentTree(arr);
        st.modify(a[0], 0);
        int r = 0;
        for (int[] q : queries) {
            while (r <= q[1]) {
                st.modify(a[r], r);
                r++;
            }
            int ret = st.query(1, q[2]);
            if (ret >= q[0]) {
                isPresent[q[2]] = true;
            }
        }

        // Mex of whole array?
        boolean[] b = new boolean[n+2];
        for (int ai : a) b[ai] = true;
        for (int i = 1; i <= n+1; i++) {
            if (!b[i]) {
                isPresent[i] = true;
                break;
            }
        }

        for (int i = 1; i <= n+2; i++) {
            if (!isPresent[i]) {
                pw.println(i);
                return;
            }
        }
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

class SegmentTree {
    int n;
    int[] st;

    // Modify identity and combine together.
    int identity = Integer.MAX_VALUE;
    int combine(int a, int b) {
        return Math.min(a, b);
    }

    SegmentTree(int[] arr) {
        n = arr.length;
        st = new int[n*2];
        for (int i = 0; i < n; i++) {
            st[n + i] = arr[i];
        }
        build();
    }

    void build() {
        for (int i = n - 1; i > 0; i--) {
            st[i] = combine(st[i*2], st[i*2+1]);
        }
    }

    void modify(int i, int value) {
        st[n + i] = value;
        for (i = (n + i) / 2; i > 0; i /= 2) {
            st[i] = combine(st[i*2], st[i*2+1]);
        }
    }

    // Note: input range is half-open [l, r)
    int query(int l, int r) {
        l += n;
        r += n;
        int resl = identity;
        int resr = identity;
        while (l < r) {
            if ((l & 1) > 0) resl = combine(resl, st[l++]);
            if ((r & 1) > 0) resr = combine(st[--r], resr);
            l /= 2;
            r /= 2;
        }
        return combine(resl, resr);
    }
}