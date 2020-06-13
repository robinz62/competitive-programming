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
            int[] p = readIntLine();
            List<Integer> stack = new ArrayList<>();
            stack.add(p[0]);
            stack.add(p[1]);
            for (int i = 2; i < n; i++) {
                int a = stack.get(stack.size() - 2);
                int b = stack.get(stack.size() - 1);
                int c = p[i];
                if (a < b && b < c) {
                    stack.remove(stack.size() - 1);
                    stack.add(c);
                } else if (a > b && b > c) {
                    stack.remove(stack.size() - 1);
                    stack.add(c);
                } else {
                    stack.add(c);
                }
            }
            pw.println(stack.size());
            for (int x : stack) {
                pw.print(x + " ");
            }
            pw.println();
        }
    }
}