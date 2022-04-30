import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Solution {
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
        for (int Ti = 1; Ti <= T; Ti++) {
            int n = ri();
            String[] towers = br.readLine().split(" ");

            char[][] reduced = new char[n][2];
            boolean ok = true;
            boolean[] singleton = new boolean[n];
            int[] singleCounts = new int[26];

            boolean[] usedInternal = new boolean[26];
            boolean[] existsCap = new boolean[26];
            for (int i = 0; ok && i < n; i++) {
                reduced[i][0] = towers[i].charAt(0);
                reduced[i][1] = towers[i].charAt(towers[i].length()-1);

                if (usedInternal[reduced[i][0] - 'A'] || usedInternal[reduced[i][1] - 'A']) {
                    ok = false;
                    break;
                }

                existsCap[reduced[i][0] - 'A'] = existsCap[reduced[i][1] - 'A'] = true;

                boolean[] done = new boolean[26];
                done[towers[i].charAt(0) - 'A'] = true;
                for (int j = 1; j < towers[i].length(); j++) {
                    if (towers[i].charAt(j) == towers[i].charAt(j-1)) continue;
                    else {
                        if (done[towers[i].charAt(j) - 'A']) ok = false;
                        else done[towers[i].charAt(j) - 'A'] = true;
                    }
                }

                if (reduced[i][0] == reduced[i][1]) {
                    char c = reduced[i][0];
                    singleCounts[c - 'A'] += towers[i].length();
                    singleton[i] = true;
                }

                boolean[] internal = new boolean[26];
                for (int j = 0; j < towers[i].length(); j++) {
                    char c = towers[i].charAt(j);
                    if (c != reduced[i][0] && c != reduced[i][1]) {
                        internal[c-'A'] = true;
                    }
                }

                for (int x = 0; x < 26; x++) {
                    if (internal[x]) {
                        if (usedInternal[x]) {
                            ok = false;
                            break;
                        }
                        if (existsCap[x]) {
                            ok = false;
                            break;
                        }
                        usedInternal[x] = true;
                    }
                }
            }

            // Can't have two nonsingletons end in the same thing or start with the same thing
            for (int i = 0; ok && i < n; i++) {
                if (singleton[i]) continue;
                for (int j = 0; j < n; j++) {
                    if (singleton[j]) continue;
                    if (i == j) continue;
                    if (reduced[i][0] == reduced[j][0]) ok = false;
                    if (reduced[i][1] == reduced[j][1]) ok = false;
                }
            }

            if (!ok) {
                printAnswer(Ti, "IMPOSSIBLE");
                continue;
            }

            StringBuilder ans = new StringBuilder();
            // Get rid of singletons first
            boolean[] used = new boolean[n];
            for (char c = 'A'; c <= 'Z'; c++) {
                // If the singleton is part of another block, tack it on, otherwise append it to the answer
                boolean foundMatching = false;
                StringBuilder me = new StringBuilder();
                for (int x = 0; x < singleCounts[c-'A']; x++) me.append(c);
                for (int i = 0; i < n; i++) {
                    if (singleton[i]) continue;
                    if (reduced[i][0] == c) {
                        towers[i] = me + towers[i];
                        foundMatching = true;
                        break;
                    }
                    if (reduced[i][1] == c) {
                        towers[i] = towers[i] + me;
                        foundMatching = true;
                        break;
                    }
                }

                if (!foundMatching) {
                    ans.append(me);
                }
            }

            for (int i = 0; i < n; i++) if (singleton[i]) used[i] = true;
            
            // At this point, we are left only with letters that have distinct start/end
            
            List<List<Integer>> adj = new ArrayList<>(n);  // points end to beginning
            for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
            int[] indegree = new int[n];
            for (int i = 0; i < n; i++) {
                if (singleton[i]) continue;
                for (int j = 0; j < n; j++) {
                    if (i == j) continue;
                    if (singleton[j]) continue;
                    if (reduced[i][1] == reduced[j][0]) {
                        adj.get(i).add(j);
                        indegree[j]++;
                    }
                }
            }

            // WE MUST FIND A PATH GRAPH NOW. CAN HAVE MULTIPLE DISJOINT PATHS.

            for (int i = 0; ok && i < n; i++) if (indegree[i] > 1) ok = false;
            if (!ok) {
                printAnswer(Ti, "IMPOSSIBLE");
                continue;
            }

            boolean go = false;
            for (boolean b : used) if (!b) go = true;
            while (go) {
                go = false;

                boolean progress = false;
                for (int i = 0; i < n; i++) {
                    if (used[i]) continue;
                    if (indegree[i] == 0) {
                        List<Integer> build = new ArrayList<>();
                        int curr = i;
                        build.add(curr);
                        used[curr] = true;
                        while (!adj.get(curr).isEmpty()) {
                            if (adj.get(curr).size() > 1) {
                                ok = false;
                                break;
                            }
                            curr = adj.get(curr).get(0);
                            build.add(curr);
                            if (used[curr]) {
                                ok = false;
                                break;
                            }
                            used[curr] = true;
                        }
                        if (!ok) break;

                        go = true;
                        for (int idx : build) ans.append(towers[idx]);
                        progress = true;
                        break;
                    }
                }
                if (!progress) {
                    ok = false;
                }
                if (!ok) break;

                boolean done = true;
                for (boolean b : used) if (!b) done = false;
                if (!done) go = true;
                else break;
            }
            
            printAnswer(Ti, ok ? ans.toString() : "IMPOSSIBLE");
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    // Template code below

    void printAnswer(int i, String ans) {
        pw.println("Case #" + i + ": " + ans);
    }

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pw = new PrintWriter(System.out);

    public static void main(String[] args) throws IOException {
        Solution m = new Solution();
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