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
    //   if (x : long) and (y : int), [y = x] does not compile, but [y += x] does
    //   sorting, or taking max, after MOD
    void solve() throws IOException {
        int n = ri();
        int[] a = ril(2 * n);

        // Want as many of minimum as possible in first half. Not true.
        // If the partner of any of the min is smaller, choose that. Answer is length 2.

        int min = a[0];
        for (int i = 1; i < n; i++) min = Math.min(min, a[i]);
        List<Integer> idxMins = new ArrayList<>();
        int idxSmallestPartner = -1;
        for (int i = 0; i < n; i++) {
            if (a[i] == min) {
                if (idxSmallestPartner == -1 || a[i + n] < a[idxSmallestPartner]) {
                    idxSmallestPartner = i;
                }
                idxMins.add(i);
            }
        }

        // Equal is also fine, since we get to have [min, min]
        if (a[idxSmallestPartner + n] <= min) {
            pw.println(a[idxSmallestPartner] + " " + a[idxSmallestPartner + n]);
            return;
        }

        SegmentTree st = new SegmentTree(a);
        List<Integer> ans = new ArrayList<>();
        for (int idx : idxMins) ans.add(idx);

        // None of the partners are <= min.
        // Now, we can be sure that we want ALL of the mins.
        // However, it is possible that we also want some numbers AFTER the last min
        // because that number is SMALLER than the FIRST partner. We want to grab
        // all of these guys "greedily"
        //
        // If the number is EQUAL TO the FIRST partner, then we should take it if
        // the partners next guy (which is not equal)
        boolean goesUp = false;
        for (int i = 0; i < ans.size() - 1; i++) {
            if (a[ans.get(i) + n] == a[ans.get(i+1) + n]) continue;
            else if (a[ans.get(i) + n] < a[ans.get(i+1) + n]) goesUp = true;
            else goesUp = false;
            break;
        }

        Map<Integer, List<Integer>> valToIdx = new HashMap<>();
        for (int i = 0; i < n; i++) {
            if (!valToIdx.containsKey(a[i])) valToIdx.put(a[i], new ArrayList<>());
            valToIdx.get(a[i]).add(i);
        }

        int mustBeSmallerThan = a[idxMins.get(0) + n];
        int l = idxMins.get(idxMins.size() - 1) + 1;
        while (true) {
            int minInOurRange = st.query(l, n);
            if (minInOurRange < mustBeSmallerThan) {
                // Take as many of these as possible
                List<Integer> indices = valToIdx.get(minInOurRange);
                int idx = Collections.binarySearch(indices, l);
                if (idx < 0) idx = -idx-1;
                for (int i = idx; i < indices.size(); i++) ans.add(indices.get(i));
                l = indices.get(indices.size() - 1) + 1;
            } else if (minInOurRange == mustBeSmallerThan) {
                int right = idxMins.get(0) + n + 1;
                if (goesUp) {
                    // Take as many of these as possible
                    List<Integer> indices = valToIdx.get(minInOurRange);
                    int idx = Collections.binarySearch(indices, l);
                    if (idx < 0) idx = -idx-1;
                    for (int i = idx; i < indices.size(); i++) ans.add(indices.get(i));
                    l = indices.get(indices.size() - 1) + 1;
                } else break;
            } else {
                break;
            }
        }

        for (int idx : ans) pw.print(a[idx] + " ");
        for (int idx : ans) pw.print(a[idx + n] + " ");
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
        int c = br.read();
        int x = 0;
        while (c >= '0' && c <= '9') {
            x = x * 10 + c - '0';
            c = br.read();
        }
        return ril(x);
    }

    long[] rkll() throws IOException {
        int c = br.read();
        int x = 0;
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
