import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);

        int n = Integer.parseInt(br.readLine());
        String[] line = br.readLine().split(" ");
        long xtgt = Long.parseLong(line[0]);
        long ytgt = Long.parseLong(line[1]);

        List<long[]> left = new ArrayList<>();
        List<long[]> right = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            line = br.readLine().split(" ");
            long[] insn = new long[2];
            insn[0] = Long.parseLong(line[0]);
            insn[1] = Long.parseLong(line[1]);
            if (i < n / 2) left.add(insn);
            else right.add(insn);
        }

        // Map<PairWithK, Long> leftWays = ways(left);
        // Map<PairWithK, Long> rightWays = ways(right);
        Map<PairWithK, Long> leftWays = new HashMap<>();
        Map<PairWithK, Long> rightWays = new HashMap<>();
        ways(left, new PairWithK(0l, 0l, 0), 0, leftWays);
        ways(right, new PairWithK(0l, 0l, 0), 0, rightWays);
        
        Map<Pair, Map<Integer, Long>> rightWays2 = new HashMap<>();
        for (var e : rightWays.entrySet()) {
            PairWithK pk = e.getKey();
            long ways = e.getValue();

            Pair p = new Pair(pk.x, pk.y);
            if (!rightWays2.containsKey(p)) rightWays2.put(p, new HashMap<>());
            rightWays2.get(p).put(pk.k, rightWays2.get(p).getOrDefault(pk.k, 0l) + ways);
        }

        long[] ans = new long[n+1];
        for (var e : leftWays.entrySet()) {
            PairWithK p = e.getKey();
            long ways = e.getValue();

            // for (int wantk = 0; p.k + wantk <= n; wantk++) {
            //     PairWithK want = new PairWithK(xtgt - p.x, ytgt - p.y, wantk);
            //     ans[p.k + wantk] += ways * rightWays.getOrDefault(want, 0l);
            // }
            Pair want = new Pair(xtgt - p.x, ytgt - p.y);
            Map<Integer, Long> counts = rightWays2.get(want);
            if (counts == null) continue;
            for (var ee : counts.entrySet()) {
                int kk = ee.getKey();
                long waysways = ee.getValue();
                ans[p.k + kk] += ways * waysways;
            }
        }

        for (int i = 1; i <= n; i++) pw.println(ans[i]);

        pw.flush();
    }

    static void ways(List<long[]> insns, PairWithK curr, int i, Map<PairWithK, Long> collect) {
        if (i == insns.size()) {
            collect.put(curr, collect.getOrDefault(curr, 0l) + 1);
            return;
        }
        ways(insns, curr, i+1, collect);
        long dx = insns.get(i)[0];
        long dy = insns.get(i)[1];
        ways(insns, new PairWithK(curr.x+dx, curr.y+dy, curr.k+1), i+1, collect);
    }

    // static Map<PairWithK, Long> ways(List<long[]> insns) {
    //     Map<PairWithK, Long> ways = new HashMap<>();
    //     ways.put(new PairWithK(0l, 0l, 0), 1l);

    //     for (int i = 0; i < insns.size(); i++) {
    //         Map<PairWithK, Long> next = new HashMap<>(ways);
    //         long dx = insns.get(i)[0];
    //         long dy = insns.get(i)[1];
    //         for (var e : ways.entrySet()) {
    //             PairWithK PairWithK = e.getKey();
    //             long v = e.getValue();

    //             PairWithK pp = new PairWithK(PairWithK.x + dx, PairWithK.y + dy, PairWithK.k + 1);
    //             next.put(pp, next.getOrDefault(pp, 0l) + v);
    //         }
    //         ways = next;
    //     }

    //     return ways;
    // }
}

class Pair {
    long x;
    long y;
    Pair(long x, long y) {
        this.x = x;
        this.y = y;
    }
    public int hashCode() {
        return (int) (x * 132415132 + y);
    }
    public boolean equals(Object o) {
        Pair p = (Pair) o;
        return x == p.x && y == p.y;
    }
}

class PairWithK {
    long x;
    long y;
    int k;

    PairWithK(long x, long y, int k) {
        this.x = x;
        this.y = y;
        this.k = k;
    }
    public int hashCode() {
        return (int) (x * 132412345 + y + k * 22432);
    }
    public boolean equals(Object o) {
        PairWithK p = (PairWithK) o;
        return x == p.x && y == p.y && k == p.k;
    }
    public String toString() {
        return "(" + x + ", " + y + ", " + k + ")";
    }
}