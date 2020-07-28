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
        int T = ri();
        for (int Ti = 0; Ti < T; Ti++) {
            int[] NM = ril(2);
            int N = NM[0];
            int M = NM[1];
            long[] C = new long[N];
            for (int i = 0; i < N; i++) C[i] = ri();
            for (int i = 0; i < N; i++) if (C[i] == 0) C[i] = Long.MAX_VALUE;
            
            if (N <= M + 1) {
                pw.println("Case #" + (Ti+1) + ": 0"); 
                continue;
            }

            long ans = Long.MAX_VALUE;
            // st[i] stores dp[i] + C_i
            SegmentTree st = new SegmentTree(C);
            for (int i = M+1; i < N - 1; i++) {
                if (C[i] == Long.MAX_VALUE) {
                    st.modify(i, Long.MAX_VALUE);
                    continue;
                }
                long min = st.query(i - M, i);
                if (min == Long.MAX_VALUE) st.modify(i, Long.MAX_VALUE);
                else st.modify(i, min + C[i]);
            }
            ans = st.query(N-1 - M, N-1);
            if (ans == Long.MAX_VALUE) ans = -1;

            pw.println("Case #" + (Ti+1) + ": " + ans);
        }
    }
}

class SegmentTree {
    int n;
    long[] st;

    // Modify identity and combine together.
    long identity = Long.MAX_VALUE;
    long combine(long a, long b) {
        return Math.min(a, b);
    }

    SegmentTree(long[] arr) {
        n = arr.length;
        st = new long[n*2];
        for (int i = 0; i < n; i++) {
            st[n + i] = arr[i];
        }
        build();
    }

    void build() {
        for (int i = n - 1; i > 0; i--) {
            st[i] = combine(st[i*2], st[i*2+1]);
        }
    }

    void modify(int i, long value) {
        st[n + i] = value;
        for (i = (n + i) / 2; i > 0; i /= 2) {
            st[i] = combine(st[i*2], st[i*2+1]);
        }
    }

    // Note: input range is half-open [l, r)
    long query(int l, int r) {
        l += n;
        r += n;
        long resl = identity;
        long resr = identity;
        while (l < r) {
            if ((l & 1) > 0) resl = combine(resl, st[l++]);
            if ((r & 1) > 0) resr = combine(st[--r], resr);
            l /= 2;
            r /= 2;
        }
        return combine(resl, resr);
    }
}