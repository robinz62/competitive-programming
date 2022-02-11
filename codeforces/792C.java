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
        char[] s = rs();
        int n = s.length;

        int[] mod = new int[n];
        int total = 0;
        for (int i = 0; i < n; i++) {
            mod[i] = (s[i] - '0') % 3;
            total += mod[i];
        }
        total %= 3;

        if (total == 0) {
            pw.println(new String(s));
            return;
        }

        // Case 1: don't remove leading digit
        int sameIdx = -1;
        int[] otherIdx = new int[]{-1, -1};
        for (int i = n-1; i >= 1; i--) {
            if (sameIdx == -1 && mod[i] == total) sameIdx = i;
            else if (mod[i] == 3 - total) {
                if (otherIdx[0] == -1) otherIdx[0] = i;
                else if (otherIdx[1] == -1) otherIdx[1] = i;
            }
        }
        if (sameIdx != -1) {
            for (int i = 0; i < n; i++) {
                if (i == sameIdx) continue;
                pw.print(s[i]);
            }
            pw.println();
            return;
        }

        int cand1 = otherIdx[1] != -1 ? 2 : Integer.MAX_VALUE;

        // Case 2: remove leading digit
        int cand2 = Integer.MAX_VALUE;
        int idx = -1;
        if (mod[0] == total) {
            cand2 = 1;
            for (int i = 1; i < n && s[i] == '0'; i++) cand2++;
            idx = 0;
        } else if (mod[0] == 3 - total) {
            cand2 = 1;
            for (int i = n-1; idx == -1 && i >= 1; i--) if (mod[i] == mod[0]) idx = i;
            for (int i = 1; i < n; i++) {
                if (i == idx) continue;
                if (s[i] == '0') cand2++;
                else break;
            }
        }

        if (cand1 <= cand2) {
            for (int i = 0; i < n; i++) {
                if (i == otherIdx[0] || i == otherIdx[1]) continue;
                pw.print(s[i]);
            }
            pw.println();
            return;
        } else if (idx != -1) {
            s[0] = 'x';
            s[idx] = 'x';
            int i = 0;
            boolean hasZero = false;
            while (i < n && (s[i] == '0' || s[i] == 'x')) {
                if (s[i] == '0') hasZero = true;
                i++;
            }
            if (i == n) {
                pw.println(hasZero ? "0" : "-1");
                return;
            }
            while (i < n) {
                if (s[i] != 'x') pw.print(s[i]);
                i++;
            }
            pw.println();
        } else {
            pw.println("-1");
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