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
    //   if (x : long) and (y : int), [y = x] does not compile, but [y += x] does
    //   sorting, or taking max, after MOD
    //
    // Interactive problems: don't forget to flush between test cases
    void solve() throws IOException {
        int T = ri();
        for (int Ti = 0; Ti < T; Ti++) {
            int n = ri();
            long[] a = rll(n);

            // a[0] and a[n-1] are irrelevant
            long ans = 0;

            TreeSet<Integer> idxEven = new TreeSet<>();
            TreeSet<Integer> idxOdd = new TreeSet<>();
            TreeSet<Integer> ones = new TreeSet<>();

            for (int i = 1; i < n-1; i++) {
                if (a[i] % 2 == 0) idxEven.add(i);
                else if (a[i] != 1) idxOdd.add(i);
                else ones.add(i);
            }

            while (!idxEven.isEmpty() || !idxOdd.isEmpty()) {
                if (!idxEven.isEmpty()) {
                    int i = idxEven.first();
                    while (!ones.isEmpty() && a[i] > 0) {
                        int idx = ones.first();
                        ones.remove(idx);
                        a[idx]++;
                        idxEven.add(idx);
                        a[i] -= 2;
                        if (a[i] == 0) idxEven.remove(i);
                        ans++;
                    }
                    while (!idxOdd.isEmpty() && a[i] > 0) {
                        int idx = idxOdd.first();
                        idxOdd.remove(idx);
                        a[idx]++;
                        idxEven.add(idx);
                        a[i] -= 2;
                        if (a[i] == 0) idxEven.remove(i);
                        ans++;
                    }
                } else {
                    int i = idxOdd.first();
                    idxOdd.remove(i);
                    if (!ones.isEmpty()) {
                        int idx = ones.first();
                        ones.remove(idx);
                        a[idx]++;
                        idxEven.add(idx);
                        a[i] -= 2;
                        if (a[i] == 1) ones.add(i);
                        else idxOdd.add(i);
                        ans++;
                    } else if (!idxOdd.isEmpty()) {
                        int idx = idxOdd.first();
                        idxOdd.remove(idx);
                        a[idx]++;
                        idxEven.add(idx);
                        a[i] -= 2;
                        if (a[i] == 1) ones.add(i);
                        else idxOdd.add(i);
                        ans++;
                    } else {
                        ans = -1;
                        break;
                    }
                }
                if (idxOdd.isEmpty() && ones.isEmpty()) break;
            }
            if (!ones.isEmpty() || ans == -1) {
                pw.println("-1");
                continue;
            }
            for (int i = 1; i < n-1; i++) {
                ans += a[i] / 2;
            }
            pw.println(ans);
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

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
        int c = br.read();
        int x = 0;
        while (c >= '0' && c <= '9') {
            x = x * 10 + c - '0';
            c = br.read();
        }
        return ril(x);
    }

    long[] rkll() throws IOException {
        int c = br.read();
        int x = 0;
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