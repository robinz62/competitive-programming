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
        long[] nx = rll();
        int n = (int) nx[0];
        long x = nx[1];
        int[] d = ril();

        // observation: optimal solution starts or ends on the start/end
        // of a month

        // allows queries for the number of hugs between days i and j
        // of a single month
        long[] prefix = new long[1000001];
        for (int i = 1; i <= 1000000; i++) {
            prefix[i] = prefix[i-1] + i;
        }

        // allows querying #days between months i and j, inclusive
        // long[] dprefix = new long[n+1];
        // for (int i = 1; i <= n; i++) {
        //     dprefix[i] = dprefix[i-1] + d[i-1];
        // }

        long ans = 0;
        long curr = 1;
        int L = 0;
        int l = 1;
        int R = 0;
        int r = 1;
        long days = 1;
        while (days < x) {
            if (days + d[R] <= x) {
                days += d[R];
                curr += query(prefix, 1, d[R]);  // note the 1 is for the next month
                R++;
            } else {
                int left = (int) (x - days);
                days += left;
                curr += query(prefix, 2, left + 1);
                r += left;
            }
        }
        ans = curr;
        do {
            int deltal = d[L] - l;
            int deltar = d[R] - r;
            if (deltal == 0 || deltar == 0) {
                curr -= l;
                if (deltal == 0) {
                    L = (L + 1) % n;
                    l = 1;
                } else {
                    l++;
                }
                if (deltar == 0) {
                    R = (R + 1) % n;
                    r = 1;
                } else {
                    r++;
                }
                curr += r;
            } else {
                int delta = Math.min(deltal, deltar);
                curr -= query(prefix, l, l + delta - 1);
                curr += query(prefix, r+1, r + delta);
                l += delta;
                r += delta;
            }
            ans = Math.max(ans, curr);
        } while (!(L == 0 && l == 1));
        pw.println(ans);
    }

    long query(long[] prefix, int i, int j) {
        return prefix[j] - prefix[i-1];
    }
}