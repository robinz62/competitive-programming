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
    //   if (x : long) and (y : int), [y = x] does not compile, but [y += x] does
    //   sorting, or taking max, after MOD
    void solve() throws IOException {
        int T = ri();
        for (int Ti = 0; Ti < T; Ti++) {
            int[] nmk = ril(3);
            int n = nmk[0];
            int m = nmk[1];
            int k = nmk[2];

            // CAREFUL OVERFLOW

            int[] x = ril(n);
            List<List<int[]>> ladders = new ArrayList<>(n);  // floor start -> [room start, floor end, room end, health]
            for (int i = 0; i < n; i++) ladders.add(new ArrayList<>());
            for (int i = 0; i < k; i++) {
                int[] abcdh = ril(5);
                int a = abcdh[0]-1;
                int b = abcdh[1]-1;
                int c = abcdh[2]-1;
                int d = abcdh[3]-1;
                int h = abcdh[4];

                ladders.get(a).add(new int[]{b, c, d, h});
            }
            for (List<int[]> laddersOnRow : ladders) {
                Collections.sort(laddersOnRow, (l1, l2) -> Integer.compare(l1[0], l2[0]));
            }

            // for each floor/room, best hp getting there?
            // tricky is the fact that there may be many rooms for a particular floor -> n^2
            // what's the minimum hp after i take *this* particular ladder?
            //
            // for a particular floor, it can be subdivided into contiguous segments
            // each segment corresponds to a particular ladder that got to there

            // floor -> (room -> best hp)
            // [room] is only INTERESTING rooms
            List<TreeMap<Integer, Long>> hp = new ArrayList<>(n);
            for (int i = 0; i < n; i++) hp.add(new TreeMap<>());
            hp.get(0).put(0, 0l);

            for (int[] ladder : ladders.get(0)) {
                long init = (long) x[0] * ladder[0];
                long sum = -init + ladder[3];
                long existing = hp.get(ladder[1]).getOrDefault(ladder[2], Long.MIN_VALUE);
                if (sum > existing) hp.get(ladder[1]).put(ladder[2], sum);
            }

            // Process ladders starting on floor i in increasing order
            for (int i = 1; i < n; i++) {
                // [hp] is the "from" list
                // [ladders] is the "to" list

                TreeMap<Integer, Long> from = hp.get(i);
                List<int[]> to = ladders.get(i);  // [room start, floor end, room end, health]

                // Find the best way to get to THIS ladder from the left
                if (from.isEmpty()) continue;
                Integer fromIdx = from.firstKey();
                int bestFromIdx = fromIdx;
                long hpOfBestFrom = from.get(bestFromIdx);

                for (int[] ladder : to) {
                    int toIdx = ladder[0];
                    if (bestFromIdx > toIdx) continue;
                    
                    while (fromIdx != null && fromIdx <= toIdx) {
                        long candidate = from.get(fromIdx) - (long) x[i] * (toIdx - fromIdx);
                        long existing = hpOfBestFrom - (long) x[i] * (toIdx - bestFromIdx);
                        if (candidate > existing) {
                            bestFromIdx = fromIdx;
                            hpOfBestFrom = from.get(fromIdx);
                        }
                        fromIdx = from.higherKey(fromIdx);
                    }

                    long score = hpOfBestFrom - (long) x[i] * (toIdx - bestFromIdx) + ladder[3];
                    long existing = hp.get(ladder[1]).getOrDefault(ladder[2], Long.MIN_VALUE);
                    if (score > existing) hp.get(ladder[1]).put(ladder[2], score);
                }

                // From the right
                fromIdx = from.lastKey();
                bestFromIdx = fromIdx;
                hpOfBestFrom = from.get(bestFromIdx);

                for (int j = to.size()-1; j >= 0; j--) {
                    int[] ladder = to.get(j);
                    int toIdx = ladder[0];
                    if (bestFromIdx < toIdx) continue;

                    while (fromIdx != null && fromIdx >= toIdx) {
                        long candidate = from.get(fromIdx) - (long) x[i] * (fromIdx - toIdx);
                        long existing = hpOfBestFrom - (long) x[i] * (bestFromIdx - toIdx);
                        if (candidate > existing) {
                            bestFromIdx = fromIdx;
                            hpOfBestFrom = from.get(fromIdx);
                        }
                        fromIdx = from.lowerKey(fromIdx);
                    }

                    long score = hpOfBestFrom - (long) x[i] * (bestFromIdx - toIdx) + ladder[3];
                    long existing = hp.get(ladder[1]).getOrDefault(ladder[2], Long.MIN_VALUE);
                    if (score > existing) hp.get(ladder[1]).put(ladder[2], score);
                }
            }

            long ans = Long.MIN_VALUE;
            Map<Integer, Long> lastFloor = hp.get(n-1);
            for (int idx : lastFloor.keySet()) {
                long damage = (long) x[n-1] * (m-1 - idx);
                long finalHp = lastFloor.get(idx) - damage;
                ans = Math.max(ans, finalHp);
            }
            if (ans == Long.MIN_VALUE) {
                pw.println("NO ESCAPE");
            } else {
                pw.println(-ans);
            }
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

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
        int c = br.read();
        int x = 0;
        while (c >= '0' && c <= '9') {
            x = x * 10 + c - '0';
            c = br.read();
        }
        return ril(x);
    }

    long[] rkll() throws IOException {
        int c = br.read();
        int x = 0;
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