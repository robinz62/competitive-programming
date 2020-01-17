public class ChristmasTravel {
    // Literally could never think of how to solve this
    // Recreating solution by Petr
    public String[] plan(int N, int A) {
        int[][] color = new int[N][N];  // the answer assignment
        int[] count = new int[A];       // count of how many times a color (letter) is used

        // this part is a NUT way of divide/conquer
        // numbers are split in half with depending on last bit, and a single color connects
        // all edges in the two halves
        // within each half, this process is repeated
        for (int i = 0; i < N; i++) {
            color[i][i] = -1;
            for (int j = i + 1; j < N; j++) {
                color[i][j] = color[j][i] = Integer.numberOfTrailingZeros(i ^ j);
                if (color[i][j] >= A) return new String[0];  // too many colors required
                count[color[i][j]]++;
            }
        }

        // this section is if we were "too efficient with given airports"
        // steal an edge from an airport serving more than 1
        // cannot create odd cycle by removing edges
        for (int i = 0; i < A; i++) {
            if (count[i] == 0) {
                boolean found = false;
                for (int j = 0; j < N && !found; j++) {
                    for (int k = j + 1; k < N && !found; k++) {
                        if (count[color[j][k]] > 1) {
                            count[color[j][k]]--;
                            found = true;
                            color[j][k] = color[k][j] = i;
                        }
                    }
                }
                if (!found) return new String[0];
            }
        }

        // format the answer
        String[] ans = new String[N];
        for (int i = 0; i < N; i++) {
            char[] row = new char[N];
            for (int j = 0; j < N; j++) {
                row[j] = color[i][j] == -1 ? '-' : (char) (color[i][j] + 'A');
            }
            ans[i] = new String(row);
        }
        return ans;
    }
}