import java.io.*;
import java.util.*;

public class Main {
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

    int[] readIntLine() throws IOException {
        String[] tokens = br.readLine().split(" ");
        int[] A = new int[tokens.length];
        for (int i = 0; i < A.length; i++) A[i] = Integer.parseInt(tokens[i]);
        return A;
    }

    long[] readLongLine() throws IOException {
        String[] tokens = br.readLine().split(" ");
        long[] A = new long[tokens.length];
        for (int i = 0; i < A.length; i++) A[i] = Long.parseLong(tokens[i]);
        return A;
    }

    void solve() throws IOException {
        int[] line = readIntLine();
        int n = line[0];
        int M = line[1];
        int[] t = readIntLine();
        int sumSoFar = 0;
        int[] times = new int[101];
        for (int i = 0; i < n; i++) {
            int time = M - sumSoFar;
            int j = 100;
            int count = 0;
            while (time < t[i]) {
                int numWithJ = times[j];
                if (time + numWithJ * j < t[i]) {
                    count += numWithJ;
                    time += numWithJ * j;
                    j--;
                } else {
                    break;
                }
            }
            if (time < t[i]) {
                int diff = t[i] - time;
                count += (diff - 1) / j + 1;
            }
            pw.print(count + " ");
            times[t[i]]++;
            sumSoFar += t[i];
        }
    }
}