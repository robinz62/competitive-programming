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
        int q = ri();
        int[][] queries = new int[q][];
        int k = 0;
        for (int qi = 0; qi < q; qi++) {
            String[] tokens = br.readLine().split(" ");
            int[] line = new int[tokens.length];
            for (int i = 0; i < line.length; i++) line[i] = Integer.parseInt(tokens[i]);
            queries[qi] = line;
            if (queries[qi][0] == 1) k++;
        }
        UnionFind uf = new UnionFind(k);
        int nextIdx = 0;
        Map<Integer, Integer> componentToValue = new HashMap<>();
        Map<Integer, Integer> valueToComponent = new HashMap<>();
        for (int[] qq : queries) {
            if (qq[0] == 1) {
                int val = qq[1];
                if (valueToComponent.containsKey(val)) {
                    int u = valueToComponent.get(val);  // u is the current leader
                    uf.union(u, nextIdx);  // join them, the leader may change
                    int newLeader = uf.find(u);
                    if (newLeader != u) {
                        valueToComponent.put(val, newLeader);
                        componentToValue.remove(u);
                        componentToValue.put(newLeader, val);
                    }
                } else {
                    valueToComponent.put(val, nextIdx);
                    componentToValue.put(nextIdx, val);
                }
                nextIdx++;
            } else {
                int prevval = qq[1];
                int newval = qq[2];
                if (!valueToComponent.containsKey(prevval)) continue;
                int comp = valueToComponent.get(prevval);  // comp is the existing leader of prevval

                if (valueToComponent.containsKey(newval)) {
                    // need to merge
                    int u = valueToComponent.get(newval);  // u is the existing leader of newval
                    uf.union(u, comp);  // join them, the leader may change
                    int newLeader = uf.find(u);
                    if (newLeader != u) {
                        componentToValue.remove(u);
                        componentToValue.put(comp, newval);
                        valueToComponent.put(newval, comp);
                        valueToComponent.remove(prevval);
                    } else {
                        componentToValue.remove(comp);
                        componentToValue.put(u, newval);
                        valueToComponent.remove(prevval);
                        valueToComponent.put(newval, u);
                    }
                } else {
                    componentToValue.put(comp, newval);
                    valueToComponent.put(newval, comp);
                    valueToComponent.remove(prevval);
                }
            }

            // pw.println(valueToComponent + " " + componentToValue);
        }

        for (int i = 0; i < k; i++) {
            int leader = uf.find(i);
            int val = componentToValue.get(leader);
            pw.print(val + " ");
        }
        pw.println();
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