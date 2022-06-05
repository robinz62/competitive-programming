#include <bits/stdc++.h>

using namespace std;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int n, p;
    cin >> n >> p;

    int min_delta = -3005;
    int max_delta = 3005;
    int offset = min_delta;

    auto delta = [](int x) {
        if (x < 10) return -(x-2);
        if (x < 100) return -(x-3);
        if (x < 1000) return -(x-4);
        if (x < 10000) return -(x-5);
        return -1;
    };

    // Count number of strings of length i whose delta is d.
    vector<vector<int64_t>> prefix(max_delta - min_delta + 1, vector<int64_t>(n+1));
    vector<vector<int64_t>> dp(max_delta - min_delta + 1, vector<int64_t>(n+1));
    dp[0][0] = 1;

    // How to get to (i, d)?
    // Last we added was, say,  1. Then we came from (i-1 , d-1)
    // Last we added was, say,  9. Then we came from (i-9 , d+7)
    // Last we added was, say, 10. Then we came from (i-10, d+7)
    // Last we added was, say, 11. Then we came from (i-11, d+8)
    //
    // Don't forget to handle 0-case separately.

    auto diag_sum = [&](int i, int j, int length) -> int64_t {
        j -= offset;
        if (i < 1 || j < 0 || j >= (int) prefix.size()) return 0;
        if (i-length < 0 || j+length >= (int) prefix.size()) return prefix[j][i];
        else return (prefix[j][i] + p - prefix[j+length][i-length]) % p;
    };

    for (int i = 1; i <= n; i++) {
        for (int d = -3000; d <= 3000; d++) {
            int64_t me = diag_sum(i-1, d-1, 9) + diag_sum(i-10, d+7, 90) + diag_sum(i-100, d+96, 900) + diag_sum(i-1000, d+995, 9000);
            me = me * 25 % p;
            if (d == delta(i)) me = (me + 26) % p;

            int d_idx = d - offset;
            dp[d_idx][i] = me;
            prefix[d_idx][i] = (prefix[d_idx+1][i-1] + me) % p;
        }
    }

    int64_t ans = 0;
    for (int d = -3000; d < 0; d++) {
        int d_idx = d - offset;
        ans = (ans + dp[d_idx][n]) % p;
    }

    cout << ans << "\n";
}