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

    void solve() throws IOException {
        int t = ri();
        for (int ti = 0; ti < t; ti++) {
            int n = ri();
            int[] a = new int[n];
            PriorityQueue<int[]> segments = new PriorityQueue<>((i1, i2) -> {
                int len1 = i1[1] - i1[0];
                int len2 = i2[1] - i2[0];
                if (len1 != len2) return -Integer.compare(len1, len2);
                return Integer.compare(i1[0], i2[0]);
            });
            segments.add(new int[]{0, n-1});
            int i = 1;
            while (!segments.isEmpty()) {
                int[] seg = segments.remove();
                int l = seg[0];
                int r = seg[1];
                int m = l + (r - l) / 2;
                a[m] = i++;
                if (m-1 - l + 1 > 0) segments.add(new int[]{l, m-1});
                if (r - (m+1) + 1 > 0) segments.add(new int[]{m+1, r});
            }
            for (int ai : a) pw.print(ai + " ");
            pw.println();
        }
    }
}