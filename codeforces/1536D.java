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
            int[] b = ril(n);
            coordinateCompress(b);

            BIT placed = new BIT(n+5);
            BIT leq = new BIT(n+5);
            BIT geq = new BIT(n+5);

            TreeMap<Integer, Integer> placedMap = new TreeMap<>();
            TreeMap<Integer, Integer> leqMap = new TreeMap<>();
            TreeMap<Integer, Integer> geqMap = new TreeMap<>();
            
            // First value
            placedMap.put(b[0], 1);
            placed.add(b[0], 1);
            boolean done = false;
            for (int i = 1; !done && i < n; i++) {
                int add = 0;
                if (!placedMap.containsKey(b[i])) {
                    placedMap.put(b[i], 1);
                    placed.add(b[i], 1);
                    add = 1;
                } else {
                    add = 2;
                }
                int leqMe = placed.query(0, b[i]) + leq.query(0, b[i]);
                int geqMe = placed.query(b[i], n+5) + geq.query(b[i], n+5);
                for (int x = 0; x < add; x++) {
                    if (leqMe < geqMe) {
                        leqMap.put(b[i], leqMap.getOrDefault(b[i], 0) + 1);
                        leq.add(b[i], 1);
                        leqMe++;
                    } else {
                        geqMap.put(b[i], geqMap.getOrDefault(b[i], 0) + 1);
                        geq.add(b[i], 1);
                        geqMe++;
                    }
                }
                while (!done && leqMe < geqMe) {
                    // We need more guys less than me. See if we can strengthen anyone on the leq side.
                    Integer canLower = leqMap.higherKey(b[i]);
                    if (canLower == null) {
                        pw.println("NO");
                        done = true;
                        break;
                    } else {
                        int curr = leqMap.get(canLower);
                        int take = Math.min(curr, geqMe - leqMe);
                        if (curr == take) leqMap.remove(canLower);
                        else leqMap.put(canLower, curr-take);
                        leq.add(canLower, -take);
                        
                        leqMap.put(b[i], leqMap.getOrDefault(b[i], 0) + take);
                        leq.add(b[i], take);
                        leqMe += take;
                    }
                }

                while (!done && leqMe > geqMe) {
                    // We need more guys gt me. See if we can strengthen anyone on the geq side.
                    Integer canRaise = geqMap.lowerKey(b[i]);
                    if (canRaise == null) {
                        pw.println("NO");
                        done = true;
                        break;
                    } else {
                        int curr = geqMap.get(canRaise);
                        int take = Math.min(curr, leqMe - geqMe);
                        if (curr == take) geqMap.remove(canRaise);
                        else geqMap.put(canRaise, curr-take);
                        geq.add(canRaise, -take);

                        geqMap.put(b[i], geqMap.getOrDefault(b[i], 0) + take);
                        geq.add(b[i], take);
                        geqMe += take;
                    }
                }
            }
            if (!done) {
                pw.println("YES");
            }
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    public void coordinateCompress(int[] a) {
        int[] b = new int[a.length];
        System.arraycopy(a, 0, b, 0, a.length);
        sort(b);
        Map<Integer, Integer> map = new HashMap<>();
        int idx = 0;
        map.put(b[0], idx++);
        for (int i = 1; i < b.length; i++) if (b[i] != b[i-1]) map.put(b[i], idx++);
        for (int i = 0; i < a.length; i++) a[i] = map.get(a[i]);
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

    int query(int l, int r) {
        return query(r) - (l-1 >= 0 ? query(l-1) : 0);
    }

    void add(int i, int value) {
        i++;
        while (i < bit.length) {
            bit[i] += value;
            i += i & -i;
        }
    }
}