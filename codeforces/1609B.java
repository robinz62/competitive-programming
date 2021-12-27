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
        int[] nq = ril(2);
        int n = nq[0];
        int q = nq[1];
        char[] s = rs();

        int count = 0;
        for (int i = 0; i + 2 < n; i++) {
            if (s[i] == 'a' && s[i+1] == 'b' && s[i+2] == 'c') count++;
        }

        for (int qi = 0; qi < q; qi++) {
            String[] line = br.readLine().split(" ");
            int i = Integer.parseInt(line[0]) - 1;
            char c = line[1].charAt(0);

            if (s[i] == c) {
                pw.println(count);
                continue;
            }

            boolean sub = false;
            if (s[i] == 'a' && i+2 < n && s[i+1] == 'b' && s[i+2] == 'c') sub = true;
            else if (s[i] == 'b' && i-1 >= 0 && i+1 < n && s[i-1] == 'a' && s[i+1] == 'c') sub = true;
            else if (s[i] == 'c' && i-2 >= 0 && s[i-2] == 'a' && s[i-1] == 'b') sub = true;

            boolean add = false;
            if (c == 'a' && i+2 < n && s[i+1] == 'b' && s[i+2] == 'c') add = true;
            else if (c == 'b' && i-1 >= 0 && i+1 < n && s[i-1] == 'a' && s[i+1] == 'c') add = true;
            else if (c == 'c' && i-2 >= 0 && s[i-2] == 'a' && s[i-1] == 'b') add = true;

            if (sub) count--;
            if (add) count++;
            s[i] = c;
            pw.println(count);
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