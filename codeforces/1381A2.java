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
        int T = ri();
        for (int ti = 0; ti < T; ti++) {
            int n = ri();
            char[] a = rs();
            char[] b = rs();
            List<Integer> ans = new ArrayList<>();
            boolean reversed = false;
            int l = 0;
            int r = n-1;
            for (int i = n-1; i >= 0; i--) {  // idx in b
                if (!reversed) {
                    if (a[l] != b[i]) {
                        ans.add(i);
                        reversed = true;
                        l++;
                    } else {
                        ans.add(0);
                        ans.add(i);
                        reversed = true;
                        l++;
                    }
                } else {
                    char me = a[r] == '0' ? '1' : '0';
                    if (me != b[i]) {
                        ans.add(i);
                        reversed = false;
                        r--;
                    } else {
                        ans.add(0);
                        ans.add(i);
                        reversed = false;
                        r--;
                    }
                }
            }
            pw.print(ans.size());
            for (int x : ans) pw.print(" " + (x+1));
            pw.println();
        }
    }
}