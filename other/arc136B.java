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
        int n = ri();
        int[] a = ril(n);
        int[] b = ril(n);

        boolean bad = false;
        for (int j = 0; j < n-2; j++) {
            int val = b[j];
            int idx = j;
            while (idx < n && a[idx] != val) idx++;
            if (idx == n) {
                bad = true;
                break;
            }
            while (idx != j) {
                if (idx >= j+2) {
                    op(a, idx-2);
                    idx -= 2;
                }
                if (idx == j+1) {
                    op(a, idx-1);
                    op(a, idx-1);
                    idx--;
                }
            }
        }
        if (bad) {
            pw.println("No");
            return;
        }
        if (a[n-2] == b[n-2] && a[n-1] == b[n-1]) {
            pw.println("Yes");
            return;
        }
        Set<Integer> set = new HashSet<>();
        for (int ai : a) set.add(ai);
        if (set.size() < n) {
            pw.println("Yes");
            return;
        }
        // for (int i = 1; i < n-2; i++) {
        //     if (a[i] == a[i-1]) {
        //         pw.println("Yes");
        //         return;
        //     }
        // }

        // Set<Integer> set = new HashSet<>();
        // set.add(a[n-1]);
        // set.add(a[n-2]);
        // set.add(a[n-3]);
        // if (set.size() <= 2) {
        //     pw.println("Yes");
        //     return;
        // }

        pw.println("No");
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    void op(int[] arr, int i) {
        int temp = arr[i];
        arr[i] = arr[i+2];
        arr[i+2] = arr[i+1];
        arr[i+1] = temp;
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