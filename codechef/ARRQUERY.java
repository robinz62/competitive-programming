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
        int[] nq = ril(2);
        int n = nq[0];
        int q = nq[1];

        // Set<Integer> vals = new HashSet<>();
        int[] a = ril(n);
        // for (int ai : a) vals.add(ai);
        int[][] queries = new int[q][];
        for (int qi = 0; qi < q; qi++) {
            queries[qi] = ril(3);
            int x = queries[qi][2];
            // vals.add(x);
        }

        TreeMap<Integer, List<Integer>> valToIdx = new TreeMap<>(Collections.reverseOrder());
        for (int i = 0; i < n; i++) {
            if (!valToIdx.containsKey(a[i])) valToIdx.put(a[i], new ArrayList<>());
            valToIdx.get(a[i]).add(i);
        }
        

        // List<Integer> uniqueVals = new ArrayList<>(vals);
        // Collections.sort(uniqueVals);
        // Map<Integer, Integer> originalToId = new HashMap<>();
        // for (int i = 0; i < uniqueVals.size(); i++) {
        //     int v = uniqueVals.get(i);
        //     originalToId.put(v, i);
        // }

        // for (int i = 0; i < n; i++) {
        //     a[i] = originalToId.get(a[i]);
        // }
        // for (int[] query : queries) {
        //     query[2] = originalToId.get(query[2]);
        // }

        int[] ans = new int[q];

        BIT bit = new BIT(n+5);
        
        List<Integer> p = new ArrayList<>(q);
        for (int i = 0; i < q; i++) p.add(i);
        Collections.sort(p, (i1, i2) -> -Integer.compare(queries[i1][2], queries[i2][2]));
        for (int qi : p) {
            int l = queries[qi][0]-1;
            int r = queries[qi][1]-1;
            int x = queries[qi][2];
            while (!valToIdx.isEmpty() && valToIdx.firstKey() >= x) {
                int v = valToIdx.firstKey();
                for (int i : valToIdx.get(v)) {
                    bit.add(i, 1);
                }
                valToIdx.remove(v);
            }

            ans[qi] = bit.query(r) - (l-1 >= 0 ? bit.query(l-1) : 0);
        }

        for (int z : ans) {
            pw.println(z);
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

class BIT {
    int[] bit;

    // n is the maximum index that can be queried/modified.
    BIT(int n) {
        bit = new int[n+2];
    }
    BIT(int[] arr) {
        bit = new int[arr.length + 1];
        for (int i = 0; i < arr.length; i++) {
            add(i, arr[i]);
        }
    }

    // Queries the closed range [0, i]
    int query(int i) {
        i++;
        int sum = 0;
        while (i >= 1) {
            sum += bit[i];
            i -= i & -i;
        }
        return sum;
    }

    void add(int i, int value) {
        i++;
        while (i < bit.length) {
            bit[i] += value;
            i += i & -i;
        }
    }
}