import java.io.*;
import java.util.*;
 
public class Main {
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

    int readInt() throws IOException {
        return Integer.parseInt(br.readLine());
    }
 
    int[] readIntLine() throws IOException {
        String[] tokens = br.readLine().split(" ");
        int[] A = new int[tokens.length];
        for (int i = 0; i < A.length; i++) A[i] = Integer.parseInt(tokens[i]);
        return A;
    }
 
    long[] readLongLine() throws IOException {
        String[] tokens = br.readLine().split(" ");
        long[] A = new long[tokens.length];
        for (int i = 0; i < A.length; i++) A[i] = Long.parseLong(tokens[i]);
        return A;
    }
 
    void solve() throws IOException {
        int T = readInt();
        for (int t = 0; t < T; t++) {
            int N = readInt();
            TrieNode root = new TrieNode();
            for (int i = 0; i < N; i++) {
                root.add(br.readLine());
            }

            long ans = 0;
            Heap<TrieNode, Integer> heap = new Heap<>();
            dfs(root, heap);
            while (!heap.isEmpty()) {
                TrieNode deepest = heap.extractMin();
                if (deepest.count < 2) continue;
                Integer fst = null;
                Integer snd = null;
                int depth = deepest.depth;
                for (int x : deepest.children.keySet()) {
                    if (fst == null) fst = x;
                    else if (snd == null) snd = x;
                    else break;
                }
                deepest.children.remove(fst);
                deepest.children.remove(snd);
                deepest.count -= 2;
                if (deepest.count >= 2) {
                    heap.add(deepest, -deepest.depth);
                }
                while (deepest.parent != null) {
                    deepest.parent.count -= 2;
                    deepest.parent.children.remove(deepest.key);
                    deepest = deepest.parent;
                }
                ans += (long) depth * depth;
            }
            pw.println(ans);
        }
    }

    void dfs(TrieNode node, Heap<TrieNode, Integer> heap) {
        if (node.count >= 2) {
            heap.add(node, -node.depth);  // hax
        }
        for (TrieNode child : node.children.values()) {
            dfs(child, heap);
        }
    }

}

// borg creates nodes (bg) -> (or) -> (ro) -> (gb)
class TrieNode {
    Map<Integer, TrieNode> children;
    int count;

    int key;
    TrieNode parent;
    int depth;
    
    public TrieNode() {
        children = new HashMap<>();
    }

    public void add(String s) {
        TrieNode curr = this;
        curr.count++;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            char d = s.charAt(s.length() - 1 - i);
            int key = (c - 'a') * 26 + (d - 'a');
            if (!curr.children.containsKey(key)) curr.children.put(key, new TrieNode());
            curr.children.get(key).parent = curr;
            curr.children.get(key).depth = curr.depth + 1;
            curr = curr.children.get(key);

            curr.count++;
            curr.key = key;
        }
    }
}

class Heap<V, K> {
    private List<Entry> heap;
    private Map<V, Integer> indexOfValue;
    private Comparator<K> comparator;

    public Heap() {
        heap = new ArrayList<>();
        indexOfValue = new HashMap<>();
    }

    public Heap(Comparator<K> comparator) {
        this();
        this.comparator = comparator;
    }

    public int size() {
        return heap.size();
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public boolean containsValue(V value) {
        return indexOfValue.containsKey(value);
    }

    public void add(V value, K key) {
        heap.add(new Entry(value, key));
        indexOfValue.put(value, heap.size() - 1);
        decreaseKey(value, key);
    }

    public void decreaseKey(V value, K newKey) {
        int i = indexOfValue.get(value);
        if (compare(newKey, heap.get(i).getKey()) > 0) {
            throw new IllegalArgumentException("New key must be smaller than previous key");
        }
        heap.get(i).setKey(newKey);
        while (i > 0 && compare(heap.get(parent(i)).getKey(), heap.get(i).getKey()) > 0) {
            Entry temp = heap.get(i);
            heap.set(i, heap.get(parent(i)));
            indexOfValue.put(heap.get(parent(i)).getValue(), i);
            heap.set(parent(i), temp);
            indexOfValue.put(temp.getValue(), parent(i));
            i = parent(i);
        }
    }

    public V peek() {
        return heap.get(0).getValue();
    }

    public V extractMin() {
        V min = heap.get(0).getValue();
        heap.set(0, heap.get(heap.size() - 1));
        indexOfValue.put(heap.get(0).getValue(), 0);
        heap.remove(heap.size() - 1);
        indexOfValue.remove(min);
        heapify(0);
        return min;
    }

    public Set<V> values() {
        Set<V> values = new HashSet<V>();
        for (Entry e : heap) values.add(e.getValue());
        return values;
    }

    private int parent(int index) {
        return index == 0 ? 0 : (index - 1) >> 1;
    }

    private int left(int index) {
        return (index << 1) + 1;
    }

    private int right(int index) {
        return (index << 1) + 2;
    }

    private void heapify(int i) {
        int l = left(i);
        int r = right(i);
        int smallest = i;
        if (l < size() && compare(heap.get(l).getKey(), heap.get(i).getKey()) < 0) {
            smallest = l;
        }
        if (r < size() && compare(heap.get(r).getKey(), heap.get(smallest).getKey()) < 0) {
            smallest = r;
        }
        if (smallest == i) {
            return;
        }
        Entry temp = heap.get(i);
        heap.set(i, heap.get(smallest));
        indexOfValue.put(heap.get(smallest).getValue(), i);
        heap.set(smallest, temp);
        indexOfValue.put(temp.getValue(), smallest);
        heapify(smallest);
    }

    @SuppressWarnings("unchecked")
	private int compare(K a, K b) {
        return comparator == null
            ? ((Comparable<K>) a).compareTo(b)
            : comparator.compare(a, b);
    }

    class Entry {
        private V value;
        private K key;

        public Entry(V v, K k) {
            value = v;
            key = k;
        }

        public V getValue() {
            return value;
        }

        public K getKey() {
            return key;
        }

        public K setKey(K k) {
            K old = key;
            key = k;
            return old;
        }
    }
}