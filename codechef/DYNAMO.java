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
            int N = readInt();
            long A = readLong();
            long S = 2 * (long) Math.pow(10, N) + A;
            pw.println(S);
            pw.flush();
            long B = readLong();
            long C = (long) Math.pow(10, N) - B;
            pw.println(C);
            pw.flush();
            long D = readLong();
            long E = (long) Math.pow(10, N) - D;
            pw.println(E);
            pw.flush();
            int ok = readInt();
            if (ok == -1) return;
        }
    }
}