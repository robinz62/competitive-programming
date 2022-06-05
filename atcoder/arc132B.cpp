#include <bits/stdc++.h>

using namespace std;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int n;
    cin >> n;
    vector<int> p(n);
    for (int& pi : p) cin >> pi;

    bool reversed;
    if (p[0] + 1 == p[1]) {
        reversed = false;
    } else if (p[0] - 1 == p[1]) {
        reversed = true;
    } else if (p[0] < p[1]) {
        reversed = true;
    } else {
        reversed = false;
    }

    int idx1 = -1;
    for (int i = 0; idx1 == -1 && i < n; i++) if (p[i] == 1) idx1 = i;
    int reversed_idx1 = n-1 - idx1;

    int ans = numeric_limits<int>::max();
    if (!reversed) {
        ans = min(ans, idx1);  // shift only
        ans = min(ans, 1 + reversed_idx1+1 + 1);  // reverse, shift, reverse
    } else {
        ans = min(ans, idx1+1 + 1);  // shift then reverse
        ans = min(ans, 1 + reversed_idx1);  // reverse then shift
    }

    cout << ans << '\n';
}