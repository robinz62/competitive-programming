#include <bits/stdc++.h>

using namespace std;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int T;
    cin >> T;
    for (int Ti = 1; Ti <= T; Ti ++) {
        int r, c;
        cin >> r >> c;

        cout << "Case #" << Ti << ":\n";
        vector<vector<char>> card(2 * r + 1, vector<char>(2 * c + 1, '.'));
        for (int i = 0; i < (int) card.size(); i += 2) {
            for (int j = 0; j < (int) card[i].size(); j++) {
                card[i][j] = j % 2 == 0 ? '+' : '-';
            }
        }
        for (int i = 1; i < (int) card.size(); i += 2) {
            for (int j = 0; j < (int) card[i].size(); j += 2) {
                card[i][j] = '|';
            }
        }
        card[0][0] = card[0][1] = card[1][0] = '.';

        for (auto& row : card) {
            for (char ch : row) cout << ch;
            cout << '\n';
        }
    }
}