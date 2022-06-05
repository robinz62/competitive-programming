#include <bits/stdc++.h>

using namespace std;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int n, m;
    double s;
    cin >> n >> m >> s;
    vector<int> a(n);
    for (int& ai : a) cin >> ai;

    vector<int64_t> prefix(n);
    prefix[0] = a[0];
    for (int i = 1; i < n; i++) prefix[i] = prefix[i-1] + a[i];
    auto sum = [&](int l, int r) {
        return prefix[r] - (l-1 >= 0 ? prefix[l-1] : 0);
    };

    vector<double> ans(n);
    int sz = n;
    while (s > 0 && sz > 0) {
        int best_idx = -1;
        double best_bang_for_buck = 0;
        for (int i = 0; i < sz; i++) {
            int len = sz - i;
            double bang_for_buck = (double) sum(i, sz-1) / len;
            if (bang_for_buck > best_bang_for_buck) {
                best_idx = i;
                best_bang_for_buck = bang_for_buck;
            }
        }
        if (best_idx == -1) break;

        int len = sz - best_idx;
        double give = min(s / len, (double) m);
        for (int i = best_idx; i < sz; i++) {
            ans[i] += give;
        }
        s -= give * len;
        sz = best_idx;
    }

    double answer = 0;
    for (int i = 0; i < n; i++) answer += ans[i] * a[i];
    cout << setprecision(16) << answer << '\n';
}