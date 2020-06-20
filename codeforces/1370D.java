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
        int[] nk = readIntLine();
        int n = nk[0];
        int k = nk[1];
        int[] a = readIntLine();
        int l = 1;
        int r = 1_000_000_000;
        int min = r;
        while (l <= r) {
            int m = l + (r - l) / 2;
            if (ok(a, m, k)) {
                min = Math.min(min, m);
                r = m - 1;
            } else {
                l = m + 1;
            }
        }
        pw.println(min);
    }

    boolean ok(int[] a, int m, int k) {
        int countMain = 0;
        int countOther = 0;
        if (k % 2 == 0) {
            // sequence starts with main
            int i = 0;
            while (i < a.length && countMain < k / 2) {
                if (a[i] <= m) {
                    countMain++;
                    i++;
                    if (i < a.length) {
                        countOther++;
                        i++;
                    }
                } else {
                    i++;
                }
            }
            if (countMain == k / 2 && countOther == k / 2) {
                return true;
            }
            // sequence starts with other
            i = 1;
            countMain = 0;
            countOther = 1;
            while (i < a.length && countMain < k / 2) {
                if (a[i] <= m) {
                    countMain++;
                    i++;
                    if (countMain == k / 2) break;
                    if (i < a.length) {
                        countOther++;
                        i++;
                    }
                } else {
                    i++;
                }
            }
            if (countMain == k / 2 && countOther == k / 2) {
                return true;
            }
        } else {
            // start with main (i.e. main has more elems)
            int i = 0;
            while (i < a.length && countMain < k / 2 + 1) {
                if (a[i] <= m) {
                    countMain++;
                    i++;
                    if (countMain == k / 2 + 1) break;
                    if (i < a.length) {
                        countOther++;
                        i++;
                    }
                } else {
                    i++;
                }
            }
            if (countMain == k / 2 + 1 && countOther == k / 2) return true;
            // start with other (i.e. other has more elems)
            i = 1;
            countOther = 1;
            countMain = 0;
            while (i < a.length && countMain < k / 2) {
                if (a[i] <= m) {
                    countMain++;
                    i++;
                    if (i < a.length) {
                        countOther++;
                        i++;
                    }
                } else {
                    i++;
                }
            }
            if (countMain == k / 2 && countOther == k / 2 + 1) return true;
        }
        return false;
    }
}