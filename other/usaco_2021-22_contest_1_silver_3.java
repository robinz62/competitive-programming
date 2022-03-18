import java.util.*;
import java.io.*;
import java.math.BigInteger;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);

        String[] line = br.readLine().split(" ");
        int n = Integer.parseInt(line[0]);
        int m = Integer.parseInt(line[1]);

        int[] startcounts = new int[m+1];
        int[] endcounts = new int[m+1];
        for (int i = 0; i < n; i++) {
            line = br.readLine().split(" ");
            startcounts[Integer.parseInt(line[0])]++;
            endcounts[Integer.parseInt(line[1])]++;
        }
        
        long[] eventline = new long[m*2+5];
        for (int startsum = 0; startsum <= 2*m; startsum++) {
            // How many pairs sum to startsum?
            for (int ai = 0; ai <= m; ai++) {
                // ai + aj = startsum -> aj = startsum - ai
                if (startsum - ai >= 0 && startsum - ai <= m) eventline[startsum] += (long) startcounts[ai] * startcounts[startsum - ai];
            }
        }
        for (int endsum = 0; endsum <= 2*m; endsum++) {
            for (int bi = 0; bi <= m; bi++) {
                if (endsum - bi >= 0 && endsum - bi <= m) eventline[endsum+1] -= (long) endcounts[bi] * endcounts[endsum - bi];
            }
        }

        long curr = 0;
        for (int i = 0; i <= 2 * m; i++) {
            curr += eventline[i];
            pw.println(curr);
        }

        pw.flush();
    }
}