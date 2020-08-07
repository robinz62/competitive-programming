import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static int MOD = 1000000007;

    void solve() throws IOException {
        int n = ri();
        int[] aa = ril(n);
        int q = ri();
        Map<Integer, Integer> lenToCount = new HashMap<>();
        TreeMap<Integer, Set<Integer>> countToLens = new TreeMap<>();
        for (int ai : aa) {
            int prevCount = lenToCount.getOrDefault(ai, 0);
            lenToCount.put(ai, prevCount + 1);
            if (prevCount != 0) countToLens.get(prevCount).remove(ai);
            if (countToLens.containsKey(prevCount) && countToLens.get(prevCount).isEmpty()) countToLens.remove(prevCount);
            if (!countToLens.containsKey(prevCount+1)) countToLens.put(prevCount+1, new HashSet<>());
            countToLens.get(prevCount+1).add(ai);
        }
        for (int qi = 0; qi < q; qi++) {
            String[] tokens = br.readLine().split(" ");
            int x = Integer.parseInt(tokens[1]);
            if (tokens[0].equals("+")) {
                int prevCount = lenToCount.getOrDefault(x, 0);
                lenToCount.put(x, prevCount + 1);
                if (prevCount != 0) countToLens.get(prevCount).remove(x);
                if (countToLens.containsKey(prevCount) && countToLens.get(prevCount).isEmpty()) countToLens.remove(prevCount);
                if (!countToLens.containsKey(prevCount+1)) countToLens.put(prevCount+1, new HashSet<>());
                countToLens.get(prevCount+1).add(x);
            } else {
                int prevCount = lenToCount.get(x);
                if (prevCount == 1) {
                    lenToCount.remove(x);
                    countToLens.get(prevCount).remove(x);
                    if (countToLens.get(prevCount).isEmpty()) countToLens.remove(prevCount);
                } else {
                    lenToCount.put(x, prevCount-1);
                    countToLens.get(prevCount).remove(x);
                    if (countToLens.get(prevCount).isEmpty()) countToLens.remove(prevCount);
                    if (!countToLens.containsKey(prevCount-1)) countToLens.put(prevCount-1, new HashSet<>());
                    countToLens.get(prevCount-1).add(x);
                }
            }
            int a = -1;
            int b = -1;
            int c = -1;
            for (int count : countToLens.descendingKeySet()) {
                for (int xxx : countToLens.get(count)) {
                    if (a == -1) a = count;
                    else if (b == -1) b = count;
                    else if (c == -1) c = count;
                    else break;
                }
                if (c != -1) break;
            }
            // pw.println(countToLens);
            // pw.println(a + " " + b + " " + c);
            if (a < 4) {
                pw.println("NO");
                continue;
            }
            a -= 4;
            if (a >= 2 && b >= 2 || b >= 2 && c >= 2 || a >= 4 || b >= 4) {
                pw.println("YES");
                continue;
            }
            pw.println("NO");
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
            A[i] ^= A[j];
            A[j] ^= A[i];
            A[i] ^= A[j];
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