import java.io.*;
import java.util.*;
 
public class Main {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pw = new PrintWriter(System.out);
 
    public static void main(String[] args) throws Exception {
        Main m = new Main();
        m.solve();
        m.close();
    }
 
    void close() throws Exception {
        pw.flush();
        pw.close();
        br.close();
    }

    int[] readInts() throws Exception {
        String[] tokens = br.readLine().split(" ");
        int[] A = new int[tokens.length];
        for (int i = 0; i < A.length; i++)
            A[i] = Integer.parseInt(tokens[i]);
        return A;
    }
    
    void solve() throws Exception {
        int q = Integer.parseInt(br.readLine());
        for (int z = 0; z < q; z++) {
            int[] line = readInts();
            int n = line[0];
            int k = line[1];
            char[] s = br.readLine().toCharArray();
            char[][] RGB = new char[][] {{'R', 'G', 'B'},
                                         {'G', 'B', 'R'},
                                         {'B', 'R', 'G'}};
            int ans = Integer.MAX_VALUE;
            for (int mode = 0; mode < 3; mode++) {
                boolean[] diff = new boolean[n];
                int diffCount = 0;
                for (int i = 0; i < k; i++) {
                    if (s[i] != RGB[mode][i % 3]) {
                        diff[i] = true;
                        diffCount++;
                    }
                }
                int minDiff = diffCount;
                for (int i = k; i < n; i++) {
                    if (diff[i - k]) diffCount--;
                    if (s[i] != RGB[mode][i % 3]) {
                        diff[i] = true;
                        diffCount++;
                    }
                    minDiff = Math.min(minDiff, diffCount);
                }
                ans = Math.min(ans, minDiff);
            }
            pw.println(ans);
        }
    }
}