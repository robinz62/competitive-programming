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
            int[] nk = ril(2);
            int n = nk[0];
            long k = nk[1];
            int[] l1r1 = ril(2);
            int l1 = l1r1[0];
            int r1 = l1r1[1];
            int[] l2r2 = ril(2);
            int l2 = l2r2[0];
            int r2 = l2r2[1];
            long ans = Long.MAX_VALUE;
            long costToTouch = 0;
            long initIntersect = 0;
            long phase2Count = 0;
            if (r1 <= l2 || r2 <= l1) {  // no intersection
                costToTouch = Math.min(Math.abs(l2 - r1), Math.abs(l1 - r2));
                initIntersect = 0;
                phase2Count = Math.max(r1, r2) - Math.min(l1, l2);
            } else {
                costToTouch = 0;
                initIntersect = Math.min(r1, r2) - Math.max(l1, l2);
                phase2Count = (r1 - l1) + (r2 - l2) - 2 * initIntersect;
                k -= n * initIntersect;
                if (k <= 0) {
                    pw.println("0");
                    continue;
                }
            }
            
            // do all in a single interval
            long cand = costToTouch;
            long required = k;
            if (phase2Count >= required) {
                cand += required;
            } else {
                cand += phase2Count;
                required -= phase2Count;
                cand += required * 2;
            }
            ans = Math.min(ans, cand);

            // do spread among intervals
            cand = 0;
            required = k;
            if (phase2Count != 0) {
                long useFull = required / phase2Count;
                useFull = Math.min(useFull, n);
                cand += useFull * (costToTouch + phase2Count);
                required -= useFull * phase2Count;
                if (required > 0) {
                    // add to existing vs use extra interval
                    if (useFull > 0) {
                        ans = Math.min(ans, cand + 2 * required);
                    }
                    if (n > useFull) {
                        ans = Math.min(ans, cand + costToTouch + required);
                    }
                } else {
                    ans = Math.min(ans, cand);
                }
            } else {
                
            }
            pw.println(ans);
        }
    }
}