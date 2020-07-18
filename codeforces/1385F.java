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
        int t = ri();
        for (int ti = 0; ti < t; ti++) {
            int[] nk = ril(2);
            int n = nk[0];
            int k = nk[1];
            List<TreeSet<Integer>> nodeToLeaves = new ArrayList<>(n);
            List<Set<Integer>> adj = new ArrayList<>(n);
            for (int i = 0; i < n; i++) {
                adj.add(new HashSet<>());
                nodeToLeaves.add(new TreeSet<>());
            }
            for (int i = 0; i < n - 1; i++) {
                int[] xy = ril(2);
                adj.get(xy[0]-1).add(xy[1]-1);
                adj.get(xy[1]-1).add(xy[0]-1);
            }
            for (int u = 0; u < n; u++) {
                if (adj.get(u).size() == 1) {
                    int v = adj.get(u).iterator().next();
                    nodeToLeaves.get(v).add(u);
                }
            }
            Heap<Integer, Integer> heap = new Heap<>(Collections.reverseOrder());
            for (int i = 0; i < n; i++) heap.add(i, nodeToLeaves.get(i).size());
            int ans = 0;
            while (!heap.isEmpty() && nodeToLeaves.get(heap.peek()).size() >= k) {
                int u = heap.extractMin();
                TreeSet<Integer> l = nodeToLeaves.get(u);
                for (int i = 0; i < k; i++) {
                    int v = l.pollFirst();
                    adj.get(u).remove(v);
                    adj.get(v).remove(u);
                    nodeToLeaves.get(v).remove(u);
                }
                heap.add(u, l.size());
                if (adj.get(u).size() == 1) {
                    int v = adj.get(u).iterator().next();
                    nodeToLeaves.get(v).add(u);
                    heap.decreaseKey(v, nodeToLeaves.get(v).size());
                }
                ans++;
            }
            pw.println(ans);
        }
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
        public String toString() {
            return value + " " + key;
        }
    }
}