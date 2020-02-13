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
            long[] nm = readLongLine();
            long n = nm[0];
            int[] a = readIntLine();
            long sum = 0;
            for (int ai : a) sum += ai;
            if (sum < n) {
                pw.println("-1");
                continue;
            }
            int[] boxes = new int[60];
            for (int ai : a) boxes[Integer.numberOfTrailingZeros(ai)]++;
            int splits = 0;
            long sumSmallBoxes = 0;
            for (int i = 0; i < 60; i++) {
                sumSmallBoxes += (1l << i) * boxes[i];  // we can use boxes up to this size freely
                if ((n & (1l << i)) != 0) {
                    if (sumSmallBoxes >= (1l << i)) {  // we can form
                        sumSmallBoxes -= (1l << i);
                    } else {
                        // find the first available larger box and split it
                        int j = i + 1;
                        while (j < 60) {
                            if (boxes[j] != 0) break;
                            j++;
                        }
                        if (j == 60) {
                            // this shouldn't happen because of the check at the very beginning
                            // but just in case...
                            pw.println("-1");
                            splits = -1;
                            break;
                        }
                        while (boxes[i] == 0) {
                            splits++;
                            boxes[j]--;
                            boxes[j-1] += 2;
                            j--;
                        }
                        sumSmallBoxes += (1l << i);  // we generated two but used one of them.
                    }
                }
            }

            if (splits >= 0) pw.println(splits);
        }
    }
}