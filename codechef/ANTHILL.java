import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pw = new PrintWriter(System.out);
    static int MOD = 1000000007;

    static PrintWriter pww;
    int totalScore;
    public static void main(String[] args) throws IOException {
        Main m = new Main();
        
        // pww = new PrintWriter(new FileWriter("out"));
        // m.br.close();
        // for (int i = 1; i <= 12; i++) {
        //     m.br = new BufferedReader(new FileReader("TODELETE/in" + i));
        //     m.solve();
        // }
        // pww.println();
        // pww.println(m.totalScore);
        // pww.flush();
        // pww.close();

        m.solve();

        // generate test cases
        // int[][] SLs = new int[][]{
        //     {1, 2048},
        //     {2, 1024},
        //     {16, 256},
        //     {64, 128},
        // };
        // int[] Ds = new int[]{0, 128, 2048};
        // m.generateTestCase(SLs[3][0], SLs[3][1], Ds[2]);

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

    // This solution ended up with a score of 68.485 / 100
    void solve() throws IOException {
        int[] line = readIntLine();
        int N = line[0];  // = 512
        int M = line[1];  // ~ 1-2 * 10^4
        int A = line[2];  // = 10
        int R = line[3];  // = 2
        List<Set<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < N; i++) adj.add(new HashSet<>());
        Set<Integer> edges = new HashSet<>();  // u < v: u * 512 + v
        for (int i = 0; i < M; i++) {
            line = readIntLine();
            adj.get(line[0] - 1).add(line[1] - 1);
            adj.get(line[1] - 1).add(line[0] - 1);
            if (line[0] < line[1]) edges.add((line[0]-1) * N + (line[1]-1));
            else edges.add((line[1]-1) * N + (line[0]-1));
        }

        Set<Integer> originalEdges = new HashSet<>(edges);  // the original target set of edges
        Set<Integer> addedEdges = new HashSet<>();          // all the edges we added, including superfluous

        List<String> instructions = new ArrayList<>();
        int score = 0;

        while (!edges.isEmpty()) {
            int bestEdge = 0;
            List<Integer> intersection = new ArrayList<>();
            for (int e : edges) {
                int u = e / N;
                int v = e % N;
                Set<Integer> s1 = adj.get(u);
                Set<Integer> s2 = adj.get(v);
                if (s2.size() > s1.size()) {
                    Set<Integer> temp = s1;
                    s1 = s2;
                    s2 = temp;
                }
                if (s1.size() < intersection.size()) continue;
                List<Integer> cand = new ArrayList<>();
                for (int x : s1) {
                    if (s2.contains(x)) {
                        cand.add(x);
                    }
                }
                if (cand.size() > intersection.size()) {
                    bestEdge = e;
                    intersection = cand;
                }
            }
            if (intersection.size() <= 1) break;
            int u = bestEdge / N;
            int v = bestEdge % N;
            int s = intersection.size();
            
            // Remove all added edges from the set of edges yet to process
            // this may over-remove (i.e. remove edges that we didn't need to process). that's fine.
            // Also add these same edges to addedEdges set

            // (1/3) the key/cornerstone edge (u, v)
            edges.remove(bestEdge);
            addedEdges.add(bestEdge);
            adj.get(u).remove(v); adj.get(v).remove(u);

            // (2/3) the triangle edges i.e. (u, x) and (v, x) for common neighbor x
            for (int x : intersection) {
                if (u < x) { edges.remove(u*N+x); addedEdges.add(u*N+x); }
                else { edges.remove(x*N+u); addedEdges.add(x*N+u); }
                adj.get(u).remove(x); adj.get(x).remove(u);
                if (v < x) { edges.remove(v*N+x); addedEdges.add(v*N+x); }
                else { edges.remove(x*N+v); addedEdges.add(x*N+v); }
                adj.get(v).remove(x); adj.get(x).remove(v);
            }

            // (3/3) additional weird edges i.e. (u, x1) + (u, x2) -> (x1, x2)
            // edges are added for any nodes xi whose index has the same parity
            // randomly choose a split
            Collections.shuffle(intersection);
            int split = s;  // experimentally, split doesn't make much difference it seems
            for (int i = 0; i < split; i++) {
                int x = intersection.get(i);
                for (int j = i + 1; j < split; j++) {
                    int y = intersection.get(j);
                    int e = x < y ? x*N+y : y*N+x;
                    if (Math.random() < 1) edges.remove(e);
                    addedEdges.add(e);
                    adj.get(x).remove(y);
                    adj.get(y).remove(x);
                }
            }
            for (int i = split; i < s; i++) {
                int x = intersection.get(i);
                for (int j = i + 1; j < s; j++) {
                    int y = intersection.get(j);
                    int e = x < y ? x*N+y : y*N+x;
                    edges.remove(e); addedEdges.add(e);
                    adj.get(x).remove(y); adj.get(y).remove(x);
                }
            }

            // print out the direct edges we decided to add
            StringBuilder sb = new StringBuilder();
            sb.append("1 ").append(s + 1).append(" ");
            for (int i = 0; i < split; i++) {
                sb.append(u+1).append(" ").append(intersection.get(i)+1).append(" ");
            }
            for (int i = split; i < s; i++) {
                sb.append(v+1).append(" ").append(intersection.get(i)+1).append(" ");
            }
            sb.append(u+1).append(" ").append(v+1);
            instructions.add(sb.toString());
            score += (s + 1) * A;
        }

        // STRATEGY2: find cliques of size 3
        // pretty sure this doesn't do anything because above already takes care of but whatever
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                for (int k = j + 1; k < N; k++) {
                    if (edges.contains(i*N + j) && edges.contains(i*N + k) && edges.contains(j*N + k)) {
                        edges.remove(i*N + j);
                        edges.remove(i*N + k);
                        edges.remove(j*N + k);
                        StringBuilder sb = new StringBuilder();
                        sb.append("1 2 ");
                        sb.append(i+1).append(" ").append(j+1).append(" ");
                        sb.append(j+1).append(" ").append(k+1);
                        instructions.add(sb.toString());
                        score += 2 * A;
                    }
                }
            }
        }

        // STRATEGY3: find remaining stragglers individually
        score += edges.size() * A;
        for (int e : edges) {
            int u = e / N;
            int v = e % N;
            StringBuilder sb = new StringBuilder();
            sb.append("1 1 ").append(u+1).append(" ").append(v+1);
            instructions.add(sb.toString());
        }

        for (int e : addedEdges) {
            // remove over-added edges
            if (!originalEdges.contains(e)) {
                StringBuilder sb = new StringBuilder();
                sb.append("2 ").append(e / N + 1).append(" ").append(e % N + 1);
                instructions.add(sb.toString());
                score += 2;
            }
        }

