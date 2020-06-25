import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pw = new PrintWriter(System.out);
    static int MOD = 1000000007;

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

    int[] ril() throws IOException {
        String[] tokens = br.readLine().split(" ");
        int[] A = new int[tokens.length];
        for (int i = 0; i < A.length; i++)
            A[i] = Integer.parseInt(tokens[i]);
        return A;
    }

    long[] rll() throws IOException {
        String[] tokens = br.readLine().split(" ");
        long[] A = new long[tokens.length];
        for (int i = 0; i < A.length; i++)
            A[i] = Long.parseLong(tokens[i]);
        return A;
    }

    void solve() throws IOException {
        int t = ri();
        for (int ti = 0; ti < t; ti++) {
            int n = ri();
            long[] a = rll();
            List<Long> eo = new ArrayList<>();
            for (int i = 0; i+1 < n; i += 2) {
                eo.add(a[i+1] - a[i]);
            }
            List<Long> oe = new ArrayList<>();
            for (int i = 1; i+1 < n; i += 2) {
                oe.add(a[i] - a[i+1]);
            }
            long base = 0;
            for (int i = 0; i < n; i += 2) base += a[i];
            long best = Math.max(maxSubarray(eo), maxSubarray(oe));
            pw.println(Math.max(base, base + best));
        }
    }

    public long maxSubarray(List<Long> nums) {
        if (nums.isEmpty()) return 0;
        long currSum = Math.max(nums.get(0), 0);
        long max = nums.get(0);
        for (int i = 1; i < nums.size(); ++i) {
            currSum += nums.get(i);
            if (currSum > max) {
                max = currSum;
            }
            if (currSum < 0) {
                currSum = 0;
            }
        }
        return max;
    }
}