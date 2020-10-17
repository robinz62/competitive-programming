import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static int MOD = 1000000007;

    // After writing solution, quick scan for:
    //   array out of bounds
    //   special cases e.g. n=1?
    //
    // Big numbers arithmetic bugs:
    //   int overflow
    //   sorting, or taking max, after MOD
    void solve() throws IOException {
        int n = ri();
        int[] a = ril(n);
        
        // 2s need to match with a 1 on the right
        // 3s match with a 3 on the right, the first 3 needs a 1 or 2, and it is
        //   better to take a 2 than 1.
        List<Integer> ones = new ArrayList<>();
        int three = -1;
        List<List<Integer>> cols = new ArrayList<>(n);
        for (int i = 0; i < n; i++) cols.add(new ArrayList<>());
        int available = n-1;
        for (int i = n-1; i >= 0; i--) {
            if (a[i] == 0) continue;
            if (a[i] == 1) {
                if (available < 0) {
                    pw.println("-1");
                    return;
                }
                cols.get(i).add(available--);
                ones.add(i);
            } else if (a[i] == 2) {
                if (ones.isEmpty()) {
                    pw.println("-1");
                    return;
                }
                int j = ones.get(ones.size()-1);
                ones.remove(ones.size()-1);
                cols.get(i).add(cols.get(j).get(0));
                three = i;
            } else if (a[i] == 3) {
                if (three == -1) {
                    if (ones.isEmpty()) {
                        pw.println("-1");
                        return;
                    }
                    int j = ones.get(ones.size()-1);
                    ones.remove(ones.size()-1);
                    if (available < 0) {
                        pw.println("-1");
                        return;
                    }
                    int id = available--;
                    cols.get(j).add(id);
                    cols.get(i).add(id);
                } else {
                    if (available < 0) {
                        pw.println("-1");
                        return;
                    }
                    int id = available--;
                    int j = three;
                    cols.get(j).add(id);
                    cols.get(i).add(id);
                }
                three = i;
            }
        }
        int ans = 0;
        for (List<Integer> c : cols) ans += c.size();
        pw.println(ans);
        for (int i = 0; i < n; i++) {
            for (int j : cols.get(i)) {
                pw.println((j+1) + " " + (i+1));
            }
        }
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
}