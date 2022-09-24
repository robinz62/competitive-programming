#include <bits/stdc++.h>

using namespace std;

void print_ans(int i, string ans) {
    cout << "Case #" << i << ": " << ans << '\n';
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int T;
    cin >> T;
    for (int Ti = 1; Ti <= T; Ti++) {
        int n, k;
        cin >> n >> k;
        vector<int> s(n);
        for (int& si : s) cin >> si;

        map<int, int> count;
        for (int si : s) count[si]++;
        int l = 0;
        int r = 0;
        
        bool ok = true;
        for (const auto& p : count) {
            if (p.second == 1) {
                if (l <= r) l++;
                else r++;
            } else if (p.second == 2) {
                l++;
                r++;
            } else {
                ok = false;
                break;
            }
        }

        if (ok && l <= k && r <= k) print_ans(Ti, "YES");
        else print_ans(Ti, "NO");
    }
}