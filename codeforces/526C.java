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
        long[] CHHWW = rll(5);
        long C = CHHWW[0];
        long hr = CHHWW[1];
        long hb = CHHWW[2];
        long wr = CHHWW[3];
        long wb = CHHWW[4];

        if (wr >= Math.sqrt(C)) {
            long best = 0;
            for (int numr = 0; numr * wr <= C; numr++) {
                long ans = numr * hr;
                long left = C - numr * wr;
                ans += left / wb * hb;
                best = Math.max(best, ans);
            }
            pw.println(best);
            return;
        }
        if (wb >= Math.sqrt(C)) {
            long best = 0;
            for (int numb = 0; numb * wb <= C; numb++) {
                long ans = numb * hb;
                long left = C - numb * wb;
                ans += left / wr * hr;
                best = Math.max(best, ans);
            }
            pw.println(best);
            return;
        }

        if (hb * wr > hr * wb) {
            long t = hr;
            hr = hb;
            hb = t;
            t = wr;
            wr = wb;
            wb = t;
        }
        // hb * wr <= hr * wb
        // crucial observation: blue candies are inferior. we only need at most
        // wr (actually, strictly less than) of them. that's because imagine
        // you ever had wr blue candies. the total weight is wr * wb. but you
        // could replace it with an equivalent weight of wr * wb by taking wb
        // red candies. and the inequality above shows that this is better for
        // us.
        long best = 0;
        for (int numb = 0; numb <= wr; numb++) {
            long ans = numb * hb;
            long left = C - numb * wb;
            ans += left / wr * hr;
            best = Math.max(best, ans);
        }
        pw.println(best);
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