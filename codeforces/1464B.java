import java.io.*;
import java.math.BigInteger;
import java.util.*;
 
public class Main {
    static int MOD = 1000000007;

    // Upsolving the editorial's solution
    void solve2() throws IOException {
        char[] s = rs();
        int n = s.length;
        long[] xy = rll(2);
        long x = xy[0];
        long y = xy[1];

        if (x > y) {
            for (int i = 0; i < n; i++) {
                if (s[i] == '0') s[i] = '1';
                else if (s[i] == '1') s[i] = '0';
            }
            long temp = x;
            x = y;
            y = temp;
        }
 
        // 0, 1, ?
        int[][] l = new int[n][3];
        int[][] r = new int[n][3];
        for (int i = 1; i < n; i++) {
            l[i][0] = l[i-1][0];
            l[i][1] = l[i-1][1];
            l[i][2] = l[i-1][2];
            if (s[i-1] == '0') l[i][0]++;
            else if (s[i-1] == '1') l[i][1]++;
            else l[i][2]++;
        }
        for (int i = n-2; i >= 0; i--) {
            r[i][0] = r[i+1][0];
            r[i][1] = r[i+1][1];
            r[i][2] = r[i+1][2];
            if (s[i+1] == '0') r[i][0]++;
            else if (s[i+1] == '1') r[i][1]++;
            else r[i][2]++;
        }
        // 0s then 1s. Start with no 0s (all ? are 1s)
        long cost = 0;
        for (int i = 1; i < n; i++) {
            if (s[i] == '0') cost += y * (l[i][1] + l[i][2]);
            else if (s[i] == '1') cost += x * l[i][0];
            else if (s[i] == '?') cost += x * l[i][0];
        }
        long best = cost;
        // Gradually increase number of 0s and adjust cost accordingly
        for (int i = 0; i < n; i++) {
            if (s[i] != '?') continue;
            // Change s[i] from a 1 to a 0
            long sub = x * (l[i][0] + l[i][2]) + y * r[i][0];
            long add = y * l[i][1] + x * (r[i][1] + r[i][2]);
            cost = cost + add - sub;
            best = Math.min(best, cost);
        }
        pw.println(best);
    }
 
    // After writing solution, quick scan for:
    //   array out of bounds
    //   special cases e.g. n=1?
    //
    // Big numbers arithmetic bugs:
    //   int overflow
    //   sorting, or taking max, after MOD
    void solve() throws IOException {
        char[] s = rs();
        int[] xy = ril(2);
        int x = xy[0];
        int y = xy[1];
 
        int numQ = 0;
        for (char c : s) if (c == '?') numQ++;
 
        // dp[i][j][k] is minimum badness for s[0..i] when there are j 0s and k 1s.
 
        // I wonder: will the sequence be place always be monotonic? i.e. 1s
        // followed by 0s or 0s followed by 1s.
 
        int l = 0;
        int r = numQ;
        long best = Long.MAX_VALUE;
        while (l <= r) {
            int m = l + (r - l) / 2;
            long ans = compute(s, m, x, y);
            best = Math.min(best, ans);
            if (m-1 < 0) {
                l = m+1;
                continue;
            }
            long left = compute(s, m-1, x, y);
            best = Math.min(best, left);
            if (left < ans) {
                r = m-1;
            } else {
                l = m+1;
            }
        }
 
        l = 0;
        r = numQ;
        while (l <= r) {
            int m = l + (r - l) / 2;
            long ans = compute2(s, m, x, y);
            best = Math.min(best, ans);
            if (m-1 < 0) {
                l = m+1;
                continue;
            }
            long left = compute2(s, m-1, x, y);
            best = Math.min(best, left);
            if (left < ans) {
                r = m-1;
            } else {
                l = m+1;
            }
        }

        best = Math.min(best, compute(s, 0, x, y));
        best = Math.min(best, compute(s, numQ, x, y));
        best = Math.min(best, compute2(s, 0, x, y));
        best = Math.min(best, compute2(s, numQ, x, y));
 
        pw.println(best);
    }
 
    long compute(char[] s, int numZ, long x, long y) {
        int[] left = new int[2];
        long ans = 0;
        if (s[0] == '?') {
            if (numZ > 0) {
                left[0]++;
                numZ--;
            } else {
                left[1]++;
            }
        } else if (s[0] == '0') left[0]++;
        else left[1]++;
        for (int i = 1; i < s.length; i++) {
            char c = s[i];
            if (c == '?') {
                if (numZ > 0) {
                    c = '0';
                    numZ--;
                } else c = '1';
            }
            if (c == '0') {
                ans += y * left[1];
                left[0]++;
            } else if (c == '1') {
                ans += x * left[0];
                left[1]++;
            }
        }
        return ans;
    }
 
    long compute2(char[] s, int numO, long x, long y) {
        int[] left = new int[2];
        long ans = 0;
        if (s[0] == '?') {
            if (numO > 0) {
                left[1]++;
                numO--;
            } else {
                left[0]++;
            }
        } else if (s[0] == '0') left[0]++;
        else left[1]++;
        int z = numO;
        for (int i = 1; i < s.length; i++) {
            char c = s[i];
            if (c == '?') {
                if (numO > 0) {
                    c = '1';
                    numO--;
                } else c = '0';
            }
            if (c == '0') {
                ans += y * left[1];
                left[0]++;
            } else if (c == '1') {
                ans += x * left[0];
                left[1]++;
            }
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