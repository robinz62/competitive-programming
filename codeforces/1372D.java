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
        int[] a = ril(n);
        long[] sumel = new long[n];
        long[] sumol = new long[n];
        sumel[0] = a[0];
        for (int i = 1; i < n; i++) {
            sumel[i] = sumel[i-1];
            sumol[i] = sumol[i-1];
            if (i % 2 == 0) sumel[i] += a[i];
            else sumol[i] += a[i];
        }
        long[] sumer = new long[n];
        long[] sumor = new long[n];
        sumer[n-1] = a[n-1];
        for (int i = n-2; i >= 0; i--) {
            sumer[i] = sumer[i+1];
            sumor[i] = sumor[i+1];
            if (i % 2 == 0) sumer[i] += a[i];
            else sumor[i] += a[i];
        }
        long ans = sumer[0];
        for (int i = 0; i < n-1; i++) {
            if (i%2==0) ans = Math.max(ans, sumel[i] + sumor[i+1]);
            else ans = Math.max(ans, sumol[i] + sumer[i+1]);
        }
        pw.println(ans);
    }
}