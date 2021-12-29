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
            char[] t = rs();
            char[] s = rs();

            List<Deque<Integer>> tchars = new ArrayList<>(26);
            for (int i = 0; i < 26; i++) tchars.add(new ArrayDeque<>());
            for (int i = 0; i < n; i++) tchars.get(t[i]-'a').addLast(i);

            BIT bit = new BIT(n+1);

            long ans = Long.MAX_VALUE;
            boolean ok = true;
            long curr = 0;
            for (int i = 0; ok == true && i < n; i++) {
                // Right now, s matches t for all indices < i

                // Try to get some remaining char in t to be less than s[i].
                for (char c = 'a'; c <= 'z'; c++) {
                    if (c >= s[i]) break;
                    if (tchars.get(c-'a').isEmpty()) continue;
                    int tidx = tchars.get(c-'a').peekFirst();

                    // Swap tidx to i, including swaps already done.
                    // During course of execution so far, how many people to the right
                    // of me have been placed to the left?
                    int swaps = tidx - i + (bit.query(n-1) - bit.query(tidx));
                    ans = Math.min(ans, curr + swaps);
                }

                // Keep going.
                if (tchars.get(s[i]-'a').isEmpty()) break;
                int tidx = tchars.get(s[i]-'a').removeFirst();
                int swaps = tidx - i + (bit.query(n-1) - bit.query(tidx));
                curr += swaps;
                bit.add(tidx, 1);
            }
            pw.println(ans == Long.MAX_VALUE ? -1 : ans);
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

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

class BIT {
    int[] bit;

    // n is the maximum index that can be queried/modified.
    BIT(int n) {
        bit = new int[n+2];
    }
    BIT(int[] arr) {
        bit = new int[arr.length + 1];
        for (int i = 0; i < arr.length; i++) {
            add(i, arr[i]);
        }
    }

    // Queries the closed range [0, i]
    int query(int i) {
        i++;
        int sum = 0;
        while (i >= 1) {
            sum += bit[i];
            i -= i & -i;
        }
        return sum;
    }

    void add(int i, int value) {
        i++;
        while (i < bit.length) {
            bit[i] += value;
            i += i & -i;
        }
    }
}