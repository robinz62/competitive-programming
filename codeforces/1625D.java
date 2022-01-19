import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static int MOD = 1000000007;

    int MAX_BIT = 29;
    int MAX = 7000000;
    int[] left = new int[MAX];
    int[] right = new int[MAX];
    int[] here = new int[MAX];
    int id = 0;

    int create() {
        left[id] = right[id] = -1;
        id++;
        return id-1;
    }

    // After writing solution, quick scan for:
    //   array out of bounds
    //   special cases e.g. n=1?
    //   npe, particularly in maps
    //
    // Big numbers arithmetic bugs:
    //   int overflow
    //   if (x : long) and (y : int), [y = x] does not compile, but [y += x] does
    //   sorting, or taking max, after MOD
    void solve() throws IOException {
        int[] nk = ril(2);
        int n = nk[0];
        int k = nk[1];

        a = ril(n);

        if (k == 0) {
            pw.println(n);
            for (int i = 0; i < n; i++) pw.print((i+1) + " ");
            pw.println();
            return;
        }

        // Require a subset such that any pair has xor >= k.
        int root = create();
        for (int i = 0; i < n; i++) here[root] = 0;
        for (int i = 0; i < n; i++) {
            int ai = a[i];
            int curr = root;
            for (int b = MAX_BIT; b >= 0; b--) {
                int bit = ((1 << b) & ai) > 0 ? 1 : 0;
                if (bit == 0) {
                    if (left[curr] == -1) left[curr] = create();
                    curr = left[curr];
                } else {
                    if (right[curr] == -1) right[curr] = create();
                    curr = right[curr];
                }
                here[curr] = i;
            }
        }

        dfs(root, k, MAX_BIT);
        if (theAns.size() <= 1) {
            pw.println("-1");
        } else {
            pw.println(theAns.size());
            for (int idx : theAns) {
                pw.print((idx+1) + " ");
            }
            pw.println();
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    int[] a;
    List<Integer> theAns = new ArrayList<>();
    void dfs(int curr, int k, int b) {
        if (b == -1) {  // we are on a leaf
            theAns.add(here[curr]);
            return;
        }

        int kBit = ((1 << b) & k) > 0 ? 1 : 0;
        if (kBit == 0) {
            if (left[curr] != -1) dfs(left[curr], k, b-1);
            if (right[curr] != -1) dfs(right[curr], k, b-1);
        } else {
            findPair(curr, k, b);
        }
    }

    // Find the largest xor possible from a pair
    void findPair(int curr, int k, int b) {
        found = new ArrayList<>();
        dfs2(curr);
        for (int idx : found) {
            int X = a[idx];
            int other = curr;
            int currBit = b;
            while (currBit >= 0) {
                int XBit = ((1 << currBit) & X) > 0 ? 1 : 0;
                int want = XBit ^ 1;
                if (want == 0) {
                    if (left[other] != -1) other = left[other];
                    else other = right[other];
                } else {
                    if (right[other] != -1) other = right[other];
                    else other = left[other];
                }
                currBit--;
            }

            int otherIdx = here[other];
            int Y = a[otherIdx];
            if ((X ^ Y) >= k) {
                theAns.add(idx);
                theAns.add(otherIdx);
                return;
            }
        }
        theAns.add(here[curr]);
        return;
    }

    List<Integer> found;
    void dfs2(int curr) {
        if (left[curr] == -1 && right[curr] == -1) {
            found.add(here[curr]);
            return;
        }
        if (left[curr] != -1) dfs2(left[curr]);
        if (right[curr] != -1) dfs2(right[curr]);
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