// pww.println(score);
// totalScore += score;
        pw.println(instructions.size());
        for (String ins : instructions) pw.println(ins);
    }

    void generateTestCase(int S, int L, int D) {
        List<Integer> edges = new ArrayList<>();
        int N = 512;
        for (int i = 0; i < N; i++)
            for (int j = i + 1; j < N; j++)
                edges.add(i * N + j);

        List<List<Integer>> sets = new ArrayList<>();
        for (int i = 0; i < S; i++) {
            Collections.shuffle(edges);
            List<Integer> set = new ArrayList<>();
            for (int j = 0; j < L; j++) set.add(edges.get(j));
            sets.add(set);
        }

        List<Integer> toRemove = new ArrayList<>();
        Collections.shuffle(edges);
        for (int i = 0; i < D; i++)
            toRemove.add(edges.get(i)); 

        Set<Integer> E = new HashSet<>();
        for (List<Integer> set : sets) {
            for (int i = 0; i < set.size(); i++) {
                for (int j = i + 1; j < set.size(); j++) {
                    // TreeSet<Integer> vs = new TreeSet<>();
                    int e1 = set.get(i);
                    int e2 = set.get(j);
                    // vs.add(e1 / N);
                    // vs.add(e1 % N);
                    // vs.add(e2 / N);
                    // vs.add(e2 % N);
                    // if (vs.size() == 3) {
                    //     Iterator<Integer> it = vs.iterator();
                    //     int a = it.next();
                    //     int b = it.next();
                    //     int c = it.next();
                    //     E.add(a * N + b);
                    //     E.add(a * N + c);
                    //     E.add(b * N + c);
                    // } else {
                        E.add(e1);
                        E.add(e2);
                    // }
                }
            }
        }

        for (Integer e : toRemove) {
            E.remove(e);
        }

        int M = E.size();
        pw.println(N + " " + M + " 10 2");
        for (int e : E) {
            pw.println((e % N + 1) + " " + (e / N + 1));
        }
    }
}
