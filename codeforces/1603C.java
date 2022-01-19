import java.io.*;
import java.math.BigInteger;
import java.util.*;

// Lesson/note: This solution TLEs if we MOD too many times! Had to make sure
// it was outside the innermost for loop.
public class Main {
    static int MOD = 998244353;

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

        // We need to initialize these only once...
        int AMAX = 100000;
        long[] rightCounts = new long[AMAX+1];
        long[] currCounts = new long[AMAX+1];

        for (int Ti = 0; Ti < T; Ti++) {
            int n = ri();
            int[] a = ril(n);

            // dp[i][x] is the number of subarrays starting at i, where after the procedure, the
            // first element becomes x.
            //
            // Let split := number of splits needed to turn a[i] into x.
            // Then the answer is the sum of contributions over all (i, x) pairs of dp[i][x] * split.

            // How to compute dp[i][x]?
            // It's pretty difficult to compute dp[i][x] using dp[i][x+e] for e in [0..?] since
            // it's hard to tell what that ? should be.
            // It's easier to "compute the other way". That is, let's loop over dp[i+1][x]. Now,
            // with fixed x as the value to the right, we can add that contribution to
            // dp[i][y] where y = floor(a[i] / ceil(a[i] / x)). (see below).

            Set<Integer> rightNonzero = new HashSet<>();
            Set<Integer> currNonzero = new HashSet<>();

            rightCounts[a[n-1]]++;
            rightNonzero.add(a[n-1]);
            long ans = 0;

            for (int i = n-2; i >= 0; i--) {
                // Add all the contributions of i+1 to i.
                for (int x : rightNonzero) {  // x is the value to the right
                    long count = rightCounts[x];  // number of subarrays [i+1..] with the value x.
                    int y = a[i] / ((a[i] + x - 1) / x);  // y is the value i become
                    int splits = (a[i] + x - 1) / x;  // splits is the new number of elems when i become x.
                    currCounts[y] += count;
                    currNonzero.add(y);

                    ans += (i+1) * count * (splits - 1);
                    
                    // Clear in preparation for swap
                    rightCounts[x] = 0;
                }

                currCounts[a[i]]++;
                currNonzero.add(a[i]);

                // Swap around the memory we're re-using.
                long[] temp = rightCounts;  // This is empty
                rightCounts = currCounts;
                currCounts = temp;

                Set<Integer> temp2 = rightNonzero;
                rightNonzero = currNonzero;
                currNonzero = temp2;
                currNonzero.clear();

                ans %= MOD;
            }

            // Clear out rightCounts in preparation for next test case
            for (int x : rightNonzero) rightCounts[x] = 0;
            pw.println(ans);
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    // Finds the extreme value for subarray a[i..j].
    int solve(int[] a, int l, int r) {
        if (l >= r) return 0;
        int ans = 0;
        int upper = a[r];
        for (int i = r-1; i >= l; i--) {
            if (a[i] <= upper) {
                upper = a[i];
                continue;
            }
            
            // Want a[i] / k <= upper (k sections)
            // So we want k >= a[i] / upper
            int k = (a[i] + upper - 1) / upper;
            ans += k-1;
            upper = a[i] / k;
        }
        return ans;
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