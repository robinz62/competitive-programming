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
        int[] s = readIntLine();
        Map<Integer, List<Integer>> cows = new HashMap<>();
        for (int i = 0; i < m; i++) {
            int[] fh = readIntLine();
            if (!cows.containsKey(fh[0])) cows.put(fh[0], new ArrayList<>());
            cows.get(fh[0]).add(fh[1]);
        }
        for (int sw : cows.keySet()) {
            Collections.sort(cows.get(sw));
        }

        Map<Integer, Integer> freqR = new HashMap<>();
        for (int i = 0; i < n; i++) freqR.put(s[i], freqR.getOrDefault(s[i], 0) + 1);
        
        List<Long> ways = new ArrayList<Long>();
        int total = 0;
        for (int sweetness : freqR.keySet()) {
            int amount = freqR.get(sweetness);
            List<Integer> cowz = cows.getOrDefault(sweetness, Arrays.asList());
            if (cowz.size() == 0) continue;
            if (cowz.size() == 1) {
                int num = Collections.binarySearch(cowz, amount);
                if (num < 0) num = -num - 1;
                else num++;
                if (num * 2 != 0) {
                    ways.add((long) (num * 2));
                    total++;
                }
                continue;
            }

            if (cowz.get(0) + cowz.get(1) > amount) {
                int num = Collections.binarySearch(cowz, amount);
                if (num < 0) num = -num - 1;
                if (num * 2 != 0) {
                    ways.add((long) (num * 2));
                    total++;
                }
                continue;
            }

            int num = Collections.binarySearch(cowz, amount);
            if (num < 0) num = -num - 1;
            int countPairs = 0;
            int l = 0;
            int r = Math.min(num, cowz.size() - 1);
            while (l < r) {
                if (cowz.get(l) + cowz.get(r) > amount) {
                    r--;
                } else if (cowz.get(l) + cowz.get(r) <= amount) {
                    countPairs += r - l;
                    l++;
                }
            }
            if (countPairs != 0) {
                ways.add((long) (countPairs * 2));
                total += 2;
            }
        }
        if (ways.isEmpty()) {
            pw.println("0 1");
        } else {
            pw.print(total);
            pw.print(" ");
            long ans = 1;
            for (long x : ways) {
                ans = (ans * x) % MOD;
            }
            pw.println(ans);
        }
    }
}