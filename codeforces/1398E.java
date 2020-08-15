import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static int MOD = 1000000007;

    // After writing solution, quick scan for:
    //   int overflow
    //   array out of bounds
    //   special cases e.g. n=1?
    void solve() throws IOException {
        int n = ri();
        // Initialize state

        TreeSet<Spell> topL = new TreeSet<>();
        TreeSet<Spell> restF = new TreeSet<>();  // rest of fires
        TreeSet<Spell> restL = new TreeSet<>();  // rest of lightnings
        long sumTopL = 0;
        long sumF = 0;  // sum of all F
        long sumL = 0;  // sum of all L
        int numLInTop = 0;
        for (int i = 0; i < n; i++) {
            int[] tpd = ril(2);
            int tp = tpd[0];
            int d = tpd[1];  // 10^9
            if (tp == 0) {  // fire
                if (d > 0) {  // learn
                    Spell me = new Spell(true, d);
                    Spell cand = topL.isEmpty() ? null : topL.first();
                    if (cand != null && me.compareTo(cand) > 0) {
                        topL.remove(cand);
                        sumTopL -= cand.value;
                        sumTopL += me.value;
                        if (cand.fire) {
                            restF.add(cand);
                            topL.add(me);
                        } else {
                            restL.add(cand);
                            topL.add(me);
                            numLInTop--;
                        }
                    } else {
                        restF.add(me);
                    }
                    sumF += d;
                } else {
                    d = -d;
                    Spell me = new Spell(true, d);
                    sumF -= d;
                    if (restF.contains(me)) {
                        restF.remove(me);
                    } else {
                        topL.remove(me);
                        sumTopL -= d;

                        if (!restF.isEmpty() && restL.isEmpty()) {
                            Spell cand = restF.last();
                            restF.remove(cand);
                            topL.add(cand);
                            sumTopL += cand.value;
                        } else if (restF.isEmpty() && !restL.isEmpty()) {
                            Spell cand = restL.last();
                            restL.remove(cand);
                            topL.add(cand);
                            sumTopL += cand.value;
                            numLInTop++;
                        } else {
                            if (restF.last().compareTo(restL.last()) > 0) {
                                Spell cand = restF.last();
                                restF.remove(cand);
                                topL.add(cand);
                                sumTopL += cand.value;
                            } else {
                                Spell cand = restL.last();
                                restL.remove(cand);
                                topL.add(cand);
                                sumTopL += cand.value;
                                numLInTop++;
                            }
                        }
                    }
                }
            } else {  // lightning. topL changes size!
                if (d > 0) {
                    Spell me = new Spell(false, d);
                    Spell cand = topL.isEmpty() ? null : topL.first();
                    if (cand != null && me.compareTo(cand) > 0) {
                        topL.remove(cand);
                        sumTopL -= cand.value;
                        sumTopL += me.value;
                        if (cand.fire) {
                            restF.add(cand);
                            topL.add(me);
                        } else {
                            restL.add(cand);
                            topL.add(me);
                            numLInTop--;
                        }
                        numLInTop++;
                    } else {
                        restL.add(me);
                    }
                    sumL += d;

                    // add the best from F or L to topL due to expanded size
                    if (!restF.isEmpty() && restL.isEmpty()) {
                        cand = restF.last();
                        restF.remove(cand);
                        topL.add(cand);
                        sumTopL += cand.value;
                    } else if (restF.isEmpty() && !restL.isEmpty()) {
                        cand = restL.last();
                        restL.remove(cand);
                        topL.add(cand);
                        sumTopL += cand.value;
                        numLInTop++;
                    } else {
                        if (restF.last().compareTo(restL.last()) > 0) {
                            cand = restF.last();
                            restF.remove(cand);
                            topL.add(cand);
                            sumTopL += cand.value;
                        } else {
                            cand = restL.last();
                            restL.remove(cand);
                            topL.add(cand);
                            sumTopL += cand.value;
                            numLInTop++;
                        }
                    }
                } else {
                    d = -d;
                    Spell me = new Spell(false, d);
                    sumL -= d;
                    if (restL.contains(me)) {
                        restL.remove(me);

                        // kill one from top L
                        Spell evict = topL.first();
                        topL.remove(evict);
                        sumTopL -= evict.value;
                        if (evict.fire) {
                            restF.add(evict);
                        } else {
                            restL.add(evict);
                            numLInTop--;
                        }
                    } else {
                        topL.remove(me);
                        sumTopL -= d;
                        numLInTop--;
                    }
                }
            }
            long ans = sumF + sumL + sumTopL;
            if (!topL.isEmpty() && numLInTop == topL.size()) {
                ans -= topL.first().value;
                ans += !restF.isEmpty() ? restF.last().value : 0;
            }
            pw.println(ans);
        }
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

    void sort(int[] A) {
        Random r = new Random();
        for (int i = A.length-1; i > 0; i--) {
            int j = r.nextInt(i+1);
            A[i] ^= A[j];
            A[j] ^= A[i];
            A[i] ^= A[j];
        }
        Arrays.sort(A);
    }

    int max(int a, int b) {
        return a >= b ? a : b;
    }

    int min(int a, int b) {
        return a <= b ? a : b;
    }
}

class Spell implements Comparable<Spell> {
    boolean fire;
    int value;
    Spell(boolean f, int val) {
        fire = f;
        value = val;
    }
    @Override
    public int hashCode() {
        return fire ? value : -value;
    }
    @Override
    public boolean equals(Object o) {
        Spell s = (Spell) o;
        return fire == s.fire && value == s.value;
    }
    @Override
    public int compareTo(Spell s) {
        if (value == s.value) {
            if (fire == s.fire) return 0;
            if (fire) return 1;
            return -1;
        }
        return Integer.compare(value, s.value);
    }
    @Override
    public String toString() {
        return Integer.toString(value) + (fire ? "F" : "L");
    }
}