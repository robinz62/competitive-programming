#include <bits/stdc++.h>

using namespace std;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int T;
    cin >> T;
    while (T--) {
        int n, k;
        cin >> n >> k;
        vector<int> a(n);
        for (int& ai : a) cin >> ai;
        
        sort(a.begin(), a.end());
        int ans = 0;
        for (int ai : a) {
            if (k >= ai) {
                ans++;
                k -= ai;
            } else if (k >= (ai + 1) / 2) {
                ans++;
                break;
            } else break;
        }

        cout << ans << '\n';
    }
}