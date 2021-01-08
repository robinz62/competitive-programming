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
    //   sorting, or taking max, after MOD
    void solve() throws IOException {
        int[] n = ril(3);
        int n1 = n[0];
        int n2 = n[1];
        int n3 = n[2];
        long[] a1 = rll(n1);
        long[] a2 = rll(n2);
        long[] a3 = rll(n3);
        sort(a1);
        sort(a2);
        sort(a3);

        long ans = 0;
        ans = Math.max(ans, helper(a1, a2, a3));
        ans = Math.max(ans, helper(a2, a1, a3));
        ans = Math.max(ans, helper(a3, a1, a2));

        pw.println(ans);
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    long helper(long[] a1, long[] a2, long[] a3) {
        long ans = 0;

        // Assume answer comes from a1 for now.
        long curr = 0;
        // We get everything from a1 for sure
        for (long x : a1) curr += x;
        // We subtract either the two mins of a2 and a3, or the entirity of a2 or a3
        // Case 1
        long case1 = 0;
        for (int i = 1; i < a2.length; i++) case1 += a2[i];
        for (int i = 1; i < a3.length; i++) case1 += a3[i];
        case1 -= a2[0] + a3[0];
        ans = Math.max(ans, curr + case1);
        // Case 2a
        long case2 = 0;
        for (long x : a2) case2 += x;
        for (long x : a3) case2 -= x;
        ans = Math.max(ans, curr + case2);
        // Case 2b
        long case3 = 0;
        for (long x : a2) case3 -= x;
        for (long x : a3) case3 += x;
        ans = Math.max(ans, curr + case3);

        return ans;
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

    void sort(long[] A) {
        Random r = new Random();
        for (int i = A.length-1; i > 0; i--) {
            int j = r.nextInt(i+1);
            long temp = A[i];
            A[i] = A[j];
            A[j] = temp;
        }
        Arrays.sort(A);
    }

    void printDouble(double d) {
        pw.printf("%.16f", d);
    }
}