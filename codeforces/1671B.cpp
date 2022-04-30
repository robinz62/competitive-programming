#include <bits/stdc++.h>

using namespace std;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int T;
    cin >> T;
    while (T--) {
        int n;
        cin >> n;
        vector<int> x(n);
        for (int& xi : x) cin >> xi;

        bool done = false;
        for (int start = x[0]-1; !done && start <= x[0]+1; start++) {
            int curr = start;
            bool bad = false;
            for (int i = 1; !bad && i < n; i++) {
                if (abs(x[i] - (curr+1) <= 1)) curr++;
                else bad = true;
            }
            if (!bad) {
                cout << "YES\n";
                done = true;
                break;
            }
        }
        if (!done) {
            cout << "NO\n";
        }
    }
}