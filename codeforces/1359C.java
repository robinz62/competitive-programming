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

    int readInt() throws IOException {
        return Integer.parseInt(br.readLine());
    }

    long readLong() throws IOException {
        return Long.parseLong(br.readLine());
    }

    int[] readIntLine() throws IOException {
        String[] tokens = br.readLine().split(" ");
        int[] A = new int[tokens.length];
        for (int i = 0; i < A.length; i++)
            A[i] = Integer.parseInt(tokens[i]);
        return A;
    }

    long[] readLongLine() throws IOException {
        String[] tokens = br.readLine().split(" ");
        long[] A = new long[tokens.length];
        for (int i = 0; i < A.length; i++)
            A[i] = Long.parseLong(tokens[i]);
        return A;
    }

    void solve() throws IOException {
        int T = readInt();
        for (int Ti = 0; Ti < T; Ti++) {
            int[] hct = readIntLine();
            int h = hct[0];
            int c = hct[1];
            int t = hct[2];
            if (t == h) {
                pw.println("1");
                continue;
            }
            if (2 * t <= c + h) {
                pw.println("2");
                continue;
            }

            int X = (t - c) / (2 * t - c - h);
            Fraction bestDiff = new Fraction(Math.abs(h + c - 2 * t), 2);
            int cupsOfBest = 2;
            for (int x = Math.max(1, X-1); x <= X+1; x++) {
                Fraction diff = new Fraction(Math.abs(h * x + c * (x-1) - (2*x-1) * t), 2*x-1);
                if (diff.compareTo(bestDiff) < 0) {
                    bestDiff = diff;
                    cupsOfBest = 2*x-1;
                }
            }
            pw.println(cupsOfBest);
        }
    }
}

class Fraction implements Comparable<Fraction> {
    long n;
    long d;
    public Fraction(long n, long d) {
        if (n == 0) {
            this.d = 1;
            return;
        }
        int sign = n * d < 0 ? -1 : 1;
        n = Math.abs(n);
        d = Math.abs(d);
        long g = gcd(n, d);
        this.n = sign * n / g;
        this.d = d / g;
    }

    static Fraction add(Fraction f1, Fraction f2) {
        return new Fraction(f1.n*f2.d+f1.d*f2.n, f1.d*f2.d);
    }

    static Fraction subtract(Fraction f1, Fraction f2) {
        return new Fraction(f1.n*f2.d-f1.d*f2.n, f1.d*f2.d);
    }

    static Fraction multiply(Fraction f1, Fraction f2) {
        return new Fraction(f1.n*f2.n, f1.d*f2.d);
    }

    static Fraction divide(Fraction f1, Fraction f2) {
        return new Fraction(f1.n*f2.d, f1.d*f2.n);
    }

    public boolean equals(Object o) {
        if (!(o instanceof Fraction)) return false;
        Fraction f = (Fraction) o;
        return n == f.n && d == f.d;
    }

    public int compareTo(Fraction f) {
        return Long.compare(n*f.d, f.n*d);
    }

    public String toString() {
        return n + "/" + d;
    }

    private static long gcd(long a, long b) {
        return a == 0 ? b : gcd(b % a, a);
    }
}