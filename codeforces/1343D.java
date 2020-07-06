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
        int t = ri();
        for (int ti = 0; ti < t; ti++) {
            int[] nk = ril(2);
            int n = nk[0];
            int k = nk[1];
            int[] a = ril(n);
            Map<Integer, Integer> sumFreq = new HashMap<>();
            int[] delta = new int[2*k + 2];
            for (int i = 0; i < n / 2; i++) {
                int s = a[i] + a[n-1-i];
                sumFreq.put(s, sumFreq.getOrDefault(s, 0) + 1);
                int l = Math.min(a[i], a[n-1-i]) + 1;
                int r = Math.max(a[i], a[n-1-i]) + k;
                delta[l]++;
                delta[r+1]--;
            }
            int[] prefix = new int[2*k + 2];
            prefix[0] = delta[0];
            for (int i = 1; i < prefix.length; i++) {
                prefix[i] = prefix[i-1] + delta[i];
            }
            int ans = n / 2;
            for (int x = 2; x <= 2 * k; x++) {
                ans = Math.min(ans, prefix[x] - sumFreq.getOrDefault(x, 0) + 2 * (n / 2 - prefix[x]));
            }
            pw.println(ans);
        }
    }
}