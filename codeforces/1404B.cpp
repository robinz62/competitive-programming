#include <bits/stdc++.h>

using namespace std;

vector<vector<int>> adj;

int dist(int u, int p, int b) {
    if (u == b) return 0;
    for (int v : adj[u]) {
        if (v == p) continue;
        int res = dist(v, u, b);
        if (res != -1) return res+1;
    }
    return -1;
}

int farthest;
int dist_of_farthest;
void dfs(int u, int p, int curr) {
    if (curr > dist_of_farthest) {
        dist_of_farthest = curr;
        farthest = u;
    }
    for (int v : adj[u]) {
        if (v == p) continue;
        dfs(v, u, curr+1);
    }
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int T;
    cin >> T;
    while (T--) {
        int n, a, b, da, db;
        cin >> n >> a >> b >> da >> db;
        a--; b--;

        adj = vector<vector<int>>(n);
        for (int i = 0; i < n-1; i++) {
            int u, v;
            cin >> u >> v;
            u--; v--;
            adj[u].push_back(v);
            adj[v].push_back(u);
        }

        if (db <= 2 * da || dist(a, -1, b) <= da) {
            cout << "Alice\n";
            continue;
        }

        // For Bob to win, the diameter has to be big enough i.e. > da * 2
        farthest = -1;
        dist_of_farthest = -1;
        dfs(0, -1, 0);
        dist_of_farthest = -1;
        dfs(farthest, -1, 0);
        if (dist_of_farthest <= da * 2) {
            cout << "Alice\n";
            continue;
        }

        cout << "Bob\n";
    }
}