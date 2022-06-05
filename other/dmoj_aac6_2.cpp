#include <bits/stdc++.h>

using namespace std;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int n, q;
    cin >> n >> q;
    vector<int> farthest(n, -1);
    for (int i = 0; i < q; i++) {
        int l, r;
        cin >> l >> r;
        l--;
        r--;
        farthest[l] = max(farthest[l], r);
    }

    int max_active_interval = -1;
    for (int i = 0; i < n; i++) {
        max_active_interval = max(max_active_interval, farthest[i]);
        if (max_active_interval >= i) farthest[i] = max_active_interval;
    }

    int id = 0;
    set<int> available;
    set<pair<int, int>> queue; // contains [when available, value]

    vector<int> ans(n);

    for (int i = 0; i < n; i++) {
        while (!queue.empty() && queue.begin()->first < i) {
            available.insert(queue.begin()->second);
            queue.erase(queue.begin());
        }

        if (available.empty()) {
            available.insert(id);
            id++;
        }

        int val = i-1 >= 0 && available.find(ans[i-1]) != available.end() ? ans[i-1] : *available.begin();
        ans[i] = val;
        if (farthest[i] != -1) {
            available.erase(val);
            queue.emplace(farthest[i], val);
        }
    }

    cout << id << '\n';
    for (int i = 0; i < n; i++) cout << ans[i]+1 << " \n"[i == n-1];
}