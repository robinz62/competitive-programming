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

    int ri() throws IOException {
        return Integer.parseInt(br.readLine());
    }

    long rl() throws IOException {
        return Long.parseLong(br.readLine());
    }

    int[] ril() throws IOException {
        String[] tokens = br.readLine().split(" ");
        int[] A = new int[tokens.length];
        for (int i = 0; i < A.length; i++)
            A[i] = Integer.parseInt(tokens[i]);
        return A;
    }

    long[] rll() throws IOException {
        String[] tokens = br.readLine().split(" ");
        long[] A = new long[tokens.length];
        for (int i = 0; i < A.length; i++)
            A[i] = Long.parseLong(tokens[i]);
        return A;
    }

    void solve() throws IOException {
        int[] abcd = ril();
        int A = abcd[0];
        int B = abcd[1];
        int C = abcd[2];
        int D = abcd[3];
        long ans = 0;
        for (int x = A; x <= B; x++) {
            int y = B;
            int z = x + y - 1;  // max z that works for y = B
                                // i.e. z can be any value <= this (while still bigger than C)
            if (z < C) {
                // z is no good. we need to increase y until z is ok
                // but if in the process y gets busted, we skip all
                int diff = C - z;
                y += diff;
                z += diff;
                if (y > C) continue;  // bad
            }
            z = Math.min(z, D);
            int lowergood = z - C + 1;  // count corresponding to minimum y that is ok
            if (C - y <= D - z) {
                // linearly increase until y hits C. z is always ok to also increase.
                int uppergood = lowergood + (C - y);
                ans += (long) (lowergood + uppergood) * (uppergood - lowergood + 1) / 2;
            } else {
                // linearly increase until z is capped at D. at that point, contribution
                // flattens out for increasing y
                int uppergood = lowergood + (D - z);
                ans += (long) (lowergood + uppergood) * (uppergood - lowergood + 1) / 2;
                int zok = D - C + 1;
                ans += (long) zok * ((C - y) - (D - z));
            }
        }
        pw.println(ans);
    }
}