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

    int[] ril() throws IOException {
        String[] tokens = br.readLine().split(" ");
        int[] A = new int[tokens.length];
        for (int i = 0; i < A.length; i++)
            A[i] = Integer.parseInt(tokens[i]);
        return A;
    }

    long[] rll() throws IOException {
        String[] tokens = br.readLine().split(" ");
        long[] A = new long[tokens.length];
        for (int i = 0; i < A.length; i++)
            A[i] = Long.parseLong(tokens[i]);
        return A;
    }

    void solve() throws IOException {
        int t = ri();
        for (int ti = 0; ti < t; ti++) {
            int n = ri();
            int[] a = ril();
            int[] b = ril();
            if (n % 2 == 1 && a[n / 2] != b[n / 2]) {
                pw.println("No");
                continue;
            }
            Map<Integer, List<Integer>> amap = new HashMap<>();
            for (int i = 0; i < n / 2; i++) {
                int x = a[i];
                int y = a[n-1-i];
                if (x <= y) {
                    if (!amap.containsKey(x)) amap.put(x, new ArrayList<>());
                    amap.get(x).add(y);
                } else {
                    if (!amap.containsKey(y)) amap.put(y, new ArrayList<>());
                    amap.get(y).add(x);
                }
            }
            Map<Integer, List<Integer>> bmap = new HashMap<>();
            for (int i = 0; i < n / 2; i++) {
                int x = b[i];
                int y = b[n-1-i];
                if (x <= y) {
                    if (!bmap.containsKey(x)) bmap.put(x, new ArrayList<>());
                    bmap.get(x).add(y);
                } else {
                    if (!bmap.containsKey(y)) bmap.put(y, new ArrayList<>());
                    bmap.get(y).add(x);
                }
            }
            if (!amap.keySet().equals(bmap.keySet())) {
                pw.println("No");
                continue;
            }
            boolean ok = true;
            for (int x : amap.keySet()) {
                Collections.sort(amap.get(x));
                Collections.sort(bmap.get(x));
                if (!amap.get(x).equals(bmap.get(x))) {
                    pw.println("No");
                    ok = false;
                    break;
                }
            }
            if (ok) {
                pw.println("Yes");
            }
        }
    }
}