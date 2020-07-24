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
        List<List<Integer>> factors = new ArrayList<>();
        factors.add(null);
        for (int i = 1; i <= 100000; i++) {
            factors.add(factors(i));
            Collections.sort(factors.get(i));
        }

        for (int ti = 0; ti < t; ti++) {
            int[] abc = ril(3);
            int a = abc[0];
            int b = abc[1];
            int c = abc[2];
            int[] ans = new int[3];
            int dist = Integer.MAX_VALUE;
            for (int i = 1; i <= 100000; i++) {
                int val = Math.abs(b - i);
                int achoice = -1;
                int cchoice = -1;
                List<Integer> f = factors.get(i);
                int idx = Collections.binarySearch(f, a);
                if (idx < 0) {
                    idx = -idx - 1;
                    int adist = Math.abs(a - f.get(idx-1));
                    achoice = f.get(idx-1);
                    if (idx < f.size() && Math.abs(a - f.get(idx)) < adist) {
                        adist = Math.abs(a - f.get(idx));
                        achoice = f.get(idx);
                    }
                    val += adist;
                } else achoice = f.get(idx);
                int ccand = c / i * i;
                if (Math.abs(ccand - c) < Math.abs(ccand + i - c)) {
                    cchoice = ccand;
                    val += Math.abs(ccand - c);
                } else {
                    cchoice = ccand + i;
                    val += Math.abs(ccand + i - c);
                }
                if (val < dist) {
                    dist = val;
                    ans[0] = achoice;
                    ans[1] = i;
                    ans[2] = cchoice;
                }
            }
            pw.println(dist);
            pw.println(ans[0] + " " + ans[1] + " " + ans[2]);
        }
    }

    public static List<Integer> factors(int n) {
        List<Integer> factors = new ArrayList<>();
        int i = 1;
        for (; i * i < n; i++) {
            if (n % i == 0) {
                factors.add(i);
                factors.add(n / i);
            }
        }
        if (i * i == n) factors.add(i);
        return factors;
    }
}