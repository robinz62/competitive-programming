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
            int n = ri();
            int[] a = ril(n);
            int[] present = new int[n+1];
            for (int ai : a) present[ai]++;
            TreeSet<Integer> missing = new TreeSet<>();
            for (int i = 0; i <= n; i++) if (present[i] == 0) missing.add(i);
            List<Integer> ans = new ArrayList<>();
            int badIdx = 0;
            while (true) {
                int mex = missing.first();
                if (mex == n) {
                    while (badIdx < n && a[badIdx] == badIdx) {
                        badIdx++;
                    }
                    if (badIdx == n) {
                        break;
                    }
                    missing.remove(mex);
                    int prev = a[badIdx];
                    a[badIdx] = mex;
                    present[mex]++;
                    present[prev]--;
                    if (present[prev] == 0) missing.add(prev);
                    ans.add(badIdx);
                } else {
                    missing.remove(mex);
                    int prev = a[mex];
                    a[mex] = mex;
                    present[mex]++;
                    present[prev]--;
                    if (present[prev] == 0) missing.add(prev);
                    ans.add(mex);
                }
            }
            pw.println(ans.size());
            for (int i : ans) pw.print((i+1) + " ");
            pw.println();
        }
    }
}