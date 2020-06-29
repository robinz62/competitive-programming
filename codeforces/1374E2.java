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

    int[] t;
    void solve() throws IOException {
        int[] nmk = ril(3);
        int n = nmk[0];
        int m = nmk[1];
        int k = nmk[2];
        t = new int[n];
        List<Integer> a = new ArrayList<>();
        List<Integer> b = new ArrayList<>();
        List<Integer> c = new ArrayList<>();
        List<Integer> d = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int[] tab = ril(3);
            t[i] = tab[0];
            if (tab[1] == 1 && tab[2] == 1) c.add(i);
            else if (tab[1] == 1) a.add(i);
            else if (tab[2] == 1) b.add(i);
            else d.add(i);
        }

        Collections.sort(a, (i1, i2) -> Integer.compare(t[i1], t[i2]));
        Collections.sort(b, (i1, i2) -> Integer.compare(t[i1], t[i2]));
        Collections.sort(c, (i1, i2) -> Integer.compare(t[i1], t[i2]));
        Collections.sort(d, (i1, i2) -> Integer.compare(t[i1], t[i2]));
        int[] aprefix = new int[a.size()+1];
        int[] bprefix = new int[b.size()+1];
        int[] cprefix = new int[c.size()+1];
        int[] dprefix = new int[d.size()+1];
        for (int i = 1; i <= a.size(); i++) aprefix[i] = aprefix[i-1] + t[a.get(i-1)];
        for (int i = 1; i <= b.size(); i++) bprefix[i] = bprefix[i-1] + t[b.get(i-1)];
        for (int i = 1; i <= c.size(); i++) cprefix[i] = cprefix[i-1] + t[c.get(i-1)];
        for (int i = 1; i <= d.size(); i++) dprefix[i] = dprefix[i-1] + t[d.get(i-1)];
        int best = Integer.MAX_VALUE;
        int[] ansOfBest = new int[2];  // [number to grab from c, min value for a/b/c]
        int[] aa = new int[a.size()]; for (int i = 0; i < a.size(); i++) aa[i] = a.get(i);
        int[] bb = new int[b.size()]; for (int i = 0; i < b.size(); i++) bb[i] = b.get(i);
        int[] cc = new int[c.size()]; for (int i = 0; i < c.size(); i++) cc[i] = c.get(i);
        int[] dd = new int[d.size()]; for (int i = 0; i < d.size(); i++) dd[i] = d.get(i);
        for (int i = 0; i <= Math.min(c.size(), m); i++) {
            int need = Math.max(0, k - i);
            if (2 * need + i > m) continue;
            if (need >= aprefix.length || need >= bprefix.length) continue;
            int cost = cprefix[i] + aprefix[need] + bprefix[need];
            int extras = m - 2 * need - i;
            // need to find smallest 'extras' numbers from a[need:], b[need:], c[i:], and d[need:]
            int l = 1;
            int r = 10000;
            int min = 10000;
            while (l <= r) {
                int mid = l + (r - l) / 2;
                int[] ret = helper(mid, aa, bb, cc, dd, need, need, i, 0);
                int count = ret[0] + ret[1] + ret[2] + ret[3];
                if (count >= extras) {
                    min = Math.min(min, mid);
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            }
            // min is the good number now
            int[] ret = helper(min, aa, bb, cc, dd, need, need, i, 0);
            int count = ret[0] + ret[1] + ret[2] + ret[3];
            int excess = (count - extras) * min;
            cost += aprefix[need+ret[0]] - aprefix[need];
            cost += bprefix[need+ret[1]] - bprefix[need];
            cost += cprefix[i+ret[2]] - cprefix[i];
            cost += dprefix[ret[3]];
            cost -= excess;

            if (cost < best) {
                best = cost;
                ansOfBest[0] = i;
                ansOfBest[1] = min;
            }
        }
        if (best == Integer.MAX_VALUE) {
            pw.println("-1");
        } else {
            pw.println(best);
            // initial c's to get
            for (int i = 0; i < ansOfBest[0]; i++) pw.print((cc[i]+1) + " ");
            // requirement 1 a's and b's to get
            int need = Math.max(0, k - ansOfBest[0]);
            for (int i = 0; i < need; i++) {
                pw.print((aa[i]+1) + " ");
                pw.print((bb[i]+1) + " ");
            }
            // finally, the extras
            int ai = need;
            int bi = need;
            int ci = ansOfBest[0];
            int di = 0;
            int extras = Math.max(0, m - 2 * need - ansOfBest[0]);
            for (int i = 0; i < extras; i++) {
                int ta = ai == aa.length ? Integer.MAX_VALUE : t[aa[ai]];
                int tb = bi == bb.length ? Integer.MAX_VALUE : t[bb[bi]];
                int tc = ci == cc.length ? Integer.MAX_VALUE : t[cc[ci]];
                int td = di == dd.length ? Integer.MAX_VALUE : t[dd[di]];
                int min = Math.min(Math.min(Math.min(ta, tb), tc), td);
                if (ta == min) {
                    pw.print((aa[ai++]+1) + " ");
                } else if (tb == min) {
                    pw.print((bb[bi++]+1) + " ");
                } else if (tc == min) {
                    pw.print((cc[ci++]+1) + " ");
                } else {
                    pw.print((dd[di++]+1) + " ");
                }
            }
            pw.println();
        }
    }

    // returns number of eligible elems <= x from each list
    int[] helper(int x, int[] a, int[] b, int[] c, int[] d, int la, int lb, int lc, int ld) {
        int[] ret = new int[4];

        int ia = binarySearchRight(a, la, a.length-1, x);
        if (ia >= 0) ret[0] = Math.max(0, ia - la + 1);
        else ret[0] = Math.max(0, (-ia-1) - la);
        
        int ib = binarySearchRight(b, lb, b.length-1, x);
        if (ib >= 0) ret[1] = Math.max(0, ib - lb + 1);
        else ret[1] = Math.max(0, (-ib-1) - lb);

        int ic = binarySearchRight(c, lc, c.length-1, x);
        if (ic >= 0) ret[2] = Math.max(0, ic - lc + 1);
        else ret[2] = Math.max(0, (-ic-1) - lc);

        int id = binarySearchRight(d, ld, d.length-1, x);
        if (id >= 0) ret[3] = Math.max(0, id - ld + 1);
        else ret[3] = Math.max(0, (-id-1) - ld);

        return ret;
    }

    public int binarySearchRight(int[] A, int l, int r, int k) {
        if (l >= A.length) return -1;
        int upper = -1;
        while (l <= r) {
            int m = l + (r - l) / 2;
            if (t[A[m]] == k && (m+1==A.length || t[A[m+1]] > k)) {
                upper = m;
                break;
            }
            if (t[A[m]] <= k) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return upper >= 0 ? upper : -l - 1;
    }
}