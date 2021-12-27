import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);

        int n = Integer.parseInt(br.readLine());
        String[] line = br.readLine().split(" ");
        int[] p = new int[n];
        for (int i = 0; i < n; i++) p[i] = Integer.parseInt(line[i]);
        line = br.readLine().split(" ");
        int[] t = new int[n];
        for (int i = 0; i < n; i++) t[i] = Integer.parseInt(line[i]);

        int[] delta = new int[n];
        for (int i = 0; i < n; i++) delta[i] = t[i] - p[i];

        int[] allpos = new int[n];
        for (int i = 0; i < n; i++) allpos[i] = Math.abs(delta[i]);
        st = new SegmentTree(allpos);

        // pw.println(tle(delta));

        long ans = 0;
        int l = 0;
        for (int r = 0; r < n; r++) {
            if (sign(delta[r]) == sign(delta[l])) continue;
            else {
                if (sign(delta[l]) < 0) for (int i = l; i <= r-1; i++) delta[i] = -delta[i];
                ans += solve(delta, l, r-1, 0);
                l = r;
            }
        }
        if (sign(delta[l]) < 0) for (int i = l; i <= n-1; i++) delta[i] = -delta[i];
        if (sign(delta[l]) != 0) {
            ans += solve(delta, l, n-1, 0);
        }

        pw.println(ans);
        pw.flush();
    }

    static SegmentTree st;
    static TreeSet<Integer> solvedIdx = new TreeSet<>();

    static long solve(int[] d, int l, int r, int add) {
        // Everything in d[l..r] is positive
        if (l > r) return 0;
        int idxMin = st.query(l, r);
        int minVal = d[idxMin] - add;
        long ans = minVal;
        ans += solve(d, l, idxMin-1, add + minVal);
        ans += solve(d, idxMin+1, r, add + minVal);
        return ans;
    }

    static long tle(int[] d) {
        int n = d.length;
        int[] delta = new int[d.length];
        System.arraycopy(d, 0, delta, 0, d.length);

        long ans = 0;
        for (int i = 0; i < n; i++) {
            while (delta[i] != 0) {
                int idxbest = i;
                int best = 1;
                int progress = 1;
                for (int j = i+1; j < n; j++) {
                    if (sign(delta[j]) == sign(delta[i])) progress++;
                    else progress--;
                    if (progress > best) {
                        idxbest = j;
                        best = progress;
                    }
                }
                int dir = -sign(delta[i]);
                for (int j = i; j <= idxbest; j++) delta[j] += dir;
                ans++;
            }
        }

        return ans;
    }

    static int sign(int x) {
        return x > 0 ? 1 : x < 0 ? -1 : 0;
    }
}

class SegmentTree {
    int[] stidx;
    int[] st;
    int[] a;
    int n;

    SegmentTree(int[] a) {
        stidx = new int[a.length*4];
        st = new int[a.length*4];
        this.a = a;
        n = a.length;
        build(a, 1, 0, a.length-1);
    }

    void build(int[] a, int node, int l, int r) {
        if (l > r) {
            st[node] = Integer.MAX_VALUE;
            return;
        }
        if (l == r) {
            st[node] = a[l];
            stidx[node] = l;
            return;
        }
        int m = (l + r) / 2;
        build(a, node * 2, l, m);
        build(a, node * 2 + 1, m+1, r);
        if (st[node*2] <= st[node*2+1]) {
            st[node] = st[node*2];
            stidx[node] = stidx[node*2];
        } else {
            st[node] = st[node*2+1];
            stidx[node] = stidx[node*2+1];
        }
    }

    int query(int node, int l, int r, int ql, int qr) {
        int m = (l + r) / 2;
        if (ql <= l && qr >= r) {  // query engulfs me
            return stidx[node];
        }
        if (qr < l || ql > r) {  // query outside of me
            return -1;
        }
        // query intersects with me in some way
        int left = query(node*2, l, m, ql, qr);
        int right = query(node*2+1, m+1, r, ql, qr);
        int res = -1;
        if (left != -1 && (res == -1 || a[left] < a[res])) res = left;
        if (right != -1 && (res == -1 || a[right] < a[res])) res = right;
        return res;
    } 

    int query(int l, int r) {
        return query(1, 0, n-1, l, r);
    }
}