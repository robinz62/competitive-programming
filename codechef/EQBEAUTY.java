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
            sort(a);

            long ans = Long.MAX_VALUE;

            // first brute force all non-overlapping intervals
            for (int i = 1; i < n-2; i++) {
                int d1 = a[i] - a[0];
                int d2 = a[n-1] - a[i+1];
                ans = Math.min(ans, Math.abs(d2 - d1));
            }

            if (n >= 4) {
                int d1 = a[n-1] - a[0];
                int d2 = a[n-2] - a[1];
                ans = Math.min(ans, Math.abs(d2 - d1));
            }

            // special cases: left partition is just a[0] and right partition is just a[n-1]
            int[] allButFirst = new int[n-1];
            System.arraycopy(a, 1, allButFirst, 0, n-1);
            int[] allButLast = new int[n-1];
            System.arraycopy(a, 0, allButLast, 0, n-1);
            ans = Math.min(ans, minMoves2(allButFirst));
            ans = Math.min(ans, minMoves2(allButLast));

            // i is the right boundary of partition 1
            for (int i = 2; i < n-1; i++) {
                int leftdiff = a[i] - a[0];
                // j must be in (0, i)
                int l = 1;
                int r = i-1;
                if (l > r) continue;
                // find 0
                while (l <= r) {
                    int m = (l + r) / 2;
                    int rightdiff = a[n-1] - a[m];
                    if (leftdiff == rightdiff) {
                        ans = 0;
                        break;
                    } else if (leftdiff < rightdiff) {
                        l = m+1;
                    } else {
                        r = m-1;
                    }
                    ans = Math.min(ans, Math.abs(a[n-1] - a[m] - leftdiff));
                }
            }

            // // temp brute force see if this even works
            // for (int j = 1; j < n-1; j++) {
            //     for (int i = j+1; i < n-1; i++) {
            //         int d1 = a[i] - a[0];
            //         int d2 = a[n-1] - a[j];
            //         ans = Math.min(ans, Math.abs(d2 - d1));
            //     }
            // }

            pw.println(ans);
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    public long minMoves2(int[] nums) {
        int k = nums.length / 2;
        int l = 0;
        int r = nums.length - 1;
        int med = -1;
        while (l <= r) {
            int pivot = l + (r - l) / 2;
            int idx = partition(nums, l, r, pivot);
            if (idx == k) {
                med = nums[idx];
                break;
            }
            if (idx < k) {
                l = idx + 1;
            } else {
                r = idx - 1;
            }
        }
        long ans = 0;
        for (int i : nums) {
            ans += Math.abs((long) med - i);
        }
        return ans;
    }
    
    int partition(int[] nums, int l, int r, int p) {
        int y = r - 1;
        int val = nums[p];
        nums[p] = nums[r];
        int i = l;
        while (i <= y) {
            if (nums[i] <= val) {
                i++;
            } else {
                int temp = nums[i];
                nums[i] = nums[y];
                nums[y] = temp;
                y--;
            }
        }
        nums[r] = nums[i];
        nums[i] = val;
        return i;
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