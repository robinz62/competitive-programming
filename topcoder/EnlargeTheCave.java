import java.util.*;

public class EnlargeTheCave {
    public String[] enlarge(String[] cave, int desiredArea) {
        int m = cave.length;
        int n = cave[0].length();
        char[][] A = new char[m][n];
        int area = 0;

        Deque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                A[i][j] = cave[i].charAt(j);
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (A[i][j] == '.') {
                    area++;
                    if (i-1 >= 0 && A[i-1][j] == '#') q.addLast((i-1)*n + j);
                    if (i+1 < m && A[i+1][j] == '#') q.addLast((i+1)*n + j);
                    if (j-1 >= 0 && A[i][j-1] == '#') q.addLast(i*n + j-1);
                    if (j+1 < n && A[i][j+1] == '#') q.addLast(i*n + j+1);
                }
            }
        }

        while (!q.isEmpty() && area < desiredArea) {
            int next = q.removeFirst();
            int i = next / n;
            int j = next % n;
            if (A[i][j] == '#' && !releasesKobold(A, i, j)) {
                A[i][j] = '.';
                area++;
                if (i-1 >= 0 && A[i-1][j] == '#') q.addLast((i-1)*n + j);
                if (i+1 < m && A[i+1][j] == '#') q.addLast((i+1)*n + j);
                if (j-1 >= 0 && A[i][j-1] == '#') q.addLast(i*n + j-1);
                if (j+1 < n && A[i][j+1] == '#') q.addLast(i*n + j+1);
            }
        }

        if (area < desiredArea) return new String[0];
        String[] ans = new String[cave.length];
        for (int i = 0; i < A.length; i++) {
            ans[i] = new String(A[i]);
        }
        return ans;
    }

    boolean releasesKobold(char[][] A, int i, int j) {
        if (i-1 >= 0 && A[i-1][j] == 'K') return true;
        if (j-1 >= 0 && A[i][j-1] == 'K') return true;
        if (i+1 < A.length && A[i+1][j] == 'K') return true;
        if (j+1 < A[0].length && A[i][j+1] == 'K') return true;
        return false;
    }
}