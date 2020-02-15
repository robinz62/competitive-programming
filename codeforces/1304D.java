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
            String[] line = br.readLine().split(" ");
            int n = Integer.parseInt(line[0]);
            char[] s = line[1].toCharArray();

            // (1)
            List<Integer> numUpsUntilDown = new ArrayList<>();
            int count = 0;
            for (int i = 0; i < s.length; i++) {
                if (s[i] == '>') {
                    numUpsUntilDown.add(count);
                    count = 0;
                } else {
                    count++;
                }
            }
            numUpsUntilDown.add(count);
            List<Integer> ans = new ArrayList<>();
            int curr = 0;
            ans.add(curr);
            for (int x : numUpsUntilDown) {
                ans.add(curr - 1 - x);
                for (int j = curr - x; j <= curr - 1; j++) ans.add(j);
                curr = curr - 1 - x;
            }
            ans.remove(0);
            int min = ans.get(0);
            for (int i : ans) min = Math.min(min, i);
            for (int i = 0; i < ans.size(); i++) {
                ans.set(i, ans.get(i) - min + 1);
            }
            for (int i : ans) pw.print(i + " ");
            pw.println();

            // (2)
            List<Integer> numDownsUntilUp = new ArrayList<>();
            count = 0;
            for (int i = 0; i < s.length; i++) {
                if (s[i] == '<') {
                    numDownsUntilUp.add(count);
                    count = 0;
                } else {
                    count++;
                }
            }
            numDownsUntilUp.add(count);
            ans.clear();
            curr = 0;
            ans.add(curr);
            for (int x : numDownsUntilUp) {
                ans.add(curr + 1 + x);
                for (int j = curr + x; j >= curr + 1; j--) ans.add(j);
                curr = curr + 1 + x;
            }
            ans.remove(0);
            min = ans.get(0);
            for (int i : ans) min = Math.min(min, i);
            for (int i = 0; i < ans.size(); i++) {
                ans.set(i, ans.get(i) - min + 1);
            }
            for (int i : ans) pw.print(i + " ");
            pw.println();
        }
    }
}