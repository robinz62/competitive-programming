import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static int MOD = 1000000007;

    // After writing solution, quick scan for:
    //   array out of bounds
    //   special cases e.g. n=1?
    //
    // Big numbers arithmetic bugs:
    //   int overflow
    //   sorting, or taking max, after MOD
    void solve() throws IOException {
        int[] nq = ril(2);
        int n = nq[0];
        int q = nq[1];
        int[] p = ril(n);

        sort(p);
        TreeMap<Integer, Integer> intervals = new TreeMap<>();
        TreeMap<Integer, Integer> lenToCount = new TreeMap<>();
        for (int i = 0; i < n-1; i++) {
            int len = p[i+1] - p[i];
            intervals.put(p[i], len);
            lenToCount.put(len, lenToCount.getOrDefault(len, 0) + 1);
        }
        intervals.put(p[n-1], 0);
        lenToCount.put(0, 1);

        pw.println(intervals.lastKey() - intervals.firstKey() - lenToCount.lastKey());

        for (int qi = 0; qi < q; qi++) {
            int[] tx = ril(2);
            int t = tx[0];
            int x = tx[1];

            if (t == 0) {
                int prevLen = intervals.get(x);
                Integer lower = intervals.lowerKey(x);
                intervals.remove(x);
                if (lenToCount.get(prevLen) == 1) lenToCount.remove(prevLen);
                else lenToCount.put(prevLen, lenToCount.get(prevLen) - 1);
                if (lower != null) {
                    int prevLen2 = intervals.get(lower);
                    if (lenToCount.get(prevLen2) == 1) lenToCount.remove(prevLen2);
                    else lenToCount.put(prevLen2, lenToCount.get(prevLen2) - 1);
                    int newLen = prevLen == 0 ? 0 : prevLen + prevLen2;
                    intervals.put(lower, newLen);
                    lenToCount.put(newLen, lenToCount.getOrDefault(newLen, 0) + 1);
                }
            } else {
                Integer lower = intervals.lowerKey(x);
                if (lower == null) {
                    if (intervals.isEmpty()) {
                        intervals.put(x, 0);
                        lenToCount.put(0, 1);
                    } else {
                        int first = intervals.firstKey();
                        int len = first - x;
                        intervals.put(x, len);
                        lenToCount.put(len, lenToCount.getOrDefault(len, 0) + 1);
                    }
                } else {
                    if (intervals.get(lower) == 0) {
                        intervals.put(lower, x - lower);
                        intervals.put(x, 0);
                        lenToCount.put(x-lower, lenToCount.getOrDefault(x-lower, 0) + 1);
                    } else {
                        int C = intervals.get(lower);
                        if (lenToCount.get(C) == 1) lenToCount.remove(C);
                        else lenToCount.put(C, lenToCount.get(C) - 1);

                        int A = x - lower;
                        int B = C - A;
                        intervals.put(lower, A);
                        intervals.put(x, B);
                        lenToCount.put(A, lenToCount.getOrDefault(A, 0) + 1);
                        lenToCount.put(B, lenToCount.getOrDefault(B, 0) + 1);
                    }
                }
            }

            if (intervals.isEmpty()) pw.println("0");
            else pw.println(intervals.lastKey() - intervals.firstKey() - lenToCount.lastKey());
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
        return Integer.parseInt(br.readLine());
    }

    long rl() throws IOException {
        return Long.parseLong(br.readLine());
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
}