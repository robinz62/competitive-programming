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
            int[] nm = ril(2);
            int n = nm[0];
            int m = nm[1];
            int[][] a = new int[n][];
            for (int i = 0; i < n; i++) a[i] = ril(m);

            int idxMin = 0;  // blue
            int idxMax = 0;  // red

            for (int i = 0; i < n; i++) {
                if (a[i][0] < a[idxMin][0]) idxMin = i;
                if (a[i][0] > a[idxMax][0]) idxMax = i;
            }
            if (idxMin == idxMax) {
                pw.println("NO");
                continue;
            }

            boolean ok = true;

            int p = 0;  // [0..p] and [p+1..m-1].
            for (int j = 0; ok && j < m-1; j++) {
                if (a[idxMin][j] == a[idxMax][j]) ok = false;
                else if (a[idxMin][j] > a[idxMax][j]) break;
                p = j;
            }
            if (!ok) {
                pw.println("NO");
                continue;
            }

            // For the left matrix, what is the min and max in each row. [idx, min, max].
            int[][] leftRows = new int[n][3];
            for (int i = 0; i < n; i++) {
                leftRows[i][0] = i;
                leftRows[i][1] = Integer.MAX_VALUE;
                leftRows[i][2] = Integer.MIN_VALUE;
            }
            for (int i = 0; i < n; i++) {
                for (int j = 0; j <= p; j++) {
                    leftRows[i][1] = Math.min(leftRows[i][1], a[i][j]);
                    leftRows[i][2] = Math.max(leftRows[i][2], a[i][j]);
                }
            }
            Arrays.sort(leftRows, (r1, r2) -> Integer.compare(r1[1], r2[1]));
            UnionFind ufLeft = new UnionFind(n);
            int f = leftRows[0][2];
            for (int i = 1; i < n; i++) {
                if (leftRows[i][1] <= f) {
                    ufLeft.union(leftRows[i][0], leftRows[i-1][0]);
                    f = Math.max(f, leftRows[i][2]);
                } else {
                    f = leftRows[i][2];
                }
            }
            if (ufLeft.getNumComponents() == 1) {
                pw.println("NO");
                continue;
            }

            // ufLeft now contains information to identify disjoint intervals.
            // map from component number to [min, max]
            Map<Integer, int[]> map = new HashMap<>();
            Map<Integer, List<Integer>> componentToRow = new HashMap<>();
            for (int i = 0; i < n; i++) {
                int c = ufLeft.find(leftRows[i][0]);
                if (!componentToRow.containsKey(c)) componentToRow.put(c, new ArrayList<>());
                componentToRow.get(c).add(leftRows[i][0]);
                if (!map.containsKey(c)) map.put(c, new int[]{c, Integer.MAX_VALUE, Integer.MIN_VALUE});
                int[] here = map.get(c);
                here[1] = Math.min(here[1], leftRows[i][1]);
                here[2] = Math.max(here[2], leftRows[i][2]);
            }
            List<int[]> intervals = new ArrayList<>(map.values());
            Collections.sort(intervals, (i1, i2) -> Integer.compare(i1[1], i2[1]));

            // Detour: compute right min/max
            int[][] rightRows = new int[n][3];
            for (int i = 0; i < n; i++) {
                rightRows[i][0] = i;
                rightRows[i][1] = Integer.MAX_VALUE;
                rightRows[i][2] = Integer.MIN_VALUE;
            }
            for (int i = 0; i < n; i++) {
                for (int j = p+1; j < m; j++) {
                    rightRows[i][1] = Math.min(rightRows[i][1], a[i][j]);
                    rightRows[i][2] = Math.max(rightRows[i][2], a[i][j]);
                }
            }

            // We can now brute force every max value (besides last) as upper bound for blues on left
            // Start off with everything red
            // Recall: on the right, we want blues bigger than reds
            char[] color = new char[n];
            Arrays.fill(color, 'R');
            Multiset<Integer> rightRed = new Multiset<>();
            Multiset<Integer> rightBlu = new Multiset<>();
            for (int[] row : rightRows) {
                rightRed.add(row[1]);
                rightRed.add(row[2]);
            }
            boolean found = false;
            for (int x = 0; !found && x < intervals.size() - 1; x++) {
                int[] here = intervals.get(x);
                for (int i : componentToRow.get(here[0])) {
                    color[i] = 'B';
                    rightRed.remove(rightRows[i][1]);
                    rightRed.remove(rightRows[i][2]);
                    rightBlu.add(rightRows[i][1]);
                    rightBlu.add(rightRows[i][2]);
                }
                if (rightRed.last() < rightBlu.first()) {
                    found = true;
                    pw.println("YES");
                    pw.println(new String(color) + " " + (p+1));
                }
            }
            if (!found) pw.println("NO");
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

class UnionFind {
    int n;
    int numComponents;
    int[] parent;
    int[] rank;

    UnionFind(int n) {
        this.n = numComponents = n;
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;
    }

    void union(int u, int v) {
        int x = find(u);
        int y = find(v);
        if (x == y) return;
        if (rank[x] < rank[y]) parent[x] = y;
        else if (rank[x] > rank[y]) parent[y] = x;
        else {
            parent[x] = y;
            rank[y]++;
        }
        numComponents--;
    }

    int find(int u) {
        int current = u;
        List<Integer> toUpdate = new ArrayList<>();
        while (parent[current] != current) {
            toUpdate.add(current);
            current = parent[current];
        }
        for (Integer node : toUpdate) parent[node] = current;
        return current;
    }

    int getNumComponents() {
        return numComponents;
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