import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);

        int n = Integer.parseInt(br.readLine());
        int[][] pref = new int[n][n];
        for (int i = 0; i < n; i++) {
            String[] line = br.readLine().split(" ");
            for (int j = 0; j < n; j++) pref[i][j] = Integer.parseInt(line[j]) - 1;
        }

        int[][] map = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                map[i][pref[i][j]] = j;
            }
        }

        List<List<Integer>> adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < map[i][i]; j++) adj.get(i).add(pref[i][j]);
        }

        List<List<Integer>> reverse = new ArrayList<>(n);
        for (int i = 0; i < n; i++) reverse.add(new ArrayList<>());
        for (int i = 0; i < n; i++) {
            for (int j : adj.get(i)) reverse.get(j).add(i);
        }

        long[][] dp3 = new long[1 << n][n];  // whether there is a hamiltonian path for mask ending at i
        for (int mask = 1; mask < (1 << n); mask++) {
            int root = Integer.highestOneBit(mask);
            int rootcow = Integer.numberOfTrailingZeros(root);
            if (root == mask) {
                dp3[mask][rootcow] = 1;
                continue;
            }

            for (int last = 0; last < n; last++) {
                if (((1 << last) & mask) == 0) continue;
                if (last == rootcow) continue;
                for (int before : reverse.get(last)) {
                    if (((1 << before) & mask) == 0) continue;
                    dp3[mask][last] += dp3[mask ^ (1 << last)][before];
                }
            }
        }

        dp2 = new long[1 << n];
        dp2[0] = 1;
        for (int mask = 1; mask < (1 << n); mask++) {
            int root = Integer.highestOneBit(mask);
            int rootcow = Integer.numberOfTrailingZeros(root);

            if (mask == root) dp2[mask]++;  // singleton

            for (int last = 0; last < n; last++) {
                if (adj.get(last).contains(rootcow)) {
                    dp2[mask] += dp3[mask][last];
                }
            }
        }

        // Want to solve the problem for all possible subsets (~2*10^5)
        // dp[0] = 1
        dp = new long[1 << n];
        dp[0] = 1;
        for (int mask = 1; mask < (1 << n); mask++) {
            int m = Integer.bitCount(mask);

            int root = Integer.highestOneBit(mask);
            int rootcow = Integer.numberOfTrailingZeros(root);

            // s is the cycle submask
            int s = mask;
            while (s > 0) {
                // use s here
                // if (dp2[s] != 0 && dp[mask ^ s] != 0) {
                // if (mask == 5) {
                //     pw.println(Integer.toString(mask, 2) + " adding " + (dp2[s]) + " * " + dp[mask ^ s]);
                //     pw.println(Integer.toString(s, 2) + " " + Integer.toString(mask ^ s, 2));
                // }
                if ((s & root) != 0) dp[mask] += dp2[s] * dp[mask ^ s];

                s = (s-1) & mask;
            }
            
            // backtrack(mask, rootcow, rootcow, adj, mask ^ root);
            // pw.println("answer: " + Integer.toString(mask, 2) + " " + dp[mask]);
        }

        // for each submask, how many ways to reach that submask?
        // then do dp[mask] += (# ways) * dp[submask]

        int q = Integer.parseInt(br.readLine());
        for (int qi = 0; qi < q; qi++) {
            char[] s = br.readLine().toCharArray();
            int gmask = 0;
            int hmask = 0;
            for (int i = 0; i < n; i++) {
                if (s[i] == 'G') gmask ^= 1 << i;
                else hmask ^= 1 << i;
            }
            pw.println(dp[gmask] * dp[hmask]);
        }

        pw.flush();
    }

    // dp2[mask] is the number of hamiltonian cycles for mask
    static long[] dp2;

    static long[] dp;
    static void backtrack(int initmask, int rootcow, int u, List<List<Integer>> adj, int mask) {
        for (int v : adj.get(u)) {
            if (v == rootcow) {
                dp[initmask] += dp[mask];
                continue;
            }
            if (((1 << v) & mask) == 0) continue;
            backtrack(initmask, rootcow, v, adj, mask ^ (1 << v));
        }
    }
}