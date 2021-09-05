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
        int[] ntk = ril(3);
        int n = ntk[0];
        int t = ntk[1];
        int k = ntk[2];
        int[] a = ril(t);

        int[] parent = new int[n];
        parent[0] = -1;

        // Main trunk: root 0, then cities 1->t
        // Node t is a leaf.
        // Node x is on level x.
        for (int i = 1; i <= t; i++) parent[i] = i-1;
        for (int i = 0; i < t; i++) a[i]--;  // Update requirements.

        int id = t+1;

        int countleaves = 1;

        // leaves on each level
        List<List<Integer>> leaves = new ArrayList<>(t+1);
        // movable if it does not have a "job"
        List<List<Integer>> movable = new ArrayList<>(t+1);
        for (int i = 0; i <= t; i++) leaves.add(new ArrayList<>());
        for (int i = 0; i <= t; i++) movable.add(new ArrayList<>());
        for (int d = 1; d <= t; d++) {
            for (int times = 0; times < a[d-1]; times++) {
                parent[id] = d-1;
                leaves.get(d).add(id);
                movable.get(d).add(id);
                id++;
                countleaves++;
            }
        }

        if (countleaves < k) {
            pw.println("-1");
            return;
        }

        int leavesIdx = 0;
        while (countleaves > k) {
            while (leavesIdx < leaves.size() && leaves.get(leavesIdx).isEmpty()) leavesIdx++;
            if (leavesIdx == leaves.size()) {
                // pretty sure this is impossible
                pw.println("-1");
                return;
            }

            if (leavesIdx+1 >= leaves.size()) {
                pw.println("-1");
                return;
            }

            if (movable.get(leavesIdx+1).isEmpty()) {
                leavesIdx++;
                continue;
            }

            int leafChoice = leaves.get(leavesIdx).remove(leaves.get(leavesIdx).size() - 1);
            int child = movable.get(leavesIdx+1).remove(movable.get(leavesIdx+1).size() - 1);
            parent[child] = leafChoice;
            countleaves--;
        }

        pw.println(n);
        for (int i = 1; i < n; i++) pw.println(i+1 + " " + (parent[i]+1));
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