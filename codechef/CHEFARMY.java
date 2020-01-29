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

    int readInt() throws IOException {
        return Integer.parseInt(br.readLine());
    }

    long readLong() throws IOException {
        return Long.parseLong(br.readLine());
    }

    int[] readIntLine() throws IOException {
        String[] tokens = br.readLine().split(" ");
        int[] A = new int[tokens.length];
        for (int i = 0; i < A.length; i++)
            A[i] = Integer.parseInt(tokens[i]);
        return A;
    }

    long[] readLongLine() throws IOException {
        String[] tokens = br.readLine().split(" ");
        long[] A = new long[tokens.length];
        for (int i = 0; i < A.length; i++)
            A[i] = Long.parseLong(tokens[i]);
        return A;
    }

    // This suboptimal, randomized solution scores roughly 89.744 / 100
    // It's good performance is likely only due to the randomized nature of the inputs
    // for this problem i.e. I did not consider defending against adversarial inputs.
    //
    // The algorithm is as follows:
    //   repeat until there are no soldiers with money to be paid left
    //     choose the soldier with minimum money needed this today
    //     select a random maximal independent set to tag along based on subordinate restrictions
    //   the randomized procedure is repeated many times (while under time limit) and the best answer taken
    void solve() throws IOException {
        int T = readInt();
        for (int t = 0; t < T; t++) {
            int[] line = readIntLine();
            int M = line[0];
            int N = line[1];

            boolean[][] hatred = new boolean[M][N];  // hatred matrix; can also be adjacency if required
            for (int i = 0; i < M; i++) {
                String s = br.readLine();
                for (int j = 0; j < N; j++) {
                    hatred[i][j] = s.charAt(j) == '1';
                }
            }
            int[] S = readIntLine();
            int[] p = readIntLine();
            for (int i = 0; i < p.length; i++) p[i]--;  // convert to 0-indexed

            List<Set<Integer>> adj = new ArrayList<>();
            for (int i = 0; i < N; i++) adj.add(new HashSet<>());
            for (int i = 0; i < p.length; i++) {
                if (p[i] != -1) adj.get(p[i]).add(i);
            }

            time = 1;
            sTime = new int[N];
            fTime = new int[N];
            dfs(adj, 0);
            List<Set<Integer>> conflict = new ArrayList<>();
            for (int i = 0; i < N; i++) {
                Set<Integer> set = new HashSet<>();
                conflict.add(set);
                for (int j = 0; j < N; j++) {
                    if (i == j) continue;
                    if (sTime[i] < sTime[j] && fTime[i] > fTime[j]
                        || sTime[j] < sTime[i] && fTime[j] > fTime[i]) set.add(j);
                }
            }

            List<String> bestList = new ArrayList<>();
            int best = Integer.MAX_VALUE;

            for (int attempts = 0; attempts < 50; attempts++) {
                List<String> ans = new ArrayList<>();
                int cost = 0;
                int[] SS = new int[N];
                System.arraycopy(S, 0, SS, 0, N);

                Heap<Integer, Integer> soldiers = new Heap<>();
                for (int i = 0; i < N; i++) soldiers.add(i, SS[i]);
                while (!soldiers.isEmpty()) {
                    int s = soldiers.extractMin();
                    int R = SS[s];
                    if (R == 0) continue;

                    List<Integer> indSet = new ArrayList<>();
                    for (int z = 0; z < 5; z++) {
                        List<Integer> cand = getMaximalIndependentSet(s, soldiers.values(), conflict);
                        if (cand.size() > indSet.size()) indSet = cand;
                    }


                    for (Integer v : indSet) {
                        SS[v] -= R;
                        soldiers.decreaseKey(v, SS[v]);
                    }

                    int numAngry = 0;
                    for (int i = 0; i < M; i++) {
                        if (hatred[i][s]) {
                            numAngry++;
                            continue;
                        }
                        for (int x : indSet) {
                            if (hatred[i][x]) {
                                numAngry++;
                                break;
                            }
                        }
                    }
                    cost += numAngry * R;
                    if (cost >= best) break;

                    StringBuilder sb = new StringBuilder();
                    sb.append(indSet.size()+1).append(" ").append(R).append(" ").append(s+1);
                    for (Integer k : indSet) sb.append(" ").append(k+1);
                    ans.add(sb.toString());
                }
                if (cost < best) {
                    best = cost;
                    bestList = ans;
                }
            }
            pw.println(bestList.size());
            for (String s : bestList) pw.println(s);
        }
    }

    int time;
    int[] sTime;
    int[] fTime;
    void dfs(List<Set<Integer>> adj, int u) {
        sTime[u] = time++;
        for (int v : adj.get(u)) {
            if (sTime[v] == 0) dfs(adj, v);
        }
        fTime[u] = time++;
    }

    // get a maximal independent set containing s from the graph G = (V, adj)
    // NOTE: the returned list does not contain s
    List<Integer> getMaximalIndependentSet(int s, Set<Integer> V, List<Set<Integer>> adj) {
        V.remove(s);
        List<Integer> vertices = new ArrayList<>(V);
        for (Integer v : adj.get(s)) V.remove(v);
        Collections.shuffle(vertices);  // randomizes selection of IS to some extent
        // Collections.sort(vertices, (i, j) -> S[i] - S[j]);  // greedily choose the cheapest

        List<Integer> indSet = new ArrayList<>();
        for (Integer u : vertices) {
            if (!V.contains(u)) continue;
            indSet.add(u);
            for (Integer v : adj.get(u)) V.remove(v);
        }
        return indSet;
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