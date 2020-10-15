import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static int MOD = 1000000007;

    // After writing solution, quick scan for:
    //   array out of bounds
    //   special cases e.g. n=1?
    //
    // Big numbers arithmetic bugs:
    //   int overflow
    //   sorting, or taking max, after MOD
    void solve() throws IOException {
        int n = ri();
        int[][] ab = new int[n][];
        int[] counts = new int[n];
        for (int i = 0; i < n-1; i++) {
            ab[i] = ril(2);
            ab[i][0]--;
            ab[i][1]--;
            if (ab[i][0] > ab[i][1]) {
                int temp = ab[i][0];
                ab[i][0] = ab[i][1];
                ab[i][1] = temp;
            }
            counts[ab[i][0]]++;
            counts[ab[i][1]]++;
        }
        if (counts[n-1] != n-1) {
            pw.println("NO");
            return;
        }
        List<int[]> ans = new ArrayList<>();
        boolean[] ok = new boolean[n];
        for (int i = n-2; i >= 0; i--) {
            if (counts[i] == 0) {
                if (!ok[i]) {
                    pw.println("NO");
                    return;
                }
            } else {
                int extra = counts[i]-1;
                List<Integer> chain = new ArrayList<>();
                for (int j = i-1; extra > 0 && j >= 0; j--) {
                    if (ok[j] || counts[j] > 0) continue;
                    ok[j] = true;
                    extra--;
                    chain.add(j);
                }
                chain.add(i);
                ans.add(new int[]{n-1, chain.get(0)});
                for (int j = 0; j < chain.size()-1; j++) ans.add(new int[]{chain.get(j), chain.get(j+1)});
            }
        }
        pw.println("YES");
        for (int[] e : ans) pw.println((e[0]+1) + " " + (e[1]+1));
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
}