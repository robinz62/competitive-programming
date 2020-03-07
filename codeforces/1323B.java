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
        int[] nmk = readIntLine();
        int n = nmk[0];
        int m = nmk[1];
        int k = nmk[2];
        int[] a = readIntLine();
        int[] b = readIntLine();

        Map<Integer, Integer> aLengths = new HashMap<>();
        Map<Integer, Integer> bLengths = new HashMap<>();

        int count = 0;
        for (int ai : a) {
            if (ai == 0) {
                if (count != 0) aLengths.put(count, aLengths.getOrDefault(count, 0) + 1);
                count = 0;
            } else {
                count++;
            }
        }
        if (count != 0) aLengths.put(count, aLengths.getOrDefault(count, 0) + 1);

        count = 0;
        for (int bi : b) {
            if (bi == 0) {
                if (count != 0) bLengths.put(count, bLengths.getOrDefault(count, 0) + 1);
                count = 0;
            } else {
                count++;
            }
        }
        if (count != 0) bLengths.put(count, bLengths.getOrDefault(count, 0) + 1);

        long ans = 0;
        List<Integer> kFactors = factors(k);
        for (int X : kFactors) {
            int Y = k / X;
            long count1 = 0;
            for (Map.Entry<Integer, Integer> kv : aLengths.entrySet()) {
                if (kv.getKey() < X) continue;
                count1 += (long) kv.getValue() * (kv.getKey() - X + 1);
            }
            long count2 = 0;
            for (Map.Entry<Integer, Integer> kv : bLengths.entrySet()) {
                if (kv.getKey() < Y) continue;
                count2 += (long) kv.getValue() * (kv.getKey() - Y + 1);
            }

            ans += count1 * count2;
        }
        pw.println(ans);
    }

    public static List<Integer> factors(int n) {
        List<Integer> factors = new ArrayList<>();
        int i = 1;
        for (; i * i < n; i++) {
            if (n % i == 0) {
                factors.add(i);
                factors.add(n / i);
            }
        }
        if (i * i == n) factors.add(i);
        return factors;
    }
}