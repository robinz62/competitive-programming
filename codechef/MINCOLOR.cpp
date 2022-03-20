#include <bits/stdc++.h>

using namespace std;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int T;
    cin >> T;
    while (T--) {
        int n, m;
        cin >> n >> m;
        vector<vector<int>> a(n, vector<int>(m));
        int x1, y1, x2, y2;
        cin >> x1 >> y1;
        a[x1-1][y1-1] = 1;
        cin >> x2 >> y2;
        a[x2-1][y2-1] = 2;

        int parity1 = ((x1-1) + (y1-1)) % 2;
        int parity2 = ((x2-1) + (y2-1)) % 2;
        if (parity1 != parity2) {
            int color_of_even = parity1 == 0 ? 1 : 2;
            int color_of_odd = 3 - color_of_even;
            for (int i = 0; i < n; i++) {
                for (int j = i % 2 == 0 ? 0 : 1; j < m; j += 2) {
                    a[i][j] = color_of_even;
                }
                for (int j = i % 2 == 0 ? 1 : 0; j < m; j += 2) {
                    a[i][j] = color_of_odd;
                }
            }
        } else {
            int flip = parity1 == 0 ? 0 : 1;
            for (int i = 0; i < n; i++) {
                for (int j = (i % 2 == 0 ? 0 : 1) ^ flip; j < m; j += 2) {
                    a[i][j] = 1;
                }
                for (int j = (i % 2 == 0 ? 0 : 1) ^ flip ^ 1; j < m; j += 2) {
                    a[i][j] = 3;
                }
            }
            a[x2-1][y2-1] = 2;
        }
        for (auto& row : a) {
            for (int val : row) cout << val << ' ';
            cout << '\n';
        }
    }
}