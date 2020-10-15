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
        if (n < 3) {
            pw.println("0");
            return;
        }
        TreeMap<Integer, Deque<Integer>> numToIdxR = new TreeMap<>();
        TreeMap<Integer, Deque<Integer>> numToIdxL = new TreeMap<>();
        for (int i = 0; i < n; i++) {
            int ai = a[i];
            if (!numToIdxR.containsKey(ai)) numToIdxR.put(ai, new ArrayDeque<>());
            numToIdxR.get(ai).addLast(i);
        }
        for (int i = 0; i < n; i++) {
            numToIdxR.get(a[i]).removeFirst();
            if (numToIdxR.get(a[i]).isEmpty()) numToIdxR.remove(a[i]);
            Integer higherL = numToIdxL.higherKey(a[i]);
            Integer higherR = numToIdxR.higherKey(a[i]);
            if (higherL != null && higherR != null) {
                int x = numToIdxL.get(higherL).peekFirst();
                int z = numToIdxR.get(higherR).peekFirst();
                pw.println("3");
                pw.println((x+1) + " " + (i+1) + " " + (z+1));
                return;
            }
            Integer lowerL = numToIdxL.lowerKey(a[i]);
            Integer lowerR = numToIdxR.lowerKey(a[i]);
            if (lowerL != null && lowerR != null) {
                int x = numToIdxL.get(lowerL).peekFirst();
                int z = numToIdxR.get(lowerR).peekFirst();
                pw.println("3");
                pw.println((x+1) + " " + (i+1) + " " + (z+1));
                return;
            }

            if (!numToIdxL.containsKey(a[i])) numToIdxL.put(a[i], new ArrayDeque<>());
            numToIdxL.get(a[i]).addLast(i);
        }
        pw.println("0");
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