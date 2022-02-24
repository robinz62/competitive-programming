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
        int T = ri();
        for (int Ti = 0; Ti < T; Ti++) {
            int[] nm = ril(2);
            int n = nm[0];
            int m = nm[1];

            int[] a = ril(n);
            int[][] badpairs = new int[m][];
            for (int i = 0; i < m; i++) {
                badpairs[i] = ril(2);
            }
            Map<Integer, Set<Integer>> bad = new HashMap<>();
            for (int[] badp : badpairs) {
                if (!bad.containsKey(badp[0])) bad.put(badp[0], new HashSet<>());
                if (!bad.containsKey(badp[1])) bad.put(badp[1], new HashSet<>());
                bad.get(badp[0]).add(badp[1]);
                bad.get(badp[1]).add(badp[0]);
            }

            Map<Integer, Integer> cnt = new HashMap<>();
            for (int ai : a) cnt.put(ai, cnt.getOrDefault(ai, 0) + 1);

            // CAREFUL OVERFLOW. CAST TO LONG.

            // tradeoff between the value of the element and the number of times it occurs
            // sort the values in some way? by size? by count?

            // How to solve with no bad pairs?
            Map<Integer, List<Integer>> cntToVals = new HashMap<>();
            for (var e : cnt.entrySet()) {
                int val = e.getKey();
                int freq = e.getValue();
                if (!cntToVals.containsKey(freq)) cntToVals.put(freq, new ArrayList<>());
                cntToVals.get(freq).add(val);
            }

            for (List<Integer> list : cntToVals.values()) Collections.sort(list);

            List<Integer> distinctCounts = new ArrayList<>(cntToVals.keySet());

            long ans = 0;
            for (int i = 0; i < distinctCounts.size(); i++) {
                int cntx = distinctCounts.get(i);
                List<Integer> valsx = cntToVals.get(cntx);

                for (int j = i; j < distinctCounts.size(); j++) {
                    int cnty = distinctCounts.get(j);
                    long cntsum = cntx + cnty;
                    List<Integer> valsy = cntToVals.get(cnty);

                    int idxx = valsx.size() - 1;
                    int idxy = valsy.size() - 1;

                    PriorityQueue<int[]> pq = new PriorityQueue<>((p1, p2) -> -Integer.compare(valsx.get(p1[0]) + valsy.get(p1[1]), valsx.get(p2[0]) + valsy.get(p2[1])));
                    pq.add(new int[]{idxx, idxy});

                    Map<Integer, Set<Integer>> seen = new HashMap<>();
                    seen.put(idxx, new HashSet<>());
                    seen.get(idxx).add(idxy);
                    
                    while (!pq.isEmpty()) {
                        int[] cand = pq.remove();
                        idxx = cand[0];
                        idxy = cand[1];
                        int xval = valsx.get(idxx);
                        int yval = valsy.get(idxy);
                        if (xval != yval && (!bad.containsKey(xval) || !bad.get(xval).contains(yval))) {
                            ans = Math.max(ans, cntsum * (xval + yval));
                            break;
                        } else {
                            if (idxx-1 >= 0 && (!seen.containsKey(idxx-1) || !seen.get(idxx-1).contains(idxy))) {
                                if (!seen.containsKey(idxx-1)) seen.put(idxx-1, new HashSet<>());
                                seen.get(idxx-1).add(idxy);
                                pq.add(new int[]{idxx-1, idxy});
                            }
                            if (idxy-1 >= 0 && (!seen.containsKey(idxx) || !seen.get(idxx).contains(idxy-1))) {
                                if (!seen.containsKey(idxx)) seen.put(idxx, new HashSet<>());
                                seen.get(idxx).add(idxy-1);
                                pq.add(new int[]{idxx, idxy-1});
                            }
                        }
                    }
                }
            }
            pw.println(ans);
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