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
        long[] nlk = rll(3);
        int n = (int) nlk[0];
        long l = nlk[1];
        int k = (int) nlk[2];
        int[] a = ril(n);

        // dp[i][x] is number of subsequences of length x which end up on an
        // index whose (mod n) value is i.
        // dp[i][x] = sum of dp[j][x-1] where j ranges over all indices where a[j] <= a[i]

        // Coordinate compress to [0..V)
        // NOTE: this part was slow and had to be constant-time optimized as coordinate compression
        // was not part of the author's solution :(
        // Eventually accepted though.
        int[] cpy = new int[n];
        System.arraycopy(a, 0, cpy, 0, n);
        sort(cpy);
        List<Integer> list = new ArrayList<>(n);
        list.add(cpy[0]);
        for (int i = 1; i < n; i++) {
            if (cpy[i] != cpy[i-1]) list.add(cpy[i]);
        }
        int V = list.size();
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < list.size(); i++) map.put(list.get(i), i);
        for (int i = 0; i < n; i++) a[i] = map.get(a[i]);

        int[] freq = new int[V];
        for (int ai : a) freq[ai]++;

        long[] counts = new long[V];
        counts[0] = freq[0];
        for (int i = 1; i < V; i++) counts[i] = counts[i-1] + freq[i];

        long[] mult = new long[n];  // NOTE: mult is NOT modded
        for (int i = 0; i < n; i++) mult[i] = i < l % n ? l / n + 1 : l / n;

        long ans = l % MOD;
        long sub = 0;
        for (int x = 2; x <= k; x++) {
            sub++;
            long[] newCounts = new long[V];
            for (int i = 0; i < n; i++) {
                int myval = a[i];
                newCounts[myval] = (newCounts[myval] + counts[myval]) % MOD;
                ans = (ans + Math.max(mult[i] - sub, 0) % MOD * counts[myval] % MOD) % MOD;
            }
            // Recompute prefix
            counts[0] = newCounts[0];
            for (int i = 1; i < V; i++) counts[i] = (counts[i-1] + newCounts[i]) % MOD;
        }
        pw.println(ans);
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