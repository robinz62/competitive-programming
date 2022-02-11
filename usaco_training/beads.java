/*
ID: robinz61
LANG: JAVA
TASK: beads
*/
import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class beads {
    static int MOD = 1000000007;

    static String input = "beads.in";
    static String output = "beads.out";

    void solve() throws IOException {
        int n = ri();
        char[] s = rs();
        List<int[]> counts = new ArrayList<>();
        int count = 1;
        for (int i = 1; i < n; i++) {
            if (s[i] == s[i-1]) count++;
            else {
                counts.add(new int[]{s[i-1], count});
                count = 1;
            }
        }
        counts.add(new int[]{s[n-1], count});
        List<int[]> copy = new ArrayList<>(counts);
        counts.addAll(copy);

        int[] colors = new int[]{'r', 'b'};

        int ans = 0;
        for (int i = 0; i < counts.size()-1; i++) {
            for (int leftcolor : colors) {
                for (int rightcolor : colors) {
                    int curr = 0;
                    for (int j = i; j >= 0 && (counts.get(j)[0] == leftcolor || counts.get(j)[0] == 'w'); j--) curr += counts.get(j)[1];
                    for (int j = i+1; j < counts.size() && (counts.get(j)[0] == rightcolor || counts.get(j)[0] == 'w'); j++) curr += counts.get(j)[1];
                    ans = Math.max(ans, Math.min(curr, n));
                }
            }
        }
        pw.println(ans);
    }

    // Template code below

    static BufferedReader br;
    static PrintWriter pw;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new FileReader(input));
        pw = new PrintWriter(new FileWriter(output));
        beads m = new beads();
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
        int c = br.read();
        int x = 0;
        while (c >= '0' && c <= '9') {
            x = x * 10 + c - '0';
            c = br.read();
        }
        return ril(x);
    }

    long[] rkll() throws IOException {
        int c = br.read();
        int x = 0;
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