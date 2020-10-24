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
        int[] nmk = ril(3);
        int n = nmk[0];
        int m = nmk[1];
        int k = nmk[2];
        List<List<int[]>> adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            int[] xyw = ril(3);
            adj.get(xyw[0]-1).add(new int[]{xyw[1]-1, xyw[2]});
            adj.get(xyw[1]-1).add(new int[]{xyw[0]-1, xyw[2]});
        }

        int[][] paths = new int[k][];
        for (int i = 0; i < k; i++) {
            paths[i] = ril(2);
            paths[i][0]--;
            paths[i][1]--;
        }

        int[][] sp = new int[n][n];
        for (int i = 0; i < n; i++) dijkstra(sp, i, adj);

        // Try every edge (u, v)
        // New path dist is best of old path or (a, u) + (v, b)
        long ans = Long.MAX_VALUE;
        for (int u = 0; u < n; u++) {
            for (int[] vw : adj.get(u)) {
                int v = vw[0];
                long sum = 0;
                for (int[] path : paths) {
                    int a = path[0];
                    int b = path[1];
                    sum += Math.min(Math.min(sp[a][b], sp[a][u] + sp[v][b]), sp[a][v] + sp[u][b]);
                }
                ans = Math.min(ans, sum);
            }
        }
        pw.println(ans);
    }

    void dijkstra(int[][] sp, int s, List<List<int[]>> adj) {
        int n = sp.length;
        Arrays.fill(sp[s], Integer.MAX_VALUE);
        Heap heap = new Heap(n);
        for (int i = 0; i < n; i++) heap.add(i, Integer.MAX_VALUE);
        sp[s][s] = 0;
        heap.decreaseKey(s, 0);
        while (!heap.isEmpty()) {
            int u = heap.extractMin();
            for (int[] vw : adj.get(u)) {
                int v = vw[0];
                int w = vw[1];
                if (heap.containsValue(v) && sp[s][u] + w < sp[s][v]) {
                    sp[s][v] = sp[s][u] + w;
                    heap.decreaseKey(v, sp[s][v]);
                }
            }
        }
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
        pw.printf("%f", d);
    }
}

class Heap {
    int[] values;
    int[] keys;
    int[] valToIdx;
    int size;

    Heap(int n) {
        values = new int[n];
        keys = new int[n];
        valToIdx = new int[n];
        Arrays.fill(valToIdx, -1);
    }

    boolean isEmpty() { return size == 0; }
    boolean containsValue(int value) { return valToIdx[value] != -1; }
    int getKey(int value) { return keys[valToIdx[value]]; }
    int peek() { return values[0]; }

    void add(int value, int key) {
        values[size] = value;
        keys[size] = key;
        valToIdx[value] = size;
        size++;
        decreaseKey(value, key);
    }

    void decreaseKey(int value, int newKey) {
        int i = valToIdx[value];
        int pi = parent(i);
        keys[i] = newKey;
        while (i > 0 && keys[pi] > keys[i]) {
            int tempV = values[i];
            int tempK = keys[i];
            values[i] = values[pi];
            keys[i] = keys[pi];
            valToIdx[values[pi]] = i;
            values[pi] = tempV;
            keys[pi] = tempK;
            valToIdx[tempV] = pi;
            i = pi;
            pi = parent(i);
        }
    }

    int extractMin() {
        int min = values[0];
        values[0] = values[size-1];
        keys[0] = keys[size-1];
        valToIdx[values[0]] = 0;
        size--;
        valToIdx[min] = -1;
        heapify(0);
        return min;
    }

    int parent(int index) { return index == 0 ? 0 : (index - 1) >> 1; }
    int left(int index) { return (index << 1) + 1; }
    int right(int index) { return (index << 1) + 2; }
    void heapify(int i) {
        int l = left(i);
        int r = right(i);
        int smallest = i;
        if (l < size && keys[l] < keys[smallest]) smallest = l;
        if (r < size && keys[r] < keys[smallest]) smallest = r;
        if (smallest == i) return;
        int tempV = values[i];
        int tempK = keys[i];
        values[i] = values[smallest];
        keys[i] = keys[smallest];
        valToIdx[values[i]] = i;
        values[smallest] = tempV;
        keys[smallest] = tempK;
        valToIdx[tempV] = smallest;
        heapify(smallest);
    }
}