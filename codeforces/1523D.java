import java.io.*;
import java.math.BigInteger;
import java.util.*;

// Takeaways/learnings:
// Consider only unique values.
// SOS dynamic programming: https://codeforces.com/blog/entry/45223
//
// Note: 50 iterations gave TLE. 30 iterations was AC.
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
        int[] nmp = ril(3);
        int n = nmp[0];
        int m = nmp[1];
        int p = nmp[2];
        long[] a = new long[n];
        for (int i = 0; i < n; i++) a[i] = Long.parseLong(br.readLine(), 2);
        int req = (n + 1) / 2;

        int ans = 0;
        long ansString = 0;

        for (int it = 0; it < 30; it++) {
            // Pick a random person
            long rand = a[(int) (Math.random() * n)];

            // Compress each person to confined bits.
            List<Integer> bits = new ArrayList<>(16);
            for (int i = 0; i < m; i++) if (((1l << i) & rand) > 0) bits.add(i);

            // Key idea here: the total number of possible values, once compressed, is small!
            int[] compressedCounts = new int[1 << bits.size()];
            for (int i = 0; i < n; i++) {
                int val = 0;
                for (int j = 0; j < bits.size(); j++) {
                    if (((1l << bits.get(j)) & a[i]) > 0) val = val | (1 << j);
                }
                compressedCounts[val]++;
            }

            // For each unique mask, count how many people are a superset of that mask.
            // We do this by considering each mask in [1, 1 << bits.size()] and, for
            // each submask of it, adding +1 to counts[submask].
            int[] counts = new int[1 << bits.size()];
            for (int mask = 1; mask < (1 << bits.size()); mask++) {
                // [mask] contributes +1 to each submask of it
                int s = mask;
                while (s > 0) {
                    // use s here
                    counts[s] += compressedCounts[mask];

                    s = (s-1) & mask;
                }
            }
            for (int i = 0; i < counts.length; i++) {
                int mask = i;
                if (Integer.bitCount(mask) > ans && counts[i] >= req) {
                    ans = Integer.bitCount(mask);
                    long str = 0;
                    for (int b = 0; b < bits.size(); b++) {
                        if (((1 << b) & mask) > 0) {
                            str = str | (1l << bits.get(b));
                        }
                    }
                    ansString = str;
                }
            }
        }

        for (int i = m-1; i >= 0; i--) pw.print(((1l << i) & ansString) > 0 ? 1 : 0);
        pw.println();
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