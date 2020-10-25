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
        long[] a = rll(6);
        int n = ri();
        long[] b = rll(n);
        sort(a);
        sort(b);
        
        // Each note b[i] gets 6 numbers, 1 for each string it's played on
        long[][] c = new long[n][6];
        for (int i = 0; i < n; i++) {
            for (int s = 0; s < 6; s++) {
                c[i][s] = b[i] - a[s];
            }
            sort(c[i]);
        }

        long ans = Long.MAX_VALUE;
        TreeMap<Long, Integer> map = new TreeMap<>();
        int[] idx = new int[n];
        for (int i = 0; i < n; i++) {
            map.put(c[i][idx[i]], map.getOrDefault(c[i][idx[i]], 0) + 1);
        }
        ans = map.lastKey() - map.firstKey();
        PriorityQueue<Integer> pq = new PriorityQueue<>((i1, i2) -> Long.compare(c[i1][idx[i1]], c[i2][idx[i2]]));
        for (int i = 0; i < n; i++) pq.add(i);
        while (!pq.isEmpty()) {
            int idxSmallest = pq.remove();
            long prevVal = c[idxSmallest][idx[idxSmallest]];
            if (idx[idxSmallest]+1 < 6) map.put(prevVal, map.get(prevVal) - 1);
            idx[idxSmallest]++;
            if (idx[idxSmallest] < 6) {
                long newVal = c[idxSmallest][idx[idxSmallest]];
                pq.add(idxSmallest);
                map.put(newVal, map.getOrDefault(newVal, 0) + 1);
            }
            if (map.get(prevVal) == 0) map.remove(prevVal);
            if (!map.isEmpty()) ans = Math.min(ans, map.lastKey() - map.firstKey());
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

    void sort(long[] A) {
        Random r = new Random();
        for (int i = A.length-1; i > 0; i--) {
            int j = r.nextInt(i+1);
            long temp = A[i];
            A[i] = A[j];
            A[j] = temp;
        }
        Arrays.sort(A);
    }

    void printDouble(double d) {
        pw.printf("%.16f", d);
    }
}