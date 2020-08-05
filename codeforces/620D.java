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
        int n = ri();
        int[] a = ril(n);
        int m = ri();
        int[] b = ril(m);
        long sumA = 0;
        long sumB = 0;
        for (int ai : a) sumA += ai;
        for (int bi : b) sumB += bi;

        long diff = sumA - sumB;

        // no swaps
        long v0 = Math.abs(diff);

        // 1 swap
        long v1 = Long.MAX_VALUE;
        long i1 = -1;
        long j1 = -1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                long val = Math.abs((sumA - a[i] + b[j]) - (sumB - b[j] + a[i]));
                if (val < v1) {
                    v1 = val;
                    i1 = i;
                    j1 = j;
                }
            }
        }

        // 2 swaps
        List<long[]> pairSumsA = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                pairSumsA.add(new long[]{(long) a[i] + a[j], i, j});
            }
        }
        List<long[]> pairSumsB = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            for (int j = i+1; j < m; j++) {
                pairSumsB.add(new long[]{(long) b[i] + b[j], i, j});
            }
        }
        Collections.sort(pairSumsB, (x1, x2) -> Long.compare(x1[0], x2[0]));
        List<Long> valsOnly = new ArrayList<>(pairSumsB.size());
        for (long[] x : pairSumsB) valsOnly.add(x[0]);

        long v2 = Long.MAX_VALUE;
        long[] v2idx = new long[4];
        for (long[] pair : pairSumsA) {
            long asum = pair[0];
            // choose pair from pairSumsB such that difference is diff / 2
            // in other words, try to find asum - diff/2
            int idx = Collections.binarySearch(valsOnly, asum-diff/2);
            if (idx < 0) idx = -idx-1;
            for (int i = Math.max(idx-1, 0); i <= Math.min(idx+1, pairSumsB.size()-1); i++) {
                long bsum = valsOnly.get(i);
                long val = Math.abs((sumA - asum + bsum) - (sumB - bsum + asum));
                if (val < v2) {
                    v2 = val;
                    v2idx[0] = pair[1];
                    v2idx[1] = pair[2];
                    v2idx[2] = pairSumsB.get(i)[1];
                    v2idx[3] = pairSumsB.get(i)[2];
                }
            }
        }

        if (v0 == Math.min(v0, Math.min(v1, v2))) {
            pw.println(v0);
            pw.println("0");
        } else if (v1 == Math.min(v0, Math.min(v1, v2))) {
            pw.println(v1);
            pw.println("1");
            pw.println((i1+1) + " " + (j1+1));
        } else {
            pw.println(v2);
            pw.println("2");
            pw.println((v2idx[0]+1) + " " + (v2idx[2]+1));
            pw.println((v2idx[1]+1) + " " + (v2idx[3]+1));
        }
    }
}