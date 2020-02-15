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
        int[] nm = readIntLine();
        int n = nm[0];
        int m = nm[1];
        Set<String> set = new HashSet<>();
        for (int i = 0; i < n; i++) {
            set.add(br.readLine());
        }
        Set<String> hasReverse = new HashSet<>();
        Set<String> isPalindrome = new HashSet<>();
        for (String s : set) {
            String reverse = (new StringBuilder(s)).reverse().toString();
            if (s.equals(reverse)) {
                isPalindrome.add(s);
            } else if (set.contains(reverse)) {
                hasReverse.add(s);
            }
        }
        List<String> asList = new ArrayList<>(hasReverse);

        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < asList.size(); i++) {
            if (hasReverse.contains(asList.get(i))) {
                String s = asList.get(i);
                ans.append(s);
                hasReverse.remove(s);
                String reverse = (new StringBuilder(s)).reverse().toString();
                hasReverse.remove(reverse);
            }
        }
        StringBuilder reverse = (new StringBuilder(ans.toString())).reverse();
        if (!isPalindrome.isEmpty()) {
            ans.append(isPalindrome.iterator().next());
        }
        ans.append(reverse);
        String finalAns = ans.toString();
        pw.println(finalAns.length());
        pw.println(finalAns);
    }
}