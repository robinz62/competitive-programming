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
        int[] nm = ril(2);
        int n = nm[0];
        int m = nm[1];
        int[] xky = ril(3);
        int x = xky[0];
        int k = xky[1];
        int y = xky[2];
        int[] a = ril(n);
        int[] b = ril(m);

        List<Integer> streakDelete = new ArrayList<>();
        List<Integer> maxs = new ArrayList<>();
        int i = 0;
        for (int j = 0; j < m; j++) {
            int count = 0;
            int max = -1;
            while (i < n && a[i] != b[j]) {
                max = Math.max(max, a[i]);
                count++;
                i++;
            }
            if (i == n) {
                streakDelete = null;
                break;
            }
            streakDelete.add(count);
            maxs.add(max);
            count = 0;
            i++;
        }
        if (streakDelete != null) {  // delete to right of last
            int count = 0;
            int max = -1;
            while (i < n) {
                max = Math.max(max, a[i]);
                count++;
                i++;
            }
            streakDelete.add(count);
            maxs.add(max);
        }

        if (streakDelete == null) {
            pw.println("-1");
            return;
        }
        long ans = 0;
        for (i = 0; i < streakDelete.size(); i++) {
            int border = Math.max(i-1>=0?b[i-1]:-1, i<m?b[i]:-1);
            int count = streakDelete.get(i);
            int max = maxs.get(i);
            if (border < max) {
                // must use fireball for last kill
                if (count < k) {
                    pw.println("-1");
                    return;
                }
                long usefire = (long) y * (count % k) + (long) x * (count / k);
                long usebeserk = (long) y * (count - k) + (long) x;
                ans += Math.min(usefire, usebeserk);
            } else {
                long usefire = (long) y * (count % k) + (long) x * (count / k);
                long usebeserk = (long) y * count;
                ans += Math.min(usefire, usebeserk);
            }
        }
        pw.println(ans);
    }
}