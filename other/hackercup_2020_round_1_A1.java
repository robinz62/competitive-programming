import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static int MOD = 1000000007;

    // After writing solution, quick scan for:
    //   int overflow
    //   array out of bounds
    //   special cases e.g. n=1?
    void solve() throws IOException {
        int t = ri();
        for (int ti = 0; ti < t; ti++) {
            long[] NKW = rll(3);
            int N = (int) NKW[0];
            int K = (int) NKW[1];
            long W = NKW[2];
            long[] LK = rll(K);
            long[] ABCDL = rll(4);
            long AL = ABCDL[0];
            long BL = ABCDL[1];
            long CL = ABCDL[2];
            long DL = ABCDL[3];
            long[] HK = rll(K);
            long[] ABCDH = rll(4);
            long AH = ABCDH[0];
            long BH = ABCDH[1];
            long CH = ABCDH[2];
            long DH = ABCDH[3];

            long[] L = new long[N];
            long[] H = new long[N];
            for (int i = 0; i < K; i++) {
                L[i] = LK[i];
                H[i] = HK[i];
            }
            for (int i = K; i < N; i++) {
                L[i] = ((AL * L[i-2] + BL * L[i-1] + CL) % DL) + 1;
                H[i] = ((AH * H[i-2] + BH * H[i-1] + CH) % DH) + 1;
            }

            // contains height info: [right bound, height]
            Deque<long[]> deque = new ArrayDeque<>();
            deque.addLast(new long[]{L[0] + W, H[0]});
            long currP = (2 * W + 2 * H[0]) % MOD;
            long ans = currP;
            for (int i = 1; i < N; i++) {
                while (!deque.isEmpty() && deque.peekFirst()[0] < L[i]) deque.removeFirst();
                if (L[i] > L[i-1] + W) {
                    currP = (currP + 2 * W) % MOD;
                    currP = (currP + 2 * H[i]) % MOD;
                } else {
                    long overlap = L[i-1] + W - L[i];
                    currP = (currP + 2 * (W - overlap)) % MOD;

                    if (!deque.isEmpty() && H[i] > deque.peekFirst()[1]) {
                        long diff = H[i] - deque.peekFirst()[1];
                        currP = (currP + 2 * diff) % MOD;
                    }
                }

                while (!deque.isEmpty() && H[i] >= deque.peekLast()[1]) deque.removeLast();
                deque.addLast(new long[]{L[i] + W, H[i]});

                ans = ans * currP % MOD;
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