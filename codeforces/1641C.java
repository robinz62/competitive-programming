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
        int[] nq = ril(2);
        int n = nq[0];
        int q = nq[1];

        TreeSet<Integer> unsure = new TreeSet<>();
        for (int i = 0; i < n; i++) unsure.add(i);
        BIT bit = new BIT(n);  // definitely healthy

        // What is interesting is the NOT SICK information that follows SICK information.
        // Basically, the NOT SICK information can help us prune the SICK ranges.

        // Keep track of SICK ranges. How to quickly update all such previous with
        // NOT SICK information?
        // Associated each SICK range with a counter initialized to how many could be sick in range

        // start index to shortest SICK segment starting at index
        int[] startToEnd = new int[n];
        Arrays.fill(startToEnd, Integer.MAX_VALUE);

        for (int qi = 0; qi < q; qi++) {
            // pw.println("CYCLE: " + qi);
            // pw.println(Arrays.toString(startToEnd));
            // pw.println(unsure);
            // for (int i = 0; i < n; i++) pw.print(bit.query(i) - (i-1>=0?bit.query(i-1):0) + " ");
            // pw.println();

            String[] line = br.readLine().split(" ");
            int t = Integer.parseInt(line[0]);
            if (t == 0) {
                int l = Integer.parseInt(line[1]) - 1;
                int r = Integer.parseInt(line[2]) - 1;
                boolean hasSick = Integer.parseInt(line[3]) == 1;

                if (!hasSick) {
                    // Any sick ranges starting in this range should be pushed to
                    // the first possible afterwards
                    // Want the first-ending sick range that ends AFTER r
                    Integer set = null;
                    Integer curr = unsure.ceiling(l);
                    while (curr != null && curr <= r) {
                        if (startToEnd[curr] != -1 && startToEnd[curr] != Integer.MAX_VALUE) {
                            if (startToEnd[curr] > r) {
                                if (set == null || startToEnd[curr] < set) set = startToEnd[curr];
                            }
                        }
                        bit.add(curr, 1);
                        startToEnd[curr] = -1;
                        unsure.remove(curr);
                        curr = unsure.higher(curr);
                    }

                    Integer next = unsure.higher(r);
                    if (set != null && next != null) {
                        startToEnd[next] = Math.min(startToEnd[next], set);
                    }
                } else {
                    Integer start = unsure.ceiling(l);
                    if (start != null) l = start;
                    startToEnd[l] = Math.min(startToEnd[l], r);
                }
            } else if (t == 1) {
                int j = Integer.parseInt(line[1]) - 1;

                if (startToEnd[j] == -1) {
                    pw.println("NO");
                } else if (startToEnd[j] == Integer.MAX_VALUE) {
                    pw.println("N/A");
                } else {
                    int len = startToEnd[j] - j + 1;  // sick potentially
                    int healthy = bit.query(startToEnd[j]) - (j-1>=0 ? bit.query(j-1) : 0);
                    if (len - healthy == 1) {
                        pw.println("YES");
                    } else {
                        pw.println("N/A");
                    }
                }
            }
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