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

    int ri() throws IOException {
        return Integer.parseInt(br.readLine());
    }

    long rl() throws IOException {
        return Long.parseLong(br.readLine());
    }

    int[] ril(int n) throws IOException {
        int[] nums = new int[n];
        int c = 0;
        for (int i = 0; i < n; i++) {
            int sign = 1;
            c = br.read();
            int x = 0;
            if (c == '-') {
                sign = -1;
                c = br.read();
            }
            while (c >= '0' && c <= '9') {
                x = x * 10 + c - '0';
                c = br.read();
            }
            nums[i] = x * sign;
        }
        while (c != '\n' && c != -1) c = br.read();
        return nums;
    }

    long[] rll(int n) throws IOException {
        long[] nums = new long[n];
        int c = 0;
        for (int i = 0; i < n; i++) {
            int sign = 1;
            c = br.read();
            long x = 0;
            if (c == '-') {
                sign = -1;
                c = br.read();
            }
            while (c >= '0' && c <= '9') {
                x = x * 10 + c - '0';
                c = br.read();
            }
            nums[i] = x * sign;
        }
        while (c != '\n' && c != -1) c = br.read();
        return nums;
    }

    char[] rs() throws IOException {
        return br.readLine().toCharArray();
    }

    void solve() throws IOException {
        int[] nk = ril(2);
        int n = nk[0];
        int k = nk[1];
        int[][] segments = new int[n][];
        List<Integer> begin = new ArrayList<>();
        List<Integer> end = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            segments[i] = ril(2);
            begin.add(segments[i][0]);
            end.add(segments[i][1]);
        }
        Collections.sort(begin);
        Collections.sort(end);
        int currSegments = 0;
        int i = 0;
        int j = 0;
        List<int[]> ans = new ArrayList<>();
        int start = Integer.MIN_VALUE;
        while (i < begin.size() || j < end.size()) {
            if (i >= begin.size() || end.get(j) < begin.get(i)) {
                if (currSegments == k) {
                    ans.add(new int[]{start, end.get(j)});
                }
                currSegments--;
                j++;
            } else if (begin.get(i) <= end.get(j)) {
                if (currSegments == k-1) {
                    start = begin.get(i);
                }
                currSegments++;
                i++;
            }
        }
        pw.println(ans.size());
        for (int[] lr : ans) {
            pw.println(lr[0] + " " + lr[1]);
        }
    }
}