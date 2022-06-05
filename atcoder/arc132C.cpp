#include <bits/stdc++.h>

using namespace std;

constexpr int MOD = 998244353;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int n, d;
    cin >> n >> d;
    vector<int> a(n);
    vector<bool> exists(n);
    for (int& ai : a) {
        cin >> ai;
        ai--;
        if (ai != -2) exists[ai] = true;
    }

    vector<int> missing_idx;
    for (int i = 0; i < n; i++) {
        if (a[i] == -2) missing_idx.push_back(i);
    }
    vector<int> missing_vals;
    for (int i = 0; i < n; i++) {
        if (!exists[i]) missing_vals.push_back(i);
    }

    int m = (int) missing_idx.size();

    if (m == 0) {
        cout << "1\n";
        return 0;
    }

    // dp[i][mask] is the number of ways to fill missing_idx[0..i] where the currently used mask is mask
    // mask contains bitmask for numbers [missing_idx[i]-d, missing_idx[i]+d].

    int x = 2 * d + 1;
    vector<vector<int>> dp(m, vector<int>(1 << x));

    // Base case. Select a value to take the place of missing_idx[0].
    int base_mask = 0;
    for (int v = max(0, missing_idx[0] - d); v <= min(n-1, missing_idx[0] + d); v++) {
        if (!exists[v]) base_mask |= 1 << (v - missing_idx[0] + d);
    }

    for (int b = 0; b < x; b++) {
        if ((base_mask & (1 << b)) == 0) continue;
        dp[0][base_mask ^ (1 << b)] = 1;
    }

    for (int i = 0; i < m-1; i++) {
        for (int mask = 0; mask < (1 << x); mask++) {
            if (dp[i][mask] == 0) continue;

            int idx_here = missing_idx[i];
            int idx_next = missing_idx[i+1];

            int dynamic = mask;
            while (idx_here < idx_next) {
                dynamic = dynamic >> 1;
                idx_here++;
                if (idx_here + d < n && !exists[idx_here + d]) dynamic |= (1 << (2 * d));
            }

            for (int v = max(0, idx_here - d); v <= min(n-1, idx_here + d); v++) {
                int idx_in_dynamic = v - idx_here + d;
                if ((dynamic & (1 << idx_in_dynamic)) == 0) continue;
                int after = dynamic ^ (1 << idx_in_dynamic);
                dp[i+1][after] += dp[i][mask];
                if (dp[i+1][after] >= MOD) dp[i+1][after] -= MOD;
            }
        }
    }

    cout << dp[m-1][0] << '\n';
}