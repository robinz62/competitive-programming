import java.io.*;
import java.util.*;
 
public class Main {
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

    int readInt() throws IOException {
        return Integer.parseInt(br.readLine());
    }
 
    int[] readIntLine() throws IOException {
        String[] tokens = br.readLine().split(" ");
        int[] A = new int[tokens.length];
        for (int i = 0; i < A.length; i++) A[i] = Integer.parseInt(tokens[i]);
        return A;
    }
 
    long[] readLongLine() throws IOException {
        String[] tokens = br.readLine().split(" ");
        long[] A = new long[tokens.length];
        for (int i = 0; i < A.length; i++) A[i] = Long.parseLong(tokens[i]);
        return A;
    }
 
    void solve() throws IOException {
        int T = readInt();
        for (int t = 0; t < T; t++) {
            int[] abc = readIntLine();
            int A = abc[0];
            int B = abc[1];
            int C = abc[2];

            long count = 0;
            for (int b = 1; b <= B; b++) {
                int z = b * b + 1;
                int ceilSqrt = ceilSqrt(z);

                // if C is large enough
                if (true) {
                    for (int a = 2; (a - 1) * (a - 1) < z && a <= A; a++) {
                        int min_c = ceil(z, a - 1) + 1;
                        if (min_c <= C) {
                            count += C - min_c + 1;
                            // pw.println("DEBUG A: a=" + a + " b=" + b);
                            // pw.println("DEBUG A: adding " + (C-min_c+1));
                        }
                    }
                }

                // if A is large enough
                if (true) {
                    for (int c = 2; (c - 1) * (c - 1) < z && c <= C; c++) {
                        int min_a = ceil(z, c - 1) + 1;
                        if (min_a <= A) {
                            count += A - min_a + 1;
                            // pw.println("DEBUG B: b=" + b);
                            // pw.println("DEBUG B: adding " + (A-min_a+1));
                        }
                    }
                }

                // if A and C are both large enough
                if ((A - 1) >= ceilSqrt && (C - 1) >= ceilSqrt) {
                    count += (long) (A - ceilSqrt) * (long) (C - ceilSqrt);
                    // pw.println("DEBUG C: b=" + b);
                    // pw.println("DEBUG C: adding " + ((long) (A - ceilSqrt) * (long) (C - ceilSqrt)));
                }
                count %= 1000000007;
            }
            pw.println(count);
        }
    }

    int ceil(int x, int y) {
        return (x - 1) / y + 1;
    }

    int ceilSqrt(int x) {
        int sqrt = (int) Math.sqrt(x);
        if (sqrt * sqrt == x) return sqrt;
        if (sqrt * sqrt < x && (sqrt + 1) * (sqrt + 1) > x) return sqrt + 1;
        System.exit(-1);
        return -1;
    }
}