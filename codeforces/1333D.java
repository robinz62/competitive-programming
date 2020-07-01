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
        int[] nk = ril(2);
        int n = nk[0];
        int k = nk[1];
        char[] s = rs();
        List<List<Integer>> pos = new ArrayList<>();
        int total = 0;
        while (true) {
            List<Integer> curr = new ArrayList<>();
            boolean moved = false;
            for (int i = 0; i < s.length - 1; i++) {
                if (s[i] == 'R' && s[i+1] == 'L') {
                    curr.add(i+1);
                    s[i] = 'L';
                    s[i+1] = 'R';
                    i++;
                    moved = true;
                    total++;
                }
            }
            if (moved) {
                pos.add(curr);
            } else break;
        }
        if (k < pos.size() || k > total) {
            pw.println("-1");
            return;
        }
        int extra = k - pos.size();
        int posIdx = 0;
        while (posIdx < pos.size()) {
            List<Integer> curr = pos.get(posIdx);
            int i = 0;
            for (i = 0; extra > 0 && i < curr.size(); i++) {
                pw.println("1 " + curr.get(i));
                if (i != curr.size() - 1) extra--;
            }
            if (extra == 0 && i < curr.size()) {
                int size = curr.size() - i;
                pw.print(size + " ");
                while (i < curr.size()) {
                    pw.print(curr.get(i++) + " ");
                }
                pw.println();
                posIdx++;
                break;
            }
            posIdx++;
        }
        while (posIdx < pos.size()) {
            List<Integer> curr = pos.get(posIdx);
            pw.print(curr.size() + " ");
            for (int i : curr) {
                pw.print(i + " ");
            }
            pw.println();
            posIdx++;
        }
    }
}