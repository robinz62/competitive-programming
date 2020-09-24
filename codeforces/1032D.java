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
        long[] abc = rll(3);
        double a = abc[0];
        double b = abc[1];
        double c = abc[2];
        c = -c;
        long[] xyxy = rll(4);
        double x1 = xyxy[0];
        double y1 = xyxy[1];
        double x2 = xyxy[2];
        double y2 = xyxy[3];
        if (x1 > x2) {
            double tempx = x1;
            x1 = x2;
            x2 = tempx;
            double tempy = y1;
            y1 = y2;
            y2 = tempy;
        }

        if (x1 == x2) {
            pw.println(Math.abs(y2 - y1));
            return;
        }
        if (y1 == y2) {
            pw.println(Math.abs(x2 - x1));
            return;
        }

        double m = (y2 - y1) / (x2 - x1);
        double meq = -a / b;
        if (m * meq < 0) {
            pw.println(Math.abs(x2-x1) + Math.abs(y2-y1));
            return;
        }
        
        double yOfX1 = -a / b * x1 + c / b;
        double yOfX2 = -a / b * x2 + c / b;
        if (yOfX1 >= Math.min(y1, y2) && yOfX1 <= Math.max(y1, y2)) {
            if (yOfX2 >= Math.min(y1, y2) && yOfX2 <= Math.max(y1, y2)) {
                double dx = x2 - x1;
                double dy = yOfX2 - yOfX1;
                double ans = Math.abs(y2 - y1) - Math.abs(dy) + Math.sqrt(dy * dy + dx * dx);
                pw.println(ans);
                return;
            } else {
                // intersects either top or bottom
                double ans;
                if (m > 0) {
                    double xinter = (c - b * Math.max(y1, y2)) / a;
                    double yinter = Math.max(y1, y2);
                    double dx = xinter - x1;
                    double dy = yinter - yOfX1;
                    ans = Math.sqrt(dx*dx + dy*dy) + x2 - xinter + yOfX1 - y1;
                } else {
                    double xinter = (c - b * Math.min(y1, y2)) / a;
                    double yinter = Math.min(y1, y2);
                    double dx = xinter - x1;
                    double dy = yinter - yOfX1;
                    ans = Math.sqrt(dx*dx + dy*dy) + x2 - xinter + Math.max(y1, y2) - yOfX1;
                }
                pw.println(ans);
                return;
            }
        }

        // Intersects right edge but not left, so either top or bottom
        if (yOfX2 >= Math.min(y1, y2) && yOfX2 <= Math.max(y1, y2)) {
            double ans;
            if (m > 0) {
                double xinter = (c - b * Math.min(y1, y2)) / a;
                double yinter = Math.min(y1, y2);
                double dx = x2 - xinter;
                double dy = yOfX2 - yinter;
                ans = Math.sqrt(dx*dx + dy*dy) + y2 - yOfX2 + xinter - x1;
            } else {
                double xinter = (c - b * Math.max(y1, y2)) / a;
                double yinter = Math.max(y1, y2);
                double dx = x2 - xinter;
                double dy = yOfX2 - yinter;
                ans = Math.sqrt(dx*dx + dy*dy) + yOfX2 - y2 + xinter - x1;
            }
            pw.println(ans);
            return;
        }

        // Intersects top and bottom only
        double xInter1 = (c - b * y1) / a;
        double xInter2 = (c - b * y2) / a;
        if (xInter1 >= x1 && xInter1 <= x2) {
            double dx = xInter2 - xInter1;
            double dy = y2 - y1;
            double ans = Math.sqrt(dx*dx+dy*dy) + x2 - x1 - Math.abs(dx);
            pw.println(ans);
            return;
        }

        pw.println(Math.abs(x2-x1) + Math.abs(y2-y1));
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