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
        int[] nmks = ril(4);
        int n = nmks[0];
        int m = nmks[1];
        int k = nmks[2];
        int s = nmks[3];
        int[] a = ril(n);
        int[] b = ril(n);
        int[] pa = new int[n];
        int[] pb = new int[n];
        pa[0] = 0;  // index of min from [0..i]
        pb[0] = 0;
        for (int i = 1; i < n; i++) {
            if (a[i] < a[pa[i-1]]) pa[i] = i;
            else pa[i] = pa[i-1];
            if (b[i] < b[pb[i-1]]) pb[i] = i;
            else pb[i] = pb[i-1];
        }
        List<int[]> t1s = new ArrayList<>();  // costs of type 1s and index
        List<int[]> t2s = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            int[] tc = ril(2);
            if (tc[0] == 1) t1s.add(new int[]{tc[1], i});
            else t2s.add(new int[]{tc[1], i});
        }
        Collections.sort(t1s, (g1, g2) -> Integer.compare(g1[0], g2[0]));
        Collections.sort(t2s, (g1, g2) -> Integer.compare(g1[0], g2[0]));
        long sum = 0;
        long[] pt1s = new long[t1s.size()];  // pt1s[i] is cost of cheapest i+1 type 1s
        for (int i = 0; i < t1s.size(); i++) {
            sum += t1s.get(i)[0];
            pt1s[i] = sum;
        }
        sum = 0;
        long[] pt2s = new long[t2s.size()];
        for (int i = 0; i < t2s.size(); i++) {
            sum += t2s.get(i)[0];
            pt2s[i] = sum;
        }
        int ansDay = Integer.MAX_VALUE;
        int ansNum1 = -1;
        int ansDay1 = -1;
        int ansNum2 = -1;
        int ansDay2 = -1;
        for (int x = 0; x <= k; x++) {
            int y = k - x;
            if (x > pt1s.length || y > pt2s.length) continue;
            // binary search for earliest day where we have enough money
            int l = 0;
            int r = n-1;
            int minDay = Integer.MAX_VALUE;
            int minD1 = n;
            int minD2 = n;
            while (l <= r) {
                int mid = l + (r - l) / 2;
                long cost = a[pa[mid]] * (x-1>=0?pt1s[x-1]:0) + b[pb[mid]] * (y-1>=0?pt2s[y-1]:0);
                if (cost <= s) {
                    if (mid < minDay) {
                        minDay = mid;
                        minD1 = pa[mid];
                        minD2 = pb[mid];
                    }
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            }
            if (minDay < ansDay) {
                ansDay = minDay;
                ansNum1 = x;
                ansDay1 = minD1;
                ansNum2 = y;
                ansDay2 = minD2;
            }
        }
        if (ansDay == Integer.MAX_VALUE) {
            pw.println("-1");
        } else {
            pw.println(ansDay+1);
            for (int i = 0; i < ansNum1; i++) {
                pw.println((t1s.get(i)[1]+1) + " " + (ansDay1+1));
            }
            for (int i = 0; i < ansNum2; i++) {
                pw.println((t2s.get(i)[1]+1) + " " + (ansDay2+1));
            }
        }
    }
}