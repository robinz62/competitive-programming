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
        int n = Integer.parseInt(br.readLine());
        int[] A = readInts();
        int countZeros = 0;
        for (int i = 0; i < A.length; i++) {
            if (A[i] < 0) A[i] = -A[i] - 1;
            if (A[i] == 0) countZeros++;
        }
        if (countZeros == n) {
            if (countZeros % 2 == 0) {
                for (int i = 0; i < A.length; i++) pw.print("-1 ");
                return;
            }
            for (int i = 0; i < A.length; i++) pw.print("0 ");
            return;
        }
        int idxMax = -1;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < A.length; i++) {
            if (A[i] == 0) A[i] = -1;
            else {
                if (A[i] > max) {
                    idxMax = i;
                    max = A[i];
                }
                A[i] = -A[i] - 1;
            }
        }
        if (n % 2 == 1) {
            A[idxMax] = -A[idxMax] - 1;
        }
        for (int i : A) pw.print(i + " ");
    }
}