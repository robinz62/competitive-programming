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
            char[] s = rs();
            int[] a = new int[n];
            int id = 0;
            Deque<Integer> needZero = new ArrayDeque<>();
            Deque<Integer> needOne = new ArrayDeque<>();
            for (int i = 0; i < n; i++) {
                if (s[i] == '0') {
                    if (needZero.isEmpty()) {
                        a[i] = id++;
                        needOne.addLast(a[i]);
                    } else {
                        a[i] = needZero.removeFirst();
                        needOne.addLast(a[i]);
                    }
                } else {
                    if (needOne.isEmpty()) {
                        a[i] = id++;
                        needZero.addLast(a[i]);
                    } else {
                        a[i] = needOne.removeFirst();
                        needZero.addLast(a[i]);
                    }
                }
            }
            pw.println(id);
            for (int ai : a) pw.print((ai+1) + " ");
            pw.println();
        }
    }
}