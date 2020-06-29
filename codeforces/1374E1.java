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
        int[] nk = ril();
        int n = nk[0];
        int k = nk[1];
        int[] t = new int[n];
        List<Integer> a = new ArrayList<>();
        List<Integer> b = new ArrayList<>();
        List<Integer> c = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int[] tab = ril();
            t[i] = tab[0];
            if (tab[1] == 1 && tab[2] == 1) c.add(i);
            else if (tab[1] == 1) a.add(i);
            else if (tab[2] == 1) b.add(i);
        }
        Collections.sort(a, (i1, i2) -> Integer.compare(t[i1], t[i2]));
        Collections.sort(b, (i1, i2) -> Integer.compare(t[i1], t[i2]));
        Collections.sort(c, (i1, i2) -> Integer.compare(t[i1], t[i2]));
        int[] aprefix = new int[a.size()+1];
        int[] bprefix = new int[b.size()+1];
        int[] cprefix = new int[c.size()+1];
        for (int i = 1; i <= a.size(); i++) aprefix[i] = aprefix[i-1] + t[a.get(i-1)];
        for (int i = 1; i <= b.size(); i++) bprefix[i] = bprefix[i-1] + t[b.get(i-1)];
        for (int i = 1; i <= c.size(); i++) cprefix[i] = cprefix[i-1] + t[c.get(i-1)];
        int best = Integer.MAX_VALUE;
        for (int i = 0; i <= c.size(); i++) {
            int need = Math.max(0, k - i);
            if (need >= aprefix.length || need >= bprefix.length) continue;
            best = Math.min(best, cprefix[i] + aprefix[need] + bprefix[need]);
        }
        pw.println(best == Integer.MAX_VALUE ? "-1" : best);
    }
}