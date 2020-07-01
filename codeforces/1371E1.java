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
        int[] np = ril(2);
        int n = np[0];
        int p = np[1];
        int[] a = ril(n);
        Arrays.sort(a);
        List<Integer> ans = new ArrayList<>();
        // min ai <= x <= max ai is sufficient
        for (int x = 1; x <= 2000; x++) {
            boolean good = true;
            for (int i = a.length - 1; good && i >= 0; i--) {
                int j = Math.max(a[i] - x, 0);
                if (j >= a.length) good = false;
                int spots = a.length-1-j+1;
                int taken = a.length-1-i;
                int factor = (spots - taken);
                if (factor % p == 0) {
                    good = false;
                }
            }
            if (good) ans.add(x);
        }
        pw.println(ans.size());
        for (int x : ans) pw.print(x + " ");
        pw.println();
    }
}