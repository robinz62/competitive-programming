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
        int[] line = readIntLine();
        int N = line[0];
        int Q = line[1];
        int[] P = readIntLine();
        MinST minSt = new MinST(P);
        MaxST maxSt = new MaxST(P);

        // subtask 1 O(N^2)
        boolean[][] ok = new boolean[N][N];  // ok[i][j] if P[i..j] is ok
        for (int i = 0; i < N; i++) {
            for (int j = i; j < N; j++) {
                int min = minSt.query(i, j + 1);
                int max = maxSt.query(i, j + 1);
                ok[i][j] = max - min == j - i;
            }
        }
        int[][] dp = new int[N][N];  // dp[i][j] is strongest in P[i..j]
        for (int i = 0; i < N; i++) dp[i][i] = 1;
        for (int len = 2; len <= N; len++) {
            for (int i = 0; i + len - 1 < N; i++) {
                dp[i][i + len - 1] = ok[i][i + len - 1] ? len : Math.max(dp[i][i + len - 2], dp[i + 1][i + len - 1]);
            }
        }

        int last = 0;
        for (int q = 0; q < Q; q++) {
            line = readIntLine();
            int X = line[0];
            int Y = line[1];
            int L = ((X + last - 1) % N) + 1;
            int R = ((Y + last - 1) % N) + 1;
            if (L > R) {
                int temp = L;
                L = R;
                R = temp;
            }

            last = dp[L-1][R-1];
            pw.println(last);
        }
    }
}

class MinST {
    private int n;
    private int[] arr;

    public MinST(int[] arr) {
        n = arr.length;
        this.arr = new int[n * 2];
        for (int i = 0; i < n; i++) {
            this.arr[n + i] = arr[i];
        }
        build();
    }

    private void build() {
        for (int i = n - 1; i > 0; i--) {
            arr[i] = Math.min(arr[i * 2], arr[i * 2 + 1]);
        }
    }

    public void modify(int i, int value) {
        arr[n + i] = value;
        for (i = (n + i) / 2; i > 0; i /= 2) {
            arr[i] = Math.min(arr[i * 2], arr[i * 2 + 1]);
        }
    }

    public int query(int l, int r) {
        int min = arr[n + l];
        l += n;
        r += n;
        while (l < r) {
            if ((l & 1) > 0) {
                min = Math.min(min, arr[l++]);
            }
            if ((r & 1) > 0) {
                min = Math.min(min, arr[--r]);
            }
            l /= 2;
            r /= 2;
        }
        return min;
    }
}

class MaxST {
    private int n;
    private int[] arr;

    public MaxST(int[] arr) {
        n = arr.length;
        this.arr = new int[n * 2];
        for (int i = 0; i < n; i++) {
            this.arr[n + i] = arr[i];
        }
        build();
    }

    private void build() {
        for (int i = n - 1; i > 0; i--) {
            arr[i] = Math.max(arr[i * 2], arr[i * 2 + 1]);
        }
    }

    public void modify(int i, int value) {
        arr[n + i] = value;
        for (i = (n + i) / 2; i > 0; i /= 2) {
            arr[i] = Math.max(arr[i * 2], arr[i * 2 + 1]);
        }
    }

    public int query(int l, int r) {
        int max = arr[n + l];
        l += n;
        r += n;
        while (l < r) {
            if ((l & 1) > 0) {
                max = Math.max(max, arr[l++]);
            }
            if ((r & 1) > 0) {
                max = Math.max(max, arr[--r]);
            }
            l /= 2;
            r /= 2;
        }
        return max;
    }
}