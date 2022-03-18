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
        int[] nk = ril(2);
        int n = nk[0];
        int k = nk[1];
        int[] counts = new int[k];
        int[] t = ril(n);
        for (int ti : t) counts[ti-1]++;

        sort(counts);

        // number of rides is between ceil(k/2)..k
        // what if we fix the number of rides and find the minimum capacity
        // where that's possible?
        
        long ans = (long) counts[k-1] * k;
        for (int numpairs = 1; numpairs <= k / 2; numpairs++) {
            int i = 0;
            int j = numpairs * 2 - 1;
            int size = counts[k-1];
            while (i < j) {
                size = Math.max(size, counts[i] + counts[j]);
                i++;
                j--;
            }
            ans = Math.min(ans, (long) size * (k - numpairs));
        }
        pw.println(ans);
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    long cost(int[] counts, int s) {
        int r = 0;
        Multiset<Integer> set = new Multiset<>();
        for (int ci : counts) set.add(ci);
        while (!set.isEmpty()) {
            int last = set.last();
            set.remove(last);
            int leftover = s - last;
            Integer buddy = set.floor(leftover);
            if (buddy != null) set.remove(buddy);
            r++;
        }
        return (long) r * s;
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