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
        int n = ri();
        int[] p = ril(n);
        boolean[] visited = new boolean[n];
        for (int i = 0; i < n; i++) p[i]--;
        Map<Integer, List<List<Integer>>> cycles = new HashMap<>();
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                int curr = i;
                List<Integer> c = new ArrayList<>();
                visited[curr] = true;
                c.add(curr);
                curr = p[curr];
                while (curr != i) {
                    visited[curr] = true;
                    c.add(curr);
                    curr = p[curr];
                }
                if (!cycles.containsKey(c.size())) cycles.put(c.size(), new ArrayList<>());
                cycles.get(c.size()).add(c);
            }
        }
        int[] ans = new int[n];
        for (int sz : cycles.keySet()) {
            if (sz % 2 == 1) {
                for (List<Integer> c : cycles.get(sz)) {
                    int offset = (sz+1) / 2;
                    for (int i = 0; i < sz; i++) {
                        int node = c.get(i);
                        int next = c.get((i + offset) % sz);
                        ans[node] = next;
                    }
                }
            } else {
                if (cycles.get(sz).size() % 2 == 1) {
                    pw.println("-1");
                    return;
                }
                for (int i = 0; i < cycles.get(sz).size(); i += 2) {
                    List<Integer> c1 = cycles.get(sz).get(i);
                    List<Integer> c2 = cycles.get(sz).get(i+1);
                    for (int j = 0; j < sz; j++) {
                        ans[c1.get(j)] = c2.get(j);
                        ans[c2.get(j)] = c1.get((j+1) % sz);
                    }
                }
            }
        }
        for (int ai : ans) pw.print((ai+1) + " ");
        pw.println();
    }
}