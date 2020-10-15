import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static int MOD = 1000000009;

    // After writing solution, quick scan for:
    //   array out of bounds
    //   special cases e.g. n=1?
    //
    // Big numbers arithmetic bugs:
    //   int overflow
    //   sorting, or taking max, after MOD
    void solve() throws IOException {
        int m = ri();
        Map<Integer, Map<Integer, Integer>> cubes = new HashMap<>();  // y -> x -> id
        int[][] input = new int[m][];
        for (int i = 0; i < m; i++) {
            int[] xy = ril(2);
            input[i] = xy;
            int x = xy[0];
            int y = xy[1];
            if (!cubes.containsKey(y)) cubes.put(y, new HashMap<>());
            cubes.get(y).put(x, i);
        }

        // edge u->v means u is supporting v
        List<Set<Integer>> adj = new ArrayList<>(m);
        List<Set<Integer>> alt = new ArrayList<>(m);
        for (int i = 0; i < m; i++) adj.add(new HashSet<>());
        for (int i = 0; i < m; i++) alt.add(new HashSet<>());
        for (int y : cubes.keySet()) {
            for (int x : cubes.get(y).keySet()) {
                int u = cubes.get(y).get(x);
                for (int xx = x-1; xx <= x+1; xx++) {
                    if (cubes.containsKey(y-1) && cubes.get(y-1).containsKey(xx)) {
                        int v = cubes.get(y-1).get(xx);
                        adj.get(v).add(u);
                        alt.get(u).add(v);
                    }
                }
            }
        }

        // can remove block u if, for all v i'm pointing to, indeg[v] > 1 OR not pointing to anyone
        int[] indeg = new int[m];
        for (int i = 0; i < m; i++)
            for (int j : adj.get(i))
                indeg[j]++;

        List<Set<Integer>> critical = new ArrayList<>();
        for (int i = 0; i < m; i++) critical.add(new HashSet<>());
        for (int i = 0; i < m; i++) for (int j : adj.get(i)) if (indeg[j] == 1) critical.get(i).add(j);

        List<Integer> ans = new ArrayList<>();
        TreeSet<Integer> queue = new TreeSet<>();
        for (int i = 0; i < m; i++) {
            if (critical.get(i).isEmpty()) queue.add(i);
        }
        boolean hi = true;
        while (!queue.isEmpty()) {
            int take = hi ? queue.last() : queue.first();
            hi = !hi;
            queue.remove(take);
            
            for (int v : adj.get(take)) {
                alt.get(v).remove(take);
                indeg[v]--;
                if (indeg[v] == 1) {
                    int x = alt.get(v).iterator().next();
                    critical.get(x).add(v);
                    queue.remove(x);
                }
            }

            for (int u : alt.get(take)) {
                adj.get(u).remove(take);
                if (critical.get(u).contains(take)) {
                    critical.get(u).remove(take);
                    if (critical.get(u).isEmpty()) queue.add(u);
                }
            }
            ans.add(take);
        }

        long ansNum = 0;
        for (int d : ans) {
            ansNum = ansNum * m % MOD;
            ansNum = (ansNum + d) % MOD;
        }
        pw.println(ansNum);
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