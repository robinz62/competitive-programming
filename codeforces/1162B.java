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
        int m = line[1];
        int[][] A = new int[n][];
        int[][] B = new int[n][];
        for (int i = 0; i < n; i++) {
            A[i] = readIntLine();
        }
        for (int i = 0; i < n; i++) {
            B[i] = readIntLine();
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (A[i][j] > B[i][j]) swap(A, B, i, j);
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < m; j++) {
                if (A[i][j] <= A[i][j - 1] || B[i][j] <= B[i][j - 1]) {
                    pw.println("Impossible");
                    return;
                }
            }
        }
        for (int j = 0; j < m; j++) {
            for (int i = 1; i < n; i++) {
                if (A[i][j] <= A[i - 1][j] || B[i][j] <= B[i - 1][j]) {
                    pw.println("Impossible");
                    return;
                }
            }
        }
        pw.println("Possible");
    }

    void swap(int[][] A, int[][] B, int i, int j) {
        int tmp = A[i][j];
        A[i][j] = B[i][j];
        B[i][j] = tmp;
    }
}