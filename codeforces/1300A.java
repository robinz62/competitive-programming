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
        int t = readInt();
        for (int ti = 0; ti < t; ti++) {
            int n = readInt();
            int[] a = readIntLine();
            int sum = 0;
            int numZeros = 0;
            for (int ai : a) {
                sum += ai;
                if (ai == 0) numZeros++;
            }
            if (numZeros == 0 && sum != 0) pw.println("0");
            else if (numZeros == 0 && sum == 0) pw.println("1");
            else if (numZeros != 0 && sum != 0) {
                if (numZeros == -sum) pw.println(numZeros+1);
                else pw.println(numZeros);
            } else if (numZeros != 0 && sum == 0) {
                pw.println(numZeros);
            }
        }
    }
}