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
    //
    // Interactive problems: don't forget to flush between test cases
    void solve() throws IOException {
        int T = ri();
        for (int Ti = 0; Ti < T; Ti++) {
            int n = ri();
            int[] a = ril(n);

            // Of interest: numbers that occur an odd number of times in input
            // That makes it impossible.
            Map<Integer, Integer> freq = new HashMap<>();
            for (int ai : a) freq.put(ai, freq.getOrDefault(ai, 0) + 1);
            boolean bad = false;
            for (int f : freq.values()) {
                if (f % 2 == 1) {
                    bad = true;
                    break;
                }
            }
            if (bad) {
                pw.println("-1");
                continue;
            }

            List<Integer> list = new ArrayList<>();
            for (int ai : a) list.add(ai);

            List<int[]> ans = new ArrayList<>();
            List<Integer> ansLen = new ArrayList<>();
            int idx = 0;
            while (idx < list.size()) {
                int matchIdx = idx+1;
                while (matchIdx < list.size() && (int) list.get(idx) != (int) list.get(matchIdx)) matchIdx++;
                if (matchIdx == list.size()) {
                    bad = true;
                    break;
                }

                int len = matchIdx - idx;
                ansLen.add(len);
                for (int j = 0; idx + j < matchIdx; j++) {
                    int me = list.get(idx + j);
                    if (matchIdx + j >= list.size() || (int) list.get(idx + j) != (int) list.get(matchIdx + j)) {
                        list.add(matchIdx + j, me);
                        list.add(matchIdx + j, me);
                        ans.add(new int[]{matchIdx + j, me});
                    }
                }
                idx = matchIdx + len;
            }

            if (bad) {
                pw.println("-1");
                continue;
            }

            pw.println(ans.size());
            for (int[] op : ans) {
                pw.println(op[0] + " " + op[1]);
            }
            pw.println(ansLen.size());
            for (int len : ansLen) pw.print((len * 2) + " ");
            pw.println();
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