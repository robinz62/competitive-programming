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
        int n = ri();
        int[] b = new int[n];
        for (int i = 0; i < n; i++) b[i] = ri();

        // Greedy? Find smallest number of digits possible, then the smallest number for that.
        String prev = smallest(b[0], 0, "0");
        pw.println(prev);
        for (int i = 1; i < n; i++) {
            String cand = smallest(b[i], prev.length(), prev);
            if (cand == null) cand = smallest(b[i], prev.length() + 1, prev);
            pw.println(cand);
            prev = cand;
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    String smallest(int b, int require, String prev) {
        int[] p = new int[prev.length()];
        for (int i = 0; i < prev.length(); i++) p[i] = prev.charAt(i) - '0';

        int min = b / 9 + (b % 9 == 0 ? 0 : 1);
        int numDigits = Math.max(min, require);
        if (numDigits > prev.length()) {
            int[] ans = new int[numDigits];
            ans[numDigits-1] = b;
            for (int i = numDigits-1; i > 0; i--) {
                if (ans[i] < 10) break;
                ans[i-1] = ans[i] - 9;
                ans[i] = 9;
            }

            if (ans[0] == 0) {
                for (int i = 1; i < numDigits; i++) {
                    if (ans[i] > 0) {
                        ans[i]--;
                        ans[0] = 1;
                        break;
                    }
                }
            }

            StringBuilder sb = new StringBuilder();
            for (int d : ans) sb.append(Integer.toString(d));
            return sb.toString();
        } else {
            int[] ans = new int[numDigits];

            // Same length, need to ensure as small as possible but larger than prev
            // Brute force on which digit is larger by 1? So same prefix until that digit.
            int n = numDigits;
            boolean done = false;
            for (int largerDig = n-1; largerDig >= 0; largerDig--) {
                if (p[largerDig] == 9) continue;
                Arrays.fill(ans, 0);
                boolean good = true;
                int available = b;
                for (int i = 0; good && i < largerDig; i++) {
                    ans[i] = p[i];
                    available -= ans[i];
                    if (available < 0) good = false;
                }
                if (!good) continue;
                ans[largerDig] = p[largerDig] + 1;
                available -= ans[largerDig];
                if (available < 0) continue;

                // We may have leftovers. We should "stuff them" as rightward as possible.
                for (int i = n-1; available != 0 && i >= 0; i--) {
                    int spotsHere = Math.min(available, 9 - ans[i]);
                    ans[i] += spotsHere;
                    available -= spotsHere;
                }
                if (available > 0) continue;
                done = true;
                break;
            }

            if (!done) return null;
            StringBuilder sb = new StringBuilder();
            for (int d : ans) sb.append(Integer.toString(d));
            return sb.toString();
        }
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