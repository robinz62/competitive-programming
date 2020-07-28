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
        int[] a = ril(n);
        int[] t = ril(n);

        // sorted by a-value
        List<Integer> l = new ArrayList<>(n);
        for (int i = 0; i < n; i++) l.add(i);
        Collections.sort(l, (i1, i2) -> Integer.compare(a[i1], a[i2]));

        // set contains the indices of entries with a-value of val
        // it is sorted by t value
        // sumt is cost to increment everyone in set
        TreeSet<Integer> set = new TreeSet<>((i1, i2) ->
            t[i1] == t[i2] ? Integer.compare(i1, i2) : Integer.compare(t[i1], t[i2]));
        int val = -1;
        long sumt = 0;

        long ans = 0;
        int i = 0;
        while (i < n || !set.isEmpty()) {
            if (set.isEmpty()) {
                set.add(l.get(i));
                val = a[l.get(i)];
                sumt = t[l.get(i)];
                i++;
            }
            while (i < n && a[l.get(i)] == val) {
                set.add(l.get(i));
                sumt += t[l.get(i)];
                i++;
            }
            if (set.size() == 1) {
                set.clear();
            } else {
                int maxt = t[set.last()];
                set.remove(set.last());
                sumt -= maxt;
                val++;
                ans += sumt;
            }
        }

        pw.println(ans);
    }
}