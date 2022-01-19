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
            int n = ri();
            int[] a = ril(n);
            sort(a);

            if (a[0] == a[n-1]) {
                pw.println(up(n) + 2);
                continue;
            }

            TreeMap<Integer, Integer> freq = new TreeMap<>();
            for (int ai : a) freq.put(ai, freq.getOrDefault(ai, 0) + 1);
            if (freq.size() == 2) {
                int X = a[0];
                int Y = a[n-1];
                int cx = freq.get(X);
                int cy = freq.get(Y);

                int cand1 = up(cx) + up(cy) + 1;
                int cand2 = up(cx + cy) + 2;
                pw.println(Math.min(cand1, cand2));
                continue;
            }

            int k = freq.size();
            int[] vals = new int[k];
            int[] counts = new int[k];
            int ins = 0;
            for (var e : freq.entrySet()) {
                int val = e.getKey();
                int f = e.getValue();
                vals[ins] = val;
                counts[ins] = f;
                ins++;
            }

            int[] prefix = new int[k];
            prefix[0] = counts[0];
            for (int i = 1; i < k; i++) prefix[i] = prefix[i-1] + counts[i];

            int countOfAns = up(n) + 2;

            // iter at i is exclusive boundary for left i.e. (-inf, i)
            for (int i = 0; i < k; i++) {
                int leftCount = i-1 < 0 ? 0 : prefix[i-1];

                // get middle count to have 1, 2, 4, 8, 16, ... elements
                for (int middleTarget = 1; middleTarget < n - leftCount; middleTarget *= 2) {
                    int idx = binarySearchRight(prefix, i, k-1, middleTarget, leftCount);
                    if (idx < 0) idx = -idx-1-1;
                    if (idx < i) {
                        // Middle partition is empty
                        countOfAns = Math.min(countOfAns, up(leftCount) + 1 + up(n - leftCount));
                    } else {
                        int midCount = prefix[idx] - leftCount;
                        int rightCount = n - leftCount - midCount;
                        int candAns = up(leftCount) + up(midCount) + up(rightCount);
                        countOfAns = Math.min(countOfAns, candAns);
                    }
                }

                // Edge case: right partition is empty
                int candAns = up(leftCount) + up(n - leftCount) + 1;
                countOfAns = Math.min(countOfAns, candAns);
            }

            pw.println(countOfAns);
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    int up(int x) {
        if (x == 0) return 1;
        if ((x & (x-1)) == 0) return 0;
        return Integer.highestOneBit(x) * 2 - x;
    }

    // search for largest index where the count is <= k.
    public static int binarySearchRight(int[] A, int l, int r, int k, int offset) {
        int upper = -1;
        while (l <= r) {
            int m = l + (r - l) / 2;
            if (A[m]-offset == k && (m+1==A.length || A[m+1]-offset > k)) {
                upper = m;
                break;
            }
            if (A[m]-offset <= k) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return upper >= 0 ? upper : -l - 1;
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