public class BlackAndWhiteBallsEasy {
    public int getNumber(int[] balls, int white, int black) {
        int MOD = 1_000_000_007;
        int size = 0;
        for (int i : balls) size += i;
        boolean[] b = new boolean[size];
        int j = 0;
        for (int i = 0; i < balls.length; i++) {
            for (int c = 0; c < balls[i]; c++) {
                b[j++] = i % 2 == 0;
            }
        }

        // dp[i] is number of ways to form B/W segments for dp[0..i)
        long[] dp = new long[size + 1];
        dp[0] = 1;
        for (int i = 1; i <= size; i++) {  // index i is in dp-space
            // try last segment white
            int w = 0;
            for (j = i - 1; w <= white && j >= 0; j--) {  // index j is in b-space
                if (b[j]) w++;
                if (w == white) {
                    dp[i] = (dp[i] + dp[j]) % MOD;
                }
            }

            // try last segment black
            int bl = 0;
            for (j = i - 1; bl <= black && j >= 0; j--) {
                if (!b[j]) bl++;
                if (bl == black) {
                    dp[i] = (dp[i] + dp[j]) % MOD;
                }
            }
        }
        return (int) dp[size];
    }
}