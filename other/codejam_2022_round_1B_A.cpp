#include <bits/stdc++.h>

using namespace std;

void print_answer(int i, string ans) {
    cout << "Case #" << i << ": " << ans << "\n";
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int T;
    cin >> T;
    for (int Ti = 1; Ti <= T; Ti++) {
        int n;
        cin >> n;
        vector<int> d(n);
        for (int& di : d) cin >> di;

        int ans = 0;
        int l = 0;
        int r = n-1;
        int prev = -1;
        while (l <= r) {
            if (d[l] <= d[r]) {
                if (d[l] >= prev) ans++;
                prev = max(prev, d[l]);
                l++;
            } else {
                if (d[r] >= prev) ans++;
                prev = max(prev, d[r]);
                r--;
            }
        }

        print_answer(Ti, to_string(ans));
    }
}