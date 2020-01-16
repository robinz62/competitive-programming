import java.util.*;

public class CompletelyDifferentStrings {

    public int count(int S, String[] forbidden) {
        int L = forbidden[0].length();
        int[] available = new int[L];
        for (int i = 0; i < L; i++) {
            boolean[] letters = new boolean[S];
            Arrays.fill(letters, true);
            for (String s : forbidden) {
                letters[s.charAt(i) - 'a']= false;
            }
            int count = 0;
            for (int j = 0; j < letters.length; j++) {
                if (letters[j]) count++;
            }
            available[i] = count;
        }
        int ans = 1;
        for (int i : available) {
            ans *= i;
        }
        return ans;
    }
}