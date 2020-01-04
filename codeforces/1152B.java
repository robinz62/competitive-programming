import java.io.*;
import java.util.*;

public class B1152 {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pw = new PrintWriter(System.out);

    public static void main(String[] args) throws IOException {
        B1152 b = new B1152();
        b.solve();
    }

    public void solve() throws IOException {
        int x = Integer.parseInt(br.readLine());
        List<Integer> idx = new ArrayList<>();
        boolean xor = true;
        int steps = 0;
        while (!(((x+1) != 0) && (((x+1) & (x)) == 0))) {
            if (xor) {
                int mask = Integer.highestOneBit(x);
                idx.add(Integer.numberOfTrailingZeros(mask) + 1);
                mask <<= 1;
                mask -= 1;
                x = x ^ mask;
            } else {
                x++;
            }
            xor = !xor;
            steps++;
        }
        pw.print(steps + "\n");
        for (int i : idx) {
            pw.print(i + " ");
        }

        pw.flush();
        pw.close();
        br.close();
    }
}