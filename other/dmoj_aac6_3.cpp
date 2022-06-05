#include <bits/stdc++.h>

using namespace std;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int n;
    cin >> n;
    vector<int> a(n);
    for (int& ai : a) cin >> ai;  // 1 <= ai <= 100...

    int max_x = 1;
    for (int ai : a) max_x = max(max_x, ai);

    int64_t ans = 0;
    int offset = 10000002;
    vector<int> freq(20000005);
    for (int x = 1; x <= max_x; x++) {
        freq[x + offset] = 1;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += a[i];
            int val = sum - x * i + offset;
            ans += freq[val];
            freq[val]++;
        }

        // Clear for next iteration
        freq[x + offset] = 0;
        sum = 0;
        for (int i = 0; i < n; i++) {
            sum += a[i];
            int val = sum - x * i + offset;
            freq[val] = 0;
        }
    }

    cout << ans << '\n';
}