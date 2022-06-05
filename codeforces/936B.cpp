#include <bits/stdc++.h>

using namespace std;

bool dfs(int u, vector<vector<int>>& adj, vector<int>& visited) {
    if (visited[u] == 1) return true;
    visited[u] = 1;
    for (int v : adj[u]) if (dfs(v, adj, visited)) return true;
    visited[u] = 2;
    return false;
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int n, m;
    cin >> n >> m;

    vector<vector<int>> adj(n);
    for (int i = 0; i < n; i++) {
        int c;
        cin >> c;
        adj[i] = vector<int>(c);
        for (int& v : adj[i]) {
            cin >> v;
            v--;
        }
    }

    int s;
    cin >> s;
    s--;

    // Draw if stuck in cycle, can't reach terminal node.
    // We want to find a path to a terminal node of odd length.

    vector<vector<int>> reversed_adj(n);
    for (int u = 0; u < n; u++) {
        for (int v : adj[u]) reversed_adj[v].push_back(u);
    }

    deque<pair<int, bool>> q;
    vector<bool> visited_winning(n);
    vector<int> parent1(n);
    vector<bool> visited_meh(n);
    vector<int> parent2(n);
    for (int u = 0; u < n; u++) {
        if (adj[u].empty()) {
            q.emplace_back(u, false);
            visited_meh[u] = true;
            parent2[u] = -1;
        }
    }
    while (!q.empty()) {
        auto [u, winning] = q.front();
        q.pop_front();
        for (int v : reversed_adj[u]) {
            if (winning) {
                if (visited_meh[v]) continue;
                visited_meh[v] = true;
                q.emplace_back(v, false);
                parent2[v] = u;
            } else {
                if (visited_winning[v]) continue;
                visited_winning[v] = true;
                q.emplace_back(v, true);
                parent1[v] = u;
            }
        }
    }

    if (visited_winning[s]) {
        vector<int> ans;
        int curr = s;
        bool win = true;
        while (curr != -1) {
            ans.push_back(curr);
            if (win) curr = parent1[curr];
            else curr = parent2[curr];
            win = !win;
        }
        cout << "Win\n";
        for (int ansi : ans) cout << ansi+1 << " ";
        cout << '\n';
        return 0;
    }

    vector<int> visited(n);
    if (dfs(s, adj, visited)) {
        cout << "Draw\n";
    } else {
        cout << "Lose\n";
    }
}