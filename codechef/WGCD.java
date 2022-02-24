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
        int MAX_N = 100000;
        for (int Ti = 0; Ti < T; Ti++) {
            long[] nm = rll(2);
            int n = (int) nm[0];
            long money = nm[1];
            int[] a = ril(n);

            long sum = 0;
            for (int ai : a) sum += ai;

            int[] counts = new int[MAX_N+1];  // will let us query count of numbers in a range
            for (int ai : a) counts[ai]++;
            for (int i = 1; i <= MAX_N; i++) counts[i] += counts[i-1];
            long[] sums = new long[MAX_N+1];  // will let us query the sum of the numbers in a range
            for (int ai : a) sums[ai] += ai;
            for (int i = 1; i <= MAX_N; i++) sums[i] += sums[i-1];

            // Can I achieve a value of k?
            // Must subtract from each number until it is a multiple of k.
            // Then the "leftover change" must be a multiple of k.

            int upperBound = a[0];
            for (int ai : a) upperBound = Math.max(upperBound, ai);

            int ans = 1;
            long term = sum;
            for (int k = 2; k <= upperBound; k++) {
                long totalCost = 0;
                for (int l = 0; l <= MAX_N; l += k) {
                    // process the group [l, l+k)
                    int upperidx = Math.min(MAX_N, l + k - 1);
                    long sumHere = sums[upperidx] - (l-1 >= 0 ? sums[l-1] : 0);
                    long countHere = counts[upperidx] - (l-1 >= 0 ? counts[l-1] : 0);
                    long costHere = sumHere - countHere * l;
                    totalCost += costHere;
                }
                if (totalCost > money) continue;
                long leftover = money - totalCost;
                if (leftover % k == 0) ans = k;
            }
            pw.println(ans);
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    public static List<Integer> factors(int n) {
        List<Integer> factors = new ArrayList<>();
        int i = 1;
        for (; i * i < n; i++) {
            if (n % i == 0) {
                factors.add(i);
                factors.add(n / i);
            }
        }
        if (i * i == n) factors.add(i);
        return factors;
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