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
            char[] p = br.readLine().toCharArray();
            char[] h = br.readLine().toCharArray();
            if (h.length < p.length) {
                pw.print("NO\n");
                continue;
            }
            int[] f = new int[26];
            int[] g = new int[26];
            for (char c : p) f[c-'a']++;
            for (int i = 0; i < p.length; i++) {
                g[h[i]-'a']++;
            }
            if (Arrays.equals(f, g)) {
                pw.print("YES\n");
                continue;
            }
            boolean success = false;
            for (int i = p.length; i < h.length; i++) {
                g[h[i]-'a']++;
                g[h[i-p.length]-'a']--;
                if (Arrays.equals(f, g)) {
                    pw.print("YES\n");
                    success = true;
                    break;
                }
            }
            if (!success) pw.print("NO\n");
        }
    }
}