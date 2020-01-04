import java.io.*;
import java.util.*;

public class CHEFOFR {
    public static void main(String[] args) throws IOException {
        CHEFOFR c = new CHEFOFR();
        c.solve();
    }

    public void solve() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        int N = Integer.parseInt(br.readLine());
        long[] A = new long[N + 1];
        String[] line = br.readLine().split(" ");
        for (int i = 1; i <= N; i++) {
            A[i] = Long.parseLong(line[i - 1]);
        }
        int Q = Integer.parseInt(br.readLine());
        for (int q = 0; q < Q; q++) {
            int K = Integer.parseInt(br.readLine());
            int[] t = new int[N + 1];
            line = br.readLine().split(" ");
            for (int i = 1; i <= N; i++) {
                t[i] = Integer.parseInt(line[i - 1]);
            }

            List<Long> slices = new ArrayList<>();
            for (int i = 1; i <= N; i++) {
                if (t[i] != 0) {
                    slices.add(A[i] * t[i]);
                }
            }
            int M = slices.size();

            // sumQuery[i][j] is the sum of slices[i..j]
            long[][] sumQuery = new long[M][M];
            for (int i = 0; i < M; i++) {
                sumQuery[i][i] = slices.get(i);
                for (int j = i + 1; j < M; j++) {
                    sumQuery[i][j] = sumQuery[i][j - 1] + slices.get(j);
                }
            }

            if (K > M) {
                System.out.println(0);
                continue;
            }
            if (K == M) {
                long ans = slices.get(0);
                for (long s : slices) {
                    ans &= s;
                }
                System.out.println(ans);
                continue;
            }

            List<List<Set<Long>>> dp = new ArrayList<>();
            for (int i = 0; i < M; i++) {
                List<Set<Long>> list = new ArrayList<>();
                dp.add(list);
                int k = Math.min(K, i + 1);
                for (int j = 0; j <= k; j++) {
                    list.add(null);
                }
            }

            // base case
            for (int i = 0; i < M; i++) {
                Set<Long> vals = new HashSet<>();
                vals.add(sumQuery[0][i]);
                dp.get(i).set(1, vals);
            }

            for (int i = 1; i < M; i++) {
                for (int j = 2; j <= Math.min(K, i + 1); j++) {
                    // find possible results when you have j clusters, considering only slices[0..i]
                    // the last cluster is slices[k..i]
                    Set<Long> vals = new HashSet<>();
                    for (int k = i; k > 0; k--) {
                        long Y = sumQuery[k][i];
                        if (j - 1 > k) {
                            break;
                        }
                        Set<Long> xVals = dp.get(k - 1).get(j - 1);
                        for (long X : xVals) {
                            vals.add(X & Y);
                        }
                    }
                    // values here could potentially be minimized?
                    // e.g. if value 101101 is present, a number like 101001 is "useless"
                    dp.get(i).set(j, vals);
                }
            }

            long max = 0;
            for (long res : dp.get(M - 1).get(K)) {
                max = Math.max(max, res);
            }
            System.out.println(max);
        }
        
        br.close();
    }
}