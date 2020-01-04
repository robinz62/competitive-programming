import java.io.*;
import java.util.*;

public class A1152 {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pw = new PrintWriter(System.out);

    public static void main(String[] args) throws IOException {
        A1152 a = new A1152();
        a.solve();
    }

    public void solve() throws IOException {
        String[] line = br.readLine().split(" ");
        int n = Integer.parseInt(line[0]);
        int m = Integer.parseInt(line[1]);
        int[] a = new int[n];
        line = br.readLine().split(" ");
        int numOddA = 0;
        int numEvenA = 0;
        for (int i = 0; i < line.length; i++) {
            a[i] = Integer.parseInt(line[i]);
            if (a[i] % 2 == 0) numEvenA++;
            else numOddA++;
        }
        int[] b = new int[m];
        line = br.readLine().split(" ");
        int numOddB = 0;
        int numEvenB = 0;
        for (int i = 0; i < line.length; i++) {
            b[i] = Integer.parseInt(line[i]);
            if (b[i] % 2 == 0) numEvenB++;
            else numOddB++;
        }

        pw.print(Math.min(numOddA, numEvenB) + Math.min(numEvenA, numOddB));
        pw.println();

        pw.flush();
        pw.close();
        br.close();
    }
}