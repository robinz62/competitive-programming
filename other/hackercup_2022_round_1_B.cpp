#include <bits/stdc++.h>

using namespace std;

constexpr int64_t MOD = 1000000007;

void print_ans(int i, string ans) {
    cout << "Case #" << i << ": " << ans << '\n';
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int T;
    cin >> T;
    for (int Ti = 1; Ti <= T; Ti++) {
        int n;
        cin >> n;

        vector<int64_t> tree_x;
        vector<int64_t> tree_y;
        vector<int64_t> well_x;
        vector<int64_t> well_y;

        int64_t in;
        for (int i = 0; i < n; i++) {
            cin >> in;
            tree_x.push_back(in);
            cin >> in;
            tree_y.push_back(in);
        }
        int m;
        cin >> m;
        for (int i = 0; i < m; i++) {
            cin >> in;
            well_x.push_back(in);
            cin >> in;
            well_y.push_back(in);
        }

        int64_t ans = 0;

        {
            int64_t sum_t = 0;
            int64_t sum_t2 = 0;
            int64_t sum_w = 0;
            int64_t sum_w2 = 0;
            for (int64_t t : tree_x) {
                sum_t = (sum_t + t) % MOD;
                sum_t2 = (sum_t2 + t * t % MOD) % MOD;
            }
            for (int64_t w : well_x) {
                sum_w = (sum_w + w) % MOD;
                sum_w2 = (sum_w2 + w * w % MOD) % MOD;
            }
            ans = (ans + sum_t2 * m % MOD) % MOD;
            ans = (ans + sum_w2 * n % MOD) % MOD;
            
            int64_t sub = sum_t * sum_w % MOD * 2 % MOD;
            ans = (ans + MOD - sub) % MOD;
        }

        {
            int64_t sum_t = 0;
            int64_t sum_t2 = 0;
            int64_t sum_w = 0;
            int64_t sum_w2 = 0;
            for (int64_t t : tree_y) {
                sum_t = (sum_t + t) % MOD;
                sum_t2 = (sum_t2 + t * t % MOD) % MOD;
            }
            for (int64_t w : well_y) {
                sum_w = (sum_w + w) % MOD;
                sum_w2 = (sum_w2 + w * w % MOD) % MOD;
            }
            ans = (ans + sum_t2 * m % MOD) % MOD;
            ans = (ans + sum_w2 * n % MOD) % MOD;
            
            int64_t sub = sum_t * sum_w % MOD * 2 % MOD;
            ans = (ans + MOD - sub) % MOD;
        }

        print_ans(Ti, to_string(ans));
    }
}