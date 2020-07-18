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
            int n = ri();
            int[][] a = new int[2][];
            a[0] = ril(n);
            a[1] = ril(n);
            for (int i = 0; i < n; i++) {
                a[0][i]--;
                a[1][i]--;
            }
            // number -> index of that number
            List<List<Integer>> map = new ArrayList<>(n);
            for (int i = 0; i < n; i++) map.add(new ArrayList<>(2));
            for (int i = 0; i < n; i++) {
                map.get(a[0][i]).add(i);
                map.get(a[1][i]).add(i);
            }
            boolean bad = false;
            for (int i = 0; i < n; i++) {
                if (map.get(i).size() != 2) {
                    pw.println("-1");
                    bad = true;
                    break;
                }
            }
            if (bad) continue;

            List<Integer> ans = new ArrayList<>();
            boolean[] visited = new boolean[n];
            for (int i = 0; !bad && i < n; i++) {
                if (!visited[i]) {
                    List<Integer> x = new ArrayList<>();  // don't flip
                    List<Integer> y = new ArrayList<>();  // flip
                    visited[i] = true;
                    if (a[0][i] == a[1][i]) continue;
                    // arbitrarily: don't flip at current index (i).
                    x.add(i);
                    int currIdx = i;
                    int currRow = 1;
                    int currVal = a[currRow][currIdx];
                    while (true) {
                        currIdx = map.get(currVal).get(0) == currIdx ? map.get(currVal).get(1) : map.get(currVal).get(0);
                        if (visited[currIdx]) {
                            if (a[currRow][currIdx] == currVal) bad = true;
                            break;
                        }
                        visited[currIdx] = true;
                        if (a[currRow][currIdx] == currVal) {
                            y.add(currIdx);
                            int temp = a[currRow][currIdx];
                            a[currRow][currIdx] = a[1 - currRow][currIdx];
                            a[1 - currRow][currIdx] = temp;
                        } else {
                            x.add(currIdx);
                        }
                        currVal = a[currRow][currIdx];
                    }
                    if (y.size() <= x.size()) {
                        ans.addAll(y);
                    } else {
                        ans.addAll(x);
                    }
                }
            }
            if (bad) {
                pw.println("-1");
            } else {
                pw.println(ans.size());
                for (int idx : ans) pw.print((idx+1) + " ");
                pw.println();
            }
        }
    }
}