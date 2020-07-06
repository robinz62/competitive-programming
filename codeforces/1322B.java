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
        int ans = 0;
        long total = (long) n * (n - 1) / 2;
        for (int i = 24; i >= 0; i--) {
            for (int j = 0; j < n; j++) {
                a[j] = a[j] % (1 << (i+1));
            }
            Arrays.sort(a);
            long x = countLt(a, 1 << i);
            long y = countLt(a, 1 << (i + 1));
            long z = countLt(a, (1 << i) + (1 << (i + 1)));
            long count1 = (y-x) + (total - z);
            if (count1 % 2 == 1) ans |= (1 << i);
        }
        pw.println(ans);
    }
    
    long countLt(int[] a, int tgt) {
        int i = 0;
        int j = a.length-1;
        long ans = 0;
        while (i < j) {
            int sum = a[i] + a[j];
            if (sum < tgt) {
                ans += j - i;
                i++;
            } else {
                j--;
            }
        }
        return ans;
    }
}