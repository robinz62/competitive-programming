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
        int[] nm = ril(2);
        int n = nm[0];
        int m = nm[1];
        int[] p = ril(n);
        int[] s = ril(m);
        Map<Integer, List<Integer>> computers = new HashMap<>();
        for (int i = 0; i < p.length; i++) {
            if (!computers.containsKey(p[i])) computers.put(p[i], new ArrayList<>());
            computers.get(p[i]).add(i);
        }
        List<int[]> sockets = new ArrayList<>(m);
        for (int i = 0; i < m; i++) sockets.add(new int[]{s[i], i});

        int c = 0;
        int u = 0;
        int[] a = new int[m];
        int[] b = new int[n];
        Arrays.fill(b, -1);
        int count = 0;
        boolean go = true;
        while (go) {
            go = false;
            List<int[]> next = new ArrayList<>();
            for (int[] socket : sockets) {
                if (socket[0] == 0) continue;
                go = true;
                List<Integer> list = computers.get(socket[0]);
                if (list == null) {
                    if (socket[0] != 1) {
                        socket[0] = (socket[0] + 1) / 2;
                        next.add(socket);
                    }
                    continue;
                }
                int idx = list.remove(list.size() - 1);
                if (list.isEmpty()) computers.remove(socket[0]);
                a[socket[1]] = count;
                b[idx] = socket[1];
                c++;
                u += count;
            }
            sockets = next;
            count++;
        }
        pw.println(c + " " + u);
        for (int i = 0; i < m; i++) pw.print(a[i] + " ");
        pw.println();
        for (int i = 0; i < n; i++) pw.print((b[i]+1) + " ");
        pw.println();
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