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
            int n = ri();
            int[] a = ril(n);

            // SUBTASK 1 ONLY
            List<Long> curr = new ArrayList<>();
            for (int ai : a) curr.add((long) ai);
            boolean go = true;
            while (go) {
                go = false;
                if (curr.size() < 2) break;
                if (curr.size() == 2) {
                    if (curr.get(0) % 2 == curr.get(1) % 2) {
                        long sum = curr.get(0) + curr.get(1) + (curr.get(0) % 2 == 0 ? 1 : 0);
                        curr.remove(curr.size()-1);
                        curr.remove(curr.size()-1);
                        curr.add(sum);
                        break;
                    } else break;
                }
                if (curr.get(curr.size()-2) % 2 == curr.get(curr.size() - 1) % 2) {
                    long sum = curr.get(curr.size()-2) + curr.get(curr.size()-1) + (curr.get(curr.size() - 1) % 2 == 0 ? 1: 0);
                    curr.remove(curr.size()-1);
                    curr.remove(curr.size()-1);
                    curr.add(sum);
                    go = true;
                    continue;
                }
                if (curr.size() >= 3) {
                    long last = curr.get(curr.size() - 1);
                    curr.remove(curr.size() - 1);
                    long sum = curr.get(curr.size()-1) + curr.get(curr.size()-2) + (curr.get(curr.size() - 1) % 2 == 0 ? 1 : 0);
                    curr.remove(curr.size()-1);
                    curr.remove(curr.size()-1);
                    curr.add(sum);
                    curr.add(last);
                    go = true;
                    continue;
                }
            }
            pw.println(curr.size());
            for (long v : curr) pw.print(v + " ");
            pw.println();
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