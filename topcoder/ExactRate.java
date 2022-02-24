import java.util.*;

// FST because I forgot to cast seed as a long, which would not have happened
// if input was from stdin like every other website
//
// Topcoder really be no chill tho
public class ExactRate {
    static long MOD = 1l << 31;
    
    public int[] getLongest(int N, int seed, int threshold, int S, int F) {
        long[] H = new long[N];
        H[0] = ((long) seed * 1103515245 + 12345) % MOD;
        for (int i = 1; i < N; i++) H[i] = (H[i-1] * 1103515245 + 12345) % MOD;
        
        int g = gcd(S, F);
        S = S / g;
        F = F / g;
        for (int i = 0; i < N; i++) H[i] = H[i] > threshold ? F : -S;
       
        Map<Long, Integer> valToIdx = new HashMap<>();
        valToIdx.put(0l, -1);
        long sum = 0;
        int[] ans = new int[]{0, -1};
        for (int i = 0; i < N; i++) {
            sum += H[i];
            if (valToIdx.containsKey(sum)) {
                int idx = valToIdx.get(sum);
                int len = i - idx;
                if (len > ans[1] - ans[0] + 1) {
                    ans[0] = idx+1;
                    ans[1] = i;
                }
            } else {
                valToIdx.put(sum, i);
            }
        }
        ans[1]++;
        return ans;
    }
    
    public static int gcd(int a, int b) {
        return a == 0 ? b : gcd(b % a, a);
    }
}