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
        int[] abcd = ril(4);
        int a = abcd[0];
        int b = abcd[1];
        int c = abcd[2];
        int d = abcd[3];

        if (a == 0 && b == 0) {
            if (c == d+1) {
                pw.println("YES");
                for (int i = 0; i < d; i++) pw.print("2 3 ");
                pw.println("2");
                return;
            }
            if (c == d-1) {
                pw.println("YES");
                for (int i = 0; i < c; i++) pw.print("3 2 ");
                pw.println("3");
                return;
            }
            if (c == d) {
                pw.println("YES");
                for (int i = 0; i < c; i++) pw.print("2 3 ");
                pw.println();
                return;
            }
            pw.println("NO");
            return;
        }
        if (a > b + 1 || d > c + 1) {
            pw.println("NO");
            return;
        }
        if (a == b+1) {
            // alternate a/bs
            if (c > 0 || d > 0) {
                pw.println("NO");
                return;
            }
            pw.println("YES");
            pw.print("0");
            for (int i = 0; i < b; i++) pw.print(" 1 0");
            pw.println();
            return;
        }
        if (a == b) {
            if (c < d) {
                pw.println("NO");
                return;
            }
            if (c == d) {
                pw.println("YES");
                for (int i = 0; i < a; i++) pw.print("0 1 ");
                for (int i = 0; i < c; i++) pw.print("2 3 ");
                pw.println();
                return;
            }
            if (c == d+1) {
                pw.println("YES");
                for (int i = 0; i < a; i++) pw.print("0 1 ");
                for (int i = 0; i < d; i++) pw.print("2 3 ");
                pw.println("2");
                return;
            }
            pw.println("NO");
            return;
        }
        // a < b
        // try start at a
        if (c == b-a+d || c == b-a+d+1) {
            pw.println("YES");
            for (int i = 0; i < a; i++) pw.print("0 1 ");
            b -= a;
            while (b > 0) {
                pw.print("2 1 ");
                b--;
                c--;
            }
            while (d > 0) {
                pw.print("2 3 ");
                c--;
                d--;
            }
            if (c == 1) pw.print("2");
            pw.println();
            return;
        }
        // try start at b
        // b -= a+1
        if (c == b-(a+1)+d || c == b-(a+1)+d+1) {
            pw.println("YES");
            for (int i = 0; i < a; i++) pw.print("1 0 ");
            pw.print("1 ");
            b -= a+1;
            while (b > 0) {
                pw.print("2 1 ");
                b--;
                c--;
            }
            while (d > 0) {
                pw.print("2 3 ");
                c--;
                d--;
            }
            if (c == 1) pw.print("2");
            pw.println();
            return;
        }
        // try start at c
        if (c - (d+1) >= 0 && (b == a+c-(d+1) || b == a+c-(d+1)+1)) {
            pw.println("YES");
            for (int i = 0; i < d; i++) pw.print("2 3 ");
            pw.print("2 ");
            c -= d+1;
            while (c > 0) {
                pw.print("1 2 ");
                c--;
                b--;
            }
            while (a > 0) {
                pw.print("1 0 ");
                b--;
                a--;
            }
            if (b == 1) pw.print("1");
            pw.println();
            return;
        }
        // try start at d
        // c -= d
        if (c-d>=0 && (b==a+c-d || b == a+c-d+1)) {
            pw.println("YES");
            for (int i = 0; i < d; i++) pw.print("3 2 ");
            c -= d;
            while (c > 0) {
                pw.print("1 2 ");
                c--;
                b--;
            }
            while (a > 0) {
                pw.print("1 0 ");
                b--;
                a--;
            }
            if (b == 1) pw.print("1");
            pw.println();
            return;
        }
        pw.println("NO");
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