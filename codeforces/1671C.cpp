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
        sort(a.begin(), a.end());

        vector<int64_t> prefix(n);
        prefix[0] = a[0];
        for (int i = 1; i < n; i++) prefix[i] = prefix[i-1] + a[i];

        // Returns last day we can get to this idx.
        auto binary_search = [&](int idx) -> int64_t {
            if (prefix[idx] > x) return -1;
            return (x - prefix[idx]) / (idx + 1);
        };

        int64_t ans = 0;
        for (int i = 0; i < n; i++) {
            int64_t days = binary_search(i);
            ans += days + 1;
        }

        cout << ans << '\n';
    }
}