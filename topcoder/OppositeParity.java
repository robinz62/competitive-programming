import java.util.*;
public class OppositeParity {
    public int[] rearrange(int[] A) {
        Deque<Integer> even = new ArrayDeque<>();
        Deque<Integer> odd = new ArrayDeque<>();
        for (int i = 0; i < A.length; i++) {
            if (A[i] % 2 == 0) {
                even.addLast(A[i]);
            } else {
                odd.addLast(A[i]);
            }
        }
        if (even.size() != odd.size()) return new int[0];
        int[] ans = new int[A.length];
        for (int i = 0; i < ans.length; i++) {
            if (A[i] % 2 == 0) {
                ans[i] = odd.removeFirst();
            } else {
                ans[i] = even.removeFirst();
            }
        }
        return ans;
    }
}