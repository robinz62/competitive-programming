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
        int T = readInt();
        for (int t = 0; t < T; t++) {
            String s = br.readLine();
            if (s.length() == 1) {
                pw.println("YES");
                pw.println("abcdefghijklmnopqrstuvwxyz");
                continue;
            }
            List<Set<Integer>> adj = new ArrayList<>();
            for (int i = 0; i < 26; i++) adj.add(new HashSet<>());
            for (int i = 0; i < s.length() - 1; i++) {
                int u = s.charAt(i) - 'a';
                int v = s.charAt(i+1) - 'a';
                adj.get(u).add(v);
                adj.get(v).add(u);
            }
            boolean good = true;
            List<Integer> deg1 = new ArrayList<>();
            for (int i = 0; i < 26 && good; i++) {
                if (adj.get(i).size() > 2) good = false;
                if (adj.get(i).size() == 1) deg1.add(i);
            }
            if (!good || deg1.size() != 2) {
                pw.println("NO");
            } else {
                pw.println("YES");
                StringBuilder sb = new StringBuilder();
                boolean[] visited = new boolean[26];
                int curr = deg1.get(0);
                visited[curr] = true;
                sb.append((char) (curr + 'a'));
                while (curr != -1) {
                    int next = -1;
                    for (int v : adj.get(curr)) {
                        if (!visited[v]) {
                            visited[v] = true;
                            next = v;
                            sb.append((char) (v + 'a'));
                        }
                    }
                    curr = next;
                }
                for (int i = 0; i < 26; i++) {
                    if (!visited[i]) sb.append((char) (i + 'a'));
                }
                pw.println(sb.toString());
            }
        }
    }
}