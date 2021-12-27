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
        int[] nm = ril(2);
        int n = nm[0];
        int m = nm[1];

        long[] balance = new long[n];
        for (int i = 0; i < m; i++) {
            int[] uvd = ril(3);
            int u = uvd[0]-1;
            int v = uvd[1]-1;
            long d = uvd[2];
            balance[u] -= d;
            balance[v] += d;
        }

        // negative gives to positive
        TreeMap<Long, List<Integer>> negative = new TreeMap<>();
        TreeMap<Long, List<Integer>> positive = new TreeMap<>();
        for (int u = 0; u < n; u++) {
            if (balance[u] < 0) {
                if (!negative.containsKey(balance[u])) negative.put(balance[u], new ArrayList<>());
                negative.get(balance[u]).add(u);
            } else if (balance[u] > 0) {
                if (!positive.containsKey(balance[u])) positive.put(balance[u], new ArrayList<>());
                positive.get(balance[u]).add(u);
            }
        }

        List<long[]> ans = new ArrayList<>();
        while (!negative.isEmpty()) {
            long currneg = negative.firstKey();
            int negnode = negative.get(currneg).remove(negative.get(currneg).size() - 1);
            if (negative.get(currneg).isEmpty()) negative.remove(currneg);
            currneg = Math.abs(currneg);
            while (currneg != 0) {
                long currpos = positive.lastKey();
                int posnode = positive.get(currpos).remove(positive.get(currpos).size() - 1);
                if (positive.get(currpos).isEmpty()) positive.remove(currpos);

                long take = Math.min(currneg, currpos);
                ans.add(new long[]{negnode, posnode, take});
                currneg -= take;
                currpos -= take;
                if (currpos > 0) {
                    if (!positive.containsKey(currpos)) positive.put(currpos, new ArrayList<>());
                    positive.get(currpos).add(posnode);
                }
            }
        }
        pw.println(ans.size());
        for (long[] uvd : ans) {
            pw.println((uvd[0]+1) + " " + (uvd[1]+1) + " " + uvd[2]);
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