import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static int MOD = 1000000007;

    // After writing solution, quick scan for:
    //   array out of bounds
    //   special cases e.g. n=1?
    //   npe, particularly in maps
    //
    // Big numbers arithmetic bugs:
    //   int overflow
    //   sorting, or taking max, after MOD
    void solve() throws IOException {
        int T = ri();
        for (int Ti = 0; Ti < T; Ti++) {
            int K = ri();
            char[] s = rs();

            // L: F and X
            // R: F and O

            // Initialize everything to the first character.

            // The starting character for all prefixes. Either X or O, although may temporarily be
            // F if not found yet.
            char startingChar = s[0];
            long prefixF = s[0] == 'F' ? 1 : 0;
            long n = 1;
            long X = 0;
            long sp = 0;
            long ss = 0;
            long ans = 0;
            char prevNonF = s[0];
            long fstreak = s[0] == 'F' ? 1 : 0;
            for (int i = 1; i < K; i++) {
                if (s[i] == '.') {
                    // startingChar is unaffected

                    if (prevNonF == 'F') {
                        // This is a RARE case. EVERYTHING so far has been an F. no new_ stuff since it's pretty simple
                        n = (n * 2) % MOD;
                        // X is still 0
                        // sp is still 0
                        // ss is still 0
                        // ans is still 0
                        // prevNonF is still the inital value of F
                        prefixF = prefixF * 2 % MOD;
                        fstreak = fstreak * 2 % MOD;
                    } else {
                        // prevNonF is not F, which means we can also assume startingChar exists (is not F)

                        long delta = startingChar == prevNonF ? 0 : 1;  // if start and end don't match, we have a "hitch" in glueing the pieces together
                        
                        long new_n = (n * 2) % MOD;
                        long new_X = (X * 2 + delta) % MOD;
                        long new_sp = (sp + n * X % MOD + sp + (n-prefixF) * delta) % MOD;
                        long new_ss = (ss + n * X % MOD + ss + (n-fstreak) * delta) % MOD;
                        long new_ans = (ans * 2 + n * (sp + ss) + (n-prefixF) * (n-fstreak) * delta) % MOD;
                        // prevNonF is unaffected
                        // fstreak is unaffected

                        n = new_n;
                        X = new_X;
                        sp = new_sp;
                        ss = new_ss;
                        ans = new_ans;
                    }
                } else {
                    long new_prefixF = s[i] == 'F' && startingChar == 'F' ? prefixF + 1 : prefixF;  // ideally not here but w/e
                    if (s[i] == 'F') {
                        n++;
                        // startingChar is unaffected
                        // X is unaffected
                        sp = (sp + X) % MOD;  // the new prefix is the whole string, adding X+0
                        // ss is unaffected
                        ans = (ans + ss) % MOD;  // all the previous suffixes can now also include this final F
                        // prevNonF is unaffected
                        fstreak++;
                    } else {
                        char new_startingChar = startingChar == 'F' ? s[i] : startingChar;
                        long new_n = n+1;

                        if (prevNonF == 'F' || s[i] == prevNonF) {
                            // CASE 1: NEW CHARACTER IS INNOCULOUS

                            // X is unaffected
                            long new_sp = (sp + X) % MOD;
                            // ss is unaffected
                            long new_ans = (ans + ss) % MOD;
                            char new_prevNonF = s[i];  // this is set in case prevNonF was F, otherwise it's the same
                            long new_fstreak = 0;

                            sp = new_sp;
                            ans = new_ans;
                            prevNonF = new_prevNonF;
                            fstreak = new_fstreak;
                        } else {
                            // CASE 2: NEW CHARACTER REQUIRES CHANGE

                            long new_X = X + 1;
                            long new_sp = (sp + new_X) % MOD;  // the one new prefix is the whole string.
                            long new_ss = (ss + n - fstreak) % MOD;    // all the previous n suffixes get the new +1, except the fstreak
                            long new_ans = (ans + new_ss) % MOD;  // the contribution is all the new suffixes
                            char new_prevNonF = s[i];
                            long new_fstreak = 0;

                            X = new_X;
                            sp = new_sp;
                            ss = new_ss;
                            ans = new_ans;
                            prevNonF = new_prevNonF;
                            fstreak = new_fstreak;
                        }

                        startingChar = new_startingChar;
                        n = new_n;
                    }
                    prefixF = new_prefixF;
                }

                // pw.println("i = " + i);
                // pw.println("startingChar: " + startingChar);
                // pw.println("n: " + n);
                // pw.println("X: " + X);
                // pw.println("sp: " + sp);
                // pw.println("ss: " + ss);
                // pw.println("ans: " + ans);
                // pw.println("prevNonF: " + prevNonF);
                // pw.println("prefixF: " + prefixF);
                // pw.println("fstreak: " + fstreak);
                // pw.println();
            }

            pw.println("Case #" + (Ti+1) + ": " + ans);

            // long myAns = ans;
            // long brute = a2(bruteGenerate(s));
            // if (myAns != brute) pw.println("FAIL " + Ti);
            // pw.println(myAns);
            // pw.println(brute);
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    char[] bruteGenerate(char[] u) {
        StringBuilder sb = new StringBuilder();
        for (char c : u) {
            if (c == '.') sb.append(sb);
            else sb.append(c);
        }
        return sb.toString().toCharArray();
    }

    long a2(char[] s) {
        int N = s.length;
        char prevNonF = s[0];
        long[] dp = new long[N];
        dp[0] = 0;
        int fstreak = s[0] == 'F' ? 1 : 0;
        for (int i = 1; i < N; i++) {
            if (s[i] == 'F') {
                dp[i] = dp[i-1];
                fstreak++;
            } else {
                if (prevNonF == 'F' || prevNonF == s[i]) {
                    prevNonF = s[i];
                    dp[i] = dp[i-1];
                } else {
                    prevNonF = s[i];
                    dp[i] = dp[i-1] + i - fstreak;
                }
                fstreak = 0;
            }
        }
        long ans = 0;
        for (long ai : dp) ans = (ans + ai) % MOD;
        return ans;
    }

    // Template code below

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

    int ri() throws IOException {
        return Integer.parseInt(br.readLine().trim());
    }

    long rl() throws IOException {
        return Long.parseLong(br.readLine().trim());
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

    int[] rkil() throws IOException {
        int sign = 1;
        int c = br.read();
        int x = 0;
        if (c == '-') {
            sign = -1;
            c = br.read();
        }
        while (c >= '0' && c <= '9') {
            x = x * 10 + c - '0';
            c = br.read();
        }
        return ril(x);
    }

    long[] rkll() throws IOException {
        int sign = 1;
        int c = br.read();
        int x = 0;
        if (c == '-') {
            sign = -1;
            c = br.read();
        }
        while (c >= '0' && c <= '9') {
            x = x * 10 + c - '0';
            c = br.read();
        }
        return rll(x);
    }

    char[] rs() throws IOException {
        return br.readLine().toCharArray();
    }

    void sort(int[] A) {
        Random r = new Random();
        for (int i = A.length-1; i > 0; i--) {
            int j = r.nextInt(i+1);
            int temp = A[i];
            A[i] = A[j];
            A[j] = temp;
        }
        Arrays.sort(A);
    }

    void printDouble(double d) {
        pw.printf("%.16f", d);
    }
}