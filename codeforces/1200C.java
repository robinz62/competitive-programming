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
        String[] line = br.readLine().split(" ");
        BigInteger n = new BigInteger(line[0]);
        BigInteger m = new BigInteger(line[1]);
        int q = Integer.parseInt(line[2]);

        BigInteger lcm = n.multiply(m).divide(n.gcd(m));
        BigInteger unitN = lcm.divide(n);
        BigInteger unitM = lcm.divide(m);
        BigInteger unitLcm = unitN.multiply(unitM).divide(unitN.gcd(unitM));
        for (int i = 0; i < q; i++) {
            line = br.readLine().split(" ");
            int sx = Integer.parseInt(line[0]);
            BigInteger sy = new BigInteger(line[1]);
            int ex = Integer.parseInt(line[2]);
            BigInteger ey = new BigInteger(line[3]);

            sy = sy.multiply(sx == 1 ? unitN : unitM);
            ey = ey.multiply(ex == 1 ? unitN : unitM);
            if (sy.compareTo(ey) <= 0) {
                ey = ey.subtract(ex == 1 ? unitN : unitM);
            } else {
                int tempx = sx;
                sx = ex;
                ex = tempx;
                BigInteger tempy = sy;
                sy = ey;
                ey = tempy;

                ey = ey.subtract(ex == 1 ? unitN : unitM);
            }

            if (sy.compareTo(ey) > 0) {
                pw.print("YES\n");
                continue;
            }
            BigInteger[] res = sy.divideAndRemainder(unitLcm);
            if (res[1].equals(BigInteger.ZERO)) {
                pw.print("NO\n");
            } else if (res[0].multiply(unitLcm).add(unitLcm).compareTo(ey) <= 0) {
                pw.print("NO\n");
            } else {
                pw.print("YES\n");
            }
        }
    }
}