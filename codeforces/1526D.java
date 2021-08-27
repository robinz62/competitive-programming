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
            char[] a = rs();
            int n = a.length;
            for (int i = 0; i < n; i++) {
                if (a[i] == 'A') a[i] = 0;
                else if (a[i] == 'N') a[i] = 1;
                else if (a[i] == 'T') a[i] = 2;
                else if (a[i] == 'O') a[i] = 3;
            }
            int[] f = new int[4];
            for (char c : a) f[c]++;
            List<List<Integer>> perms = permute(new int[]{0, 1, 2, 3});
            long ofBest = -1;
            char[] best = null;
            for (List<Integer> perm : perms) {
                StringBuilder sb = new StringBuilder();
                for (int p : perm) for (int i = 0; i < f[p]; i++) sb.append((char) p);
                char[] s = sb.toString().toCharArray();
                long count = count(a, s);
                if (count > ofBest) {
                    ofBest = count;
                    best = s;
                }
            }
            for (int i = 0; i < n; i++) {
                if (best[i] == 0) best[i] = 'A';
                else if (best[i] == 1) best[i] = 'N';
                else if (best[i] == 2) best[i] = 'T';
                else if (best[i] == 3) best[i] = 'O';
            }
            pw.println(new String(best));
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    long count(char[] a, char[] b) {
        BIT bit = new BIT(a.length);
        List<List<Integer>> l = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) l.add(new ArrayList<>());
        for (int i = 0; i < a.length; i++) l.get((int) a[i]).add(i);
        int[] ptrs = new int[4];
        long ans = 0;
        for (int i = 0; i < b.length; i++) {
            int c = b[i];
            int idx = l.get(c).get(ptrs[c]++);
            ans += idx - i + (bit.query(a.length) - bit.query(idx));
            bit.add(idx, 1);
        }
        return ans;
    }

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        helper(nums, new boolean[nums.length], ans, new ArrayList<>());
        return ans;
    }
    
    public void helper(int[] nums, boolean[] used, List<List<Integer>> ans, List<Integer> curr) {
        if (allDone(used)) {
            List<Integer> solution = new ArrayList<>(curr);
            ans.add(solution);
        }
        for (int i = 0; i < nums.length; ++i) {
            if (used[i]) continue;
            curr.add(nums[i]);
            used[i] = true;
            helper(nums, used, ans, curr);
            used[i] = false;
            curr.remove(curr.size() - 1);
        }
    }
    
    private boolean allDone(boolean[] used) {
        for (boolean b : used) {
            if (!b) return false;
        }
        return true;
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

    void add(int i, int value) {
        i++;
        while (i < bit.length) {
            bit[i] += value;
            i += i & -i;
        }
    }
}