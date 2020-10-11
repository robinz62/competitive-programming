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
        int n = ri();
        int[] a = ril(n);
        List<Integer> list = new ArrayList<>(n);
        for (int ai : a) list.add(ai);
        Collections.sort(list);
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            map.put(list.get(i), i);
        }
        Map<Integer, Integer> coordToIndex = new HashMap<>();
        for (int i = 0; i < n; i++) {
            a[i] = map.get(a[i]);
            coordToIndex.put(a[i], i);
        }
        // coords are now 0..n-1

        int ans = 0;
        int numOfAns = -1;
        TreeMap<Integer, Integer> intervals = new TreeMap<>();  // intervals of staying still. begin->end
        Map<Integer, Integer> sizeToCount = new HashMap<>();    // frequency of each interval size

        for (int k = 0; k <= n-1; k++) {
            int idx = coordToIndex.get(k);
            if (intervals.isEmpty()) {
                intervals.put(idx, idx);
                sizeToCount.put(1, sizeToCount.getOrDefault(1, 0) + 1);
            } else {
                Integer left = intervals.floorKey(idx);
                if (left == null || intervals.get(left) != idx-1) {
                    if (intervals.ceilingKey(idx) != null && intervals.ceilingKey(idx) == idx+1) {
                        int r = intervals.get(idx+1);
                        intervals.remove(idx+1);
                        int prevSize = r - (idx+1) + 1;
                        if (sizeToCount.get(prevSize) == 1) sizeToCount.remove(prevSize);
                        else sizeToCount.put(prevSize, sizeToCount.get(prevSize) - 1);
                        intervals.put(idx, r);
                        sizeToCount.put(prevSize+1, sizeToCount.getOrDefault(prevSize+1, 0) + 1);
                    } else {
                        intervals.put(idx, idx);
                        sizeToCount.put(1, sizeToCount.getOrDefault(1, 0) + 1);
                    }
                } else {
                    if (intervals.ceilingKey(idx) != null && intervals.ceilingKey(idx) == idx+1) {
                        int prevLSize = intervals.get(left) - left + 1;
                        int prevRSize = intervals.get(idx+1) - (idx+1) + 1;
                        int r = intervals.get(idx+1);
                        intervals.remove(left);
                        intervals.remove(idx+1);
                        if (sizeToCount.get(prevLSize) == 1) sizeToCount.remove(prevLSize);
                        else sizeToCount.put(prevLSize, sizeToCount.get(prevLSize) - 1);
                        if (sizeToCount.get(prevRSize) == 1) sizeToCount.remove(prevRSize);
                        else sizeToCount.put(prevRSize, sizeToCount.get(prevRSize) - 1);
                        intervals.put(left, r);
                        int size = prevLSize + prevRSize + 1;
                        sizeToCount.put(size, sizeToCount.getOrDefault(size, 0) + 1);
                    } else {
                        int prevSize = intervals.get(left) - left + 1;
                        if (sizeToCount.get(prevSize) == 1) sizeToCount.remove(prevSize);
                        else sizeToCount.put(prevSize, sizeToCount.get(prevSize) - 1);
                        intervals.put(left, idx);
                        sizeToCount.put(prevSize+1, sizeToCount.getOrDefault(prevSize+1, 0) + 1);
                    }
                }
            }
            int sz = sizeToCount.keySet().iterator().next();
            if (sizeToCount.size() == 1 && sizeToCount.get(sz) > numOfAns) {
                ans = k;
                numOfAns = sizeToCount.get(sz);
            }
        }
        pw.println(list.get(ans)+1);
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
}