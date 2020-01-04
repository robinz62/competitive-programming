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

    int readInt() throws IOException {
        return Integer.parseInt(br.readLine());
    }

    long readLong() throws IOException {
        return Long.parseLong(br.readLine());
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
        int T = readInt();
        for (int t = 0; t < T; t++) {
            int[] line = readIntLine();
            int N = line[0];  // rows
            int M = line[1];  // cols
            int[][] A = new int[N][];
            for (int i = 0; i < N; i++) {
                A[i] = readIntLine();
            }

            long ans = N * M;  // all single elements
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    for (int len = 1; i-len>=0 && i+len<N && j-len>=0 && j+len<M; len++) {
                        if (A[i][j-len] == A[i][j+len] && A[i-len][j] == A[i+len][j]) {
                            ans++;
                        } else {
                            break;
                        }
                    }
                }
            }
            pw.println(ans);
        }
    }
}