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
        long[] nT = rll(2);
        int n = (int) nT[0];
        long T = nT[1];
        char[] s = rs();

        // Set<String> wow = play(7, true);
        // pw.println(wow.size());
        // for (String ss : wow) pw.println(ss);

        // Based on playground analysis, we must assign signs to first n-2
        // elements however we want to try to obtain T.

        int[] nums = new int[n-2];
        for (int i = 0; i < n-2; i++) nums[i] = s[i] - 'a';
        T -= 1l << (s[n-1] - 'a');
        T += 1l << (s[n-2] - 'a');

        TreeMap<Integer, Integer> bits = new TreeMap<>();
        long total = 0;
        for (int b : nums) {
            total += 1l << b;
            bits.put(b, bits.getOrDefault(b, 0) + 1);
        }

        // I think we can make T positive by just flipping all the signs...
        T = Math.abs(T);

        if (total == T) {
            pw.println("Yes");
            return;
        }
        if (total < T) {
            pw.println("No");
            return;
        }
        // total > T
        long diff = total - T;
        if (diff % 2 != 0) {
            pw.println("No");
            return;
        }
        diff /= 2;
        // Can we form diff from the available bits?
        // Try forming from lowest to highest bit.
        for (int i = 59; i >= 0; i--) {
            if (((1l << i) & diff) > 0) {
                long tgt = 1l << i;
                long curr = 0;
                Integer b = bits.floorKey(i);
                if (b == null) {
                    pw.println("No");
                    return;
                }
                curr += 1l << b;
                while (curr < tgt) {
                    int have = bits.get(b);
                    if (have == 1) {
                        bits.remove(b);
                        curr += 1l << b;
                        b = bits.floorKey(i);
                        if (b == null) {
                            pw.println("No");
                            return;
                        }
                    } else {
                        bits.put(b, have - 1);
                        curr += 1l << b;
                    }
                }
                if (curr > tgt) {
                    pw.println("No");
                    return;
                }
            }
        }
        pw.println("Yes");
    }

    Set<String> play(int n, boolean sign) {
        if (n == 1) {
            Set<String> s = new HashSet<>();
            s.add(sign ? "+" : "-");
            return s;
        }
        Set<String> ans = new HashSet<>();
        for (int i = 1; i <= n-1; i++) {
            Set<String> l = play(i, !sign);
            Set<String> r = play(n-i, sign);
            for (String li : l) {
                for (String ri : r) {
                    ans.add(li + " " + ri);
                }
            }
        }
        return ans;
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