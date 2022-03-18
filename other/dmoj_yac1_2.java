import java.io.*;
import java.math.BigInteger;
import java.util.*;

// AGAIN on dmoj, I needed to transcribe to cpp to get AC.
// Very sad times for Java folks.
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
    //
    // Interactive problems: don't forget to flush between test cases
    void solve() throws IOException {
        int n = ri();
        s = rs();
        List<List<Integer>> adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int i = 0; i < n-1; i++) {
            int[] uv = ril(2);
            adj.get(uv[0]-1).add(uv[1]-1);
            adj.get(uv[1]-1).add(uv[0]-1);
        }
        
        long[][] res = dfs(0, -1, adj);
        long ans = res[0][9] + res[1][9];
        pw.println(ans);
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    char[] s;
    long[][] dfs(int u, int p, List<List<Integer>> adj) {
        long count = 0;
        long[][] accum = new long[2][10];  // 0 means end at root, 1 means inaccessible
        int me = s[u] == 'W' ? 0 : 1;
        int mew = s[u] == 'W' ? 1 : 0;
        int meb = s[u] == 'B' ? 1 : 0;
        int justme = encode(mew, meb);
        for (int v : adj.get(u)) {
            if (v == p) continue;
            long[][] sub = dfs(v, u, adj);

            // add all of sub to inaccessible
            for (int i = 0; i < 10; i++) {
                accum[1][i] += sub[0][i] + sub[1][i];
            }

            long[] add = new long[10];

            // add paths that go through root to existing
            for (int i = 1; i < 10; i++) {
                int[] wbnew = decode(i);
                for (int j = 1; j < 10; j++) {
                    int[] wbexist = decode(j);
                    int code = encode(wbnew[0] + wbexist[0], wbnew[1] + wbexist[1]);
                    add[code] += sub[0][i] * accum[0][j];
                }
            }

            for (int i = 1; i < 10; i++) accum[1][i] += add[i];

            // extend all the extendable sub to include me
            for (int i = 1; i < 10; i++) {
                int[] wb = decode(i);
                wb[me]++;
                int code = encode(wb[0], wb[1]);
                accum[0][code] += sub[0][i];
            }
        }
        accum[0][justme]++;

        return accum;
    }

    int encode(int w, int b) {
        if (w >= 3 || b >= 3) return 9;
        return w * 3 + b;
    }

    int[] decode(int code) {
        return new int[]{code / 3, code % 3};
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

// #include <bits/stdc++.h>

// using namespace std;

// int encode(int w, int b) {
//     if (w >= 3 || b >= 3) return 9;
//     return w * 3 + b;
// }

// pair<int, int> decode(int code) {
//     return make_pair<int, int>(code / 3, code % 3);
// }

// vector<char> s;

// vector<vector<long long int>> dfs(int u, int p, vector<vector<int>>& adj) {
//     long long int count = 0;
//     vector<vector<int>> accum(2, vector<int>(10, 0));
//     int me = s[u] == 'W' ? 0 : 1;
//     int mew = s[u] == 'W' ? 1 : 0;
//     int meb = s[u] == 'B' ? 1 : 0;
//     int justme = encode(mew, meb);
//     for (int v : adj.get(u)) {
//         if (v == p) continue;
//         vector<vector<long long int>> sub = dfs(v, u, adj);

//         for (int i = 0; i < 10; i++) {
//             accum[1][i] += sub[0][i] + sub[1][i];
//         }

//         vector<long long int> add(10, 0);

//         for (int i = 1; i < 10; i++) {
//             pair<int, int> wbnew = decode(i);
//             for (int j = 1; j < 10; j++) {
//                 pair<int, int> wbexist = decode(j);
//                 int code = encode(wbnew.first + wbexist.first, wbnew.second + wbexist.second);
//                 add[code] += sub[0][i] * accum[0][j];
//             }
//         }

//         for (int i = 1; i < 10; i++) accum[1][i] += add[i];

//         // extend all the extendable sub to include me
//         for (int i = 1; i < 10; i++) {
//             pair<int, int> wb = decode(i);
//             if (mew) wb.first++;
//             else wb.second++;
//             int code = encode(wb.first, wb.second);
//             accum[0][code] += sub[0][i];
//         }
//     }
//     accum[0][justme]++;

//     return accum;
// }

// int main() {
//     ios_base::sync_with_stdio(false);
//     std::cin.tie(nullptr);

//     int n;
//     cin >> n;
    
//     s.reserve(n);
//     for (char& c : s) cin >> c;

//     vector<vector<int>> adj(n, vector<int>());
//     for (int i = 0; i < n-1; i++) {
//         int u, v;
//         cin >> u >> v;
//         adj[u-1].push_back(v-1);
//         adj[v-1].push_back(u-1);
//     }

//     vector<vector<long long int>> res = dfs(0, -1, adj);
//     long long int ans = res[0][9] + res[1][9];
//     cout << ans;
// }