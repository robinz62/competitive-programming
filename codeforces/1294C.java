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
        int t = readInt();
        for (int ti = 0; ti < t; ti++) {
            int n = readInt();
            Map<Integer, Integer> primeFactors = primeFactorize(n);
            List<Integer> factors = new ArrayList<>();
            for (int p : primeFactors.keySet()) {
                int k = primeFactors.get(p);
                for (int i = 0; i < k; i++) factors.add(p);
            }
            int[] abc = new int[]{1, 1, 1};
            int i = 0;
            boolean done = false;
            while (i < factors.size()) {
                if (abc[0] == 1) {
                    abc[0] = factors.get(i++);
                } else if (abc[1] == 1) {
                    abc[1] = factors.get(i++);
                    if (abc[1] == abc[0]) {
                        if (i >= factors.size()) {
                            pw.println("NO");
                            done = true;
                            break;
                        }
                        abc[1] *= factors.get(i++);
                    }
                } else {
                    while (i < factors.size()) {
                        abc[2] *= factors.get(i++);
                    }
                }
            }
            if (done) continue;
            if (abc[2] == abc[0] || abc[2] == abc[1] || abc[0] == 1 || abc[1] == 1 || abc[2] == 1) pw.println("NO");
            else {
                pw.println("YES");
                pw.println(abc[0] + " " + abc[1] + " " + abc[2]);
            }
        }
    }

    Map<Integer, Integer> primeFactorize(int n) {
        Map<Integer, Integer> factors = new HashMap<>();
        while (n % 2 == 0) {
            factors.put(2, factors.getOrDefault(2, 0) + 1);
            n /= 2;
        }
        for (int i = 3; i * i <= n; i += 2) {
            while (n % i == 0) {
                factors.put(i, factors.getOrDefault(i, 0) + 1);
                n /= i;
            }
        }
        if (n > 2) {
            factors.put(n, factors.getOrDefault(n, 0) + 1);
        }
        return factors;
    }
}