#include <bits/stdc++.h>

using namespace std;

vector<vector<int>> adj;
vector<int> color;

void dfs(int u, int p) {
    for (int v : adj[u]) {
        if (v == p) continue;
        color[v] = color[u] ^ 1;
        dfs(v, u);
    }
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int T;
    cin >> T;
    while (T--) {
        int n;
        cin >> n;
        adj = vector<vector<int>>(n);
        for (int i = 0; i < n-1; i++) {
            int u, v;
            cin >> u >> v;
            adj[u-1].push_back(v-1);
            adj[v-1].push_back(u-1);
        }

        color = vector<int>(n);
        dfs(0, -1);

        int count0 = 0;
        for (int c : color) if (c == 0) count0++;
        int count1 = n - count0;

        // swap colors if needed
        if (count0 > count1) {
            swap(count0, count1);
            for (int& c : color) c = c ^ 1;
        }

        vector<int> left;
        vector<bool> used(n+1);
        for (int b = 1; b <= count0; b <<= 1) {
            if ((count0 & b) == 0) continue;
            for (int val = b; val < 2 * b; val++) {
                left.push_back(val);
                used[val] = true;
            }
        }
        vector<int> right;
        for (int i = 1; i <= n; i++) if (!used[i]) right.push_back(i);

        vector<int> ans(n);
        for (int i = 0; i < n; i++) {
            if (color[i] == 0) {
                ans[i] = left.back();
                left.pop_back();
            } else {
                ans[i] = right.back();
                right.pop_back();
            }
        }

        for (int ansi : ans) cout << ansi << ' ';
        cout << '\n';
    }
}