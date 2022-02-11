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
            int[] nq = ril(2);
            int n = nq[0];
            int q = nq[1];
            a = ril(n);
            List<List<Integer>> adj = new ArrayList<>(n);
            for (int i = 0; i < n; i++) adj.add(null);
            int[] parent = ril(n-1);
            for (int i = 0; i < n-1; i++) {
                if (adj.get(parent[i]-1) == null) adj.set(parent[i]-1, new ArrayList<>());
                adj.get(parent[i]-1).add(i+1);
            }
            
            int[][] queries = new int[q][];
            for (int i = 0; i < q; i++) {
                int[] query = ril(3);
                queries[i] = new int[]{query[0]-1, query[1], query[2], i};
            }

            queriesByNode = new ArrayList<>(n);
            for (int i = 0; i < n; i++) queriesByNode.add(null);
            for (int[] query : queries) {
                if (queriesByNode.get(query[0]) == null) queriesByNode.set(query[0], new ArrayList<>());
                queriesByNode.get(query[0]).add(query);
            }

            ans = new int[q];
            List<Integer> stack = new ArrayList<>(n);
            int[] origIdx = new int[n];
            int[] phase = new int[n];
            stack.add(0);
            int[] freq = new int[n+1];
            int[] valToIdx = new int[n+1];
            List<Integer> curr = new ArrayList<>();
            int[] ptr = new int[n];
            while (!stack.isEmpty()) {
                int u = stack.get(stack.size() - 1);
                if (phase[u] == 0) {
                    if (freq[a[u]] == 0) {
                        freq[a[u]] = 1;
                        valToIdx[a[u]] = curr.size();
                        curr.add(a[u]);
                    } else {
                        int myFreq = freq[a[u]] + 1;
                        int currIdx = valToIdx[a[u]];
                        origIdx[u] = currIdx;
                        int swapIdx = binarySearchRight(curr, freq, 0, curr.size()-1, myFreq);
                        if (swapIdx < 0) swapIdx = -swapIdx-1 - 1;
                        swapIdx++;
                        if (swapIdx < currIdx) {
                            curr.set(currIdx, curr.get(swapIdx));
                            valToIdx[curr.get(currIdx)] = currIdx;
                            curr.set(swapIdx, a[u]);
                            valToIdx[a[u]] = swapIdx;
                        }
                        freq[a[u]] = myFreq;
                    }

                    if (queriesByNode.get(u) != null) {
                        for (int[] query : queriesByNode.get(u)) {
                            int l = query[1];
                            int k = query[2]-1;
                            int idx = binarySearchRight(curr, freq, 0, curr.size()-1, l);
                            if (idx < 0) idx = -idx-1 - 1;
                            int ansIdx = idx - k;
                            if (ansIdx >= 0 && ansIdx < curr.size()) {
                                ans[query[3]] = curr.get(ansIdx);
                            } else {
                                ans[query[3]] = -1;
                            }
                        }
                    }

                    phase[u] = 1;
                } else if (phase[u] == 1) {
                    if (adj.get(u) != null && ptr[u] < adj.get(u).size()) {
                        stack.add(adj.get(u).get(ptr[u]));
                        ptr[u]++;
                    } else {
                        phase[u] = 2;
                    }
                } else if (phase[u] == 2) {
                    if (freq[a[u]] == 1) {
                        freq[a[u]] = 0;
                        curr.remove(curr.size() - 1);
                    } else {
                        freq[a[u]]--;
                        int currIdx = valToIdx[a[u]];
                        if (currIdx < origIdx[u]) {
                            curr.set(currIdx, curr.get(origIdx[u]));
                            valToIdx[curr.get(currIdx)] = currIdx;
                            curr.set(origIdx[u], a[u]);
                            valToIdx[a[u]] = origIdx[u];
                        }
                    }
                    stack.remove(stack.size() - 1);
                }
            }

            for (int x : ans) pw.print(x + " ");
            pw.println();
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    int[] a;
    List<List<int[]>> queriesByNode;
    int[] ans;

    // Find rightmost where frequency = k
    // 8 5
    //   ^ 7 goes here
    public static int binarySearchRight(List<Integer> A, int[] freq, int l, int r, int k) {
        int upper = -1;
        while (l <= r) {
            int m = l + (r - l) / 2;
            if (freq[A.get(m)] == k && (m+1==A.size() || freq[A.get(m+1)] > k)) {
                upper = m;
                break;
            }
            if (freq[A.get(m)] >= k) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return upper >= 0 ? upper : -l - 1;
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