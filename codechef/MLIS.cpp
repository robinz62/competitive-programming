#include <bits/stdc++.h>

using namespace std;

vector<int> length_of_lis(vector<int>& a) {
    vector<int> dp(a.size());
    vector<int> ans;
    int len = 0;

    for (int ai : a) {
        auto it = lower_bound(dp.begin(), dp.begin() + len, ai);
        if (it == dp.begin() + len || *it != ai) {
            int idx = (int) (it - dp.begin());
            dp[idx] = ai;
            if (idx == len) len++;
        }
        ans.push_back(len);
    }
    return ans;
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int T;
    cin >> T;
    while (T--) {
        int n;
        cin >> n;
        vector<int> a(n);
        for (int& ai : a) cin >> ai;

        vector<int> forward = length_of_lis(a);
        for (int& ai : a) ai = -ai;
        reverse(a.begin(), a.end());
        vector<int> backward = length_of_lis(a);
        reverse(backward.begin(), backward.end());

        int ans = max(backward[0], forward[n-1]);
        for (int i = 0; i < n-1; i++) ans = max(ans, forward[i] + backward[i+1]);
        cout << ans << '\n';
    }
}