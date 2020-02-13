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
        int n = readInt();
        int[] a = readIntLine();
        Arrays.sort(a);
        
        // find the number with highest unique bit
        int[] count = new int[32];  // how many times each bit appears
        int[] numSet = new int[32]; // numSet[i] the number to last set bit i
        for (int ai : a) {
            for (int i = 0; i < 32; i++) {
                if (((1 << i) & ai) != 0) {
                    count[i]++;
                    numSet[i] = ai;
                }
            }
        }
        int num = -1;
        for (int i = 31; i >= 0; i--) {
            if (count[i] == 1) {
                num = numSet[i];
                break;
            }
        }
        if (num == -1) {
            for (int i = 0; i < n; i++) pw.print(a[i] + " ");
        } else {
            pw.print(num + " ");
            for (int i = 0; i < n; i++) {
                if (a[i] != num) pw.print(a[i] + " ");
            }
        }
        pw.println();
    }
}