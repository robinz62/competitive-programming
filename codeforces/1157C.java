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
        int n = readInt();
        int[] A = readIntLine();
        int l = 0;
        int r = n - 1;
        int prev = -1;
        StringBuilder ans = new StringBuilder();
        while (l <= r) {
            if (A[l] > prev && A[r] <= prev) {
                while (l <= r && A[l] > prev) {
                    prev = A[l++];
                    ans.append('L');
                }
                pw.println(ans.length());
                pw.println(ans.toString());
                return;
            } else if (A[l] <= prev && A[r] > prev) {
                while (r >= l && A[r] > prev) {
                    prev = A[r--];
                    ans.append('R');
                }
                pw.println(ans.length());
                pw.println(ans.toString());
                return;
            } else if (A[l] <= prev && A[r] <= prev) {
                pw.println(ans.length());
                pw.println(ans.toString());
                return;
            } else {
                if (A[l] < A[r]) {
                    prev = A[l++];
                    ans.append('L');
                } else if (A[l] > A[r]) {
                    prev = A[r--];
                    ans.append('R');
                } else {
                    StringBuilder altAns = new StringBuilder(ans);
                    int altL = l;
                    int altR = r;
                    int altPrev = prev;

                    // try the left
                    while (l <= r && A[l] > prev) {
                        prev = A[l++];
                        ans.append('L');
                    }

                    // try the right
                    while (altR >= altL && A[altR] > altPrev) {
                        altPrev = A[altR--];
                        altAns.append('R');
                    }
                    if (altAns.length() > ans.length()) {
                        pw.println(altAns.length());
                        pw.println(altAns.toString());
                    } else {
                        pw.println(ans.length());
                        pw.println(ans.toString());
                    }
                    return;
                }
            }
        }

        pw.println(ans.length());
        pw.println(ans.toString());
    }
}