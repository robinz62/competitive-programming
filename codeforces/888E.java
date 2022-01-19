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
        int[] nm = ril(2);
        int n = nm[0];
        m = nm[1];

        int[] a = ril(n);  // n is very small.
        for (int i = 0; i < n; i++) a[i] %= m;

        // Knapsack obviously works, but that would take 10^9 space.
        // Meet-in-the-middle.

        int mid = n / 2;
        TreeSet<Integer> left = new TreeSet<>();
        Set<Integer> right = new HashSet<>();
        left.add(0);
        right.add(0);
        generate(left, a, 0, mid, 0);
        generate(right, a, mid+1, n-1, mid+1);

        // left and right contain O(10^5) elems
        int ans = 0;
        for (int x : left) ans = Math.max(ans, x);
        for (int x : right) ans = Math.max(ans, x);
        if (!left.isEmpty()) {
            for (int x : right) {
                // Find the best y in left to make as big as possible.
                // Want biggest such that x + y < m.
                // Want y < m - x.
                //
                // Alternatively, just get biggest

                int cand1 = x + left.last();
                if (cand1 >= m) cand1 -= m;
                ans = Math.max(ans, cand1);

                Integer y = left.lower(m - x);
                if (y != null) {
                    int cand2 = x + y;
                    if (cand2 >= m) cand2 -= m;  // Pretty sure this shouldn't happen
                    ans = Math.max(ans, cand2);
                }
            }
        }
        pw.println(ans);
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    int m;
    void generate(Set<Integer> ans, int[] a, int l, int r, int i) {
        if (i > r) return;
        List<Integer> curr = new ArrayList<>(ans);
        for (int x : curr) {
            int with = x + a[i];
            if (with >= m) with -= m;
            ans.add(with);
        }
        generate(ans, a, l, r, i+1);
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