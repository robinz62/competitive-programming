#include <bits/stdc++.h>

using namespace std;

void print_answer(int i, string ans) {
    cout << "Case #" << i << ": " << ans << "\n";
}

vector<int64_t> f;
vector<vector<int>> adj;
vector<int64_t> dp1;  // lowest possible max to me
vector<int> dp2;      // child with the above value

// Finds the lowest possible min to me.
void dfs1(int u, int parent) {
    int64_t min_max_to_me = INT_MAX;
    int child = -1;

    for (int v : adj[u]) {
        if (v == parent) continue;
        dfs1(v, u);
        if (max(dp1[v], f[v]) < min_max_to_me) {
            min_max_to_me = max(dp1[v], f[v]);
            child = v;
        }
    }

    dp1[u] = min_max_to_me == INT_MAX ? INT_MIN : min_max_to_me;
    dp1[u] = max(dp1[u], f[u]);
    dp2[u] = child;
}

int64_t dfs2(int u) {
    int64_t ans = 0;
    
    if (adj[u].empty()) ans += f[u];
    for (int v : adj[u]) {
        if (v == dp2[u]) {
            ans += dp1[u];

            int curr = dp2[u];
            while (curr != -1) {
                for (int w : adj[curr]) {
                    if (w == dp2[curr]) continue;
                    ans += dfs2(w);
                }
                curr = dp2[curr];
            }
        } else {
            ans += dfs2(v);
        }
    }

    return ans;
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int T;
    cin >> T;
    for (int Ti = 1; Ti <= T; Ti ++) {
        int n;
        cin >> n;
        f.resize(n);
        for (int64_t& fi : f) cin >> fi;
        vector<int> p(n);
        adj.resize(n);
        fill(adj.begin(), adj.end(), vector<int>());
        for (int i = 0; i < n; i++) {
            cin >> p[i];
            p[i]--;
            if (p[i] >= 0) adj[p[i]].push_back(i);
        }

        dp1.resize(n);
        dp2.resize(n);
        int64_t ans = 0;
        for (int i = 0; i < n; i++) {
            if (p[i] == -1) {
                dfs1(i, -1);
                ans += dfs2(i);
            }
        }

        print_answer(Ti, to_string(ans));
    }
}