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
            String str = br.readLine();
            char[] s = str.toCharArray();

            // boolean[][] isPalindrome = new boolean[s.length][s.length];
            // for (int i = 0; i < s.length; i++) isPalindrome[i][i] = true;
            // for (int i = 0; i < s.length - 1; i++) if (s[i] == s[i+1]) isPalindrome[i][i+1] = true;
            // for (int len = 3; len <= s.length; len++) {
            //     for (int i = 0; i + len - 1 < s.length; i++) {
            //         isPalindrome[i][i+len-1] = s[i] == s[i+len-1] && isPalindrome[i+1][i+len-2];
            //     }
            // }

            // Manacher's algorithm: https://cp-algorithms.com/string/manacher.html
            int n = s.length;
            int[] d1 = new int[n];
            for (int i = 0, l = 0, r = -1; i < n; i++) {
                int k = (i > r) ? 1 : Math.min(d1[l + r - i], r - i + 1);
                while (0 <= i - k && i + k < n && s[i - k] == s[i + k]) {
                    k++;
                }
                d1[i] = k--;
                if (i + k > r) {
                    l = i - k;
                    r = i + k;
                }
            }
            int[] d2 = new int[s.length];
            for (int i = 0, l = 0, r = -1; i < n; i++) {
                int k = (i > r) ? 0 : Math.min(d2[l + r - i + 1], r - i + 1);
                while (0 <= i - k - 1 && i + k < n && s[i - k - 1] == s[i + k]) {
                    k++;
                }
                d2[i] = k--;
                if (i + k > r) {
                    l = i - k - 1;
                    r = i + k ;
                }
            }

            int l = 0;
            int r = s.length - 1;
            StringBuilder prefix = new StringBuilder();
            StringBuilder suffix = new StringBuilder();
            while (l < r && s[l] == s[r]) {
                prefix.append(s[l]);
                suffix.append(s[r]);
                l++;
                r--;
            }
            if (l == r) {
                suffix.reverse();
                pw.print(prefix);
                pw.print(s[l]);
                pw.print(suffix);
                pw.println();
                continue;
            }
            suffix.reverse();
            String mid = "";
            if (l < r) {
                int bestL = l;
                int bestR = r;
                for (int i = l; i <= r; i++) {
                    int lenRequiredL = i - l;
                    if (d1[i] != 0 && d1[i] - 1 >= lenRequiredL && i + lenRequiredL <= r) {
                        bestL = Math.max(bestL, i + lenRequiredL);
                    }
                    if (d2[i] != 0 && d2[i] >= lenRequiredL && i + lenRequiredL - 1 <= r) {
                        bestL = Math.max(bestL, i + lenRequiredL - 1);
                    }
                    int lenRequiredR = r - i;
                    if (d1[i] != 0 && d1[i] - 1 >= lenRequiredR && i - lenRequiredR >= l) {
                        bestR = Math.min(bestR, i - lenRequiredR);
                    }
                    if (d2[i] != 0 && d2[i] - 1 >= lenRequiredR && i - lenRequiredR - 1 >= l) {
                        bestR = Math.min(bestR, i - lenRequiredR - 1);
                    }
                }
                if (bestL - l + 1 >= r - bestR + 1) {
                    mid = str.substring(l, bestL + 1);
                } else {
                    mid = str.substring(bestR, r + 1);
                }
            }
            pw.print(prefix);
            pw.print(mid);
            pw.print(suffix);
            pw.println();
        }
    }
}