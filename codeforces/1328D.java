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
        int q = ri();
        for (int qi = 0; qi < q; qi++) {
            int n = ri();
            int[] t = ril(n);
            List<Integer> ans = new ArrayList<>();
            ans.add(1);
            int curr = 1;
            int colors = 1;
            for (int i = 1; i < n; i++) {
                if (t[i] != t[i-1]) {
                    colors = 2;
                    curr = 3 - curr;
                }
                ans.add(curr);
            }
            if (t[0] != t[n-1] && ans.get(0) == ans.get(n-1)) {
                boolean fixed = false;
                for (int i = 1; i < n; i++) {
                    if (t[i] == t[i-1]) {
                        fixed = true;
                        for (int j = i; j < n; j++) {
                            ans.set(j, 3 - ans.get(j));
                        }
                        break;
                    }
                }
                if (!fixed) {
                    ans.set(n-1, 3);
                    colors = 3;
                }
            }
            pw.println(colors);
            for (int i : ans) pw.print(i + " ");
            pw.println();
        }
    }
}