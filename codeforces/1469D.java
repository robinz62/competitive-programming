import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static int MOD = 1000000007;

    // After writing solution, quick scan for:
    //   array out of bounds
    //   special cases e.g. n=1?
    //   npe, particularly in maps
    //
    // Big numbers arithmetic bugs:
    //   int overflow
    //   sorting, or taking max, after MOD
    void solve() throws IOException {
        int T = ri();
        for (int Ti = 0; Ti < T; Ti++) {
            int n = ri();
            List<Integer> keypoints = new ArrayList<>();
            int x = n;
            while (x > 2) {
                keypoints.add(x);
                x = (int) (Math.sqrt(x) + 1);
            }
            Set<Integer> set = new HashSet<>();
            for (int i : keypoints) set.add(i);
            List<int[]> ans = new ArrayList<>();
            for (int i = 3; i <= n; i++) {
                if (!set.contains(i)) ans.add(new int[]{i, n});
            }
            for (int i = 0; i < keypoints.size()-1; i++) {
                ans.add(new int[]{keypoints.get(i), keypoints.get(i+1)});
                ans.add(new int[]{keypoints.get(i), keypoints.get(i+1)});
            }
            ans.add(new int[]{keypoints.get(keypoints.size()-1), 2});
            ans.add(new int[]{keypoints.get(keypoints.size()-1), 2});
            pw.println(ans.size());
            for (int[] arr : ans) {
                pw.println(arr[0] + " " + arr[1]);
            }
            // check(n, ans);
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    void check(int n, List<int[]> ans) {
        int[] arr = new int[n+1];
        for (int i = 1; i <= n; i++) arr[i] = i;
        for (int[] a : ans) {
            int x = a[0];
            int y = a[1];
            arr[x] = arr[x] % arr[y] == 0 ? arr[x] / arr[y] : arr[x] / arr[y] + 1;
        }
        Map<Integer, Integer> res = new HashMap<>();
        for (int i = 1; i <= n; i++) res.put(arr[i], res.getOrDefault(arr[i], 0) + 1);
        pw.println(res);
    }

    // Template code below

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pw = new PrintWriter(System.out);

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
        return Integer.parseInt(br.readLine().trim());
    }

    long rl() throws IOException {
        return Long.parseLong(br.readLine().trim());
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

    int[] rkil() throws IOException {
        int sign = 1;
        int c = br.read();
        int x = 0;
        if (c == '-') {
            sign = -1;
            c = br.read();
        }
        while (c >= '0' && c <= '9') {
            x = x * 10 + c - '0';
            c = br.read();
        }
        return ril(x);
    }

    long[] rkll() throws IOException {
        int sign = 1;
        int c = br.read();
        int x = 0;
        if (c == '-') {
            sign = -1;
            c = br.read();
        }
        while (c >= '0' && c <= '9') {
            x = x * 10 + c - '0';
            c = br.read();
        }
        return rll(x);
    }

    char[] rs() throws IOException {
        return br.readLine().toCharArray();
    }

    void sort(int[] A) {
        Random r = new Random();
        for (int i = A.length-1; i > 0; i--) {
            int j = r.nextInt(i+1);
            int temp = A[i];
            A[i] = A[j];
            A[j] = temp;
        }
        Arrays.sort(A);
    }

    void printDouble(double d) {
        pw.printf("%.16f", d);
    }
}