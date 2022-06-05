#include <bits/stdc++.h>

using namespace std;

void print_answer(int i, string ans) {
    cout << "Case #" << i << ": " << ans << "\n";
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    constexpr int total = 1000000;

    int T;
    cin >> T;
    for (int Ti = 1; Ti <= T; Ti ++) {
        int c, m, y, k;
        c = m = y = k = total;
        for (int i = 0; i < 3; i++) {
            int ci, mi, yi, ki;
            cin >> ci >> mi >> yi >> ki;
            c = min(c, ci);
            m = min(m, mi);
            y = min(y, yi);
            k = min(k, ki);
        }

        int sum = c + m + y + k;
        if (sum < total) {
            print_answer(Ti, "IMPOSSIBLE");
        } else {
            if (sum > total) {
                int take = min(c, sum - total);
                sum -= take;
                c -= take;
            }
            if (sum > total) {
                int take = min(m, sum - total);
                sum -= take;
                m -= take;
            }
            if (sum > total) {
                int take = min(y, sum - total);
                sum -= take;
                y -= take;
            }
            if (sum > total) {
                int take = min(k, sum - total);
                sum -= take;
                k -= take;
            }
            print_answer(Ti, to_string(c) + " " + to_string(m) + " " + to_string(y) + " " + to_string(k));
        }
    }
}