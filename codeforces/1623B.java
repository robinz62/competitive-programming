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
    //   sorting, or taking max, after MOD
    void solve() throws IOException {
        int T = ri();
        for (int Ti = 0; Ti < T; Ti++) {
            int n = ri();
            int[][] ranges = new int[n][];
            for (int i = 0; i < n; i++) ranges[i] = ril(2);

            TreeMap<Integer, TreeSet<Integer>> all = new TreeMap<>();
            TreeMap<Integer, Integer> allends = new TreeMap<>();
            for (int[] range : ranges) {
                int l = range[0];
                int r = range[1];
                if (!all.containsKey(l)) all.put(l, new TreeSet<>());
                all.get(l).add(r);
                allends.put(r, allends.getOrDefault(r, 0) + 1);
            }

            TreeMap<Integer, Integer> curr = new TreeMap<>();
            curr.put(1, n);

            while (!curr.isEmpty()) {
                int l = curr.firstKey();
                int r = curr.get(l);
                curr.remove(l);
                if (allends.get(r) == 1) allends.remove(r);
                else allends.put(r, allends.get(r) - 1);
                all.get(l).remove(r);
                if (all.get(l).isEmpty()) all.remove(l);
                if (l == r) {
                    pw.println(l + " " + r + " " + l);
                } else if (!all.containsKey(l)) {
                    // l must have been removed, leaving 1 interval behind
                    pw.println(l + " " + r + " " + l);
                    curr.put(l+1, r);
                    // allends.put(r, allends.getOrDefault(r, 0) + 1);
                } else if (!allends.containsKey(r)) {
                    pw.println(l + " " + r + " " + r);
                    curr.put(l, r-1);
                    // allends.put(r-1, allends.getOrDefault(r-1, 0) + 1);
                } else {
                    int splitleft = all.get(l).lower(r);  // Impossible to fail
                    int d = splitleft+1;
                    pw.println(l + " " + r + " " + d);
                    curr.put(l, d-1);
                    curr.put(d+1, r);
                    // allends.put(d-1, allends.getOrDefault(d-1, 0) + 1);
                    // allends.put(r, allends.getOrDefault(r, 0) + 1);
                }
            }
            pw.println();
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