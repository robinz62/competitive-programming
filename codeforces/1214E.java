import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static int MOD = 1000000007;

    // After writing solution, quick scan for:
    //   array out of bounds
    //   special cases e.g. n=1?
    //
    // Big numbers arithmetic bugs:
    //   int overflow
    //   sorting, or taking max, after MOD
    void solve() throws IOException {
        int n = ri();
        int[] din = ril(n);
        int[][] a = new int[n][];
        for (int i = 0; i < n; i++) a[i] = new int[]{din[i], 2*i+1, 2*i+2};
        Arrays.sort(a, (i, j) -> -Integer.compare(i[0], j[0]));

        // Make a chain of the maximum length
        List<Integer> main = new ArrayList<>();
        List<List<Integer>> l = new ArrayList<>();
        Deque<Integer> q = new ArrayDeque<>();  // Keeps track of idx in l that need filling
        int x = a[0][0];
        for (int i = 0; i <= x; i++) main.add(0);
        for (int i = 0; i <= x; i++) l.add(new ArrayList<>());
        for (int i = 1; i < x; i++) q.addLast(i);
        main.set(0, a[0][1]);
        main.set(x, a[0][2]);
        for (int i = 1; i < n; i++) {
            int d = a[i][0];
            if (!q.isEmpty()) {
                int u = q.removeFirst();
                main.set(u, a[i][1]);
                if (d == 1) {
                    pw.println(a[i][1] + " " + a[i][2]);
                    continue;
                }
                if (u+d-1 == l.size()-1) {
                    main.add(a[i][2]);
                    l.add(new ArrayList<>());
                } else {
                    l.get(u+d-1).add(a[i][2]);
                }
            } else {
                main.add(a[i][1]);
                l.add(new ArrayList<>());
                int u = l.size()-1;
                if (d == 1) {
                    pw.println(a[i][1] + " " + a[i][2]);
                    continue;
                }
                // go left always works
                l.get(u-d+1).add(a[i][2]);
            }
        }
        for (int i = 0; i < main.size()-1; i++) pw.println(main.get(i) + " " + main.get(i+1));
        for (int i = 0; i < l.size(); i++) {
            for (int j = 0; j < l.get(i).size(); j++) {
                pw.println(main.get(i) + " " + l.get(i).get(j));
            }
        }
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