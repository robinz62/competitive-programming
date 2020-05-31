import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pw = new PrintWriter(System.out);
    static int MOD = 1000000007;

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
        for (int i = 0; i < A.length; i++)
            A[i] = Integer.parseInt(tokens[i]);
        return A;
    }

    long[] readLongLine() throws IOException {
        String[] tokens = br.readLine().split(" ");
        long[] A = new long[tokens.length];
        for (int i = 0; i < A.length; i++)
            A[i] = Long.parseLong(tokens[i]);
        return A;
    }

    void solve() throws IOException {
        int t = readInt();
        for (int ti = 0; ti < t; ti++) {
            char[] s = br.readLine().toCharArray();
            int[] numZerosL = new int[s.length];  // including me
            int[] numZerosR = new int[s.length];  // excluding me
            int[] numOnesL = new int[s.length];
            int[] numOnesR = new int[s.length];
            if (s[0] == '0') numZerosL[0] = 1;
            else numOnesL[0] = 1;
            for (int i = 1; i < s.length; i++) {
                if (s[i] == '0') {
                    numZerosL[i] = numZerosL[i-1] + 1;
                    numOnesL[i] = numOnesL[i-1];
                } else {
                    numZerosL[i] = numZerosL[i-1];
                    numOnesL[i] = numOnesL[i-1] + 1;
                }
            }
            for (int i = s.length - 2; i >= 0; i--) {
                if (s[i+1] == '0') {
                    numZerosR[i] = numZerosR[i+1] + 1;
                    numOnesR[i] = numOnesR[i+1];
                } else {
                    numZerosR[i] = numZerosR[i+1];
                    numOnesR[i] = numOnesR[i+1] + 1;
                }
            }
            int countZeros = 0;
            int countOnes = 0;
            for (char c : s) {
                if (c == '0') countZeros++;
                else countOnes++;
            }
            int min = Math.min(countZeros, countOnes);
            for (int i = 0; i < s.length; i++) {
                min = Math.min(min, numOnesL[i] + numZerosR[i]);
                min = Math.min(min, numZerosL[i] + numOnesR[i]);
            }
            pw.println(min);
        }
    }
}