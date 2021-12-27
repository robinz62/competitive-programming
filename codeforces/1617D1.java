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
            int n = ri();

            // We are given at most 2n queries

            // What do the constraints tell us?
            // k > n/3, meaning "there are not too few imposters"
            // k < 2n/3, meaning "there are not too many imposters"

            // what happens if we query all disjoint triplets?
            // in the extreme cases, let's say we get all 0s, meaning there are more imposters.
            // if all of them are 2:1, then there are 2n/3 imposters. contradiction. so there
            // must be at least one triplet that is a 1 i.e. there are more crewmates than imposters.

            // similarly, if we get all 1s, there are more crewmates, but if all of them are 2:1 there are 2n/3 crewmates, contradiction.
            // so there must be at least one triplet that is a 0 i.e. there are more imposters than crewmates.

            // so all we've proven is if we query all disjoint triplets, we'll definitely get a 0 and a 1.

            // what if we can identify 1 imposter and 1 non-imposter?
            // then we're done, because we can query those 2 guys with any other, and the majority determines which that 3rd guy is.

            // So let's try to find 1 imposter and 1 non-imposter.

            // What about first two with everyone else?
            // if first two are the same, then we'll always get the same answer for sure
            // if first two are different, then we'll get answers in the ratio of I:C. It is impossible for all of these queries to be the same, since that would imply (n-2) are the same guys, which for the minimum
            // n = 6, is 4/6, which is too much.

            // So the second case is easy.
            // What about the first case? We get all 0s or all 1s. former means a1 and a2 are imposters. otherwise a1 and a2 are crewmates.

            // WLOG, have [I, I, _, _, _, ...]
            // We know we can find a triplet (distinct) that returns 0 (more crewmates)



            // [I, C, _, _, _, ...] Lets say we query up to 2n/3+1 and the answers are all the same.
            // Then that means there are (2n/3+1) - 2 + 1 = 2n/3 that are the same. Contradiction.

            int[] ans = new int[n+1];
            Arrays.fill(ans, -1);

            // res1[i] is the answer to query (1 2 i) for i in [3..2n/3+1]
            int[] res1 = new int[n+1];
            for (int i = 3; i <= 2 * n / 3 + 1; i++) {
                int r = query(1, 2, i);
                res1[i] = r;
            }

            boolean allsame = true;
            for (int i = 3; i <= 2 * n / 3 + 1; i++) {
                if (res1[i] != res1[3]) {
                    allsame = false;
                }
            }

            // 2n/3 - 1 queries used

            if (allsame) {
                if (res1[3] == 0) {
                    // first two are imposters
                    ans[1] = ans[2] = 0;

                    int idx0 = -1;
                    // we need to find a triplet that returns a 1, which must be possible
                    for (int i = 4; i <= n; i += 3) {
                        int r = query(i, i+1, i+2);
                        if (r == 1) {
                            idx0 = i;
                            break;
                        }
                    }

                    // [idx0, idx0+1, idx0+2] has more crewmates than imposters
                    boolean found = false;
                    int idxC = -1;
                    if (!found) {
                        int r = query(1, idx0, idx0+1);
                        if (r == 1) {
                            found = true;
                            ans[idx0] = ans[idx0+1] = 1;
                            idxC = idx0;
                        }
                    }
                    if (!found) {
                        int r = query(1, idx0, idx0+2);
                        if (r == 1) {
                            found = true;
                            ans[idx0] = ans[idx0+2] = 1;
                            idxC = idx0;
                        }
                    }
                    if (!found) {
                        // Don't need to query
                        found = true;
                        ans[idx0+1] = ans[idx0+2] = 1;
                        idxC = idx0+1;
                    }

                    for (int i = 1; i <= n; i++) {
                        if (ans[i] != -1) continue;
                        int r = query(1, idxC, i);
                        if (r == 0) {
                            ans[i] = 0;
                        } else {
                            ans[i] = 1;
                        }
                    }
                } else {
                    // opposite case
                    // first two are crewmates
                    ans[1] = ans[2] = 1;

                    int idx0 = -1;
                    // we need to find a triplet that returns a 0, which must be possible
                    for (int i = 4; i <= n; i += 3) {
                        int r = query(i, i+1, i+2);
                        if (r == 0) {
                            idx0 = i;
                            break;
                        }
                    }

                    // [idx0, idx0+1, idx0+2] has more imposters than crewmates
                    boolean found = false;
                    int idxI = -1;
                    if (!found) {
                        int r = query(1, idx0, idx0+1);
                        if (r == 0) {
                            found = true;
                            ans[idx0] = ans[idx0+1] = 0;
                            idxI = idx0;
                        }
                    }
                    if (!found) {
                        int r = query(1, idx0, idx0+2);
                        if (r == 0) {
                            found = true;
                            ans[idx0] = ans[idx0+2] = 0;
                            idxI = idx0;
                        }
                    }
                    if (!found) {
                        // Don't need to query
                        found = true;
                        ans[idx0+1] = ans[idx0+2] = 0;
                        idxI = idx0+1;
                    }

                    for (int i = 1; i <= n; i++) {
                        if (ans[i] != -1) continue;
                        int r = query(1, idxI, i);
                        if (r == 0) {
                            ans[i] = 0;
                        } else {
                            ans[i] = 1;
                        }
                    }
                }
            } else {
                // not all same. easy case.
                for (int i = 3; i <= n; i++) {
                    int r = query(1, 2, i);
                    if (r == 0) {
                        ans[i] = 0;
                    } else {
                        ans[i] = 1;
                    }
                }
                int idx0 = -1;
                int idx1 = -1;
                for (int i = 3; i <= n; i++) {
                    if (ans[i] == 0) idx0 = i;
                    else if (ans[i] == 1) idx1 = i;
                }
                int r = query(idx0, idx1, 1);
                if (r == 0) {
                    ans[1] = 0;
                    ans[2] = 1;
                } else {
                    ans[1] = 1;
                    ans[2] = 0;
                }
            }

            List<Integer> imposters = new ArrayList<>();
            for (int i = 1; i <= n; i++) {
                if (ans[i] == 0) imposters.add(i);
            }
            pw.print("! " + imposters.size());
            for (int idx : imposters) {
                pw.print(" " + idx);
            }
            pw.println();
            pw.flush();
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    int query(int a, int b, int c) throws IOException {
        pw.println("? " + a + " " + b + " " + c);
        pw.flush();
        int r = ri();
        return r;
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