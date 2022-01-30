import java.io.*;
import java.math.BigInteger;
import java.util.*;

import javax.swing.text.Segment;

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
    void solve() throws IOException {
        int n = ri();
        int[] a = ril(n);

        // gcd = 1: elements can not equal 1
        // gcd = 2: adjacent elements cannot have gcd = 2 (but (4, 8) would be ok)


        // bad if there is a subarray whose gcd equals the length of the subarray
        // need answer for every prefix
        //
        // if we need to replace a number, we can replace it with an arbitrarily large prime

        // what information about prefixes would be helpful?
        // let's say we "solved" prefix up to i. what else could we know about it?
        //
        // gcd segment tree seems useful.
        //
        // if we're adding an element to the end, we need to preserve all changes
        // from before, since one could just consider the subarrays without this
        // last element

        int LEFT = 0;  // left boundary. primes will cut this

        // SegmentTree st = new SegmentTree(a);

        int[] f = new int[n];
        List<int[]> prev = new ArrayList<>();
        if (a[0] == 1) {
            f[0] = 1;
            LEFT = 1;
        } else {
            f[0] = 0;
            prev.add(new int[]{0, a[0]});
        }
        for (int i = 1; i < n; i++) {
            if (a[i] == 1) {
                f[i] = f[i-1] + 1;
                LEFT = i+1;
                prev.clear();
                continue;
            }

            List<int[]> curr = new ArrayList<>();
            boolean done = false;
            for (int j = 0; !done && j < prev.size(); j++) {
                int[] here = prev.get(j);
                int prevGVal = here[1];
                int leftIdx = here[0];
                int rightIdx = j == prev.size() - 1 ? i-1 : prev.get(j+1)[0] - 1;

                
                leftIdx = Math.max(leftIdx, LEFT);
                if (LEFT > rightIdx) continue;
                
                // prevGVal is present from [leftIdx, rightIdx]
                int combined = gcd(prevGVal, a[i]);
                int banned = i + 1 - combined;
                if (banned >= leftIdx && banned <= rightIdx) {
                    f[i] = f[i-1] + 1;
                    LEFT = i+1;
                    prev.clear();
                    done = true;
                    break;
                }

                if (curr.isEmpty() || curr.get(curr.size()-1)[1] != combined) {
                    curr.add(new int[]{leftIdx, combined});
                }
            }
            if (done) continue;
            if (curr.isEmpty() || curr.get(curr.size()-1)[1] != a[i]) curr.add(new int[]{i, a[i]});

            f[i] = f[i-1];
            prev = curr;
        }
        for (int x : f) {
            pw.print(x + " ");
        }
        pw.println();
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    public static int gcd(int a, int b) {
        return a == 0 ? b : gcd(b % a, a);
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

class SegmentTree {
    int n;
    int[] st;

    public static int gcd(int a, int b) {
        return a == 0 ? b : gcd(b % a, a);
    }

    // Modify identity and combine together.
    int identity = -1;
    int combine(int a, int b) {
        if (a == -1) return b;
        if (b == -1) return a;
        return gcd(a, b);
    }

    SegmentTree(int[] arr) {
        n = arr.length;
        st = new int[n*2];
        for (int i = 0; i < n; i++) {
            st[n + i] = arr[i];
        }
        build();
    }

    void build() {
        for (int i = n - 1; i > 0; i--) {
            st[i] = combine(st[i*2], st[i*2+1]);
        }
    }

    void modify(int i, int value) {
        st[n + i] = value;
        for (i = (n + i) / 2; i > 0; i /= 2) {
            st[i] = combine(st[i*2], st[i*2+1]);
        }
    }

    // Note: input range is half-open [l, r)
    int query(int l, int r) {
        l += n;
        r += n;
        int resl = identity;
        int resr = identity;
        while (l < r) {
            if ((l & 1) > 0) resl = combine(resl, st[l++]);
            if ((r & 1) > 0) resr = combine(st[--r], resr);
            l /= 2;
            r /= 2;
        }
        return combine(resl, resr);
    }
}