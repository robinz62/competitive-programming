#include <bits/stdc++.h>

using namespace std;

constexpr int MOD = 1000000007;

int64_t mod_pow(int64_t base, int64_t exponent, int64_t m) {
    int64_t ans = 1;
    base = base % m;
    while (exponent > 0) {
        if ((exponent & 1) == 1) ans = ans * base % m;
        exponent >>= 1;
        base = base * base % m;
    } 
    return ans;
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int n, m;
    cin >> n >> m;
    vector<vector<int>> a(2, vector<int>(n));
    for (int& ai : a[0]) cin >> ai;
    for (int& ai : a[1]) cin >> ai;

    // int even_cols_sum = -1;
    // set<int> even_cols_singles;
    // int odd_cols_sum = -1;
    // set<int> odd_cols_singles;

    int even_min_sum = 2;
    int even_max_sum = 2 * m;
    int odd_min_sum = 2;
    int odd_max_sum = 2 * m;
    for (int i = 0; i < n; i++) {
        if (i % 2 == 0) {
            if (a[0][i] == 0 && a[1][i] == 0) continue;
            if (a[0][i] != 0 && a[1][i] != 0) {
                int sum = a[0][i] + a[1][i];
                even_min_sum = max(even_min_sum, sum);
                even_max_sum = min(even_max_sum, sum);
            } else {
                int here = max(a[0][i], a[1][i]);
                even_min_sum = max(even_min_sum, here+1);
                even_max_sum = min(even_max_sum, here+m);
            }
        } else {
            if (a[0][i] == 0 && a[1][i] == 0) continue;
            if (a[0][i] != 0 && a[1][i] != 0) {
                int sum = a[0][i] + a[1][i];
                odd_min_sum = max(odd_min_sum, sum);
                odd_max_sum = min(odd_max_sum, sum);
            } else {
                int here = max(a[0][i], a[1][i]);
                odd_min_sum = max(odd_min_sum, here+1);
                odd_max_sum = min(odd_max_sum, here+m);
            }
        }
    }

    if (even_min_sum > even_max_sum || odd_min_sum > odd_max_sum) {
        cout << "0";
        return 0;
    }

    int64_t even_ways = 0;
    int count_free_even = 0;
    for (int i = 0; i < n; i += 2) if (a[0][i] == 0 && a[1][i] == 0) count_free_even++;
    for (int sum = even_min_sum; sum <= even_max_sum; sum++) {
        int64_t ways_for_sum = mod_pow(sum-1 - max(0, sum-m-1) * 2, count_free_even, MOD);
        // for (int i = 0; i < n; i += 2) {
        //     if (a[0][i] == 0 && a[1][i] == 0) {
        //         int ways = sum-1 - max(0, sum-m-1) * 2;
        //         ways_for_sum = ways_for_sum * ways % MOD;
        //     } else {}  // only 1 way
        // }
        even_ways = (even_ways + ways_for_sum) % MOD;
    }
    int64_t odd_ways = 0;
    int count_free_odd = 0;
    for (int i = 1; i < n; i += 2) if (a[0][i] == 0 && a[1][i] == 0) count_free_odd++;
    for (int sum = odd_min_sum; sum <= odd_max_sum; sum++) {
        int64_t ways_for_sum = mod_pow(sum-1 - max(0, sum-m-1) * 2, count_free_odd, MOD);
        // for (int i = 1; i < n; i += 2) {
        //     if (a[0][i] == 0 && a[1][i] == 0) {
        //         int ways = sum-1 - max(0, sum-m-1) * 2;
        //         ways_for_sum = ways_for_sum * ways % MOD;
        //     } else {}  // only 1 way
        // }
        odd_ways = (odd_ways + ways_for_sum) % MOD;
    }

    cout << even_ways * odd_ways % MOD;
}