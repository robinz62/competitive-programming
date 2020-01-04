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
        for (int t = 0; t < T; t++) {
            int[] line = readIntLine();
            int n = line[0];
            int p = line[1];
            int k = line[2];
            int[] A = readIntLine();
            Arrays.sort(A);
            int[] prefix = new int[n];
            int sum = 0;
            for (int i = 0; i < n; i++) {
                sum += A[i];
                prefix[i] = sum;
            }

            int[] kPrefix = new int[n];  // kPrefix[i] = A[i] + A[i-k] + A[i-2k] + ...
            for (int i = k - 1; i < n; i++) {
                kPrefix[i] = A[i] + (i - k >= 0 ? kPrefix[i - k] : 0);
            }

            int m = 0;
            for (int i = 0; i <= n; i++) {
                int sumIndiv = i - 1 >= 0 ? prefix[i-1] : 0;
                if (p < sumIndiv) break;
                int idx = binarySearch(kPrefix, i, kPrefix.length, p - sumIndiv + (i-1 >= 0 ? kPrefix[i-1] : 0));
                if (idx < 0) idx = -idx - 1 - 1;
                idx -= i;
                idx += 1;
                m = Math.max(m, i + (idx / k) * k);
            }
            pw.println(m);
        }
    }

    int binarySearch(int[] A, int l, int r, int key) {
        int upper = -1;
        l = 0;
        r = A.length - 1;
        while (l <= r) {
            int m = l + (r - l) / 2;
            if (A[m] == key && (m+1==A.length || A[m+1] > key)) {
                upper = m;
                break;
            }
            if (A[m] <= key) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return upper >= 0 ? upper : -l - 1;
    }
}