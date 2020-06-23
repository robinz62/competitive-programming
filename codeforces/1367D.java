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
        int q = readInt();
        for (int qi = 0; qi < q; qi++) {
            char[] s = br.readLine().toCharArray();
            int[] freq = new int[26];
            for (char c : s) freq[c-'a']++;
            int m = readInt();
            int[] b = readIntLine();
            
            char[] t = new char[m];
            int freqIdx = 25;

            int accounted = 0;
            while (accounted < m) {
                List<Integer> idxMax = new ArrayList<>();
                for (int i = 0; i < m; i++) {
                    if (b[i] == 0) {
                        idxMax.add(i);
                    }
                }
                while (freq[freqIdx] < idxMax.size()) freqIdx--;
                for (int i = 0; i < m; i++) {
                    if (b[i] == 0) {
                        b[i] = -1;
                        t[i] = (char) (freqIdx + 'a');
                        accounted++;
                    }
                }
                for (int i = 0; i < m; i++) {
                    if (b[i] != 0) {
                        for (int j : idxMax) {
                            b[i] -= Math.abs(i - j);
                        }
                    }
                }
                freqIdx--;
            }
            pw.println(new String(t));
        }
    }
}