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
        char[] s = rs();
        int n = s.length;

        double[] recip = new double[n+1];
        for (int i = 1; i <= n; i++) recip[i] = recip[i-1] + 1.0 / i;

        double ans = 0;

        double top = 1;
        double mid = 0;
        for (int i = 2; i <= n; i++) mid += 1.0 / i;
        double bot = 0;

        int midl = 2;
        int midr = n;
        int lim = n % 2 == 0 ? n / 2 : n / 2 + 1;
        for (int i = 0; i < lim; i++) {
            if (isVowel(s[i])) ans += top + top * mid + bot;
            
            top++;
            mid -= 1.0 / midl + 1.0 / midr;
            midl++;
            midr--;
            bot += (double) (i+1) * 1.0 / (midr + 1);
        }

        s = new StringBuilder(new String(s)).reverse().toString().toCharArray();

        top = 1;
        mid = 0;
        for (int i = 2; i <= n; i++) mid += 1.0 / i;
        bot = 0;

        midl = 2;
        midr = n;
        for (int i = 0; i < n / 2; i++) {
            if (isVowel(s[i])) ans += top + top * mid + bot;

            top++;
            mid -= 1.0 / midl + 1.0 / midr;
            midl++;
            midr--;
            bot += (double) (i+1) * 1.0 / (midr + 1);
        }

        printDouble(ans);
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    boolean isVowel(char c) {
        return c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U' || c == 'Y';
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

    void printDouble(double d) {
        pw.printf("%.16f", d);
    }
}