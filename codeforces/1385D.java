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

    int[] ril(int n) throws IOException {
        int[] nums = new int[n];
        int c = 0;
        for (int i = 0; i < n; i++) {
            int sign = 1;
            c = br.read();
            int x = 0;
            if (c == '-') {
                sign = -1;
                c = br.read();
            }
            while (c >= '0' && c <= '9') {
                x = x * 10 + c - '0';
                c = br.read();
            }
            nums[i] = x * sign;
        }
        while (c != '\n' && c != -1) c = br.read();
        return nums;
    }

    long[] rll(int n) throws IOException {
        long[] nums = new long[n];
        int c = 0;
        for (int i = 0; i < n; i++) {
            int sign = 1;
            c = br.read();
            long x = 0;
            if (c == '-') {
                sign = -1;
                c = br.read();
            }
            while (c >= '0' && c <= '9') {
                x = x * 10 + c - '0';
                c = br.read();
            }
            nums[i] = x * sign;
        }
        while (c != '\n' && c != -1) c = br.read();
        return nums;
    }

    char[] rs() throws IOException {
        return br.readLine().toCharArray();
    }

    Map<State, Integer> memo;
    void solve() throws IOException {
        int t = ri();
        for (int ti = 0; ti < t; ti++) {
            int n = ri();
            char[] s = rs();
            counts = new int[n][26];
            counts[0][s[0]-'a']++;
            for (int i = 1; i < n; i++) {
                for (int c = 0; c < 26; c++) counts[i][c] = counts[i-1][c];
                counts[i][s[i]-'a']++;
            }
            memo = new HashMap<>();
            pw.println(helper(s, 0, n-1, 'a'));
        }
    }

    int[][] counts;

    int helper(char[] s, int l, int r, char c) {
        if (c > 'z') return -1;
        State curr = new State(l, r, c);
        if (l == r) return s[l] == c ? 0 : 1;
        if (memo.containsKey(curr)) return memo.get(curr);
        int m = l + (r - l) / 2;
        int best = Integer.MAX_VALUE;
        int n = r - l + 1;
        // put c on the left
        int countCsLeft = counts[m][c-'a'] - (l-1>=0?counts[l-1][c-'a']:0);
        int ret = helper(s, m+1, r, (char) (c+1));
        if (ret != -1) best = n / 2 - countCsLeft + ret;
        // put c on the right
        int countCsRight = counts[r][c-'a'] - counts[m][c-'a'];
        ret = helper(s, l, m, (char) (c+1));
        if (ret != -1) best = Math.min(best, n / 2 - countCsRight + ret);
        best = best == Integer.MAX_VALUE ? -1 : best;
        memo.put(curr, best);
        return best;
    }
}

class State {
    int fst;
    int snd;
    char c;
    State(int f, int s, char c) {
        fst = f;
        snd = s;
        this.c = c;
    }
    public int hashCode() {
        return fst * 9991 + snd + c;
    }
    public boolean equals(Object o) {
        if (!(o instanceof State)) return false;
        State p = (State) o;
        return fst == p.fst && snd == p.snd && c == p.c;
    }
    public String toString() {
        return fst + " " + snd + " " + c;
    }
}