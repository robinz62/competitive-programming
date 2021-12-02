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
            int[] nm = ril(2);
            int n = nm[0];
            int m = nm[1];
            forward = new ArrayList<>(n);
            backward = new ArrayList<>(n);
            visited = new int[n];
            isImposter = new boolean[n];
            for (int i = 0; i < n; i++) {
                forward.add(new ArrayList<>());
                backward.add(new ArrayList<>());
            }
            for (int i = 0; i < m; i++) {
                String[] line = br.readLine().split(" ");
                int u = Integer.parseInt(line[0])-1;
                int v = Integer.parseInt(line[1])-1;
                int c = line[2].charAt(0) == 'c' ? 0 : 1;
                forward.get(u).add(new int[]{v, c});
                backward.get(v).add(new int[]{u, c});
            }

            int ans = 0;
            for (int i = 0; ans != -1 && i < n; i++) {
                if (visited[i] == 2) continue;
                visited[i] = 1;
                isImposter[i] = false;
                int res1 = dfs(i, 1);
                visited[i] = 2;
                isImposter[i] = true;
                int res2 = dfs(i, 2);
                int res = Math.max(res1, res2);
                if (res == -1) {
                    ans = -1;
                    break;
                }
                ans += res;
            }
            pw.println(ans);
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    List<List<int[]>> forward;
    List<List<int[]>> backward;
    int[] visited;  // 0 means total unvisited, 1 means visited first run, 2 means visited second run.
    boolean[] isImposter;
    int dfs(int u, int visitkey) {
        int count = 0;
        if (isImposter[u]) count++;
        for (int[] v : forward.get(u)) {
            boolean myDictation;
            if (isImposter[u]) {
                if (v[1] == 0) myDictation = true;
                else myDictation = false;
            } else {
                if (v[1] == 0) myDictation = false;
                else myDictation = true;
            }

            if (visited[v[0]] == visitkey) {  // only verify
                if (myDictation != isImposter[v[0]]) return -1;
            } else {  // visit for first time
                visited[v[0]] = visitkey;
                isImposter[v[0]] = myDictation;
                int res = dfs(v[0], visitkey);
                if (res == -1) return -1;
                count += res;
            }
        }

        for (int[] v : backward.get(u)) {
            if (visited[v[0]] != visitkey) {  // not yet visited, need to assign a value
                visited[v[0]] = visitkey;
                boolean hesays = v[1] == 1;
                if (hesays != isImposter[u]) isImposter[v[0]] = true;
                else isImposter[v[0]] = false;

                int res = dfs(v[0], visitkey);
                if (res == -1) return -1;
                count += res;
            }
        }
        return count;
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
