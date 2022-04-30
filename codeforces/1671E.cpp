#include <bits/stdc++.h>

using namespace std;

constexpr int MOD = 998244353;

vector<int64_t> dp1;
vector<string> dp2;

int left(int u) { return 2 * u; }
int right(int u) { return 2 * u + 1; }

void dfs(string& s, int u) {
    int l = left(u);
    int r = right(u);
    if (l >= (int) s.size()) {
        dp1[u] = 1;
        dp2[u] = string(1, s[u]);
        return;
    }

    dfs(s, l);
    dfs(s, r);
    if (dp2[l] > dp2[r]) {
        swap(dp2[l], dp2[r]);
        swap(dp1[l], dp1[r]);
    }
    dp2[u] = s[u] + dp2[l] + dp2[r];
    if (dp2[l] == dp2[r]) dp1[u] = dp1[l] + (dp1[l] * (MOD + dp1[l] - 1));
    else dp1[u] = 2l * dp1[l] * dp1[r];
    dp1[u] %= MOD;
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int n;
    cin >> n;
    string s(1 << n, ' ');
    for (int i = 1; i < (1 << n); i++) cin >> s[i];

    dp1 = vector<int64_t>(1 << n);
    dp2 = vector<string>(1 << n);

    dfs(s, 1);
    cout << dp1[1] << '\n';
}