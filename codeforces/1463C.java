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
        int T = ri();
        for (int Ti = 0; Ti < T; Ti++) {
            int n = ri();
            int ans = 0;
            long[][] tx = new long[n][];
            for (int i = 0; i < n; i++) {
                tx[i] = rll(2);
            }

            int i = 0;
            long pos = 0;  // corresponds the where curr started from
            int dir = 1;
            long[] curr = null;  // describes the actual movement of the robot
            long[] prev = null;  // describes the previous command, the one we are trying to validate success of
            while (i < n) {
                if (curr == null) {
                    curr = tx[i];
                    prev = tx[i];
                    dir = curr[1] >= pos ? 1 : -1;
                    i++;
                    continue;
                }
                // Verify two things
                // 1) did the actual movement ever cross the previous command
                // 2) has the actual movement concluded
                long timePrev = tx[i-1][0];
                long timeNow = tx[i][0];
                if (timeNow < curr[0] + Math.abs(curr[1] - pos)) {
                    // curr has not completed, so travel the entire time
                    long start = pos + (timePrev - curr[0]) * dir;
                    long end = pos + (timeNow - curr[0]) * dir;
                    if (prev != null && (prev[1] >= start && prev[1] <= end || prev[1] >= end && prev[1] <= start)) {
                        ans++;
                    }
                    prev = tx[i];
                } else {
                    // curr has completed, so we stopped travelling at some point
                    // also we can boot up the new command
                    long start = pos + (timePrev - curr[0]) * dir;
                    long end = curr[1];
                    if (prev != null && (prev[1] >= start && prev[1] <= end || prev[1] >= end && prev[1] <= start)) {
                        ans++;
                    }
                    pos = curr[1];
                    dir = tx[i][1] >= pos ? 1 : -1;
                    curr = tx[i];
                    prev = tx[i];
                }
                i++;
            }
            // Ending
            long timeNow = 1000000000000l;
            long timePrev = tx[n-1][0];
            if (timeNow < curr[0] + Math.abs(curr[1] - pos)) {
                // curr has not completed, so travel the entire time
                long start = pos + (timePrev - curr[0]) * dir;
                long end = pos + (timeNow - curr[0]) * dir;
                if (prev != null && (prev[1] >= start && prev[1] <= end || prev[1] >= end && prev[1] <= start)) {
                    ans++;
                }
            } else {
                // curr has completed, so we stopped travelling at some point
                // also we can boot up the new command
                long start = pos + (timePrev - curr[0]) * dir;
                long end = curr[1];
                if (prev != null && (prev[1] >= start && prev[1] <= end || prev[1] >= end && prev[1] <= start)) {
                    ans++;
                }
            }
            pw.println(ans);
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