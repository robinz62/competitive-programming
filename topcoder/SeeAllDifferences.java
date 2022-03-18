import java.util.*;

public class SeeAllDifferences {
    public double solve(int D, int[] rolled) {
        int init = (1 << D) - 1;
        for (int i = 1; i < rolled.length; i++) {
            int diff = Math.abs(rolled[i] - rolled[i-1]);
            init = init & ~(1 << diff);
        }
        
        int start = rolled[rolled.length-1];
        
        // dp[mask][v] is the expected rolls for mask if we start on v
        double[][] dp = new double[1 << D][D];  // rolls are [0..D-1]
        Arrays.fill(dp[0], 0);
        for (int i = 1; i < (1 << D); i++) {
            double[][] system = new double[D][D+1];
            for (int di = 0; di < D; di++) system[di][di] = 1;
            for (int v = 0; v < D; v++) {
                double p = 1.0 / D;
                for (int w = 0; w < D; w++) {
                    int diff = Math.abs(v - w);
                    if (((1 << diff) & i) == 0) {
                        system[v][w] -= p;
                        system[v][D] += p;
                    } else {
                        system[v][D] += p * (1 + dp[i ^ (1 << diff)][w]);
                    }
                }
            }

            
            double[] ans = new double[D];
            gauss(system, ans);
            
            for (int v = 0; v < D; v++) dp[i][v] = ans[v];
        }
        
        return dp[init][start-1];
    }
    
    double EPS = 1e-9;
	int INF = 2; // it doesn't actually have to be infinity or a big number
    
    int gauss(double[][] a, double[] ans) {
        int n = a.length;
        int m = a[0].length - 1;

        int[] where = new int[m];
        Arrays.fill(where, -1);
        for (int col=0, row=0; col<m && row<n; ++col) {
            int sel = row;
            for (int i=row; i<n; ++i)
                if (Math.abs(a[i][col]) > Math.abs(a[sel][col]))
                    sel = i;
            if (Math.abs(a[sel][col]) < EPS)
                continue;
            for (int i=col; i<=m; ++i) {
                double temp = a[sel][i];
                a[sel][i] = a[row][i];
                a[row][i] = temp;
            }
            where[col] = row;

            for (int i=0; i<n; ++i)
                if (i != row) {
                    double c = a[i][col] / a[row][col];
                    for (int j=col; j<=m; ++j)
                        a[i][j] -= a[row][j] * c;
                }
            ++row;
        }

        // ans.assign (m, 0);
        for (int i=0; i<m; ++i)
            if (where[i] != -1)
                ans[i] = a[where[i]][m] / a[where[i]][i];
        for (int i=0; i<n; ++i) {
            double sum = 0;
            for (int j=0; j<m; ++j)
                sum += ans[j] * a[i][j];
            if (Math.abs(sum - a[i][m]) > EPS)
                return 0;
        }

        for (int i=0; i<m; ++i)
            if (where[i] == -1)
                return INF;
        return 1;
    }
}