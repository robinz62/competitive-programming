import java.io.*;
import java.util.*;
 
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

    int[] readInts() throws Exception {
        String[] tokens = br.readLine().split(" ");
        int[] A = new int[tokens.length];
        for (int i = 0; i < A.length; i++)
            A[i] = Integer.parseInt(tokens[i]);
        return A;
    }
    
    void solve() throws Exception {
        int[] line = readInts();
        int n = line[0];
        int k = line[1];
        int[] A = readInts();
        Arrays.sort(A);
        int mid = A.length / 2;
        long aHeight = A[mid];
        for (int i = mid + 1; i < A.length; i++) {
            long height = A[i];
            int numBlocks = i - mid;
            long diff = height - aHeight;
            if (numBlocks * diff <= k) {
                aHeight += diff;
                k -= numBlocks * diff;
            } else {
                pw.println(aHeight + k / numBlocks);
                return;
            }
        }
        pw.println(aHeight + k / (A.length - mid));
    }
}