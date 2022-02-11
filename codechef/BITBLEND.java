import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static int MOD = 1000000007;

    // After writing solution, quick scan for:
    //   array out of bounds
    //   special cases e.g. n=1?
    //   npe, particularly in maps
    //
    // Big numbers arithmetic bugs:
    //   int overflow
    //   if (x : long) and (y : int), [y = x] does not compile, but [y += x] does
    //   sorting, or taking max, after MOD
    void solve() throws IOException {
        int T = ri();
        for (int Ti = 0; Ti < T; Ti++) {
            int n = ri();
            int[] a = ril(n);
            for (int i = 0; i < n; i++) a[i] %= 2;
            if (n == 1) {
                pw.println("0");
                continue;
            }

            int m = Integer.MAX_VALUE;
            List<int[]> ans = null;

            // Case 1: 01...
            boolean ok = true;
            List<int[]> cand = new ArrayList<>();
            if (a[0] == 1) {
                int idx1 = -1;
                for (int i = 1; idx1 == -1 && i < n; i++) if (a[i] == 1) idx1 = i;
                if (idx1 == -1) ok = false;
                else cand.add(new int[]{0, idx1});
            }
            if (ok) {
                if (a[1] == 0) {
                    int idx1 = -1;
                    for (int i = 2; idx1 == -1 && i < n; i++) if (a[i] == 1) idx1 = i;
                    if (idx1 == -1) ok = false;
                    else cand.add(new int[]{1, idx1});
                }
            }
            if (ok) {
                for (int i = 2; i < n; i++) {
                    if (a[i] != i % 2) cand.add(new int[]{i, 1});
                }
                m = cand.size();
                ans = cand;
            }

            // Case 2: 10...
            ok = true;
            cand = new ArrayList<>();
            if (a[0] == 0) {
                int idx1 = -1;
                for (int i = 1; idx1 == -1 && i < n; i++) if (a[i] == 1) idx1 = i;
                if (idx1 == -1) ok = false;
                else cand.add(new int[]{0, idx1});
            }
            if (ok) {
                if (a[1] == 1) cand.add(new int[]{1, 0});
                for (int i = 2; i < n; i++) {
                    if (a[i] != (i + 1) % 2) cand.add(new int[]{i, 0});
                }
                if (cand.size() < m) {
                    m = cand.size();
                    ans = cand;
                }
            }

            if (ans == null) pw.println("-1");
            else {
                pw.println(ans.size());
                for (int[] insn : ans) pw.println((insn[0]+1) + " " + (insn[1]+1));
                pw.println();
            }
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

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
        int c = br.read();
        int x = 0;
        while (c >= '0' && c <= '9') {
            x = x * 10 + c - '0';
            c = br.read();
        }
        return ril(x);
    }

    long[] rkll() throws IOException {
        int c = br.read();
        int x = 0;
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

    void printDouble(double d) {
        pw.printf("%.16f", d);
    }
}