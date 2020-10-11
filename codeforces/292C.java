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
    List<String> ans = new ArrayList<>();
    void solve() throws IOException {
        int n = ri();
        int[] digits = ril(n);
        Arrays.sort(digits);
        int[] used = new int[n];

        for (int len = 4; len <= 12; len++) {
            helper(digits, used, 0, new StringBuilder(), len);
        }
        pw.println(ans.size());
        for (String s : ans) pw.println(s);
    }

    void print(String s) {
        char[] c = s.toCharArray();
        for (int i = 0; i < c.length; i++) c[i] += '0';
        pw.println(new String(c));
    }

    void helper(int[] digits, int[] used, int usedCount, StringBuilder curr, int len) {
        int left = len % 2 == 0 ? len / 2 - curr.length() : len / 2 + 1 - curr.length();
        if (digits.length-usedCount > left) return;
        if (len % 2 == 0 && curr.length() * 2 == len) {
            StringBuilder sb = new StringBuilder();
            sb.append(curr);
            curr.reverse();
            sb.append(curr);
            curr.reverse();
            helper2(sb.toString());
            return;
        } else if (len % 2 == 1 && curr.length() * 2 - 1 == len) {
            StringBuilder sb = new StringBuilder();
            sb.append(curr);
            char c = curr.charAt(curr.length() - 1);
            curr.deleteCharAt(curr.length() - 1);
            curr.reverse();
            sb.append(curr);
            curr.reverse();
            curr.append(c);
            helper2(sb.toString());
            return;
        }

        for (int i = 0; i < digits.length; i++) {
            curr.append((char) digits[i]);
            if (used[i] == 0) usedCount++;
            used[i]++;
            helper(digits, used, usedCount, curr, len);
            used[i]--;
            if (used[i] == 0) usedCount--;
            curr.deleteCharAt(curr.length() - 1);
        }
    }

    void helper2(String s) {
        for (int i = 0; i < s.length()-1; i++) {
            String a = s.substring(0, i+1);
            if (!checkNum(a)) continue;
            for (int j = i+1; j < s.length()-1; j++) {
                String b = s.substring(i+1, j+1);
                if (!checkNum(b)) continue;
                for (int k = j+1; k < s.length()-1; k++) {
                    String c = s.substring(j+1, k+1);
                    if (!checkNum(c)) continue;
                    String d = s.substring(k+1);
                    if (!checkNum(d)) continue;
                    StringBuilder sb = new StringBuilder();
                    char x = (char) ('.'-'0');
                    sb.append(a).append(x).append(b).append(x).append(c).append(x).append(d);
                    char[] cc = sb.toString().toCharArray();
                    for (int z = 0; z < cc.length; z++) cc[z] += '0';
                    ans.add(new String(cc));
                }
            }
        }
    }

    boolean checkNum(String s) {
        if (s.length() == 0) return false;
        if (s.charAt(0) == 0 && s.length() > 1) return false;
        int val = 0;
        for (int i = 0; i < s.length(); i++) {
            val *= 10;
            val += s.charAt(i);
        }
        if (val > 255) return false;
        return true;
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