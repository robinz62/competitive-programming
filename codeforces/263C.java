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
        List<Set<Integer>> adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new HashSet<>());
        for (int i = 0; i < 2 * n; i++) {
            int[] ab = ril(2);
            adj.get(ab[0]-1).add(ab[1]-1);
            adj.get(ab[1]-1).add(ab[0]-1);
        }
        if (n <= 7) {
            brute(n, adj);
            return;
        }
        List<Integer> ans = new ArrayList<>(n);
        int X = 0;
        Set<Integer> l = adj.get(X);
        int Y = -1;
        int W = -1;
        for (int x : l) {
            int count = 0;
            for (int y : adj.get(x)) if (l.contains(y) || y == 0) count++;
            if (count == 3) {
                if (Y == -1) Y = x;
                else W = x;
            }
        }

        if (Y == -1 || W == -1) {
            pw.println("-1");
            return;
        }

        boolean[] used = new boolean[n];
        used[X] = used[Y] = used[W] = true;

        ans.add(W); ans.add(X); ans.add(Y);
        
        boolean ok = true;

        // Circle is now [W, X, Y]
        for (int i = 0; i < n - 3; i++) {
            // Find the neighbor of X and Y that isn't W
            boolean found = false;
            for (int cand : adj.get(X)) {
                if (!used[cand] && cand != W && adj.get(Y).contains(cand)) {
                    W = X;
                    X = Y;
                    Y = cand;
                    used[cand] = true;
                    found = true;
                    ans.add(cand);
                    break;
                }
            }
            if (!found) {
                ok = false;
                break;
            }
        }

        if (!ok) {
            pw.println("-1");
            return;
        }

        // Check answer
        for (int i = 0; ok && i < n; i++) {
            for (int d = -2; d <= 2; d++) {
                if (d == 0) continue;
                if (!adj.get(ans.get(i)).contains(ans.get((i+d+n)%n))) {
                    ok = false;
                    break;
                }
            }
        }

        if (!ok) pw.println("-1");
        else {
            for (int i : ans) pw.print((i+1) + " ");
            pw.println();
        }
    }

    void brute(int n, List<Set<Integer>> adj) {
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) nums[i] = i;
        for (List<Integer> p : permute(nums)) {
            // Check answer
            boolean ok = true;
            for (int i = 0; ok && i < n; i++) {
                for (int d = -2; d <= 2; d++) {
                    if (d == 0) continue;
                    if (!adj.get(p.get(i)).contains(p.get((i+d+n)%n))) {
                        ok = false;
                        break;
                    }
                }
            }
            if (ok) {
                for (int i : p) pw.print((i+1) + " ");
                pw.println();
                return;
            }
        }
        pw.println("-1");
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