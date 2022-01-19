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
    //   if (x : long) and (y : int), [y = x] does not compile, but [y += x] does
    //   sorting, or taking max, after MOD
    void solve() throws IOException {
        int T = ri();
        for (int Ti = 0; Ti < T; Ti++) {
            int[] xs = ril(2);
            int x = xs[0];
            int s = xs[1];

            if (s < x) {
                pw.println("-1");
                continue;
            }

            // create target from bits of X
            int target = s - x;  // we automatically use 1 number as X.
            if (target == 0) {
                pw.println("1");
                continue;
            }
            
            int l = 1;
            int r = 1000000000;
            int ans = Integer.MAX_VALUE;

            while (l <= r) {
                int m = (l + r) / 2;

                int idxInX = 29;
                int[] have = new int[30];
                for (int b = 29; b >= 0; b--) if (((1 << b) & x) > 0) have[b] = m;

                boolean good = true;
                for (int targetBit = 29; targetBit >= 0; targetBit--) {
                    if (((1 << targetBit) & target) == 0) continue;
                    while (idxInX >= 0 && (idxInX > targetBit || have[idxInX] == 0)) idxInX--;
                    if (idxInX < 0) {
                        good = false;
                        break;
                    }

                    int tgt = 1 << targetBit;
                    while (tgt > 0) {
                        int need = tgt / (1 << idxInX);
                        if (have[idxInX] >= need) {
                            have[idxInX] -= need;
                            tgt = 0;
                            if (have[idxInX] == 0) idxInX--;
                            break;
                        } else {
                            int add = have[idxInX];
                            have[idxInX] = 0;
                            tgt -= add * (1 << idxInX);
                            idxInX--;
                            if (idxInX < 0) {
                                good = false;
                                break;
                            }
                        }
                    }
                    if (!good) break;
                }
                if (good) {
                    ans = m;
                    r = m-1;
                } else {
                    l = m+1;
                }
            }
            pw.println(ans == Integer.MAX_VALUE ? -1 : ans+1);
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
        int c = br.read();
        int x = 0;
        while (c >= '0' && c <= '9') {
            x = x * 10 + c - '0';
            c = br.read();
        }
        return ril(x);
    }

    long[] rkll() throws IOException {
        int c = br.read();
        int x = 0;
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