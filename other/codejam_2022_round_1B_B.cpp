#include <bits/stdc++.h>

using namespace std;

void print_answer(int i, string ans) {
    cout << "Case #" << i << ": " << ans << "\n";
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int T;
    cin >> T;
    for (int Ti = 1; Ti <= T; Ti++) {
        int n, p;
        cin >> n >> p;
        vector<vector<int>> a(n, vector<int>(p));
        for (int i = 0; i < n; i++) {
            for (int& pi : a[i]) cin >> pi;
        }

        for (vector<int>& customer : a) sort(customer.begin(), customer.end());
        vector<pair<int, int>> min_max;
        for (int i = 0; i < n; i++) {
            min_max.emplace_back(*a[i].begin(), *a[i].rbegin());
        }

        vector<int64_t> dp1(n, numeric_limits<int64_t>::max());  // end low
        vector<int64_t> dp2(n, numeric_limits<int64_t>::max());  // end high
        dp1[0] = min_max[0].second + min_max[0].second - min_max[0].first;
        dp2[0] = min_max[0].second;
        for (int i = 1; i < n; i++) {
            dp1[i] = min(dp1[i], dp1[i-1] + abs(min_max[i-1].first - min_max[i].second) + abs(min_max[i].second - min_max[i].first));
            dp1[i] = min(dp1[i], dp2[i-1] + abs(min_max[i-1].second - min_max[i].second) + abs(min_max[i].second - min_max[i].first));
            dp2[i] = min(dp2[i], dp1[i-1] + abs(min_max[i-1].first - min_max[i].first) + abs(min_max[i].second - min_max[i].first));
            dp2[i] = min(dp2[i], dp2[i-1] + abs(min_max[i-1].second - min_max[i].first) + abs(min_max[i].second - min_max[i].first));
        }

        print_answer(Ti, to_string(min(dp1[n-1], dp2[n-1])));
    }
}