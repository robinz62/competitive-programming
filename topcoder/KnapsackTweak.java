import java.util.*;

public class KnapsackTweak {
    public int smallest(int[] items, int target) {
        int n = items.length;
        int max_x = 200005;
        
        // dp[i][X] is if we can make value x with items[0..i]
        boolean[][] dp = new boolean[n][max_x+1];
        int[][] count = new int[n][max_x+1];  // max number of elems to make the corresponding sum
        dp[0][0] = dp[0][items[0]] = true;
        dp[0][0] = true;
        count[0][items[0]] = 1;
        for (int i = 0; i < n-1; i++) {
            for (int x = 0; x <= max_x; x++) {
                if (!dp[i][x]) continue;
                dp[i+1][x] = true;  // carry over results
                count[i+1][x] = Math.max(count[i+1][x], count[i][x]);
                if (x + items[i] <= max_x) {
                    dp[i+1][x + items[i]] = true;
                    count[i+1][x + items[i]] = Math.max(count[i+1][x + items[i]], count[i][x] + 1);
                }
            }
        }
        
        // In the final row, we have (sum, count to make that sum)
        List<int[]> ranges = new ArrayList<>();
        for (int x = 0; x <= max_x; x++) {
            if (!dp[n-1][x]) continue;
            ranges.add(new int[]{x, count[n-1][x]});
        }

        for (int[] r : ranges) System.out.println(Arrays.toString(r));

        int l = 0;
        int r = 100000;
        int ans = r;
        while (l <= r) {
            int m = l + (r - l) / 2;
            if (possible(ranges, m, target)) {
                ans = m;
                r = m-1;
            } else {
                l = m+1;
            }
        }
        return ans;
    }
    
    boolean possible(List<int[]> ranges, int X, int target) {
        for (int[] range : ranges) {
            int sum = range[0];
            int count = range[1];
            if (sum - (long) count * X <= target && target <= sum + (long) count * X) return true;
        }
        return false;
    }
}