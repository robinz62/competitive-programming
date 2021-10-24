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
        int n = ri();
        int[] a = ril(n);
        int[] b = new int[2 * n];
        System.arraycopy(a, 0, b, 0, n);
        System.arraycopy(a, 0, b, n, n);

        // Compute for each elem, the first elem to the left that it kills, if any
        int[] kills = new int[2 * n];
        Arrays.fill(kills, -1);

        List<Integer> stackVals = new ArrayList<>();
        List<Integer> stackIdxs = new ArrayList<>();
        for (int i = 0; i < 2 * n; i++) {
            int idxHigher = Collections.binarySearch(stackVals, 2 * b[i] + 1, Comparator.reverseOrder());
            if (idxHigher < 0) idxHigher = -idxHigher-1-1;
            if (idxHigher >= 0) {
                kills[i] = stackIdxs.get(idxHigher);
            }
            while (!stackVals.isEmpty() && b[i] >= stackVals.get(stackVals.size() - 1)) {
                stackVals.remove(stackVals.size() - 1);
                stackIdxs.remove(stackIdxs.size() - 1);
            }
            stackVals.add(b[i]);
            stackIdxs.add(i);
        }

        TreeMap<Integer, Integer> finishingTimes = new TreeMap<>();
        Map<Integer, List<Integer>> startToFinishes = new HashMap<>();
        for (int i = 0; i < 2 * n; i++) {
            if (kills[i] != -1) {
                finishingTimes.put(i, finishingTimes.getOrDefault(i, 0) + 1);
                if (!startToFinishes.containsKey(kills[i])) startToFinishes.put(kills[i], new ArrayList<>());
                startToFinishes.get(kills[i]).add(i);
            }
        }

        // pw.println(Arrays.toString(kills));
        // pw.println(finishingTimes);
        // pw.println(startToFinishes);

        TreeSet<Integer> otherFinish = new TreeSet<>();

        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            if (i-1 >= 0) {
                if (startToFinishes.containsKey(i-1)) {
                    for (int f : startToFinishes.get(i-1)) {
                        finishingTimes.put(f, finishingTimes.get(f) - 1);
                        otherFinish.add(f);
                        if (finishingTimes.get(f) == 0) finishingTimes.remove(f);
                    }
                }
            }
            if (finishingTimes.isEmpty()) {
                if (otherFinish.isEmpty()) {
                    ans[i] = -1;
                } else {
                    ans[i] = (2*n-1) - i + 1 + otherFinish.first() - n;
                }
            } else {
                int finish = finishingTimes.firstKey();
                ans[i] = finish - i;
            }
        }

        for (int x : ans) pw.print(x + " ");
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