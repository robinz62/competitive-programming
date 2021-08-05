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
    void solve() throws IOException {
        int T = ri();
        for (int Ti = 0; Ti < T; Ti++) {
            int n = ri();
            char[] s = rs();
            String str = new String(s);
            boolean done = false;
            for (char x = 'a'; !done && x <= 'z'; x++) {
                String word = "" + x;
                if (!str.contains(word)) {
                    pw.println(word);
                    done = true;
                }
            }

            for (char x = 'a'; !done && x <= 'z'; x++) {
                for (char y = 'a'; !done && y <= 'z'; y++) {
                    String word = "" + x + y;
                    if (!str.contains(word)) {
                        pw.println(word);
                        done = true;
                    }
                }
            }

            for (char x = 'a'; !done && x <= 'z'; x++) {
                for (char y = 'a'; !done && y <= 'z'; y++) {
                    for (char z = 'a'; !done && z <= 'z'; z++) {
                        String word = "" + x + y + z;
                        if (!str.contains(word)) {
                            pw.println(word);
                            done = true;
                        }
                    }
                }
            }


            // String ans = backtrack("", str);
            // pw.println(ans);
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    // Template code below

    String backtrack(String word, String text) {
        if (word.length() > 3) return null;
        if (word.length() != 0) {
            if (!text.contains(word)) {
                return word;
            }
        }
        for (char c = 'a'; c <= 'z'; c++) {
            // word.add(c);
            String res = backtrack(word + c, text);
            if (res != null) return res;
            // word.remove(word.size() - 1);
        }
        return null;
    }

    public static int search(List<Character> word, String text) {
        int[] kmp = getKmpArray(word);
        int i = 0;
        int j = 0;
        while (i < text.length()) {
            if (text.charAt(i) == word.get(j)) {
                ++i;
                ++j;
                if (j == word.size()) {
                    return i - word.size();
                }
            } else if (j != 0) {
                j = kmp[j - 1];
            } else {
                ++i;
            }
        }
        return -1;
    }

    public static int[] getKmpArray(List<Character> word) {
        int[] kmp = new int[word.size()];
        int i = 1;
        int j = 0;
        while (i < kmp.length) {
            if (word.get(i) == word.get(j)) {
                kmp[i] = j + 1;
                ++i;
                ++j;
            } else if (j != 0) {
                j = kmp[j - 1];
            } else {
                ++i;
            }
        }
        return kmp;
    }

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