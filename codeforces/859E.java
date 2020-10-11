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
        int n = ri();
        int[] adj = new int[2*n];
        Arrays.fill(adj, -1);
        List<List<Integer>> inAdj = new ArrayList<>(2*n);
        for (int i = 0; i < 2*n; i++) inAdj.add(new ArrayList<>());
        for (int i = 0; i < n; i++) {
            int[] uv = ril(2);
            int curr = uv[0]-1;
            int want = uv[1]-1;
            adj[curr] = want;
            inAdj.get(want).add(curr);
        }

        long ans = 1;

        // Get cycles count
        int numCycles = 0;
        int[] visited = new int[2*n];
        Arrays.fill(visited, -1);
        for (int i = 0; i < 2*n; i++) {
            if (visited[i] != -1) continue;
            int curr = i;
            visited[curr] = i;
            if (adj[curr] == curr) continue;
            while (true) {
                if (adj[curr] == -1 || adj[curr] == curr) break;
                curr = adj[curr];
                if (visited[curr] == i) {
                    numCycles++;
                    break;
                }
                if (visited[curr] != -1) break;
                visited[curr] = i;
            }
        }
        for (int i = 0; i < numCycles; i++) ans = ans * 2 % MOD;

        // Handle DAGs
        for (int i = 0; i < 2*n; i++) {
            // find outdeg = 0 nodes
            if (adj[i] == -1 && !inAdj.get(i).isEmpty()) {
                long x = helper(inAdj, i);
                ans = ans * x % MOD;
            }
        }
        pw.println(ans);
    }

    long helper(List<List<Integer>> inAdj, int u) {
        long ans = 1;
        for (int v : inAdj.get(u)) {
            ans += helper(inAdj, v);
        }
        return ans;
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