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
        long[] nkS = rll(3);
        int n = (int) nkS[0];
        int k = (int) nkS[1];
        long S = nkS[2];
        long[] a = rll(n);
        Arrays.sort(a);

        long[] fact = new long[19];
        fact[0] = 1;
        for (int i = 1; i <= 18; i++) fact[i] = fact[i-1] * i;

        long[] left = new long[n / 2];
        long[] right = new long[n - n / 2];
        for (int i = 0; i < n / 2; i++) left[i] = a[i];
        for (int i = n / 2; i < n; i++) right[i - n / 2] = a[i];
        int lsize = left.length;
        int rsize = right.length;

        // Only apply factorials to numbers <= 18.

        // L[i] is all possible sums using K factorials (count of each).
        List<Map<Long, Integer>> L = new ArrayList<>(k+1);
        for (int i = 0; i <= k; i++) L.add(new HashMap<>());
        for (int mask = 0; mask < (1 << lsize); mask++) {
            int s = mask;  // s is the guys we apply factorials to
            while (s >= 0) {
                // use s here
                if (Integer.bitCount(s) > k) {
                    s = (s-1) & mask;
                    continue;
                }

                boolean skip = false;

                // Might need to precompute this part:
                long sum = 0;
                for (int bit = 0; bit < lsize; bit++) {
                    if (((1 << bit) & s) > 0) {
                        if (left[bit] > 18) {
                            skip = true;
                            break;
                        }
                        sum += fact[(int) left[bit]];
                    } else if (((1 << bit) & mask) > 0) {
                        sum += left[bit];
                    }
                }
                if (!skip) {
                    int bc = Integer.bitCount(s);
                    L.get(bc).put(sum, L.get(bc).getOrDefault(sum, 0) + 1);
                }

                if (s == 0) break;
                s = (s-1) & mask;
            }
        }

        List<Map<Long, Integer>> R = new ArrayList<>(k+1);
        for (int i = 0; i <= k; i++) R.add(new HashMap<>());
        for (int mask = 0; mask < (1 << rsize); mask++) {
            int s = mask;  // s is the guys we apply factorials to
            while (s >= 0) {
                // use s here
                if (Integer.bitCount(s) > k) {
                    s = (s-1) & mask;
                    continue;
                }

                boolean skip = false;

                // Might need to precompute this part:
                long sum = 0;
                for (int bit = 0; bit < rsize; bit++) {
                    if (((1 << bit) & s) > 0) {
                        if (right[bit] > 18) {
                            skip = true;
                            break;
                        }
                        sum += fact[(int) right[bit]];
                    } else if (((1 << bit) & mask) > 0) {
                        sum += right[bit];
                    }
                }
                if (!skip) {
                    int bc = Integer.bitCount(s);
                    R.get(bc).put(sum, R.get(bc).getOrDefault(sum, 0) + 1);
                }
                if (s == 0) break;
                s = (s-1) & mask;
            }
        }

        long ans = 0;
        for (int lused = 0; lused <= k; lused++) {
            for (Map.Entry<Long, Integer> e : L.get(lused).entrySet()) {
                long val = e.getKey();
                int count = e.getValue();
                long need = S - val;

                for (int rused = 0; lused + rused <= k; rused++) {
                    long countr = R.get(rused).getOrDefault(need, 0);
                    ans += count * countr;
                }
            }
        }

        pw.println(ans);
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    public static int binarySearchRight(List<Integer> A, int l, int r, int k) {
        int upper = -1;
        while (l <= r) {
            int m = l + (r - l) / 2;
            if (A.get(m) == k && (m+1==A.size() || A.get(m+1) > k)) {
                upper = m;
                break;
            }
            if (A.get(m) <= k) {
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