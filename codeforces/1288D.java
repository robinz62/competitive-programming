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
        int[] nm = ril(2);
        int n = nm[0];
        int m = nm[1];
        int[][] a = new int[n][];
        for (int i = 0; i < n; i++) a[i] = ril(m);

        int l = 0;
        int r = 1000000000;
        int[] idx = new int[2];
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (possible(a, mid)) {
                l = mid+1;
            } else {
                r = mid-1;
            }
        }
        pw.println(ans[0] + " " + ans[1]);
    }

    int[] ans = new int[2];
    boolean possible(int[][] a, int m) {
        int n = a.length;
        int k = a[0].length;
        Map<Integer, Integer> bitsToIdx = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int x = 0;
            for (int j = 0; j < k; j++) {
                if (a[i][j] >= m) x |= (1 << j);
            }
            bitsToIdx.put(x, i);
        }
        int[] seen = new int[1 << k];
        Arrays.fill(seen, -1);
        for (int x : bitsToIdx.keySet()) {
            seen[x] = bitsToIdx.get(x);
            for (int s = 0; s < (1 << k); s++) {
                if ((x | s) == (1 << k) - 1 && seen[s] != -1) {
                    ans[0] = seen[s] + 1;
                    ans[1] = bitsToIdx.get(x)+1;
                    return true;
                }
            }
        }
        return false;
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
}