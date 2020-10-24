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
        int[] nm = ril(2);
        int n = nm[0];
        int m = nm[1];
        List<TreeSet<Integer>> adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new TreeSet<>());
        for (int i = 0; i < m; i++) {
            int[] ab = ril(2);
            if (ab[0] < ab[1]) {
                int tmp = ab[0];
                ab[0] = ab[1];
                ab[1] = tmp;
            }
            adj.get(ab[0]-1).add(ab[1]-1);
            adj.get(ab[1]-1).add(ab[0]-1);
        }

        Deque<Integer> q = new ArrayDeque<>();
        TreeSet<Integer> need = new TreeSet<>();
        for (int i = 1; i < n; i++) need.add(i);
        q.addLast(0);
        int ans = 0;

        while (!need.isEmpty()) {
            if (q.isEmpty()) {
                int x = need.first();
                need.remove(x);
                q.addLast(x);
                ans++;
            }
            if (need.isEmpty()) break;
            int u = q.removeFirst();
            Iterator<Integer> needIter = need.iterator();
            int x = needIter.next();
            List<Integer> addFree = new ArrayList<>();  // delete these from need
            for (int v : adj.get(u)) {
                while (x < v) {
                    addFree.add(x);
                    q.addLast(x);
                    if (!needIter.hasNext()) break;
                    x = needIter.next();
                }
                if (x == v) {
                    if (!needIter.hasNext()) break;
                    x = needIter.next();
                }
            }
            if (adj.get(u).isEmpty() || x > adj.get(u).last()) {
                addFree.add(x);
                q.addLast(x);
                while (needIter.hasNext()) {
                    x = needIter.next();
                    addFree.add(x);
                    q.addLast(x);
                }
            }
            for (int y : addFree) {
                need.remove(y);
            }
        }
        
        pw.println(ans);
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