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
            int[][] problems = new int[n][];
            for (int i = 0; i < n; i++) problems[i] = ril(2);

            long ans = 0;

            Arrays.sort(problems, (p1, p2) -> Integer.compare(p1[0], p2[0]));

            long lowera = 0;
            long highera = n;

            Multiset<Integer> leftBs = new Multiset<>();
            Multiset<Integer> rightBs = new Multiset<>();
            for (int[] p : problems) rightBs.add(p[1]);

            // at any time, this is the sum over all possible bi of leftBs[bi] * rightBs[bi]
            long samebs = 0;

            // initialize to being on the first element
            int l = 0;
            int r = 0;
            for (int i = 0; i < n; i++) {
                int a = problems[i][0];
                int b = problems[i][1];
                while (problems[l][0] < a) {
                    lowera++;
                    leftBs.add(problems[l][1]);
                    int bb = problems[l][1];
                    samebs += rightBs.count(bb);
                    l++;
                }

                while (r < n && problems[r][0] == a) {
                    highera--;
                    rightBs.remove(problems[r][1]);
                    int bb = problems[r][1];
                    samebs -= leftBs.count(bb);
                    r++;
                }

                ans += lowera * highera;

                // These also need to be different from each other...
                ans -= (lowera - leftBs.count(problems[i][1])) * (highera - rightBs.count(problems[i][1])) - samebs + (long) leftBs.count(b) * rightBs.count(b);
            }

            Arrays.sort(problems, (p1, p2) -> Integer.compare(p1[1], p2[1]));
            long lowerb = 0;
            long higherb = n;
            l = 0;
            r = 0;
            for (int i = 0; i < n; i++) {
                int b = problems[i][1];
                while (problems[l][1] < b) {
                    lowerb++;
                    l++;
                }
                while (r < n && problems[r][1] == b) {
                    higherb--;
                    r++;
                }
                ans += lowerb * higherb;
            }

            pw.println(ans);
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

class Multiset<T> {
    private HashMap<T, Integer> map = new HashMap<>();
    private int size;

    boolean isEmpty() { return size == 0; }
    int size() { return size; }
    int sizeUnique() { return map.size(); }
    int count(T t) { return map.getOrDefault(t, 0); }
    void add(T t) { add(t, 1); }
    void add(T t, int count) { map.put(t, map.getOrDefault(t, 0) + count); size += count; }
    void remove(T t) { remove(t, 1); }
    void remove(T t, int count) {
        int curr = map.get(t);
        if (count > curr) throw new RuntimeException();
        if (curr == count) map.remove(t);
        else map.put(t, curr - count);
        size -= count;
    }
    Set<Map.Entry<T, Integer>> entrySet() { return map.entrySet(); }
}