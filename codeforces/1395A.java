import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static int MOD = 1000000007;

    void solve() throws IOException {
        int t = ri();
        for (int ti = 0; ti < t; ti++) {
            int[] rgbw = ril(4);
            int r = rgbw[0];
            int g = rgbw[1];
            int b = rgbw[2];
            int w = rgbw[3];
            int numOdds = 0;
            for (int c : rgbw) if (c % 2 == 1) numOdds++;
            if (numOdds <= 1) {
                pw.println("Yes");
                continue;
            }
            if (r >= 1 && g >= 1 && b >= 1) {
                numOdds = 0;
                rgbw[0]--;
                rgbw[1]--;
                rgbw[2]--;
                rgbw[3] += 3;
                for (int c : rgbw) if (c % 2 == 1) numOdds++;
                if (numOdds <= 1) {
                    pw.println("Yes");
                    continue;
                }
            }
            if (rgbw[0] >= 1 && rgbw[1] >= 1 && rgbw[2] >= 1) {
                numOdds = 0;
                rgbw[0]--;
                rgbw[1]--;
                rgbw[2]--;
                rgbw[3] += 3;
                for (int c : rgbw) if (c % 2 == 1) numOdds++;
                if (numOdds <= 1) {
                    pw.println("Yes");
                    continue;
                }
            }
            pw.println("No");
        }
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
        return Integer.parseInt(br.readLine());
    }

    long rl() throws IOException {
        return Long.parseLong(br.readLine());
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

    char[] rs() throws IOException {
        return br.readLine().toCharArray();
    }

    void sort(int[] A) {
        Random r = new Random();
        for (int i = A.length-1; i > 0; i--) {
            int j = r.nextInt(i+1);
            A[i] ^= A[j];
            A[j] ^= A[i];
            A[i] ^= A[j];
        }
        Arrays.sort(A);
    }

    int max(int a, int b) {
        return a >= b ? a : b;
    }

    int min(int a, int b) {
        return a <= b ? a : b;
    }
}