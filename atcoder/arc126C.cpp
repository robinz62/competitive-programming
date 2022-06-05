#include <bits/stdc++.h>

using namespace std;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int n;
    int64_t k;
    cin >> n >> k;
    vector<int> a(n);
    for (int& ai : a) cin >> ai;

    sort(a.begin(), a.end());

    int64_t make_all_same = 0;
    for (int ai : a) make_all_same += a[n-1] - ai;
    if (k >= make_all_same) {
        k -= make_all_same;
        int64_t ans = a[n-1] + k / n;
        cout << ans << '\n';
        return 0;
    }

    // Otherwise, let's try to make all possible gcds in [1..a[n-1]]
    int64_t S = 0;
    for (int ai : a) S += ai;
    int64_t threshold = k + S;

    vector<int> counts(a[n-1] + 1);
    for (int ai : a) counts[ai]++;
    for (int i = 1; i <= a[n-1]; i++) counts[i] += counts[i-1];

    for (int g = a[n-1]; g >= 0; g--) {
        int64_t sum = 0;
        for (int d = 0; d * g + 1 <= a[n-1]; d++) {
            // range is (dg, (d+1)g]
            int count_here = counts[min((d+1) * g, a[n-1])] - counts[d*g];
            sum += (int64_t) count_here * (d + 1) * g;
            if (sum > threshold) break;
        }
        if (sum <= threshold) {
            cout << g << "\n";
            return 0;
        }
    }
}