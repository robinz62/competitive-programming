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
        int[] abk = ril(3);
        int a = abk[0];
        int b = abk[1];
        int k = abk[2];

        // k = 0 is always possible, just use same numbers
        // what is the maximum k we can get?
        // gonna just guess
        int max = a + b - 2;
        if (k == 0) {
            pw.println("Yes");
            for (int i = 0; i < b; i++) pw.print("1");
            for (int i = 0; i < a; i++) pw.print("0");
            pw.println();
            for (int i = 0; i < b; i++) pw.print("1");
            for (int i = 0; i < a; i++) pw.print("0");
            pw.println();
        } else if (k > max) pw.println("No");
        else if (k > 0 && b == 1) pw.println("No");
        else if (k <= a) {
            pw.println("Yes");
            for (int i = 0; i < b; i++) pw.print("1");
            for (int i = 0; i < a; i++) pw.print("0");
            pw.println();
            char[] y = new char[a + b];
            Arrays.fill(y, '0');
            for (int i = 0; i < b-1; i++) y[i] = '1';
            y[y.length-1 - (a - k)] = '1';
            pw.println(new String(y));
        } else if (a > 0) {
            pw.println("Yes");
            for (int i = 0; i < b; i++) pw.print("1");
            for (int i = 0; i < a; i++) pw.print("0");
            pw.println();
            char[] y = new char[a + b];
            Arrays.fill(y, '0');
            y[y.length-1] = '1';
            for (int i = 0; i < b; i++) y[i] = '1';
            y[1 + (b-2 - (k-a))] = '0';
            pw.println(new String(y));
        } else {
            pw.println("No");
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

    void printDouble(double d) {
        pw.printf("%.16f", d);
    }
}