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
            List<List<int[]>> adj = new ArrayList<>(n);
            List<Map<Integer, Integer>> originalSpecified = new ArrayList<>(n);  // For printing answer
            for (int i = 0; i < n; i++) originalSpecified.add(new HashMap<>());
            for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
            for (int i = 0; i < n-1; i++) {
                int[] uvw = ril(3);
                int u = uvw[0]-1;
                int v = uvw[1]-1;
                int w = uvw[2];

                if (w != -1) {
                    originalSpecified.get(u).put(v, w);
                    originalSpecified.get(v).put(u, w);
                }

                w = w == -1 ? -1 : Integer.bitCount(w) % 2;

                adj.get(u).add(new int[]{v, w});
                adj.get(v).add(new int[]{u, w});
            }

            boolean ok = true;

            List<Map<Integer, Integer>> adj2 = new ArrayList<>(n);
            for (int i = 0; i < n; i++) adj2.add(new HashMap<>());
            for (int i = 0; i < m; i++) {
                int[] abp = ril(3);
                int a = abp[0]-1;
                int b = abp[1]-1;
                int p = abp[2];  // parity

                // check duplicate contradictory...?
                if (adj2.get(a).containsKey(b) && adj2.get(a).get(b) != p) {
                    ok = false;
                }

                adj2.get(a).put(b, p);
                adj2.get(b).put(a, p);
            }

            if (!ok) {
                pw.println("NO");
                continue;
            }

            // We treat the given edges as constraints as well
            for (int i = 0; i < n; i++) {
                for (int[] jp : adj.get(i)) {
                    int j = jp[0];
                    int p = jp[1];
                    if (p == -1) continue;  // Unknown = not a constraint
                    if (adj2.get(i).containsKey(j) && adj2.get(i).get(j) != p) ok = false;
                    adj2.get(i).put(j, p);
                    adj2.get(j).put(i, p);
                }
            }
            if (!ok) {
                pw.println("NO");
                continue;
            }

            int[] r = new int[n];
            Arrays.fill(r, -1);
            for (int i = 0; ok && i < n; i++) {
                if (r[i] != -1) continue;
                r[i] = 0;
                ok = ok && dfs(i, r, adj2);
            }
            if (!ok) {
                pw.println("NO");
                continue;
            }

            pw.println("YES");
            for (int i = 0; i < n; i++) {
                for (int[] jp : adj.get(i)) {
                    int j = jp[0];
                    int p = jp[1];
                    if (i > j) continue;

                    if (originalSpecified.get(i).containsKey(j)) {
                        pw.println((i+1) + " " + (j+1) + " " + originalSpecified.get(i).get(j));
                        continue;
                    }

                    if (p == -1) {
                        pw.println((i+1) + " " + (j+1) + " " + (r[i] ^ r[j]));
                    }
                }
            }
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    boolean dfs(int u, int[] r, List<Map<Integer, Integer>> adj) {
        for (var vp : adj.get(u).entrySet()) {
            int v = vp.getKey();
            int p = vp.getValue();

            // require r_u ^ r_v = p
            int rvDesired = r[u] ^ p;
            if (r[v] != -1) {
                if (r[v] == rvDesired) continue;
                else {
                    return false;
                }
            } else {
                r[v] = rvDesired;
                if (!dfs(v, r, adj)) return false;
            }
        }
        return true;
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
        pw.printf("%.16f", d);
    }
}
