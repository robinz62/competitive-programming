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
        int[] t = ril(n);
        // even index up, odd index down

        int numbad = 0;
        for (int i = 0; i < n-1; i++) {
            if (i % 2 == 0 && t[i] >= t[i+1] || i % 2 == 1 && t[i] <= t[i+1]) numbad++;
        }

        long ans = 0;
        for (int i = 0; i < n-1; i++) {
            if (i % 2 == 0 && t[i] >= t[i+1] || i % 2 == 1 && t[i] <= t[i+1]) {
                for (int j = 0; j < n; j++) {
                    // try swapping t[i] with t[j]
                    Set<Integer> set = new HashSet<>();
                    set.add(i-1);
                    set.add(i);
                    set.add(i+1);
                    set.add(j-1);
                    set.add(j);
                    set.add(j+1);
                    int before = 0;
                    for (int x : set) if (!ok(t, x)) before++;
                    swap(t, i, j);
                    int after = 0;
                    for (int x : set) if (!ok(t, x)) after++;
                    int delta = after - before;
                    if (numbad + delta == 0) {
                        ans++;
                    }
                    swap(t, i, j);
                }
                for (int j = 0; j < n; j++) {
                    if (j == i) continue;
                    // try swapping t[i] with t[j]
                    Set<Integer> set = new HashSet<>();
                    set.add(i);
                    set.add(i+1);
                    set.add(i+2);
                    set.add(j-1);
                    set.add(j);
                    set.add(j+1);
                    int before = 0;
                    for (int x : set) if (!ok(t, x)) before++;
                    swap(t, i+1, j);
                    int after = 0;
                    for (int x : set) if (!ok(t, x)) after++;
                    int delta = after - before;
                    if (numbad + delta == 0) {
                        ans++;
                    }
                    swap(t, i+1, j);
                }
                break;
            }
        }
        pw.println(ans);
    }

    boolean ok(int[] t, int i) {
        if (i >= t.length-1 || i < 0) return true;
        if (i % 2 == 0) return t[i] < t[i+1];
        return t[i] > t[i+1];
    }

    void swap(int[] t, int i, int j) {
        int temp = t[i];
        t[i] = t[j];
        t[j] = temp;
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