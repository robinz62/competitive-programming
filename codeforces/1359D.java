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
        int n = ri();
        int[] a = ril();
        pw.println(helper(a, 0, n-1));
    }

    int helper(int[] a, int l, int r) {
        if (l > r) return Integer.MIN_VALUE;
        if (l == r) return 0;
        int m = l + (r - l) / 2;
        int ans = Math.max(helper(a, l, m), helper(a, m+1, r));

        int[] auxr = new int[61];  // auxr[i] is max val going right using values <= i
        Arrays.fill(auxr, Integer.MIN_VALUE);
        int max = a[m+1];
        int currsum = 0;
        for (int i = m+1; i <= r; i++) {
            max = Math.max(max, a[i]);
            currsum += a[i];
            for (int j = max+30; j <= 60; j++) {
                auxr[j] = Math.max(auxr[j], currsum);
            }
        }

        int[] auxl = new int[61];
        Arrays.fill(auxl, Integer.MIN_VALUE);
        max = a[m];
        currsum = 0;
        for (int i = m; i >= l; i--) {
            max = Math.max(max, a[i]);
            currsum += a[i];
            for (int j = max+30; j <= 60; j++) {
                auxl[j] = Math.max(auxl[j], currsum);
            }
        }

        for (int x = Math.max(a[m], a[m+1]); x <= 30; x++) {
            ans = Math.max(ans, auxl[x+30] + auxr[x+30] - x);
        }

        return ans;
    }
}