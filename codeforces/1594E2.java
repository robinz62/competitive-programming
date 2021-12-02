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
        Map<String, Integer> colorMap = new HashMap<>();
        colorMap.put("white", 0);
        colorMap.put("yellow", 1);
        colorMap.put("green", 2);
        colorMap.put("blue", 3);
        colorMap.put("red", 4);
        colorMap.put("orange", 5);

        long[] invs = new long[]{ -1, modInverseFermat(1, MOD), modInverseFermat(2, MOD), modInverseFermat(3, MOD),
                                      modInverseFermat(4, MOD), modInverseFermat(5, MOD), modInverseFermat(6, MOD) };

        Map<Long, Integer> nodeToColor = new HashMap<>();  // contains only those that are fixed
        Map<Long, Integer> nodeToDepth = new HashMap<>();  // contains all "interesting" nodes

        int k = ri();

        // FROM EASY VERSION
        // dp[i] is number of colors of height i tree, no restrictions
        long factor = 4l * modInverseFermat(9, MOD) % MOD;
        long[] dp = new long[k+1];
        dp[1] = 6;
        for (int i = 2; i <= k; i++) {
            long sub = dp[i-1] * dp[i-1] % MOD * factor % MOD;
            dp[i] = 6 * sub % MOD;
        }

        int n = ri();
        if (n == 0) {
            pw.println(dp[k]);
            return;
        }
        for (int i = 0; i < n; i++) {
            String[] line = br.readLine().split(" ");
            long u = Long.parseLong(line[0]);
            int c = colorMap.get(line[1]);
            int d = depth(u);

            nodeToColor.put(u, c);
            for (long x = u; x >= 1 && !nodeToDepth.containsKey(x); x /= 2) nodeToDepth.put(x, depth(x));
        }

        List<List<Long>> depthToNodes = new ArrayList<>(k);
        for (int i = 0; i < k; i++) depthToNodes.add(new ArrayList<>());
        for (var e : nodeToDepth.entrySet()) {
            long node = e.getKey();
            int depth = e.getValue();
            depthToNodes.get(depth).add(node);
        }

        // node -> number of colorings where the node is color 0, 1, 2, 3, 4, 5
        // for the interesting nodes.
        Map<Long, int[]> computed = new HashMap<>();

        for (int depth = k-1; depth >= 0; depth--) {
            for (long node : depthToNodes.get(depth)) {
                // Handle last layer separately
                if (depth == k-1) {
                    if (nodeToColor.containsKey(node)) {
                        int color = nodeToColor.get(node);
                        int[] counts = new int[6];
                        counts[color] = 1;
                        computed.put(node, counts);
                    } else {
                        int[] counts = new int[]{1, 1, 1, 1, 1, 1};
                        computed.put(node, counts);
                    }
                    continue;
                }

                long left = node * 2;
                long right = node * 2 + 1;
                if (!computed.containsKey(left)) {
                    int levels = k - depth - 1;
                    int x = (int) (dp[levels] * invs[6] % MOD);
                    int[] counts = new int[]{x, x, x, x, x, x};
                    computed.put(left, counts);
                }
                if (!computed.containsKey(right)) {
                    int levels = k - depth - 1;
                    int x = (int) (dp[levels] * invs[6] % MOD);
                    int[] counts = new int[]{x, x, x, x, x, x};
                    computed.put(right, counts);
                }
                int[] leftcounts = computed.get(left);
                int[] rightcounts = computed.get(right);
                
                int[] here = new int[6];
                List<Integer> mycolors;
                if (nodeToColor.containsKey(node)) {
                    mycolors = Arrays.asList(nodeToColor.get(node));
                } else {
                    mycolors = Arrays.asList(0, 1, 2, 3, 4, 5);
                }
                for (int me : mycolors) {
                    int bad1 = me;
                    int bad2 = me % 2 == 0 ? me+1 : me-1;
                    for (int leftcolor = 0; leftcolor < 6; leftcolor++) {
                        if (leftcolor == bad1 || leftcolor == bad2) continue;
                        for (int rightcolor = 0; rightcolor < 6; rightcolor++) {
                            if (rightcolor == bad1 || rightcolor == bad2) continue;
                            here[me] = (int) ((here[me] + (long) leftcounts[leftcolor] * rightcounts[rightcolor] % MOD) % MOD);
                        }
                    }
                }
                computed.put(node, here);
            }
        }
        long ans = 0;
        for (int x : computed.get(1l)) {
            ans = (ans + x) % MOD;
        }
        pw.println(ans);
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    int depth(long v) {
        int ans = 0;
        while (v != 1) {
            v /= 2;
            ans++;
        }
        return ans;
    }

    public static long modPow(long base, long exponent, long m) {
        long ans = 1;
        base = base % m;
        while (exponent > 0) {
            if ((exponent & 1) == 1) ans = (ans * base) % m;
            exponent >>= 1;
            base = (base * base) % m;
        } 
        return ans;
    }

    // Computes a^(-1) mod m, the modular inverse of a (modulo m). This
    // algorithm is based on Fermat's little theorem. m must be prime. O(log m).
    public static long modInverseFermat(long a, long m) {
        return modPow(a, m - 2, m);
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