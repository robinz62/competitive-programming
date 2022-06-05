#include <bits/stdc++.h>

using namespace std;

vector<vector<int>> adj;
vector<vector<int>> dp;  // dp[u][0/1] is the best count when u is unhappy / happy
vector<vector<int>> weights;  // weights[u][0/1] is the weight of u when it is unhappy/happy

vector<vector<int>> sum_weights;  // sum of u's subtree when u is unhappy/happy

vector<int> ans;

void dfs(int u, int p) {
    for (int v : adj[u]) {
        if (v == p) continue;
        dfs(v, u);

        // when u is unhappy, children can be whatever
        if (dp[v][0] > dp[v][1] || (dp[v][0] == dp[v][1] && sum_weights[v][0] <= sum_weights[v][1])) {
            dp[u][0] += dp[v][0];
            sum_weights[u][0] += sum_weights[v][0];
        } else {
            dp[u][0] += dp[v][1];
            sum_weights[u][0] += sum_weights[v][1];
        }

        dp[u][1] += dp[v][0];
        sum_weights[u][1] += sum_weights[v][0];
        weights[u][1] += weights[v][0];
    }

    if (p != -1) weights[u][1]++;  // +1 for unhappy parent
    sum_weights[u][0] += weights[u][0];
    sum_weights[u][1] += weights[u][1];
    dp[u][1]++;
}

void dfs2(int u, int p, int me) {
    ans[u] = weights[u][me];

    for (int v : adj[u]) {
        if (v == p) continue;
        if (me == 1) {
            dfs2(v, u, 0);
        } else {
            if (dp[v][0] == dp[v][1]) {
                if (sum_weights[v][0] <= sum_weights[v][1]) {
                    dfs2(v, u, 0);
                } else {
                    dfs2(v, u, 1);
                }
            } else if (dp[v][0] > dp[v][1]) {
                dfs2(v, u, 0);
            } else {
                dfs2(v, u, 1);
            }
        }
    }
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int n;
    cin >> n;
    adj = vector<vector<int>>(n);
    for (int i = 0; i < n-1; i++) {
        int u, v;
        cin >> u >> v;
        adj[u-1].push_back(v-1);
        adj[v-1].push_back(u-1);
    }

    // n = 2 is probably edge case
    if (n == 2) {
        cout << "2 2\n";
        cout << "1 1\n";
        return 0;
    }

    // dp[u][0/1] is the best answer for subtree rooted at u where u is happy/unhappy
    dp = vector<vector<int>>(n, vector<int>(2));
    weights = vector<vector<int>>(n, {1, 0});
    sum_weights = vector<vector<int>>(n, vector<int>(2));
    ans = vector<int>(n);

    dfs(0, -1);

    if (dp[0][0] == dp[0][1]) {
        if (sum_weights[0][0] <= sum_weights[0][1]) {
            dfs2(0, -1, 0);
        } else {
            dfs2(0, -1, 1);
        }
    } else if (dp[0][0] > dp[0][1]) {
        dfs2(0, -1, 0);
    } else {
        dfs2(0, -1, 1);
    }

    int sum = 0;
    for (int w : ans) sum += w;
    cout << max(dp[0][0], dp[0][1]) << " " << sum << "\n";
    for (int w : ans) cout << w << ' ';
    cout << '\n';
}