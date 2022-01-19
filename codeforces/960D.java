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
        int q = ri();
        long[] rotateValues = new long[60];
        long[] rotateNodes = new long[60];
        long[] shifts = new long[60];
        for (int qi = 0; qi < q; qi++) {
            // Level i contains nodes [ 2^i, 2^(i+1) )
            //   2^i nodes total.
            String[] line = br.readLine().split(" ");
            int type = Integer.parseInt(line[0]);
            if (type == 1) {
                long x = Long.parseLong(line[1]);
                long k = Long.parseLong(line[2]);
                int level = level(x);
                long countHere = 1l << level;
                if (k < 0) {
                    k %= countHere;
                    k = countHere + k;
                } else k %= countHere;

                rotateValues[level] += k;
                if (rotateValues[level] >= countHere) rotateValues[level] -= countHere;
            } else if (type == 2) {
                long x = Long.parseLong(line[1]);
                long k = Long.parseLong(line[2]);
                int level = level(x);
                long countHere = 1l << level;
                if (k < 0) {
                    k %= countHere;
                    k = countHere + k;
                } else k %= countHere;

                rotateNodes[level] += k;
                if (rotateNodes[level] >= countHere) rotateNodes[level] -= countHere;
            } else if (type == 3) {
                long x = Long.parseLong(line[1]);

                int level = level(x);
                long countHere = 1l << level;
                long cumRotate = 0;
                for (int l = 0; l <= level; l++) {
                    cumRotate *= 2;
                    long here = 1l << l;
                    
                    long shiftsHere = cumRotate + rotateValues[l];
                    if (shiftsHere >= here) shiftsHere -= here;
                    shiftsHere += rotateNodes[l];
                    if (shiftsHere >= here) shiftsHere -= here;

                    cumRotate += rotateNodes[l];
                    if (cumRotate >= here) cumRotate -= here;

                    shifts[l] = shiftsHere;
                }

                long ghost = ((x - countHere) + shifts[level]) % countHere + countHere;
                pw.print(x + " ");
                for (int l = level-1; l >= 0; l--) {
                    ghost /= 2;
                    countHere /= 2;

                    long actual = ((ghost - countHere) + (countHere - shifts[l])) % countHere + countHere;
                    pw.print(actual + " ");
                }
                pw.println();
            }
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    int level(long x) {
        int level = 0;
        while (x > 1) {
            level++;
            x /= 2;
        }
        return level;
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