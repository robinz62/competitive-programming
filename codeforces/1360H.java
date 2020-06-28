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

    int[] ril() throws IOException {
        String[] tokens = br.readLine().split(" ");
        int[] A = new int[tokens.length];
        for (int i = 0; i < A.length; i++)
            A[i] = Integer.parseInt(tokens[i]);
        return A;
    }

    long[] rll() throws IOException {
        String[] tokens = br.readLine().split(" ");
        long[] A = new long[tokens.length];
        for (int i = 0; i < A.length; i++)
            A[i] = Long.parseLong(tokens[i]);
        return A;
    }

    void solve() throws IOException {
        int t = ri();
        for (int ti = 0; ti < t; ti++) {
            int[] nm = ril();
            int n = nm[0];
            int m = nm[1];
            long[] a = new long[n];
            TreeSet<long[]> nums = new TreeSet<>((i1, i2) -> Long.compare(i1[0], i2[0]));
            nums.add(new long[]{0, (1l << m) - 1});
            for (int i = 0; i < n; i++) {
                a[i] = Long.parseLong(br.readLine(), 2);
                remove(nums, a[i]);
            }

            List<long[]> intervals = new ArrayList<>(nums);
            int L = 0;
            int R = intervals.size() - 1;
            long lval = intervals.get(L)[0];
            long rval = intervals.get(R)[1];
            while (L < R) {
                long ll = intervals.get(L)[1]-lval+1;
                long rr = rval-intervals.get(R)[0]+1;
                if (ll == rr) {
                    L++;
                    R--;
                    lval = intervals.get(L)[0];
                    rval = intervals.get(R)[1];
                } else if (ll < rr) {
                    L++;
                    lval = intervals.get(L)[0];
                    rval -= ll;
                } else {
                    R--;
                    lval += rr;
                    rval = intervals.get(R)[1];
                }
            }

            String ans;
            if (L > R) {
                ans = Long.toString(intervals.get(R)[1], 2);
            } else {
                ans = Long.toString(lval + (rval - lval) / 2, 2);
            }
            if (ans.length() < m) {
                char[] prefix = new char[m - ans.length()];
                Arrays.fill(prefix, '0');
                ans = new String(prefix) + ans;
            }
            pw.println(ans);
        }
    }

    void remove(TreeSet<long[]> set, long x) {
        long[] i = set.floor(new long[]{x, x});
        if (i[0] == i[1]) {
            set.remove(i);
            return;
        }
        if (i[0] == x) {
            set.remove(i);
            i[0]++;
            set.add(i);
            return;
        }
        if (i[1] == x) {
            i[1]--;
            return;
        }
        set.remove(i);
        long[] a = new long[]{i[0], x-1};
        long[] b = new long[]{x+1, i[1]};
        set.add(a);
        set.add(b);
    }
}