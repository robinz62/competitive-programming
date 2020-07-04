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
        int[] l = ril(m);
        List<Integer> ans = new ArrayList<>();
        int[] reach = new int[m];  // reach[i] is reach of numbers up to i
        boolean good = true;
        for (int i = 0; good && i < m; i++) {
            ans.add(i);
            if (i + l[i] - 1 >= n) {
                good = false;
                break;
            }
            reach[i] = Math.max(i-1 >= 0 ? reach[i-1] : Integer.MIN_VALUE, i + l[i] - 1);
        }
        if (!good) {
            pw.println("-1");
            return;
        }

        int lbound = n;

        // while reach[i] < lbound-1
        int i = m-1;
        for (; reach[i] < lbound - 1 && i > 0; i--) {
            // move current to the right just touching lbound-1
            ans.set(i, lbound - l[i]);
            lbound = lbound - l[i];
        }

        if (reach[i] >= lbound - 1) {
            for (int x : ans) pw.print((x+1) + " ");
            pw.println();
            return;
        } else {
            pw.println("-1");
        }
    }
}