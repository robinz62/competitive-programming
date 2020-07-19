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
        int t = ri();
        for (int ti = 0; ti < t; ti++) {
            int n = ri();
            String s = br.readLine();
            char[] tgt = "abacaba".toCharArray();
            boolean exists = s.indexOf("abacaba") >= 0;
            if (exists) {
                if (s.lastIndexOf("abacaba") != s.indexOf("abacaba")) {
                    pw.println("No");
                    continue;
                }
                char[] cs = s.toCharArray();
                for (int i = 0; i < cs.length; i++) {
                    if (cs[i] == '?') cs[i] = 'z';
                }
                pw.println("Yes");
                pw.println(new String(cs));
                continue;
            }
            char[] cs = s.toCharArray();
            boolean found = false;
            for (int i = 0; i + 7 <= s.length(); i++) {
                boolean match = true;
                for (int j = 0; match && j < 7; j++) {
                    if (s.charAt(i+j) == '?') {
                        continue;
                    }
                    if (s.charAt(i+j) != tgt[j]) match = false;
                }
                if (match) {
                    for (int j = 0; j < 7; j++) {
                        if (s.charAt(i+j) == '?') cs[i+j] = tgt[j];
                    }
                    for (int j = 0; j < cs.length; j++) if (cs[j] == '?') cs[j] = 'f';
                    String temp = new String(cs);
                    if (temp.indexOf("abacaba") != temp.lastIndexOf("abacaba")) {
                        cs = s.toCharArray();
                    } else {
                        pw.println("Yes");
                        pw.println(new String(cs));
                        found = true;
                        break;
                    }
                }
            }
            if (!found) pw.println("No");
        }
    }
}