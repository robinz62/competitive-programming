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
        List<Integer> counts = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        int count = 1;
        for (int i = 1; i < s.length; i++) {
            if (s[i] == s[i-1]) count++;
            else {
                counts.add(count);
                count = 0;
                sb.append(s[i-1]);
            }
        }
        counts.add(count);
        sb.append(s[s.length-1]);
        int n = counts.size();

        String condensed = sb.toString();

        // dpa[i][0/1] is number of a's to the right of i at even/odd idx
        int[][] dpa = new int[s.length][2];
        int[][] dpb = new int[s.length][2];
        if (s[s.length-1] == 'a') dpa[s.length-1][(s.length-1)%2]++;
        else dpb[s.length-1][(s.length-1)%2]++;
        for (int i = s.length-2; i >= 0; i--) {
            int bit = i % 2;
            dpa[i][0] = dpa[i+1][0];
            dpa[i][1] = dpa[i+1][1];
            dpb[i][0] = dpb[i+1][0];
            dpb[i][1] = dpb[i+1][1];
            if (s[i] == 'a') {
                dpa[i][bit]++;
            } else {
                dpb[i][bit]++;
            }
        }

        long even = 0;
        long odd = 0;

        for (int i = 0; i < s.length; i++) {
            if (i % 2 == 0) {
                if (s[i] == 'a') {
                    even += dpa[i][1];
                    odd += dpa[i][0];
                } else {
                    even += dpb[i][1];
                    odd += dpb[i][0];
                }
            } else {
                if (s[i] == 'a') {
                    even += dpa[i][0];
                    odd += dpa[i][1];
                } else {
                    even += dpb[i][0];
                    odd += dpb[i][1];
                }
            }
        }

        pw.println(even + " " + odd);
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