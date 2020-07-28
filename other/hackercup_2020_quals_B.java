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
        for (int Ti = 0; Ti < T; Ti++) {
            int N = ri();
            char[] C = rs();
            int cntA = 0;
            int cntB = 0;
            for (char c : C) if (c == 'A') cntA++; else cntB++;
            char ans = Math.abs(cntA - cntB) == 1 ? 'Y' : 'N';
            // List<Character> stack = new ArrayList<>();
            // for (char c : C) {
            //     stack.add(c);
            //     while (stack.size() >= 3) {
            //         int a = 0;
            //         int b = 0;
            //         if (stack.get(stack.size() - 1) == 'A') a++; else b++;
            //         if (stack.get(stack.size() - 2) == 'A') a++; else b++;
            //         if (stack.get(stack.size() - 3) == 'A') a++; else b++;
            //         if (a != 3 && b != 3) {
            //             stack.remove(stack.size() - 1);
            //             stack.remove(stack.size() - 1);
            //             stack.remove(stack.size() - 1);
            //         } else break;
            //         if (a > b) stack.add('A');
            //         else stack.add('B');
            //     }
            // }
            // char ans = stack.size() == 1 ? 'Y' : 'N';
            pw.println("Case #" + (Ti+1) + ": " + ans);
        }
    }
}