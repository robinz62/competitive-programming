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
        int[] nk = ril(2);
        int n = nk[0];
        int k = nk[1];
        int[] p = ril(n);
        int[] b = ril(k);
        for (int i = 0; i < n; i++) p[i]--;
        for (int i = 0; i < k; i++) b[i]--;

        int[] numToIdx = new int[n];
        for (int i = 0; i < n; i++) numToIdx[p[i]] = i;

        boolean[] remove = new boolean[n];
        Arrays.fill(remove, true);
        for (int bi : b) remove[bi] = false;
        List<Integer> toRemove = new ArrayList<>(n - k);
        for (int i = 0; i < n; i++) if (remove[i]) toRemove.add(i);
        Collections.sort(toRemove);

        int[] prevSmaller = new int[n];
        prevSmaller[0] = -1;
        int prevIdx = remove[p[0]] ? -1 : 0;
        for (int i = 1; i < n; i++) {
            int j = prevIdx;
            while (j != -1 && p[i] < p[j]) j = prevSmaller[j];
            prevSmaller[i] = j;
            if (!remove[p[i]]) prevIdx = i;
        }
        int[] nextSmaller = new int[n];
        nextSmaller[n-1] = n;
        prevIdx = remove[p[n-1]] ? n : n-1;
        for (int i = n-2; i >= 0; i--) {
            int j = prevIdx;
            while (j != n && p[i] < p[j]) j = nextSmaller[j];
            nextSmaller[i] = j;
            if (!remove[p[i]]) prevIdx = i;
        }

        int[] init = new int[n];
        Arrays.fill(init, 1);
        SegmentTree st = new SegmentTree(init);
        long ans = 0;
        for (int x : toRemove) {
            int idx = numToIdx[x];
            int l = prevSmaller[idx] + 1;
            int r = nextSmaller[idx] - 1;
            ans += st.query(l, r+1);
            st.modify(idx, 0);
        }
        pw.println(ans);
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
    int identity = 0;
    int combine(int a, int b) {
        return a + b;
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