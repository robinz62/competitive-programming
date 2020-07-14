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
        int[] nm = ril(2);
        int n = nm[0];
        int m = nm[1];
        int[] t = ril(n);
        List<TreeSet<Integer>> towers = new ArrayList<>(m);
        for (int i = 0; i < m; i++) towers.add(new TreeSet<>());
        for (int i = 0; i < n; i++) towers.get(t[i]-1).add(i);
        int[] s = new int[n];
        int skips = 0;
        for (int i = 0; i < towers.size(); i++) {
            TreeSet<Integer> tower = towers.get(i);
            Integer prev = null;
            for (Integer x : tower) {
                if (prev != null && x - prev > 1) s[i]++;
                prev = x;
            }
            skips += s[i];
        }
        pw.println(m - 1 + skips);
        int tcount = m;
        for (int i = 0; i < m - 1; i++) {
            tcount--;
            int[] ab = ril(2);
            int ia = ab[0]-1;
            int ib = ab[1]-1;
            TreeSet<Integer> a = towers.get(ia);
            TreeSet<Integer> b = towers.get(ib);
            if (a.size() < b.size()) {
                int tmp = ia;
                ia = ib;
                ib = tmp;
                TreeSet<Integer> temp = a;
                a = b;
                b = temp;
            }
            int skipsCount = s[ia];
            for (int x : b) {
                a.add(x);
                Integer l = a.lower(x);
                Integer r = a.higher(x);
                if (l != null && r != null) skipsCount--;
                if (l != null && x - l > 1) skipsCount++;
                if (r != null && r - x > 1) skipsCount++;
            }
            towers.set(ab[0]-1, a);
            skips = skips - s[ia] - s[ib] + skipsCount;
            s[ab[0]-1] = skipsCount;
            s[ab[1]-1] = 0;
            pw.println(tcount-1+skips);
        }
    }
}