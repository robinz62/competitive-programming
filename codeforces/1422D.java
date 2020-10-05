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
        int[] nm = ril(2);
        int n = nm[0];
        int m = nm[1];
        long[] sxyfxy = rll(4);
        long sx = sxyfxy[0];
        long sy = sxyfxy[1];
        long fx = sxyfxy[2];
        long fy = sxyfxy[3];

        Map<Point, Map<Point, Long>> adj = new HashMap<>();
        Point src = new Point(sx, sy);
        Point tgt = new Point(fx, fy);
        adj.put(src, new HashMap<>());
        adj.put(tgt, new HashMap<>());
        adj.get(src).put(tgt, Math.abs(sx-fx) + Math.abs(sy-fy));

        List<Point> insta = new ArrayList<>(m);
        for (int i = 0; i < m; i++) {
            long[] xy = rll(2);
            Point p = new Point(xy[0], xy[1]);
            adj.put(p, new HashMap<>());
            insta.add(p);

            adj.get(src).put(p, Math.min(Math.abs(sx-p.x), Math.abs(sy-p.y)));
            adj.get(p).put(tgt, Math.abs(fx-p.x) + Math.abs(fy-p.y));
        }

        // Sort by x, link nearest left/right
        Collections.sort(insta, (p1, p2) -> Long.compare(p1.x, p2.x));
        for (int i = 0; i < m-1; i++) {
            Point p1 = insta.get(i);
            Point p2 = insta.get(i+1);
            long dist = Math.min(Math.abs(p1.x-p2.x), Math.abs(p1.y-p2.y));
            adj.get(p1).put(p2, dist);
            adj.get(p2).put(p1, dist);
        }

        // Sort by y, link nearest up/down
        Collections.sort(insta, (p1, p2) -> Long.compare(p1.y, p2.y));
        for (int i = 0; i < m-1; i++) {
            Point p1 = insta.get(i);
            Point p2 = insta.get(i+1);
            long dist = Math.min(Math.abs(p1.x-p2.x), Math.abs(p1.y-p2.y));
            adj.get(p1).put(p2, dist);
            adj.get(p2).put(p1, dist);
        }

        // Run Dijkstra
        Map<Point, Long> dist = new HashMap<>();
        Heap<Point, Long> heap = new Heap<>();
        heap.add(src, 0l);
        dist.put(src, 0l);
        heap.add(tgt, Long.MAX_VALUE);
        dist.put(tgt, Long.MAX_VALUE);
        for (Point p : insta) {
            heap.add(p, Long.MAX_VALUE);
            dist.put(p, Long.MAX_VALUE);
        }
        while (!heap.isEmpty()) {
            Point p = heap.extractMin();
            long curr = dist.get(p);
            if (curr == Long.MAX_VALUE) {
                pw.println("fatal error");
                return;
            }
            for (Map.Entry<Point, Long> qd : adj.get(p).entrySet()) {
                long estimate = curr + qd.getValue();
                if (estimate < dist.get(qd.getKey())) {
                    dist.put(qd.getKey(), estimate);
                    heap.decreaseKey(qd.getKey(), estimate);
                }
            }
        }
        pw.println(dist.get(tgt));
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

class Point {
    long x;
    long y;
    Point(long x, long y) {
        this.x = x;
        this.y = y;
    }
}

class Heap<V, K> {
    List<Entry> heap = new ArrayList<>();
    Map<V, Integer> indexOfValue = new HashMap<>();
    Comparator<K> comparator;

    Heap() {}
    Heap(Comparator<K> comparator) {
        this.comparator = comparator;
    }

    int size() { return heap.size(); }
    boolean isEmpty() { return heap.isEmpty(); }
    boolean containsValue(V value) { return indexOfValue.containsKey(value); }
    K getKey(V value) { return heap.get(indexOfValue.get(value)).key; }
    V peek() { return heap.get(0).value; }

    void add(V value, K key) {
        heap.add(new Entry(value, key));
        indexOfValue.put(value, heap.size() - 1);
        decreaseKey(value, key);
    }

    void decreaseKey(V value, K newKey) {
        int i = indexOfValue.get(value);
        heap.get(i).key = newKey;
        while (i > 0 && compare(heap.get(parent(i)).key, heap.get(i).key) > 0) {
            Entry temp = heap.get(i);
            heap.set(i, heap.get(parent(i)));
            indexOfValue.put(heap.get(parent(i)).value, i);
            heap.set(parent(i), temp);
            indexOfValue.put(temp.value, parent(i));
            i = parent(i);
        }
    }

    V extractMin() {
        V min = heap.get(0).value;
        heap.set(0, heap.get(heap.size() - 1));
        indexOfValue.put(heap.get(0).value, 0);
        heap.remove(heap.size() - 1);
        indexOfValue.remove(min);
        heapify(0);
        return min;
    }

    Set<V> values() {
        Set<V> values = new HashSet<V>();
        for (Entry e : heap) values.add(e.value);
        return values;
    }

    int parent(int index) { return index == 0 ? 0 : (index - 1) >> 1; }
    int left(int index) { return (index << 1) + 1; }
    int right(int index) { return (index << 1) + 2; }
    void heapify(int i) {
        int l = left(i);
        int r = right(i);
        int smallest = i;
        if (l < size() && compare(heap.get(l).key, heap.get(i).key) < 0) {
            smallest = l;
        }
        if (r < size() && compare(heap.get(r).key, heap.get(smallest).key) < 0) {
            smallest = r;
        }
        if (smallest == i) {
            return;
        }
        Entry temp = heap.get(i);
        heap.set(i, heap.get(smallest));
        indexOfValue.put(heap.get(smallest).value, i);
        heap.set(smallest, temp);
        indexOfValue.put(temp.value, smallest);
        heapify(smallest);
    }

    @SuppressWarnings("unchecked")
	int compare(K a, K b) {
        return comparator == null
            ? ((Comparable<K>) a).compareTo(b)
            : comparator.compare(a, b);
    }

    class Entry {
        V value;
        K key;
        Entry(V v, K k) {
            value = v;
            key = k;
        }
    }
}