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
            char[] s = rs();
            int k = ri();
            boolean[][] adj = new boolean[26][26];
            for (int i = 0; i < k; i++) {
                char[] uv = rs();
                adj[uv[0]-'A'][uv[1]-'A'] = true;
            }

            int[][] shortest = new int[26][26];
            for (int[] row : shortest) Arrays.fill(row, Integer.MAX_VALUE);
            for (int src = 0; src < 26; src++) {
                Deque<Integer> q = new ArrayDeque<>();
                q.addLast(src);
                int dist = 0;
                shortest[src][src] = dist;
                dist++;
                while (!q.isEmpty()) {
                    int sz = q.size();
                    for (int i = 0; i < sz; i++) {
                        int u = q.removeFirst();
                        for (int v = 0; v < 26; v++) {
                            if (!adj[u][v] || shortest[src][v] != Integer.MAX_VALUE) continue;
                            shortest[src][v] = dist;
                            q.addLast(v);
                        }
                    }
                    dist++;
                }
            }

            for (int i = 0; i < s.length; i++) s[i] -= 'A';
            int ans = Integer.MAX_VALUE;
            for (int tgt = 0; tgt < 26; tgt++) {
                boolean bad = false;
                int count = 0;
                for (char src : s) {
                    if (shortest[src][tgt] == Integer.MAX_VALUE) {
                        bad = true;
                        break;
                    }
                    count += shortest[src][tgt];
                }
                if (bad) continue;
                ans = Math.min(ans, count);
            }
            if (ans == Integer.MAX_VALUE) ans = -1;
            pw.println("Case #" + (Ti+1) + ": " + ans);
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