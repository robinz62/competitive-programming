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
        int n = ri();
        int[] a = ril(n);
        sort(a);
        TreeMap<Integer, Integer> map = new TreeMap<>();
        List<Integer> lineup = new ArrayList<>(n);
        for (int ai : a) map.put(ai, map.getOrDefault(ai, 0) + 1);

        lastans = a;

        int l = 0;
        int r = (n-1) / 2;
        int best = 0;
        while (l <= r) {
            int m = (l + r) / 2;
            if (possible(a, m)) {
                best = m;
                l = m + 1;
            } else {
                r = m - 1;
            }
        }

        pw.println(best);
        for (int i : lastans) pw.print(i + " ");
        pw.println();
    }

    int[] lastans;
    boolean possible(int[] a, int m) {
        int[] ans = new int[a.length];
        int j = 1;
        for (int i = 0; i < m; i++) {
            ans[j] = a[i];
            j += 2;
        }
        int i = a.length-1;
        j--;
        for (i = a.length-1; j > 0; i--) {
            ans[j] = a[i];
            j -= 2;
        }
        ans[j] = a[i--];
        j = 1 + 2 * m;
        while (i >= m) {
            ans[j] = a[i--];
            j++;
        }
        int count = 0;
        for (i = 1; i < ans.length-1; i++) {
            if (ans[i] < ans[i-1] && ans[i] < ans[i+1]) count++;
        }
        if (count >= m) {
            lastans = ans;
            return true;
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