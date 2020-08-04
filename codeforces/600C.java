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
        int[] freq = new int[26];
        for (char c : s) freq[c-'a']++;
        List<Integer> odds = new ArrayList<>();
        for (int i = 0; i < 26; i++) if (freq[i] % 2 == 1) odds.add(i);
        if (odds.size() <= 1) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 26; i++) {
                for (int j = 0; j < freq[i] / 2; j++) sb.append((char) ('a' + i));
            }
            pw.print(sb.toString());
            if (!odds.isEmpty()) pw.print((char) (odds.get(0) + 'a'));
            pw.println(sb.reverse().toString());
        } else {
            if (odds.size() % 2 == 0) {
                for (int i = 0; i < odds.size() / 2; i++) freq[odds.get(i)]++;
                for (int i = odds.size() / 2; i < odds.size(); i++) freq[odds.get(i)]--;
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < 26; i++) {
                    for (int j = 0; j < freq[i] / 2; j++) sb.append((char) ('a' + i));
                }
                pw.print(sb.toString());
                pw.println(sb.reverse().toString());
            } else {
                for (int i = 0; i < odds.size() / 2; i++) freq[odds.get(i)]++;
                for (int i = odds.size() / 2 + 1; i < odds.size(); i++) freq[odds.get(i)]--;
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < 26; i++) {
                    for (int j = 0; j < freq[i] / 2; j++) sb.append((char) ('a' + i));
                }
                pw.print(sb.toString());
                pw.print((char) (odds.get(odds.size() / 2) + 'a'));
                pw.println(sb.reverse().toString());
            }
        }
    }
}