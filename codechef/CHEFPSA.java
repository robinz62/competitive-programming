import java.io.*;
import java.math.BigInteger;
import java.util.*;
 
public class Main {
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

    int readInt() throws IOException {
        return Integer.parseInt(br.readLine());
    }
 
    int[] readIntLine() throws IOException {
        String[] tokens = br.readLine().split(" ");
        int[] A = new int[tokens.length];
        for (int i = 0; i < A.length; i++) A[i] = Integer.parseInt(tokens[i]);
        return A;
    }
 
    long[] readLongLine() throws IOException {
        String[] tokens = br.readLine().split(" ");
        long[] A = new long[tokens.length];
        for (int i = 0; i < A.length; i++) A[i] = Long.parseLong(tokens[i]);
        return A;
    }

    long mod = 1000000007;
    BigInteger MOD = BigInteger.valueOf(mod);
 
    void solve() throws IOException {
        int T = readInt();
        factorials();
        powersOfTwo();
        for (int t = 0; t < T; t++) {
            int N = readInt();
            int[] X = readIntLine();
            Arrays.sort(X);
            int S = Math.min(X[0], 0) + Math.max(X[2 * N - 1], 0);
            boolean ok = true;
            Map<Integer, Integer> pairs = new HashMap<>();
            int i = 0;
            int j = X.length - 1;
            int zerosUsed = 0;
            int z = 0;
            for (; i <= j && z < N + 1; z++) {  // +1 from adding 2 sentinal 0s
                if (X[i] == S && zerosUsed < 2) {
                    zerosUsed++;
                    int key = Math.min(X[i], S - X[i]);
                    pairs.put(key, pairs.getOrDefault(key, 0) + 1);
                    i++;
                } else if (X[j] == S && zerosUsed < 2) {
                    zerosUsed++;
                    int key = Math.min(X[j], S - X[j]);
                    pairs.put(key, pairs.getOrDefault(key, 0) + 1);
                    j--;
                } else if (X[i] + X[j] == S) {
                    int key = Math.min(X[i], X[j]);
                    pairs.put(key, pairs.getOrDefault(key, 0) + 1);
                    i++;
                    j--;
                } else {
                    ok = false;
                    break;
                }
            }
            if (z != N + 1 || !ok || zerosUsed != 2) {
                pw.println("0");
                continue;
            }
            int numPairs = N - 1;
            int zeroPairing = Math.min(0, S - 0);
            if (pairs.get(zeroPairing) == 2) pairs.remove(zeroPairing);
            else pairs.put(zeroPairing, pairs.get(zeroPairing) - 2);

            // calculates # multiset permutations of pairs times 2 if pair is not (x, x)
            BigInteger minTwo = MOD.subtract(BigInteger.valueOf(2));
            long ans = factorial[numPairs];

            Map<Integer, Long> memo = new HashMap<>();

            for (int p : pairs.keySet()) {
                int count = pairs.get(p);
                if (count > 1) {
                    Long factor = memo.get(count);
                    if (factor == null) {
                        factor = BigInteger.valueOf(factorial[count]).modPow(minTwo, MOD).longValue();
                        memo.put(count, factor);
                    }
                    ans = ans * factor % mod;
                }
                if (p != S - p) {
                    ans = ans * powersOfTwo[count] % mod;
                }
            }
            pw.println(ans);
        }
    }

    long[] factorial = new long[100002];
    void factorials() {
        factorial[0] = factorial[1] = 1;
        for (int i = 2; i < factorial.length; i++) {
            factorial[i] = factorial[i-1] * i % mod;
        }
    }

    long[] powersOfTwo = new long[100002];
    void powersOfTwo() {
        powersOfTwo[0] = 1;
        for (int i = 1; i < powersOfTwo.length; i++) {
            powersOfTwo[i] = powersOfTwo[i-1] * 2 % mod;
        }
    }
}