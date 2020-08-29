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
        int t = ri();
        for (int ti = 0; ti < t; ti++) {
            int[] NK = ril(2);
            int N = NK[0];
            int K = NK[1];
            long[] SK = rll(K);
            long[] ABCDS = rll(4);
            long AS = ABCDS[0];
            long BS = ABCDS[1];
            long CS = ABCDS[2];
            long DS = ABCDS[3];
            long[] XK = rll(K);
            long[] ABCDX = rll(4);
            long AX = ABCDX[0];
            long BX = ABCDX[1];
            long CX = ABCDX[2];
            long DX = ABCDX[3];
            long[] YK = rll(K);
            long[] ABCDY = rll(4);
            long AY = ABCDY[0];
            long BY = ABCDY[1];
            long CY = ABCDY[2];
            long DY = ABCDY[3];

            long[] S = new long[N];
            long[] X = new long[N];
            long[] Y = new long[N];
            System.arraycopy(SK, 0, S, 0, K);
            System.arraycopy(XK, 0, X, 0, K);
            System.arraycopy(YK, 0, Y, 0, K);
            for (int i = K; i < N; i++) {
                S[i] = (AS * S[i-2] + BS * S[i-1] + CS) % DS;
                X[i] = (AX * X[i-2] + BX * X[i-1] + CX) % DX;
                Y[i] = (AY * Y[i-2] + BY * Y[i-1] + CY) % DY;
            }

            long surplus = 0;
            long deficit = 0;
            long canTake = 0;
            long canAdd = 0;
            for (int i = 0; i < N; i++) {
                long L = X[i];
                long R = X[i] + Y[i];
                if (S[i] < L) {
                    deficit += L - S[i];
                    canAdd += (L - S[i]) + R - L;
                } else if (S[i] > R) {
                    surplus += S[i] - R;
                    canTake += (S[i] - R) + R - L;
                } else {
                    canTake += S[i] - L;
                    canAdd += R - S[i];
                }
            }

            long ans = 0;
            if (surplus == 0 && deficit == 0) {
                pw.println("Case #" + (ti+1) + ": 0");
                continue;
            } else if (surplus > deficit) {
                ans += deficit;
                surplus -= deficit;
                canAdd -= deficit;
                deficit = 0;
                if (canAdd >= surplus) {
                    ans += surplus;
                } else {
                    ans = -1;
                }
            } else {
                ans += surplus;
                deficit -= surplus;
                canTake -= surplus;
                surplus = 0;
                if (canTake >= deficit) {
                    ans += deficit;
                } else {
                    ans = -1;
                }
            }

            pw.println("Case #" + (ti+1) + ": " + ans);
        }
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

    int max(int a, int b) {
        return a >= b ? a : b;
    }

    int min(int a, int b) {
        return a <= b ? a : b;
    }
}