/*
ID: robinz61
LANG: JAVA
TASK: friday
*/
import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class friday {
    static int MOD = 1000000007;

    static String input = "friday.in";
    static String output = "friday.out";

    void solve() throws IOException {
        int n = ri();
        int dayOfWeek = 2;
        int[] count = new int[7];
        for (int year = 1900; year < 1900 + n; year++) {
            for (int month = 0; month < 12; month++) {
                int days = days(month, year);
                for (int day = 1; day <= days; day++) {
                    if (day == 13) count[dayOfWeek]++;
                    dayOfWeek = (dayOfWeek + 1) % 7;
                }
            }
        }
        pw.print(count[0]);
        for (int i = 1; i < 7; i++) pw.print(" " + count[i]);
        pw.println();
    }

    int days(int month, int year) {
        if (month == 0 || month == 2 || month == 4 || month == 6 || month == 7 || month == 9 || month == 11) return 31;
        if (month == 1) {
            if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) return 29;
            return 28;
        }
        return 30;
    }

    // Template code below

    static BufferedReader br;
    static PrintWriter pw;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new FileReader(input));
        pw = new PrintWriter(new FileWriter(output));
        friday m = new friday();
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