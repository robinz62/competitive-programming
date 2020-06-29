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

    void solve() throws IOException {
        int t = ri();
        for (int ti = 0; ti < t; ti++) {
            int n = ri();
            int[] a = ril(n);

            int[] orig = new int[n];
            System.arraycopy(a, 0, orig, 0, n);
            List<Integer> insn = new ArrayList<>();
            for (int i = 0; i < a.length - 2; i++) {
                int min = a[i];
                int idxMin = i;
                for (int j = i+1; j < a.length; j++) {
                    if (a[j] < min) {
                        min = a[j];
                        idxMin = j;
                    }
                }
                while (idxMin != i) {
                    if (idxMin - 2 >= i) {
                        insn.add(idxMin-2);
                        a[idxMin] = a[idxMin-1];
                        a[idxMin-1] = a[idxMin-2];
                        a[idxMin-2] = min;
                        idxMin -= 2;
                    } else {
                        insn.add(idxMin-1);
                        insn.add(idxMin-1);
                        a[idxMin] = a[idxMin+1];
                        a[idxMin+1] = a[idxMin-1];
                        a[idxMin-1] = min;
                        idxMin -= 1;
                    }
                }
            }
            if (a[n-2] <= a[n-1]) {
                pw.println(insn.size());
                for (int i : insn) pw.print((i+1) + " ");
                pw.println();
                continue;
            }
            // Try again, but flip last instance of X X Y Z
            insn = new ArrayList<>();
            a = orig;

            int maxduped = -1;
            int count = 0;
            Map<Integer, Integer> freq = new HashMap<>();
            for (int ai : a) {
                freq.put(ai, freq.getOrDefault(ai, 0) + 1);
            }
            for (int i : freq.keySet()) {
                if (freq.get(i) > 1 && i > maxduped) {
                    maxduped = i;
                    count = freq.get(i);
                }
            }
            if (maxduped == -1) {
                pw.println("-1");
                continue;
            }

            int z = 0;
            for (int i = 0; i < a.length - 2; i++) {
                int min = a[i];
                int idxMin = i;
                for (int j = i+1; j < a.length; j++) {
                    if (a[j] < min) {
                        min = a[j];
                        idxMin = j;
                    }
                }
                while (idxMin != i) {
                    if (idxMin - 2 >= i) {
                        insn.add(idxMin-2);
                        a[idxMin] = a[idxMin-1];
                        a[idxMin-1] = a[idxMin-2];
                        a[idxMin-2] = min;
                        idxMin -= 2;
                    } else {
                        insn.add(idxMin-1);
                        insn.add(idxMin-1);
                        a[idxMin] = a[idxMin+1];
                        a[idxMin+1] = a[idxMin-1];
                        a[idxMin-1] = min;
                        idxMin -= 1;
                    }
                }
                if (min == maxduped) z++;
                if (min == maxduped && z == count) {
                    int x = i-1;
                    insn.add(x+1);
                    insn.add(x);
                    int temp = a[x+2];
                    a[x+2] = a[x+3];
                    a[x+3] = temp;
                }
            }
            if (a[n-2] <= a[n-1]) {
                pw.println(insn.size());
                for (int i : insn) pw.print((i+1) + " ");
                pw.println();
            } else {
                // yolo flip around the last 3
                int temp = a[n-1];
                a[n-1] = a[n-2];
                a[n-2] = a[n-3];
                a[n-3] = temp;
                insn.add(n-3);
                if (a[n-3] <= a[n-2] && a[n-2] <= a[n-1]) {
                    pw.println(insn.size());
                    for (int i : insn) pw.print((i+1) + " ");
                    pw.println();
                    continue;
                }
                temp = a[n-1];
                a[n-1] = a[n-2];
                a[n-2] = a[n-3];
                a[n-3] = temp;
                insn.add(n-3);
                if (a[0] <= a[n-2] && a[n-2] <= a[n-1]) {
                    pw.println(insn.size());
                    for (int i : insn) pw.print((i+1) + " ");
                    pw.println();
                    continue;
                }
                pw.println("-1");
            }
        }
    }
}