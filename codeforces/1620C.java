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
            long[] nkx = rll(3);
            int n = (int) nkx[0];
            int k = (int) nkx[1];
            long x = nkx[2] - 1;

            char[] s = rs();

            // n^2 or nk is alright.
            // the lexicographically smallest string is all a's i.e. don't add any bs
            // the lexicographically largest string is adding k b's to as each *
            // to increase lexicographically, add a b to the last * that still has < k.
            // we should combine asterisks that are consecutive! since it doesn't matter which we add the b to.

            // str is alternating [a count, * count, a count, * count, ...]
            List<Integer> str = new ArrayList<>();
            int count = 1;
            for (int i = 1; i < n; i++) {
                if (s[i] == s[i-1]) count++;
                else {
                    str.add(count);
                    count = 1;
                }
            }
            str.add(count);
            if (s[0] != 'a') str.add(0, 0);
            if (s[n-1] != 'a') str.add(0);

            // no buckets at all
            if (str.size() == 1) {
                pw.println(new String(s));
                continue;
            }

            int countBuckets = (str.size() - 1) / 2;
            int[] capacity = new int[countBuckets];
            int[] ans = new int[countBuckets];
            int j = 0;
            for (int i = 1; i < str.size(); i += 2) {
                capacity[j++] = k * str.get(i);
            }

            long[] suffixProduct = new long[countBuckets];
            suffixProduct[countBuckets-1] = capacity[countBuckets-1] + 1;
            BigInteger curr = BigInteger.valueOf(suffixProduct[countBuckets-1]);
            for (int i = countBuckets-2; i >= 0; i--) {
                curr = curr.multiply(BigInteger.valueOf(capacity[i] + 1));
                if (curr.compareTo(BigInteger.valueOf(x + 10)) > 0) {
                    for (int z = i; z >= 0; z--) suffixProduct[z] = Long.MAX_VALUE;
                    break;
                }
                suffixProduct[i] = suffixProduct[i+1] * (capacity[i] + 1);
            }

            // We start off with 0 that is < me.
            // As we add b's to the first segment of asterisks, the number < me becomes:

            int i = 0;
            while (i < countBuckets && x > 0) {
                long best = 0;
                for (int here = 0; here <= capacity[i]; here++) {
                    long countle = here;
                    if (i+1 < countBuckets) {
                        if (suffixProduct[i+1] > x) continue;
                        else {
                            if (BigInteger.valueOf(suffixProduct[i+1]).multiply(BigInteger.valueOf(countle)).compareTo(BigInteger.valueOf(x)) > 0) {
                                continue;
                            } else {
                                countle *= suffixProduct[i+1];
                            }
                        }
                    }
                    if (countle <= x) {
                        best = countle;
                        ans[i] = here;
                    } else break;
                }
                x -= best;
                i++;
            }

            // for (int z = i; z < countBuckets; z++) ans[z] = capacity[z];

            int bucketIdx = 0;
            for (int z = 0; z < str.size(); z++) {
                if (z % 2 == 0) {
                    for (int zz = 0; zz < str.get(z); zz++) {
                        pw.print('a');
                    }
                } else {
                    for (int zz = 0; zz < ans[bucketIdx]; zz++) {
                        pw.print('b');
                    }
                    bucketIdx++;
                }
            }
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