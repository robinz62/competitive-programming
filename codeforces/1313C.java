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
        int n = ri();
        int[] m = ril(n);

        if (n == 1) {
            pw.println(m[0]);
            return;
        }

        // dp1[i] is best sum coming from left and increasing
        long[] dp1 = new long[n];
        Deque<int[]> stack = new ArrayDeque<>();  // monotonic inc, holding [val, count]
        dp1[0] = m[0];
        stack.addLast(new int[]{m[0], 1});
        for (int i = 1; i < n; i++) {
            int count = 0;
            long dec = 0;
            while (!stack.isEmpty() && stack.peekLast()[0] >= m[i]) {
                int[] s = stack.removeLast();
                dec += (long) s[0] * s[1];
                count += s[1];
            }
            count++;
            stack.addLast(new int[]{m[i], count});
            dp1[i] = dp1[i-1] - dec + (long) m[i] * count;
        }

        long[] dp2 = new long[n];
        stack.clear();
        dp2[n-1] = m[n-1];
        stack.addLast(new int[]{m[n-1], 1});
        for (int i = n-2; i >= 0; i--) {
            int count = 0;
            long dec = 0;
            while (!stack.isEmpty() && stack.peekLast()[0] >= m[i]) {
                int[] s = stack.removeLast();
                dec += (long) s[0] * s[1];
                count += s[1];
            }
            count++;
            stack.addLast(new int[]{m[i], count});
            dp2[i] = dp2[i+1] - dec + (long) m[i] * count;
        }

        int ansIdx = 0;
        long maxFloors = 0;
        for (int i = 0; i < n-1; i++) {
            if (dp1[i] + dp2[i+1] > maxFloors) {
                ansIdx = i;
                maxFloors = dp1[i] + dp2[i+1];
            }
        }

        int[] ans = new int[n];
        ans[ansIdx] = m[ansIdx];
        ans[ansIdx+1] = m[ansIdx+1];
        for (int i = ansIdx-1; i >= 0; i--) {
            ans[i] = Math.min(ans[i+1], m[i]);
        }
        for (int i = ansIdx+2; i < n; i++) {
            ans[i] = Math.min(ans[i-1], m[i]);
        }
        for (int ai : ans) pw.print(ai + " ");
        pw.println();
    }
}