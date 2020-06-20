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
        int n = readInt();
        char[] s = br.readLine().toCharArray();
        char[] t = br.readLine().toCharArray();
        int count0 = 0;
        for (char c : s) if (c == '0') count0++;
        for (char c : t) if (c == '0') count0--;
        if (count0 != 0) {
            pw.println("-1");
            return;
        }
        int count = 0;
        // set of bad indices
        List<Integer> idx = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (s[i] != t[i]) idx.add(i);
        }
        if (idx.size() == 0) {
            pw.println("0");
            return;
        }
        int max = 0;
        int count0Tail = 0;
        int count1Tail = 0;
        for (int x = 0; x < idx.size(); x++) {
            int i = idx.get(x);
            if (s[i] == '0') {
                if (count1Tail > 0) {
                    count1Tail--;
                    count0Tail++;
                } else {
                    count0Tail++;
                }
            } else {
                if (count0Tail > 0) {
                    count0Tail--;
                    count1Tail++;
                } else {
                    count1Tail++;
                }
            }
            max = Math.max(max, count0Tail + count1Tail);
        }
        pw.println(max);
    }
}