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
        vector<int> a(n);
        for (int& ai : a) cin >> ai;
        vector<int> b(n);
        for (int& bi : b) cin >> bi;

        if (k == 0) {
            if (a == b) print_ans(Ti, "YES");
            else print_ans(Ti, "NO");
            continue;
        }

        if (n == 2) {
            if (a == b) {
                if (k % 2 == 0) print_ans(Ti, "YES");
                else print_ans(Ti, "NO");
            } else {
                if (k % 2 == 0) print_ans(Ti, "NO");
                else print_ans(Ti, "YES");
            }
            continue;
        }

        // k >= 1
        // n >= 3

        if (a == b) {
            if (k == 1) print_ans(Ti, "NO");
            else print_ans(Ti, "YES");
            continue;
        }

        // First check that it's order-able at all.
        int idx_1_a = 0;
        int idx_1_b = 0;
        for (int i = 0; i < n; i++) {
            if (a[i] == 1) idx_1_a = i;
            if (b[i] == 1) idx_1_b = i;
        }

        bool bad = false;
        for (int j = 0; j < n; j++) {
            if (a[(idx_1_a + j) % n] != b[(idx_1_b + j) % n]) {
                bad = true;
                break;
            }
        }

        if (bad) {
            print_ans(Ti, "NO");
            continue;
        }

        print_ans(Ti, "YES");
    }
}