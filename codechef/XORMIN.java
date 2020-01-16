import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Main m = new Main();
        m.solve();
    }

    int MAXN = 10000000;

    List<List<Integer>> adjList;

    int[] w;        // w[i] is weight of vertex i
    int[] roots;    // roots[i] is root trienode for vertex i

    int[] L = new int[MAXN];    // L[i] is left child of trienode i
    int[] R = new int[MAXN];    // R[i] is right child of trienode i
    int[] min = new int[MAXN];  // min[i] is the minimum vertex number at trienode i
    int counter;

    public void solve() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        
        int T = Integer.parseInt(br.readLine());
        for (int t = 0; t < T; t++) {
            String[] line = br.readLine().split(" ");
            int N = Integer.parseInt(line[0]);
            int Q = Integer.parseInt(line[1]);
            w = new int[N + 1];
            roots = new int[N + 1];
            counter = 0;
            line = br.readLine().split(" ");
            adjList = new ArrayList<>(N + 2);
            adjList.add(null);
            for (int i = 1; i <= N; i++) {
                w[i] = Integer.parseInt(line[i - 1]);
                adjList.add(new ArrayList<>());
            }
            for (int i = 1; i <= N - 1; i++) {
                line = br.readLine().split(" ");
                int x = Integer.parseInt(line[0]);
                int y = Integer.parseInt(line[1]);
                adjList.get(x).add(y);
                adjList.get(y).add(x);
            }

            dfs(1, -1);

            int prevVal = 0;
            int prevVertex = 0;
            for (int q = 1; q <= Q; q++) {
                line = br.readLine().split(" ");
                int a = Integer.parseInt(line[0]);
                int b = Integer.parseInt(line[1]);
                int v = a ^ prevVertex;
                int k = b ^ prevVal;

                int curr = roots[v];
                for (int mask = 1 << 19; mask != 0; mask >>= 1) {
                    int digit = (k & mask) == 0 ? 0 : 1;
                    if ((digit == 1 && L[curr] != -1) || R[curr] == -1) {
                        curr = L[curr];
                    } else {
                        curr = R[curr];
                    }
                }

                prevVertex = min[curr];
                prevVal = w[prevVertex] ^ k;
                pw.print(prevVertex + " " + prevVal + "\n");
            }
        }
        pw.flush();

        br.close();
        pw.close();
    }

    int makeNode() {
        int v = counter++;
        L[v] = R[v] = min[v] = -1;
        return v;
    }

    void dfs(int root, int p) {
        int node = makeNode();
        int weight = w[root];
        int curr = node;
        for (int mask = 1 << 19; mask != 0; mask >>= 1) {
            int digit = (weight & mask) == 0 ? 0 : 1;
            if (digit == 0) {
                L[curr] = makeNode();
                curr = L[curr];
            } else {
                R[curr] = makeNode();
                curr = R[curr];
            }
        }
        min[curr] = root;

        for (int child : adjList.get(root)) {
            if (child == p) continue;
            dfs(child, root);
            node = merge(node, roots[child]);
        }

        roots[root] = node;
    }

    int merge(int a, int b) {
        if (a == -1 && b == -1) return -1;
        if (a == -1) return b;
        if (b == -1) return a;
        if (min[a] != -1) {
            if (min[a] < min[b]) return a;
            return b;
        }
        int newNode = makeNode();
        L[newNode] = merge(L[a], L[b]);
        R[newNode] = merge(R[a], R[b]);
        return newNode;
    }
}