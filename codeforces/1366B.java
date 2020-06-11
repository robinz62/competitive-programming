import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pw = new PrintWriter(System.out);
    static int MOD = 1000000007;

    public static void main(String[] args) throws IOException {
        Main m = new Main();
        m.solve();
        m.close();
    }

    void close() throws IOException {
        pw.flush();
        pw.close();
        br.close();
    }

    int readInt() throws IOException {
        return Integer.parseInt(br.readLine());
    }

    long readLong() throws IOException {
        return Long.parseLong(br.readLine());
    }

    int[] readIntLine() throws IOException {
        String[] tokens = br.readLine().split(" ");
        int[] A = new int[tokens.length];
        for (int i = 0; i < A.length; i++)
            A[i] = Integer.parseInt(tokens[i]);
        return A;
    }

    long[] readLongLine() throws IOException {
        String[] tokens = br.readLine().split(" ");
        long[] A = new long[tokens.length];
        for (int i = 0; i < A.length; i++)
            A[i] = Long.parseLong(tokens[i]);
        return A;
    }

    void solve() throws IOException {
        int t = readInt();
        for (int ti = 0; ti < t; ti++) {
            int[] nxm = readIntLine();
            int n = nxm[0];
            int x = nxm[1];  // a[x] = 1
            int m = nxm[2];
            int[][] lr = new int[m][];
            List<int[]> intervals = new ArrayList<>();
            intervals.add(new int[]{x, x});
            for (int i = 0; i < m; i++) {
                lr[i] = readIntLine();
                boolean intersect = false;
                for (int[] interval : intervals) {
                    if (lr[i][1] >= interval[0] && interval[1] >= lr[i][0]) {
                        intersect = true;
                        break;
                    }
                }
                if (intersect) {
                    intervals.add(lr[i]);
                    intervals = merge(intervals);
                }
            }
            int ans = 0;
            for (int[] a : intervals) {
                ans += a[1] - a[0] + 1;
            }
            pw.println(ans);
        }
    }

    public List<int[]> merge(List<int[]> intervals) {
        if (intervals.size() == 0) return intervals;
        List<int[]> ans = new ArrayList<>();
        Collections.sort(intervals, new Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                return a[0] - b[0];
            }
        });
        
        int i = 0;
        int start;
        int end;
        while (i < intervals.size()) {
            start = intervals.get(i)[0];
            end = intervals.get(i)[1];
            ++i;
            while (i < intervals.size() && intervals.get(i)[0] <= end) {
                end = Math.max(end, intervals.get(i)[1]);
                ++i;
            }
            ans.add(new int[]{start, end});
        }
        return ans;
    }
}