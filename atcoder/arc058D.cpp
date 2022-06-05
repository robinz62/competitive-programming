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

int64_t mod_inverse(int64_t a, int64_t m) {
    return mod_pow(a, m - 2, m);
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int h, w, a, b;
    cin >> h >> w >> a >> b;
    int x = h - a;
    // int y = w - b;

    vector<int64_t> fact(200001);
    vector<int64_t> inv_fact(200001);
    fact[0] = 1;
    for (int i = 1; i <= 200000; i++) fact[i] = fact[i-1] * i % MOD;
    for (int i = 0; i <= 200000; i++) inv_fact[i] = mod_inverse(fact[i], MOD);

    auto ways = [&](int rows, int cols) {\
        return fact[rows + cols - 2] * inv_fact[rows - 1] % MOD * inv_fact[cols - 1] % MOD;
    };

    int r = x-1;
    int c = b;
    int64_t ans = 0;
    while (r >= 0 && c < w) {
        int64_t add = ways(r+1, c+1) * ways(h-r, w-c) % MOD;
        ans += add;
        if (ans >= MOD) ans -= MOD;
        r--;
        c++;
    }

    cout << ans << '\n';
}