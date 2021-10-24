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
        int n = ri();
        int[][] points = new int[n][];
        for (int i = 0; i < n; i++) points[i] = ril(2);

        // This also asymptotically solution doesn't work? Wtf?
        // TreeMap<Fraction, TreeMap<Integer, Integer>> counts = new TreeMap<>();
        // for (int i = 0; i < n; i++) {
        //     int x0 = points[i][0];
        //     int y0 = points[i][1];
        //     for (int j = i+1; j < n; j++) {
        //         int x1 = points[j][0];
        //         int y1 = points[j][1];

        //         Fraction slope = new Fraction(y1 - y0, x1 - x0);
        //         TreeMap<Integer, Integer> m = counts.get(slope);
        //         if (m == null) {
        //             m = new TreeMap<>();
        //             counts.put(slope, m);
        //         }
        //         int D = Math.abs(x1 - x0) + Math.abs(y1 - y0);
        //         m.put(D, m.getOrDefault(D, 0) + 1);
        //     }
        // }

        // long ans = 0;
        // for (Map<Integer, Integer> m : counts.values()) {
        //     for (int v : m.values()) {
        //         ans += v * (v-1) / 2;
        //     }
        // }
        // pw.println(ans / 2);

        Map<Pair, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int x0 = points[i][0];
            int y0 = points[i][1];
            for (int j = i+1; j < n; j++) {
                int x1 = points[j][0];
                int y1 = points[j][1];

                int m1 = x0 + x1;
                int m2 = y0 + y1;
                Pair p = new Pair(m1, m2);
                map.put(p, map.getOrDefault(p, 0) + 1);
            }
        }

        long ans = 0;
        for (int x : map.values()) {
            ans += x * (x - 1) / 2;
        }
        pw.println(ans);
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

class Fraction implements Comparable<Fraction> {
    final int n;
    final int d;
    Fraction(int n, int d) {
        if (n == 0) {
            this.n = 0;
            this.d = 1;
            return;
        }
        if (d == 0) {
            this.n = 1;
            this.d = 0;
            return;
        }
        int sign = n * d < 0 ? -1 : 1;
        n = Math.abs(n);
        d = Math.abs(d);
        int g = gcd(n, d);
        this.n = sign * n / g;
        this.d = d / g;
    }

    public boolean equals(Object o) {
        if (!(o instanceof Fraction)) return false;
        Fraction f = (Fraction) o;
        return n == f.n && d == f.d;
    }

    public int hashCode() {
        return n * 2 + d * 3;
    }

    public int compareTo(Fraction f) {
        return n == f.n ? Integer.compare(d, f.d) : Integer.compare(n, f.n);
    }

    public String toString() {
        return n + "/" + d;
    }

    private static int gcd(int a, int b) {
        return a == 0 ? b : gcd(b % a, a);
    }
}

class Pair {
    int fst;
    int snd;
    Pair(int f, int s) {
        fst = f;
        snd = s;
    }
    public int hashCode() {
        return fst * 9991 + snd;
    }
    public boolean equals(Object o) {
        if (!(o instanceof Pair)) return false;
        Pair p = (Pair) o;
        return fst == p.fst && snd == p.snd;
    }
}