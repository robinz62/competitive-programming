import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static int MOD = 1000000007;

    void solve() throws IOException {
        int[] ndm = ril(3);
        int n = ndm[0];
        int d = ndm[1];
        int m = ndm[2];
        int[] a = ril(n);
        List<Integer> x = new ArrayList<>();  // ok
        List<Integer> y = new ArrayList<>();  // muzzled
        for (int ai : a) {
            if (ai <= m) x.add(ai);
            else y.add(ai);
        }
        Collections.sort(x, Collections.reverseOrder());
        Collections.sort(y, Collections.reverseOrder());
        long[] px = new long[x.size()];
        long[] py = new long[y.size()];
        if (!x.isEmpty()) px[0] = x.get(0);
        for (int i = 1; i < px.length; i++) px[i] = px[i-1] + x.get(i);
        if (!y.isEmpty()) py[0] = y.get(0);
        for (int i = 1; i < py.length; i++) py[i] = py[i-1] + y.get(i);

        // answer is X from ok followed by Y from muzzled
        // brute force over how many taken from muzzled
        long ans = 0;
        for (int Y = 0; Y <= y.size(); Y++) {
            long cand = 0;
            long daysNeeded = Y == 0 ? 0 : (long) (d+1) * (Y-1) + 1;
            cand += Y > 0 ? py[Y-1] : 0;

            long leftover = n - daysNeeded;
            if (leftover < 0) break;
            int X = (int) leftover;
            cand += px.length > 0 ? (X > 0 ? px[Math.min(X-1, px.length-1)] : 0) : 0;
            ans = Math.max(cand, ans);
        }
        pw.println(ans);
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
        return Integer.parseInt(br.readLine());
    }

    long rl() throws IOException {
        return Long.parseLong(br.readLine());
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

    char[] rs() throws IOException {
        return br.readLine().toCharArray();
    }

    void sort(int[] A) {
        Random r = new Random();
        for (int i = A.length-1; i > 0; i--) {
            int j = r.nextInt(i+1);
            A[i] ^= A[j];
            A[j] ^= A[i];
            A[i] ^= A[j];
        }
        Arrays.sort(A);
    }

    int max(int a, int b) {
        return a >= b ? a : b;
    }

    int min(int a, int b) {
        return a <= b ? a : b;
    }
}