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
    //   sorting, or taking max, after MOD
    int n;
    int m;
    int q;
    void solve() throws IOException {
        int[] nmq = ril(3);
        n = nmq[0];
        m = nmq[1];
        q = nmq[2];

        boolean[][] a = new boolean[n][m];
        int[][][] b = new int[n][m][2];  // 0 is horiz, 1 is vert
        for (int j = 1; j < m; j++) b[0][j][0] = 1;
        for (int i = 1; i < n; i++) b[i][0][1] = 1;
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                b[i][j][0] = b[i][j-1][1] + 1;
                b[i][j][1] = b[i-1][j][0] + 1;
            }
        }

        int count = n * m;
        for (int[][] x : b) for (int[] y : x) for (int z : y) count += z;
        // count is now total in an empty grid

        // Obervation: there will never be too many locked cells (q <= 10^4).
        for (int qi = 0; qi < q; qi++) {
            int[] xy = ril(2);
            int x = xy[0]-1;
            int y = xy[1]-1;

            if (a[x][y]) {  // locked: make free
                a[x][y] = false;
                count -= count(a, x, y);
                // Same as 
            } else {  // free: make locked
                count += count(a, x, y);
                a[x][y] = true;
            }

            pw.println(count);
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    int count(boolean[][] a, int x, int y) {
        int count = 0;

        // Case 1: this block is a "top right" block.
        boolean horiz = true;
        int i = x; int j = y;
        int c1 = 0;
        while (i >= 0 && j >= 0 && !a[i][j]) {
            c1++;
            if (horiz) j--;
            else i--;
            horiz = !horiz;
        }
        horiz = false;
        i = x; j = y;
        int c2 = 0;
        while (i < n && j < m && !a[i][j]) {
            c2++;
            if (horiz) j++;
            else i++;
            horiz = !horiz;
        }
        count -= c1 * c2;

        // Case 2: this block is a "bottom left" block
        horiz = false;
        i = x; j = y;
        c1 = 0;
        while (i >= 0 && j >= 0 && !a[i][j]) {
            c1++;
            if (horiz) j--;
            else i--;
            horiz = !horiz;
        }
        horiz = true;
        i = x; j = y;
        c2 = 0;
        while (i < n && j < m && !a[i][j]) {
            c2++;
            if (horiz) j++;
            else i++;
            horiz = !horiz;
        }
        count -= c1 * c2;

        count++;  // double counted singleton removal
        return count;
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

    void printDouble(double d) {
        pw.printf("%.16f", d);
    }
}