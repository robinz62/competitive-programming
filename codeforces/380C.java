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
        int m = ri();
        SegmentTree st = new SegmentTree(s);
        for (int q = 0; q < m; q++) {
            int[] lr = ril(2);
            int l = lr[0]-1;
            int r = lr[1]-1;
            pw.println(st.query(l, r+1));
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

class SegmentTree {
    int n;
    int[] total;
    int[] excessLeft;
    int[] excessRight;

    char[] arr;

    SegmentTree(char[] arr) {
        n = arr.length;
        total = new int[n*2];
        excessLeft = new int[n*2];
        excessRight = new int[n*2];
        this.arr = arr;
        for (int i = 0; i < n; i++) {
            if (arr[i] == '(') excessLeft[n+i]++;
            else excessRight[n+i]++;
        }
        build();
    }

    void build() {
        for (int i = n - 1; i > 0; i--) {
            int min = Math.min(excessLeft[i*2], excessRight[i*2+1]);
            total[i] = total[i*2] + total[i*2+1] + 2 * min;
            excessLeft[i] = excessLeft[i*2] - min + excessLeft[i*2+1];
            excessRight[i] = excessRight[i*2+1] - min + excessRight[i*2];
        }
    }

    // Note: input range is half-open [l, r)
    int query(int l, int r) {
        int totall = 0;
        int excessll = 0;
        int excesslr = 0;
        int totalr = 0;
        int excessrl = 0;
        int excessrr = 0;
        l += n;
        r += n;
        while (l < r) {
            if ((l & 1) > 0) {
                // merge currL and st[l]
                int min = Math.min(excessll, excessRight[l]);
                totall = totall + total[l] + 2 * min;
                excessll = excessll - min + excessLeft[l];
                excesslr = excesslr + excessRight[l] - min;
                l++;
            }
            if ((r & 1) > 0) {
                // merge st[r] and currR
                r--;
                int min = Math.min(excessLeft[r], excessrr);
                totalr = total[r] + totalr + 2 * min;
                excessrl = excessLeft[r] - min + excessrl;
                excessrr = excessRight[r] + excessrr - min;
            }
            l /= 2;
            r /= 2;
        }
        int min = Math.min(excessll, excessrr);
        return totall + totalr + 2 * min;
    }
}