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
        int T = ri();
        for (int Ti = 0; Ti < T; Ti++) {
            int n = ri();
            int[] a = ril(n);

            // 3s are good, obviously, can get any mod 3 = 0
            // we at most have a very small number of 1s and 2s, which can replace a 3.
            // i'm conjecturing we need at most 2 1's and 2 2's

            int ans = Integer.MAX_VALUE;
            for (int numOnes = 0; numOnes <= 2; numOnes++) {
                for (int numTwos = 0; numTwos <= 2; numTwos++) {
                    boolean bad = false;
                    int curr = 0;  // number of 3s needed
                    for (int ai : a) {
                        int remainder = ai % 3;
                        if (remainder == 0) {
                            int need = ai;
                            int x = numOnes;
                            int y = numTwos;
                            while (need > 0 && x > 0 && y > 0) {
                                need -= 3;
                                x--;
                                y--;
                            }
                            curr = Math.max(curr, need / 3);
                        } else if (remainder == 1) {
                            int bingo = Integer.MAX_VALUE;
                            // we can either use {1}, {2, 2}
                            if (numOnes > 0) {
                                int need = ai;
                                int x = numOnes;
                                int y = numTwos;
                                x--;
                                need -= 1;
                                while (need > 0 && x > 0 && y > 0) {
                                    need -= 3;
                                    x--;
                                    y--;
                                }
                                bingo = Math.min(bingo, need / 3);
                            }
                            if (numTwos >= 2 && ai - 4 >= 0) {
                                int need = ai;
                                int x = numOnes;
                                int y = numTwos;
                                y -= 2;
                                need -= 4;
                                while (need > 0 && x > 0 && y > 0) {
                                    need -= 3;
                                    x--;
                                    y--;
                                }
                                bingo = Math.min(bingo, need / 3);
                            }
                            if (bingo == Integer.MAX_VALUE) {
                                bad = true;
                                break;
                            } else {
                                curr = Math.max(curr, bingo);
                            }
                        } else if (remainder == 2) {
                            // we can do {1, 1} or {2}
                            int bingo = Integer.MAX_VALUE;
                            if (numOnes >= 2) {
                                int need = ai;
                                int x = numOnes;
                                int y = numTwos;
                                x -= 2;
                                need -= 2;
                                while (need > 0 && x > 0 && y > 0) {
                                    need -= 3;
                                    x--;
                                    y--;
                                }
                                bingo = Math.min(bingo, need / 3);
                            }
                            if (numTwos >= 1) {
                                int need = ai;
                                int x = numOnes;
                                int y = numTwos;
                                y--;
                                need -= 2;
                                while (need > 0 && x > 0 && y > 0) {
                                    need -= 3;
                                    x--;
                                    y--;
                                }
                                bingo = Math.min(bingo, need / 3);
                            }
                            if (bingo == Integer.MAX_VALUE) {
                                bad = true;
                                break;
                            } else {
                                curr = Math.max(curr, bingo);
                            }
                        }
                    }
                    if (bad) continue;
                    ans = Math.min(ans, curr + numOnes + numTwos);
                }
            }
            pw.println(ans);
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