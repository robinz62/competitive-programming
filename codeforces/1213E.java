import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static int MOD = 1000000007;

    // After writing solution, quick scan for:
    //   array out of bounds
    //   special cases e.g. n=1?
    //
    // Big numbers arithmetic bugs:
    //   int overflow
    //   sorting, or taking max, after MOD
    void solve() throws IOException {
        int n = ri();
        char[] s = rs();
        char[] t = rs();
        List<char[]> combos = new ArrayList<>();
        for (char x = 'a'; x <= 'c'; x++) {
            for (char y = 'a'; y <= 'c'; y++) {
                if (x == y) continue;
                for (char z = 'a'; z <= 'c'; z++) {
                    if (z == x || z == y) continue;
                    combos.add(new char[]{x, y, z});
                }
            }
        }
        char[] ans = null;
        String s_str = "" + s[0] + s[1];
        String t_str = "" + t[0] + t[1];
        for (char[] combo : combos) {
            String X = new String(combo);
            X = X + X;
            if (X.indexOf(s_str) < 0 && X.indexOf(t_str) < 0) {
                ans = combo;
                break;
            }
        }
        pw.println("YES");
        if (ans != null) {
            String X = new String(ans);
            for (int i = 0; i < n; i++) pw.print(X);
            pw.println();
        } else {
            for (char[] combo : combos) {
                String X = new String(combo);
                if (X.indexOf(s_str) < 0 && X.indexOf(t_str) < 0) {
                    ans = combo;
                    break;
                }
            }
            for (int i = 0; i < n; i++) pw.print(ans[0]);
            for (int i = 0; i < n; i++) pw.print(ans[1]);
            for (int i = 0; i < n; i++) pw.print(ans[2]);
        }
    }

    // Template code below

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pw = new PrintWriter(System.out);

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
        return Integer.parseInt(br.readLine().trim());
    }

    long rl() throws IOException {
        return Long.parseLong(br.readLine().trim());
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

    int[] rkil() throws IOException {
        int sign = 1;
        int c = br.read();
        int x = 0;
        if (c == '-') {
            sign = -1;
            c = br.read();
        }
        while (c >= '0' && c <= '9') {
            x = x * 10 + c - '0';
            c = br.read();
        }
        return ril(x);
    }

    long[] rkll() throws IOException {
        int sign = 1;
        int c = br.read();
        int x = 0;
        if (c == '-') {
            sign = -1;
            c = br.read();
        }
        while (c >= '0' && c <= '9') {
            x = x * 10 + c - '0';
            c = br.read();
        }
        return rll(x);
    }

    char[] rs() throws IOException {
        return br.readLine().toCharArray();
    }

    void sort(int[] A) {
        Random r = new Random();
        for (int i = A.length-1; i > 0; i--) {
            int j = r.nextInt(i+1);
            int temp = A[i];
            A[i] = A[j];
            A[j] = temp;
        }
        Arrays.sort(A);
    }
}