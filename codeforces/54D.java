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
        int[] nk = ril(2);
        int n = nk[0];
        int k = nk[1];
        char[] p = rs();
        char[] positions = rs();
        char[] ans = new char[n];

        // Place the fixed characters.
        for (int i = 0; i < positions.length; i++) {
            if (positions[i] == '0') continue;
            for (int j = 0; j < p.length; j++) {
                if (ans[i+j] != 0 && ans[i+j] != p[j]) {
                    pw.println("No solution");
                    return;
                }
                ans[i+j] = p[j];
            }
        }

        // Check for coincidental matches
        for (int i = 0; i + p.length - 1 < n; i++) {
            if (positions[i] == '1') continue;
            boolean matches = true;
            for (int j = i; matches && j < i + p.length; j++) {
                if (ans[j] != p[j - i]) matches = false;
            }
            if (matches) {
                pw.println("No solution");
                return;
            }
        }

        // constraints is a map from an index idx in ans to a list of
        // starting indices that impose a constraint on idx.
        List<List<Integer>> constraints = new ArrayList<>(n);
        for (int i = 0; i < n; i++) constraints.add(new ArrayList<>());

        // prohibited tells you for each index, the prohibitions
        List<Map<Character, Integer>> prohibited = new ArrayList<>(n);
        for (int i = 0; i < n; i++) prohibited.add(new HashMap<>());

        // Craft constraints
        // Any starting position that isn't allowed imposes a constraint.
        for (int i = 0; i + p.length - 1 < n; i++) {
            if (positions[i] == '1') continue;
            for (int j = i; j < i + p.length; j++) {
                char badChar = p[j - i];
                prohibited.get(j).put(badChar, prohibited.get(j).getOrDefault(badChar, 0) + 1);
                constraints.get(j).add(i);
            }
        }

        // handled[i] is true if we already deleted the constraints from starting position i.
        boolean[] handled = new boolean[n];
        boolean go = true;
        while (go) {
            go = false;

            // Option 1: Discover a constraint that isn't actually needed
            for (int i = 0; i < positions.length; i++) {
                if (handled[i]) continue;
                boolean hasDiff = false;
                for (int j = i; !hasDiff && j < i + p.length; j++) {
                    if (ans[j] != 0 && ans[j] != p[j - i]) hasDiff = true;
                }
                if (hasDiff) {
                    handled[i] = true;
                    go = true;
                    for (int j = i; j < i + p.length; j++) {
                        char c = p[j - i];
                        int curr = prohibited.get(j).get(c);
                        if (curr == 1) prohibited.get(j).remove(c);
                        else prohibited.get(j).put(c, curr-1);
                    }
                }
            }

            if (go) continue;

            // Option 2: We can place a character somewhere
            for (int i = 0; i < n; i++) {
                if (ans[i] == 0 && prohibited.get(i).size() != k) {
                    char okChar = 0;
                    for (char c = 'a'; c < 'a' + k; c++) {
                        if (!prohibited.get(i).containsKey(c)) {
                            okChar = c;
                            break;
                        }
                    }
                    ans[i] = okChar;

                    go = true;
                    break;
                }
            }
        }

        for (char c : ans) {
            if (c == 0) {
                pw.println("No solution");
                return;
            }
        }
        pw.println(new String(ans));
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

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