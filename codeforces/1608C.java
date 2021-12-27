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
            int n = ri();
            int[] a = ril(n);
            int[] b = ril(n);

            boolean[] ans = new boolean[n];
            // Player with max map1 score can obviously win. Who can beat him? If so, then that person can win too.
            int imaxa = 0;
            int imaxb = 0;
            for (int i = 0; i < n; i++) {
                if (a[i] > a[imaxa]) imaxa = i;
                if (b[i] > b[imaxb]) imaxb = i;
            }
            ans[imaxa] = ans[imaxb] = true;
            Deque<Integer> q = new ArrayDeque<>();
            q.addLast(imaxa);
            q.addLast(imaxb);
            TreeSet<Integer> idxByA = new TreeSet<>((i1, i2) -> Integer.compare(a[i1], a[i2]));
            TreeSet<Integer> idxByB = new TreeSet<>((i1, i2) -> Integer.compare(b[i1], b[i2]));
            for (int i = 0; i < n; i++) {
                idxByA.add(i);
                idxByB.add(i);
            }
            boolean[] visited = new boolean[n];
            visited[imaxa] = visited[imaxb] = true;

            while (!q.isEmpty()) {
                int idx = q.removeFirst();
                int ai = a[idx];
                int bi = b[idx];
                while (!idxByA.isEmpty() && a[idxByA.last()] > ai) {
                    int v = idxByA.last();
                    idxByA.remove(v);
                    if (!visited[v]) {
                        visited[v] = true;
                        q.addLast(v);
                        ans[v] = true;
                    }
                }

                while (!idxByB.isEmpty() && b[idxByB.last()] > bi) {
                    int v = idxByB.last();
                    idxByB.remove(v);
                    if (!visited[v]) {
                        visited[v] = true;
                        q.addLast(v);
                        ans[v] = true;
                    }
                }
            }

            for (boolean bb : ans) pw.print(bb ? "1" : "0");
            pw.println();
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