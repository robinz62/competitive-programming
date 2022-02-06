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
    void solve() throws IOException {
        int T = ri();
        for (int Ti = 0; Ti < T; Ti++) {
            int n = ri();  // 4 <= n <= 1000
            queriesMade = 0;

            // nums are the indices that are still in the game
            nums = new ArrayList<>();
            for (int i = 1; i <= n; i++) nums.add(i);

            while (nums.size() > 3) {
                List<Integer> next = new ArrayList<>();
                int i = 0;
                while (i + 3 < nums.size()) {
                    int a = query(i, i+1, i+2);
                    int b = query(i, i+2, i+3);
                    int c = query(i, i+1, i+3);
                    int d = query(i+1, i+2, i+3);
                    int max = Math.max(a, Math.max(b, Math.max(c, d)));

                    if (a == max && b == max) {
                        next.add(nums.get(i)); next.add(nums.get(i+2));
                    } else if (a == max && c == max) {
                        next.add(nums.get(i)); next.add(nums.get(i+1));
                    } else if (a == max && d == max) {
                        next.add(nums.get(i+1)); next.add(nums.get(i+2));
                    } else if (b == max && c == max) {
                        next.add(nums.get(i)); next.add(nums.get(i+3));
                    } else if (b == max && d == max) {
                        next.add(nums.get(i+2)); next.add(nums.get(i+3));
                    } else if (c == max && d == max) {
                        next.add(nums.get(i+1)); next.add(nums.get(i+3));
                    }
                    i += 4;
                }
                while (i < nums.size()) {
                    next.add(nums.get(i));
                    i++;
                }

                if (next.size() == 3) {
                    int a = next.get(0);
                    int b = next.get(1);
                    int c = next.get(2);
                    int d = -1;
                    
                    // find an arbitrary guy
                    for (int ii = 1; d == -1 && ii <= n; ii++) {
                        if (ii != a && ii != b && ii != c) {
                            d = ii;
                        }
                    }
                    next.add(d);
                }

                nums = next;
            }

            if (nums.size() == 1) {
                int x = nums.get(0);
                pw.println("! " + x + " " + x);
            } else if (nums.size() == 2) {
                int x = nums.get(0);
                int y = nums.get(1);
                pw.println("! " + x + " " + y);
            } else {
                // pw.println(nums);
            }
            pw.flush();
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    List<Integer> nums;
    int queriesMade;
    int query(int i, int j, int k) throws IOException {
        queriesMade++;
        pw.println("? " + nums.get(i) + " " + nums.get(j) + " " + nums.get(k));
        pw.flush();
        return ri();
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