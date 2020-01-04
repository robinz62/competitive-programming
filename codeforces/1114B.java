import java.io.*;
import java.util.*;
import java.math.BigInteger;
 
public class Main {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pw = new PrintWriter(System.out);
 
    public static void main(String[] args) throws Exception {
        Main m = new Main();
        m.solve();
        m.close();
    }
 
    void close() throws Exception {
        pw.flush();
        pw.close();
        br.close();
    }

    int[] readIntArray() throws Exception {
        String[] tokens = br.readLine().split(" ");
        int[] A = new int[tokens.length];
        for (int i = 0; i < A.length; i++)
            A[i] = Integer.parseInt(tokens[i]);
        return A;
    }
 
    void solve() throws Exception {
        int[] line = readIntArray();
        int n = line[0];
        int m = line[1];
        int k = line[2];
        int[] A = readIntArray();
        PriorityQueue<Integer> pq = new PriorityQueue<>((i, j) -> Integer.compare(A[i], A[j]));
        for (int i = 0; i < m * k; i++) pq.add(i);
        for (int i = m * k; i < n; i++) {
            if (A[i] > A[pq.peek()]) {
                pq.remove();
                pq.add(i);
            }
        }
        Set<Integer> specialIndices = new HashSet<>(pq);
        long max = 0;
        for (int i : specialIndices) max += A[i];
        pw.println(max);
        int counter = 0;
        int timesPrinted = 0;
        for (int i = 0; i < A.length; i++) {
            if (specialIndices.contains(i)) {
                counter++;
                if (counter == m) {
                    pw.print(i + 1);
                    pw.print(" ");
                    counter = 0;
                    timesPrinted++;
                    if (timesPrinted == k - 1) break;
                }
            }
        }
    }
}