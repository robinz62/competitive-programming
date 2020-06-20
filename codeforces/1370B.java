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
            int[] a = readIntLine();
            List<Integer> even = new ArrayList<>();
            List<Integer> odd = new ArrayList<>();
            for (int i = 0; i < a.length; i++) {
                if (a[i] % 2 == 0) even.add((i+1));
                else odd.add((i+1));
            }
            if (even.size() % 2 == 0 && odd.size() % 2 == 0) {
                if (!even.isEmpty()) {
                    even.remove(even.size() - 1);
                    even.remove(even.size() - 1);
                } else {
                    odd.remove(odd.size() - 1);
                    odd.remove(odd.size() - 1);
                }
            } else if (even.size() % 2 == 1 && odd.size() % 2 == 1) {
                even.remove(even.size() - 1);
                odd.remove(odd.size() - 1);
            }
            for (int i = 0; i < even.size(); i += 2) {
                pw.println(even.get(i) + " " + even.get(i+1));
            }
            for (int i = 0; i < odd.size(); i += 2) {
                pw.println(odd.get(i) + " " + odd.get(i+1));
            }
        }
    }
}