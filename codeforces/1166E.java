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
        int[] mn = ril(2);
        int m = mn[0];
        int n = mn[1];
        int[][] take = new int[m][];
        for (int i = 0; i < m; i++) {
            take[i] = rkil();
            sort(take[i]);
            for (int j = 0; j < take[i].length; j++) take[i][j]--;
        }

        int[] doracount = new int[n];
        for (int[] me : take) {
            for (int x : me) {
                doracount[x]++;
            }
        }

        // If there's an element dora always takes, then possible (set that to inf).
        for (int c : doracount) {
            if (c == m) {
                pw.println("possible");
                return;
            }
        }

        // If any two of dora's picks are disjoint, then impossible
        List<Set<Integer>> takesets = new ArrayList<>(m);
        for (int i = 0; i < m; i++) {
            Set<Integer> curr = new HashSet<>();
            for (int x : take[i]) curr.add(x);
            takesets.add(curr);
        }
        for (int i = 0; i < m; i++) {
            Set<Integer> a = takesets.get(i);
            for (int j = i+1; j < m; j++) {
                Set<Integer> b = takesets.get(j);
                boolean hasoverlap = false;
                for (int x = 0; !hasoverlap && x < n; x++) {
                    if (a.contains(x) && b.contains(x)) hasoverlap = true;
                }
                if (!hasoverlap) {
                    pw.println("impossible");
                    return;
                }
            }
        }

        pw.println("possible");
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