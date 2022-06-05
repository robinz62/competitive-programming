#include <bits/stdc++.h>

using namespace std;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int n, m;
    cin >> n >> m;
    vector<int> a(n);
    for (int& ai : a) cin >> ai;

    multiset<int> set_;
    for (int ai : a) set_.insert(ai);

    vector<int> ans;
    ans.reserve(n);
    while (!set_.empty()) {
        auto it = set_.begin();
        int val = *it;
        ans.push_back(val);
        set_.erase(it);

        if (set_.empty()) break;

        int need = m - val;
        it = set_.lower_bound(need);
        if (it == set_.end()) {
            cout << "-1";
            return 0;
        }
        val = *it;
        ans.push_back(val);
        set_.erase(it);
    }

    cout << ans[0];
    for (int i = 1; i < n; i++) cout << ' ' << ans[i];
}