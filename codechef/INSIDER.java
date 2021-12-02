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
            int[] a = ril(n);

            // Convert to only local min/max
            List<Integer> dedup = new ArrayList<>(n);
            dedup.add(a[0]);
            for (int i = 1; i < n; i++) {
                if (a[i] != a[i-1]) dedup.add(a[i]);
            }
            List<Integer> critical = new ArrayList<>(dedup.size());
            critical.add(dedup.get(0));
            for (int i = 1; i < dedup.size() - 1; i++) {
                if (dedup.get(i) > dedup.get(i-1) && dedup.get(i) > dedup.get(i+1)
                || dedup.get(i) < dedup.get(i-1) && dedup.get(i) < dedup.get(i+1)) critical.add(dedup.get(i));
            }
            critical.add(dedup.get(dedup.size() - 1));

            int sz = critical.size();
            TreeMap<Integer, Integer> delta = new TreeMap<>();
            for (int i = 0; i < sz-1; i++) {
                int curr = critical.get(i);
                int next = critical.get(i+1);
                int min = Math.min(curr, next);
                int max = Math.max(curr, next);
                min++;
                max--;
                if (min <= max) {
                    delta.put(min, delta.getOrDefault(min, 0) + 1);
                    delta.put(max+1, delta.getOrDefault(max+1, 0) - 1);
                }
            }

            int curr = 0;
            TreeMap<Integer, Integer> valToCount = new TreeMap<>();
            for (Map.Entry<Integer, Integer> e : delta.entrySet()) {
                int val = e.getKey();
                int d = e.getValue();
                curr += d;
                valToCount.put(val, curr);

                Integer next = delta.higherKey(val);
                if (next != null && next-1 != val) {
                    valToCount.put(next-1, curr);
                }
            }

            int[] m = new int[n+1];
            int[] M = new int[n+1];
            Arrays.fill(m, Integer.MAX_VALUE);
            Arrays.fill(M, Integer.MIN_VALUE);
            for (Map.Entry<Integer, Integer> e : valToCount.entrySet()) {
                int val = e.getKey();
                int length = e.getValue() + 1;
                m[length] = Math.min(m[length], val);
                M[length] = Math.max(M[length], val);
            }

            int min = m[n];
            for (int i = n-1; i >= 2; i--) {
                min = Math.min(min, m[i]);
                m[i] = min;
            }
            for (int i = 2; i <= n; i++) if (m[i] == Integer.MAX_VALUE) m[i] = -1;

            int max = M[n];
            for (int i = n-1; i >= 2; i--) {
                max = Math.max(max, M[i]);
                M[i] = max;
            }
            for (int i = 2; i <= n; i++) if (M[i] == Integer.MIN_VALUE) M[i] = -1;

            for (int i = 2; i <= n; i++) pw.print(m[i] + " ");
            pw.println();
            for (int i = 2; i <= n; i++) pw.print(M[i] + " ");
            pw.println();
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

class Multiset<T extends Comparable<T>> {
    private TreeMap<T, Integer> map = new TreeMap<>();
    private int size;

    Multiset() {}
    Multiset(Comparator<T> comp) { map = new TreeMap<>(comp); }
    boolean isEmpty() { return size == 0; }
    int size() { return size; }
    int count(T t) { return map.getOrDefault(t, 0); }
    boolean contains(T t) { return map.containsKey(t); }
    void add(T t) { add(t, 1); }
    void add(T t, int count) { map.put(t, map.getOrDefault(t, 0) + count); size += count; }
    void remove(T t) { remove(t, 1); }
    void remove(T t, int count) {
        int curr = map.get(t);
        if (count > curr) throw new RuntimeException();
        if (curr == count) map.remove(t);
        else map.put(t, curr - count);
        size -= count;
    }
    T first() { return map.firstKey(); };
    T last() { return map.lastKey(); };
    T lower(T t) { return map.lowerKey(t); }
    T higher(T t) { return map.higherKey(t); }
    T floor(T t) { return map.floorKey(t); }
    T ceiling(T t) { return map.ceilingKey(t); }
    Set<Map.Entry<T, Integer>> entrySet() { return map.entrySet(); }
}