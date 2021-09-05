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
    //   sorting, or taking max, after MOD
    void solve() throws IOException {
        int n = ri();
        int[] p = ril(n);

        int[] up_right = new int[n];
        int[] dn_right = new int[n];
        for (int i = n-2; i >= 0; i--) {
            if (p[i] < p[i+1]) up_right[i] = up_right[i+1] + 1;
            else dn_right[i] = dn_right[i+1] + 1;
        }
        int[] up_left = new int[n];
        int[] dn_left = new int[n];
        for (int i = 1; i < n; i++) {
            if (p[i] > p[i-1]) dn_left[i] = dn_left[i-1] + 1;
            else up_left[i] = up_left[i-1] + 1;
        }

        TreeSet<Slope> slopesByLength = new TreeSet<>((s1, s2) -> {
            int c = Integer.compare(s1.length, s2.length);
            if (c != 0) return -c; else return -Integer.compare(s1.start, s2.start);
        });
        Slope curr = new Slope();
        curr.increasing = p[1] > p[0];
        curr.start = 0;
        curr.length = 1;
        for (int i = 2; i < n; i++) {
            boolean dir = p[i] > p[i-1];
            if (dir == curr.increasing) {
                curr.length++;
            } else {
                slopesByLength.add(curr);
                curr = new Slope();
                curr.increasing = dir;
                curr.start = i-1;
                curr.length = 1;
            }
        }
        slopesByLength.add(curr);

        int ans = 0;
        for (int x = 1; x < n-1; x++) {
            // Valley pattern
            if (p[x-1] > p[x] && p[x+1] > p[x]) {
                ans++;
                continue;
            }

            // Peak pattern
            if (p[x-1] < p[x] && p[x] > p[x+1]) {
                // Cut off the left ramp.
                int slopelenleft = dn_left[x];
                if (slopelenleft % 2 == 0) slopelenleft--;
                // Q's last chance is to go down to the right.
                int slopelenright = dn_right[x];
                if (slopelenright <= slopelenleft) {
                    ans++;
                    continue;
                }

                // Cut off the right.
                slopelenright = dn_right[x];
                if (slopelenright % 2 == 0) slopelenright--;
                slopelenleft = dn_left[x];
                if (slopelenleft <= slopelenright) {
                    ans++;
                    continue;
                }

                // Choose an unrelated slope.
                int qmax = Math.max(dn_left[x], dn_right[x]);
                Iterator<Slope> it = slopesByLength.iterator();
                Slope cand = it.next();
                boolean found = false;
                if (cand.increasing && cand.start == x - dn_left[x] || !cand.increasing && cand.start == x) {
                    // bad
                } else {
                    found = true;
                }
                if (!found) {
                    cand = it.next();
                    if (cand.increasing && cand.start == x - dn_left[x] || !cand.increasing && cand.start == x) {
                        // bad
                    } else {
                        found = true;
                    }
                }
                if (!found) {
                    if (it.hasNext()) {
                        cand = it.next();
                        if (cand.increasing && cand.start == x - dn_left[x] || !cand.increasing && cand.start == x) {
                            // bad
                            // should be impossible
                        } else {
                            found = true;
                        }
                    }
                }
                if (!found) continue;
                if (cand.length >= qmax) {
                    ans++;
                    continue;
                }
            }

            // Increasing
            if (p[x-1] < p[x] && p[x] < p[x+1]) {
                ans++;
                continue;
            }

            // Decreasing
            if (p[x-1] > p[x] && p[x] > p[x+1]) {
                ans++;
                continue;
            }
        }

        pw.println(n-2 - ans);
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    class Slope {
        boolean increasing;
        int start;
        int length;
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