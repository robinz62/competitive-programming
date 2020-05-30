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
            int N = readInt();
            char[] A = br.readLine().toCharArray();
            char[] B = br.readLine().toCharArray();
            int[] freqA = new int[26];
            int[] freqB = new int[26];
            for (char c : A) freqA[c-'a']++;
            for (char c : B) freqB[c-'a']++;
            boolean impossible = false;
            for (int i = 0; i < N; i++) if (B[i] > A[i]) impossible = true;
            for (int i = 0; i < 26; i++) if (freqB[i] > 0 && freqA[i] == 0) impossible = true;
            if (impossible) {
                pw.println("-1");
                continue;
            }

            instructions = new ArrayList<>();

            int[] charToAIdx = new int[26];
            for (int i = 0; i < N; i++) charToAIdx[A[i]-'a'] = i;
            Map<Character, Map<Character, List<Integer>>> adj = new HashMap<>();
            for (char c = 'a'; c <= 'z'; c++) adj.put(c, new HashMap<>());
            for (int i = 0; i < N; i++) {
                if (A[i] != B[i]) {
                    if (!adj.get(B[i]).containsKey(A[i])) {
                        adj.get(B[i]).put(A[i], new ArrayList<>());
                    }
                    adj.get(B[i]).get(A[i]).add(i);
                }
            }
            boolean[] visited = new boolean[26];
            for (int i = 0; i < 26; i++) {
                char c = (char) (i + 'a');
                if (!visited[c-'a']) {
                    visited[c-'a'] = true;
                    dfs(adj, charToAIdx, c, visited);
                }
            }
            pw.println(instructions.size());
            for (String s : instructions) pw.println(s);
        }
    }

    List<String> instructions;

    void dfs(Map<Character, Map<Character, List<Integer>>> adj, int[] charToAIdx,
             char c, boolean[] visited) {
        int count = 0;
        for (char d : adj.get(c).keySet()) {
            count += adj.get(c).get(d).size();
            if (!visited[d-'a']) {
                visited[d-'a'] = true;
                dfs(adj, charToAIdx, d, visited);
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(count+1).append(" ");
        sb.append(charToAIdx[c-'a']).append(" ");
        boolean use = false;
        for (char d : adj.get(c).keySet()) {
            use = true;
            for (int idx : adj.get(c).get(d)) {
                sb.append(idx).append(" ");
            }
        }
        if (use) instructions.add(sb.toString());
    }
}