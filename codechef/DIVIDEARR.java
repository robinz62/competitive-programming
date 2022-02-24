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
    //   if (x : long) and (y : int), [y = x] does not compile, but [y += x] does
    //   sorting, or taking max, after MOD
    //
    // Interactive problems: don't forget to flush between test cases
    void solve() throws IOException {
        int T = ri();
        for (int Ti = 0; Ti < T; Ti++) {
            int n = ri();
            int[] a = ril(n);

            if (n == 1) {
                pw.println("0");
                continue;
            }

            int constant = a[0];
            Map<Integer, Integer> map = new HashMap<>();  // possible values of the other guy
            map.put(-1, 0);

            for (int i = 1; i < n; i++) {
                int nextconstant = a[i];
                Map<Integer, Integer> next = new HashMap<>();

                for (int v : map.keySet()) {
                    // Let a[i] replace the previous constant
                    int score = map.get(v) + Math.abs(a[i] - constant);
                    next.put(v, Math.max(next.getOrDefault(v, 0), score));

                    // Let a[i] replace the non constant
                    if (v == -1) {
                        score = map.get(v);
                        next.put(constant, Math.max(next.getOrDefault(constant, 0), score));
                    } else {
                        score = map.get(v) + Math.abs(a[i] - v);
                        next.put(constant, Math.max(next.getOrDefault(constant, 0), score));
                    }
                }

                constant = nextconstant;
                map = next;
            }

            int ans = 0;
            for (int v : map.values()) ans = Math.max(ans, v);
            pw.println(ans);

            // Below fails for test case [1, 3, 10, 9]
            // List<int[]> distinct = new ArrayList<>();
            // int curr = 1;
            // for (int i = 1; i < n; i++) {
            //     if (a[i] == a[i-1]) curr++;
            //     else {
            //         distinct.add(new int[]{a[i-1], curr});
            //         curr = 1;
            //     }
            // }
            // distinct.add(new int[]{a[n-1], curr});

            // if (distinct.size() == 1) {
            //     pw.println("0");
            //     continue;
            // }

            // List<Integer> extrema = new ArrayList<>();
            // extrema.add(0);
            // for (int i = 1; i < distinct.size()-1; i++) {
            //     int prev = distinct.get(i-1)[0];
            //     int me = distinct.get(i)[0];
            //     int next = distinct.get(i+1)[0];
            //     if (prev < me && me > next || prev > me && me < next) extrema.add(i);
            // }
            // extrema.add(distinct.size() - 1);

            // int ans = 0;
            // for (int i = 0; i < extrema.size()-1; i++) {
            //     int idxme = extrema.get(i);
            //     int idxnext = extrema.get(i+1);
            //     int[] me = distinct.get(idxme);
            //     int[] next = distinct.get(idxnext);
            //     ans += Math.abs(me[0] - next[0]);
            // }
            // for (int idx : extrema) distinct.get(idx)[1]--;
            // int prev = -1;
            // for (int[] val : distinct) {
            //     if (val[1] == 0) continue;
            //     if (prev != -1) {
            //         ans += Math.abs(val[0] - prev);
            //     }
            //     prev = val[0];
            // }
            // pw.println(ans);
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
        int c = br.read();
        int x = 0;
        while (c >= '0' && c <= '9') {
            x = x * 10 + c - '0';
            c = br.read();
        }
        return ril(x);
    }

    long[] rkll() throws IOException {
        int c = br.read();
        int x = 0;
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