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
        int n = Integer.parseInt(br.readLine());
        int[] A = readIntArray();
        int max = Integer.MIN_VALUE;
        long sum = 0;
        for (int i = 0; i < A.length; i++) {
            max = Math.max(max, A[i]);
            sum += A[i];
        }
        pw.println(sum % 2 == 0 && max <= sum - max ? "YES" : "NO");
    }
}