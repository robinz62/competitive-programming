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

            if (n == 2) {
                pw.println(a[1] + " " + (-a[0]));
                continue;
            }

            int[] b = new int[n];
            List<List<Integer>> valToIdx = new ArrayList<>(10001);
            for (int i = 0; i < 10001; i++) valToIdx.add(new ArrayList<>());

            int[] counts = new int[10001];
            for (int i = 0; i < n-3; i++) {
                valToIdx.get(Math.abs(a[i])).add(i);
            }

            List<Integer> singles = new ArrayList<>();
            for (int i = 1; i <= 10000; i++) {
                if (valToIdx.get(i).size() == 1) singles.add(valToIdx.get(i).get(0));
            }
            singles.add(n-3);
            singles.add(n-2);
            singles.add(n-1);
            for (int i = 1; i <= 10000; i++) {
                int sz = valToIdx.get(i).size();
                if (sz < 2) continue;

                int sum = 0;
                for (int j = 0; j < sz-1; j++) {
                    int idx = valToIdx.get(i).get(j);
                    int me = a[idx];
                    if (me > 0) {
                        if (sum >= 0) {
                            b[idx] = -1;
                            sum -= me;
                        } else {
                            b[idx] = 1;
                            sum += me;
                        }
                    } else {
                        if (sum >= 0) {
                            b[idx] = 1;
                            sum += me;
                        } else {
                            b[idx] = -1;
                            sum -= me;
                        }
                    }
                }
                if (sum == 0) {
                    int idx0 = valToIdx.get(i).get(0);
                    int me0 = a[idx0];
                    if (b[idx0] > 0) {
                        b[idx0]++;
                        sum += me0;
                    } else {
                        b[idx0]--;
                        sum -= me0;
                    }
                }
                int idxLast = valToIdx.get(i).get(sz-1);
                int last = a[idxLast];
                int mult = Math.abs(sum / i);
                if (sum * last < 0) {
                    b[idxLast] = mult;
                } else {
                    b[idxLast] = -mult;
                }
            }

            // pair off singles
            int sz = singles.size();
            if (sz % 2 == 0) {
                for (int i = 0; i < sz; i += 2) {
                    int idx1 = singles.get(i);
                    int idx2 = singles.get(i+1);
                    b[idx1] = a[idx2];
                    b[idx2] = -a[idx1];
                }
            } else {
                for (int i = 0; i < sz-3; i += 2) {
                    int idx1 = singles.get(i);
                    int idx2 = singles.get(i+1);
                    b[idx1] = a[idx2];
                    b[idx2] = -a[idx1];
                }
                int aIdx = singles.get(sz-3);
                int bIdx = singles.get(sz-2);
                int cIdx = singles.get(sz-1);
                long abc = (long) a[aIdx] * a[bIdx] * a[cIdx];
                long x = abc / a[aIdx];
                long y = abc / a[bIdx];
                long z = abc / a[cIdx];
                b[aIdx] = (int) (x);
                b[bIdx] = (int) (y);
                b[cIdx] = (int) (-2 * z);
            }

            for (int bi : b) pw.print(bi + " ");
            pw.println();

            long zzz = 0;
            long abs = 0;
            for (int i = 0; i < n; i++) {
                zzz += (long) a[i] * b[i];
                abs += Math.abs(b[i]);
            }
            if (zzz != 0) pw.println("FAIL nonzero");
            if (abs >= 1000000000) pw.println("FAIL too large");
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    public static int gcd(int a, int b) {
        return a == 0 ? b : gcd(b % a, a);
    }

    int extgcd(int a, int b, int[] xy) {
        if (b == 0) {
            xy[0] = 1;
            xy[1] = 0;
            return a;
        }
        int[] xy1 = new int[2];
        int d = extgcd(b, a % b, xy1);
        int x1 = xy1[0];
        int y1 = xy1[1];
        xy[0] = y1;
        xy[1] = x1 - y1 * (a / b);
        return d;
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