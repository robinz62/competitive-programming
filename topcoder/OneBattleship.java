// This problem is the same as https://codeforces.com/problemset/problem/1450/C1
// so kinda sux that I didn't remember how to do it during contest :(

import java.util.*;

public class OneBattleship {
    public String[] hit(String[] a) {
        int m = a.length;
        int n = a[0].length();
        char[][] grid = new char[m][];
        for (int i = 0; i < m; i++) {
            grid[i] = a[i].toCharArray();
        }
        
        List<List<int[]>> dots = new ArrayList<>(3);
        for (int i = 0; i < 3; i++) dots.add(new ArrayList<>());

        int N = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '.') {
                    dots.get((i + j) % 3).add(new int[]{i, j});
                    N++;
                }
            }
        }
        int x = 2;
        if (dots.get(0).size() * 3 <= N) x = 0;
        else if (dots.get(1).size() * 3 <= N) x = 1;

        for (int[] rc : dots.get(x)) {
            grid[rc[0]][rc[1]] = '*';
        }

        String[] ans = new String[m];
        for (int i = 0; i < m; i++) ans[i] = new String(grid[i]);
        return ans;
    }
}