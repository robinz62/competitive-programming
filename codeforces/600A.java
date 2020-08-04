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

    void solve() throws IOException {
        char[] s = rs();
        List<String> a = new ArrayList<String>();
        List<String> b = new ArrayList<String>();
        StringBuilder curr = new StringBuilder();
        boolean allDig = true;
        for (int i = 0; i < s.length; i++) {
            if (s[i] == ',' || s[i] == ';') {
                if (curr.length() == 0) b.add("");
                else if (allDig && curr.length() == 1) a.add(curr.toString());
                else if (allDig && curr.charAt(0) != '0') a.add(curr.toString());
                else b.add(curr.toString());
                allDig = true;
                curr.setLength(0);
            } else {
                curr.append(s[i]);
                if (s[i] < '0' || s[i] > '9') allDig = false;
            }
        }
        if (curr.length() == 0) b.add("");
        else if (allDig && curr.length() == 1) a.add(curr.toString());
        else if (allDig && curr.charAt(0) != '0') a.add(curr.toString());
        else b.add(curr.toString());
        allDig = true;
        String ansA = a.isEmpty() ? "-" : "\"" + String.join(",", a) + "\"";
        String ansB = b.isEmpty() ? "-" : "\"" + String.join(",", b) + "\"";
        pw.println(ansA);
        pw.println(ansB);
    }
}