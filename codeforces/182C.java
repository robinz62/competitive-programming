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
        int[] nl = ril(2);
        int n = nl[0];
        int len = nl[1];
        int[] a = ril(n);
        int k = ri();

        
        Multiset<Integer> pos = new Multiset<>();
        Multiset<Integer> neg = new Multiset<>();
        long sum = 0;
        for (int i = 0; i < len; i++) {
            sum += a[i];
            if (a[i] >= 0) pos.add(a[i]);
            else neg.add(a[i]);
        }
        long deltaForNeg = 0;  // convert positives to negative. this is positive
        long deltaForPos = 0;  // convert negatives to positive. this is negative
        Multiset<Integer> incl1 = new Multiset<>();
        Multiset<Integer> incl2 = new Multiset<>();
        List<Map.Entry<Integer, Integer>> backwards = new ArrayList<>(pos.entrySet());
        Collections.reverse(backwards);
        for (var e : backwards) {
            int val = e.getKey();
            int c = e.getValue();
            int take = Math.min(c, k - incl1.size());
            if (take == 0) break;
            deltaForNeg += (long) val * take;
            incl1.add(val, take);
        }
        for (var e : neg.entrySet()) {
            int val = e.getKey();
            int c = e.getValue();
            int take = Math.min(c, k - incl2.size());
            if (take == 0) break;
            deltaForPos += (long) val * take;
            incl2.add(val, take);
        }

        long ans = Math.max(Math.abs(sum - deltaForNeg * 2), Math.abs(sum - deltaForPos * 2));
        ans = Math.max(ans, Math.abs(sum));
        for (int i = len; i < n; i++) {
            // Get rid of old
            int old = a[i - len];
            sum -= old;
            if (old >= 0) pos.remove(old);
            else neg.remove(old);
            if (incl1.count(old) == pos.count(old) + 1) {
                deltaForNeg -= old;
                incl1.remove(old);
            } else if (incl2.count(old) == neg.count(old) + 1) {
                deltaForPos -= old;
                incl2.remove(old);
            }

            sum += a[i];
            if (a[i] >= 0) {
                // If necessary to remove gaps, perform an eviction
                if (!incl1.isEmpty()) {
                    int fst = incl1.first();
                    if (a[i] > fst) {
                        incl1.remove(fst);
                        deltaForNeg -= fst;

                        incl1.add(a[i]);
                        deltaForNeg += a[i];
                    }
                }
                pos.add(a[i]);
            } else {
                if (!incl2.isEmpty()) {
                    int lst = incl2.last();
                    if (a[i] < lst) {
                        incl2.remove(lst);
                        deltaForPos -= lst;

                        incl2.add(a[i]);
                        deltaForPos += a[i];
                    }
                }
                neg.add(a[i]);
            }

            // Add if available.
            while (incl1.size() < k) {
                if (incl1.isEmpty()) {
                    if (pos.isEmpty()) break;
                    int max = pos.last();
                    incl1.add(max);
                    deltaForNeg += max;
                } else {
                    int fst = incl1.first();
                    Integer lower = pos.lower(fst);
                    if (pos.count(fst) > incl1.count(fst)) {
                        incl1.add(fst);
                        deltaForNeg += fst;
                    } else if (lower != null) {
                        incl1.add(lower);
                        deltaForNeg += lower;
                    } else break;
                }
            }

            while (incl2.size() < k) {
                if (incl2.isEmpty()) {
                    if (neg.isEmpty()) break;
                    int min = neg.first();
                    incl2.add(min);
                    deltaForPos += min;
                } else {
                    int lst = incl2.last();
                    Integer higher = neg.higher(lst);
                    if (neg.count(lst) > incl2.count(lst)) {
                        incl2.add(lst);
                        deltaForPos += lst;
                    } else if (higher != null) {
                        incl2.add(higher);
                        deltaForPos += higher;
                    } else break;
                }
            }

            ans = Math.max(ans, Math.max(Math.abs(sum - deltaForNeg * 2), Math.abs(sum - deltaForPos * 2)));
            ans = Math.max(ans, Math.abs(sum));
        }
        pw.println(ans);
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