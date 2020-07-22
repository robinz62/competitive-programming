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
            char[] a = rs();
            char[] b = rs();
            List<Integer> ans = new ArrayList<>();
            for (int i = n-1; i >= 0; i--) {
                if (a[i] != b[i]) {
                    if (a[0] == b[i]) {
                        ans.add(0);
                        a[0] = a[0] == '0' ? '1' : '0';
                    }
                    reverseAndFlip(a, 0, i);
                    ans.add(i);
                }
            }
            pw.print(ans.size());
            for (int x : ans) pw.print(" " + (x+1));
            pw.println();
        }
    }

    void reverseAndFlip(char[] a, int i, int j) {
        while (i <= j) {
            char tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
            a[i] = a[i] == '0' ? '1' : '0';
            if (i != j) a[j] = a[j] == '0' ? '1' : '0';
            i++;
            j--;
        }
    }
}