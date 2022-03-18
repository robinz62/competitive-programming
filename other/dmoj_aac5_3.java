import java.io.*;
import java.math.BigInteger;
import java.util.*;

// Optimal solution but does not receive AC on website unless converted to cpp.
// mildly infuriating
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
        int[] nk = ril(2);
        int n = nk[0];
        int k = nk[1];
        List<List<int[]>> adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int i = 0; i < n-1; i++) {
            int[] uvw = ril(3);
            int u = uvw[0]-1;
            int v = uvw[1]-1;
            int w = uvw[2];
            adj.get(u).add(new int[]{v, w});
            adj.get(v).add(new int[]{u, w});
        }
        for (List<int[]> list : adj) Collections.sort(list, (x, y) -> Integer.compare(x[1], y[1]));

        lower = new int[n];
        upper = new int[n];
        Arrays.fill(lower, Integer.MAX_VALUE / 2);
        Arrays.fill(upper, Integer.MIN_VALUE / 2);
        dfs(0, -1, adj, 1, 1000000000);

        int[] s = ril(k);
        sort(s);

        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            if (lower[i] > upper[i]) continue;
            int topIdx = binarySearchRight(s, 0, k-1, upper[i]);
            if (topIdx < 0) topIdx = -topIdx-1-1;
            int botIdx = binarySearchRight(s, 0, k-1, lower[i]-1);
            if (botIdx < 0) botIdx = -botIdx-1-1;
            ans[i] = Math.max(0, topIdx - botIdx);
        }
        pw.print(ans[0]);
        for (int i = 1; i < n; i++) pw.print(" " + ans[i]);
        pw.println();
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    int[] lower;
    int[] upper;
    void dfs(int u, int p, List<List<int[]>> adj, int l, int r) {
        lower[u] = l;
        upper[u] = r;
        if (lower[u] > upper[u]) return;
        List<int[]> edges = adj.get(u);
        int skip = -1;
        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(i)[0] == p) {
                skip = i;
                break;
            }
        }
        for (int i = 0; i < edges.size(); i++) {
            int v = edges.get(i)[0];
            int w = edges.get(i)[1];
            if (v == p) continue;

            int L = i-1 == skip ? i-2 : i-1;
            int R = i+1 == skip ? i+2 : i+1;
            int myleft = L < 0 ? Integer.MIN_VALUE / 2 : (w + edges.get(L)[1]) / 2 + 1;
            int myright = R >= edges.size() ? Integer.MAX_VALUE / 2 : (edges.get(R)[1] + w) / 2;

            int start = Math.max(l, myleft);
            int end = Math.min(r, myright);
            dfs(v, u, adj, start, end);
        }
    }

    public static int binarySearchRight(int[] A, int l, int r, int k) {
        int upper = -1;
        while (l <= r) {
            int m = l + (r - l) / 2;
            if (A[m] == k && (m+1==A.length || A[m+1] > k)) {
                upper = m;
                break;
            }
            if (A[m] <= k) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return upper >= 0 ? upper : -l - 1;
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

// void dfs(int u, int p, int l, int r, vector<vector<pair<int, int>>>& adj, vector<int>& lower, vector<int>& upper) {
//     lower[u] = l;
//     upper[u] = r;
//     if (lower[u] > upper[u]) return;
//     vector<pair<int, int>>& edges = adj[u];
//     int skip = -1;
//     for (int i = 0; i < edges.size(); i++) {
//         if (edges[i].second == p) {
//             skip = i;
//             break;
//         }
//     }
//     for (int i = 0; i < edges.size(); i++) {
//         int v = edges[i].second;
//         int w = edges[i].first;
//         if (v == p) continue;

//         int L = i-1 == skip ? i-2 : i-1;
//         int R = i+1 == skip ? i+2 : i+1;
//         int myleft = L < 0 ? 0 : (w + edges[L].first) / 2 + 1;
//         int myright = R >= edges.size() ? 1000000001 : (edges[R].first + w) / 2;

//         int start = max(l, myleft);
//         int end = min(r, myright);
//         dfs(v, u, start, end, adj, lower, upper);
//     }
// }

// int main() {
//     int n, k;
//     cin >> n >> k;
//     vector<vector<pair<int, int>>> adj;
//     for (int i = 0; i < n; i++) adj.push_back(vector<pair<int, int>>());
//     for (int i = 0; i < n-1; i++) {
//         int u, v, w;
//         cin >> u >> v >> w;
//         u--; v--;
//         adj[u].push_back(make_pair(w, v));
//         adj[v].push_back(make_pair(w, u));
//     }
//     for (vector<pair<int, int>>& list : adj) sort(list.begin(), list.end());
    
//     vector<int> lower(n, 1000000001);
//     vector<int> upper(n, 0);
//     dfs(0, -1, 1, 1000000000, adj, lower, upper);
    
//     vector<int> s;
//     int si;
//     for (int i = 0; i < k; i++) {
//         cin >> si;
//         s.push_back(si);
//     }
//     sort(s.begin(), s.end());
    
//     vector<int> ans;
//     for (int i = 0; i < n; i++) {
//         if (lower[i] > upper[i]) {
//             ans.push_back(0);
//             continue;
//         }
//         auto topIdx = lower_bound(s.begin(), s.end(), upper[i] + 1);
//         int top = topIdx - s.begin() - 1;
//         auto botIdx = lower_bound(s.begin(), s.end(), lower[i]);
//         int bot = botIdx - s.begin() - 1;
//         ans.push_back(max(0, top - bot));
//     }
//     cout << ans[0];
//     for (int i = 1; i < n; i++) cout << " " << ans[i];
//     cout << "\n";
// }