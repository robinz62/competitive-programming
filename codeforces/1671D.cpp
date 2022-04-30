#include <bits/stdc++.h>

using namespace std;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int T;
    cin >> T;
    while (T--) {
        int n, x;
        cin >> n >> x;
        vector<int> a(n);
        for (int& ai : a) cin >> ai;

        int64_t ans = 0;
        int mn = a[0];
        int mx = a[0];
        for (int i = 1; i < n; i++) {
            ans += abs(a[i] - a[i-1]);
            mx = max(mx, a[i]);
            mn = min(mn, a[i]);
        }
        
        ans += min({
            x - 1 + abs(a[0] - 1),  // x, 1, rest
            x - 1 + abs(x - a[0]),  // 1, x, rest
            x - 1 + abs(x - a[n-1]),  // rest, x, 1
            x - 1 + abs(a[n-1] - 1),  // rest, 1, x
            abs(x - a[0]) + abs(a[n-1] - 1),  // x, rest, 1
            abs(a[0] - 1) + abs(x - a[n-1]),  // 1, rest, x
            abs(a[0] - 1) + (x <= mx ? 0 : 2 * (x - mx)),  // 1, rest with x
            abs(x - a[0]) + 2 * (mn - 1),  // x, rest with 1
            abs(a[n-1] - 1) + (x <= mx ? 0 : 2 * (x - mx)),  // rest with x, 1
            abs(x - a[n-1]) + 2 * (mn - 1),  // rest with 1, x
            (x <= mx ? 0 : 2 * (x - mx)) + 2 * (mn - 1)
        });

        cout << ans << '\n';
    }
}