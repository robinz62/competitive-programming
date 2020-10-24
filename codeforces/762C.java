import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static int MOD = 1000000007;

    // After writing solution, quick scan for:
    //   array out of bounds
    //   special cases e.g. n=1?
    //
    // Big numbers arithmetic bugs:
    //   int overflow
    //   sorting, or taking max, after MOD
    void solve() throws IOException {
        char[] a = rs();
        int n1 = a.length;
        char[] b = rs();
        int n2 = b.length;
        int i = n1-1;
        int j = n2-1;
        int[] aToB = new int[n1];  // maps characters of a to characters of b
        Arrays.fill(aToB, -1);
        while (i >= 0 && j >= 0) {
            if (a[i] == b[j]) {
                aToB[i] = j;
                i--;
                j--;
            } else {
                i--;
            }
        }
        // j is at an unmatched character (or 0)
        // b[j+1..] is ok
        j++;
        int[] ans = new int[]{-1, j};  // answer is b[0..ans[0]] + b[ans[1]..n2-1]
        i = 0;
        int k = 0;
        while (i < n1 && k < n2) {
            j = Math.max(j, k+1);
            if (aToB[i] != -1) {
                // undo this usage i.e. free up the letter
                j = Math.max(j, aToB[i] + 1);  // to be safe
            }
            if (a[i] == b[k]) {
                // new answer if we keep more letters
                if (k+1 + (n2-1-j+1) > ans[0]+1 + (n2-1-ans[1]+1)) {
                    ans[0] = k;
                    ans[1] = j;
                }
                i++;
                k++;
            } else {
                i++;
            }
        }
        if (ans[0]+1 + (n2-1-ans[1]+1) == 0) {
            pw.println("-");
            return;
        }
        
        String bstr = new String(b);
        pw.println(bstr.substring(0, ans[0]+1) + (ans[1] >= n2 ? "" : bstr.substring(ans[1])));
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
        int sign = 1;
        int c = br.read();
        int x = 0;
        if (c == '-') {
            sign = -1;
            c = br.read();
        }
        while (c >= '0' && c <= '9') {
            x = x * 10 + c - '0';
            c = br.read();
        }
        return ril(x);
    }

    long[] rkll() throws IOException {
        int sign = 1;
        int c = br.read();
        int x = 0;
        if (c == '-') {
            sign = -1;
            c = br.read();
        }
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
}