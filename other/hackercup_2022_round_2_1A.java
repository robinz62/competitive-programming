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
    //   if (x : long) and (y : int), [y = x] does not compile, but [y += x] does
    //   sorting, or taking max, after MOD
    //
    // Interactive problems: don't forget to flush between test cases
    void solve() throws IOException {
        int T = ri();
        for (int Ti = 1; Ti <= T; Ti++) {
            char[] s = rs();
            int n = s.length;
            int q = ri();

            int[][] counts = new int[n][26];
            counts[0][s[0]-'a']++;
            for (int i = 1; i < n; i++) {
                for (int c = 0; c < 26; c++) counts[i][c] = counts[i-1][c];
                counts[i][s[i]-'a']++;
            }

            int ans = 0;
            for (int qi = 0; qi < q; qi++) {
                int[] lr = ril(2);
                int l = lr[0]-1;
                int r = lr[1]-1;

                if ((r - l + 1) % 2 == 0) continue;
                if (l == r) {
                    ans++;
                    continue;
                }

                int m = (l + r) / 2;

                int[] left = new int[26];
                int[] right = new int[26];
                for (int c = 0; c < 26; c++) {
                    left[c] = counts[m-1][c] - (l-1 >= 0 ? counts[l-1][c] : 0);
                    right[c] = counts[r][c] - counts[m][c];
                }

                // Try deleting middle
                boolean good = true;
                for (int c = 0; good && c < 26; c++) {
                    if (left[c] != right[c]) good = false;
                }
                if (good) {
                    ans++;
                    continue;
                }

                // Try deleting on the left. Must be true that exactly one letter, left has one more than right
                left[s[m]-'a']++;
                good = true;
                boolean foundDiff = false;
                for (int c = 0; good && c < 26; c++) {
                    if (left[c] == right[c]) continue;
                    if (left[c] == right[c] + 1) {
                        if (foundDiff) good = false;
                        foundDiff = true;
                    } else good = false;
                }
                if (good && foundDiff) {
                    ans++;
                    continue;
                }

                // Ditto
                left[s[m]-'a']--;
                right[s[m]-'a']++;
                good = true;
                foundDiff = false;
                for (int c = 0; good && c < 26; c++) {
                    if (left[c] == right[c]) continue;
                    if (left[c] + 1 == right[c]) {
                        if (foundDiff) good = false;
                        foundDiff = true;
                    } else good = false;
                }
                if (good && foundDiff) {
                    ans++;
                    continue;
                }
            }

            printAnswer(Ti, "" + ans);
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    void printAnswer(int i, String ans) {
        pw.println("Case #" + i + ": " + ans);
    }

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
        int c = br.read();
        int x = 0;
        while (c >= '0' && c <= '9') {
            x = x * 10 + c - '0';
            c = br.read();
        }
        return ril(x);
    }

    long[] rkll() throws IOException {
        int c = br.read();
        int x = 0;
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