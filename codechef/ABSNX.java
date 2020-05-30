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
        int T = readInt();
        for (int t = 0; t < T; t++) {
            int N = readInt();
            int[] A = readIntLine();
            int[] prevLesser = new int[N];
            int[] prevGreater = new int[N];
            int[] streak = new int[N];
            prevLesser[0] = prevGreater[0] = -1;
            streak[0] = 1;
            for (int i = 1; i < N; i++) {
                int j = i-1;
                while (j >= 0 && A[i] <= A[j]) j = prevLesser[j];
                prevLesser[i] = j;
                j = i-1;
                while (j >= 0 && A[i] >= A[j]) j = prevGreater[j];
                prevGreater[i] = j;
                streak[i] = A[i] == A[i-1] ? streak[i-1] + 1 : 1;
            }
            long count = 0;
            List<Integer> mins = new ArrayList<>();
            List<Integer> maxs = new ArrayList<>();
            for (int i = 0; i < N; i++) {
                while (!mins.isEmpty() && A[i] < A[mins.get(mins.size() - 1)]) mins.remove(mins.size() - 1);
                mins.add(i);
                while (!maxs.isEmpty() && A[i] > A[maxs.get(maxs.size() - 1)]) maxs.remove(maxs.size() - 1);
                maxs.add(i);

                // treat A[i] as max
                int lower = prevGreater[i] + 1;
                int idx = Collections.binarySearch(mins, lower);
                if (idx < 0) idx = -idx - 1;
                count += mins.size() - idx;

                // treat A[i] as min
                lower = prevLesser[i] + 1;
                idx = Collections.binarySearch(maxs, lower);
                if (idx < 0) idx = -idx - 1;
                count += maxs.size() - idx;

                // remove duplicate when min = max by searching for length of
                // streak of A[i] ending at i.
                count -= streak[i];
            }
            pw.println(count);
        }
    }
}