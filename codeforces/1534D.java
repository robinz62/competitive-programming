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
        int n = ri();

        // Observations:
        // - for querying node r, distance to r is 0 of course
        // - distance = 1 gives us an immediate edge to add easily
        // - distance = 2 means there is some intermediate node which links the two, not sure this is helpful...
        // - if only one distance = 1 returned, then this is a leaf.
        // - more generally, #distance=1 returned is the degree
        //
        // - if there's only 1 distance = 1, then we can also infer the distance = 2 edges.
        //
        // - however, if there's more than one distance=1, then we don't know which to pick
        //   for the distance=2

        List<int[]> edges = new ArrayList<>(n);
        pw.println("? 1"); pw.flush();
        int[] d = ril(n);
        
        int max = 0;
        for (int i = 0; i < n; i++) max = Math.max(max, d[i]);
        List<List<Integer>> levels = new ArrayList<>(max+1);
        for (int i = 0; i <= max; i++) levels.add(new ArrayList<>());
        for (int i = 0; i < n; i++) levels.get(d[i]).add(i);

        // level one people
        boolean[] special = new boolean[n];

        // Add level 1 edges
        for (int u : levels.get(1)) {
            edges.add(new int[]{0, u});
            special[u] = true;
        }

        int oddCount = 0;
        int evenCount = 0;
        for (int i = 1; i <= max; i++) {
            if (i % 2 == 0) evenCount += levels.get(i).size();
            else oddCount += levels.get(i).size();
        }

        int lvl = oddCount < evenCount ? 1 : 2;
        while (lvl <= max) {
            for (int u : levels.get(lvl)) {
                pw.println("? " + (u+1)); pw.flush();
                int[] dist = ril(n);
                for (int i = 0; i < n; i++) {
                    if (dist[i] == 1 && i != 0) {
                        edges.add(new int[]{u, i});
                    }
                }
            }
            lvl += 2;
        }

        pw.println("!");
        for (int[] e : edges) {
            e[0]++; e[1]++;
            pw.println(e[0] + " " + e[1]);
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