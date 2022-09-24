#include <bits/stdc++.h>

using namespace std;

void print_ans(int i, string ans) {
    cout << "Case #" << i << ": " << ans << '\n';
}

vector<pair<int, int>> dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

// Pretty sure right idea but there's a bug in here somewhere that I'm too
// lazy to find.
int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int T;
    cin >> T;
    for (int Ti = 1; Ti <= T; Ti++) {
        int r, c;
        cin >> r >> c;
        vector<string> grid(r);
        for(string& row : grid) cin >> row;

        vector<vector<char>> ans(r, vector<char>(c));
        vector<vector<int>> count(r, vector<int>(c));
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (grid[i][j] != '#') {
                    ans[i][j] = '^';
                    for (auto [di, dj] : dirs) {
                        if (i+di >= 0 && i+di < r && j+dj >= 0 && j+dj < c) count[i+di][j+dj]++;
                    }
                } else {
                    ans[i][j] = '#';
                }
            }
        }

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (grid[i][j] == '#') count[i][j] = -1;
            }
        }

        // Entries must not be 0 or 1.
        deque<pair<int, int>> zeros;
        deque<pair<int, int>> ones;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (count[i][j] == 0) zeros.emplace_back(i, j);
                else if (count[i][j] == 1) ones.emplace_back(i, j);
            }
        }

        bool ok = true;
        while (ok && (!zeros.empty() || !ones.empty())) {
            if (!zeros.empty()) {
                auto [i, j] = zeros.front();
                zeros.pop_front();
                if (count[i][j] != 0) continue;
                if (grid[i][j] == '^') {
                    ok = false;
                    break;
                }

                // remove the tree
                ans[i][j] = '.';
                for (auto [di, dj] : dirs) {
                    if (i+di >= 0 && i+di < r && j+dj >= 0 && j+dj < c && ans[i+di][j+dj] != '#') {
                        count[i+di][j+dj]--;
                        if (count[i+di][j+dj] == 0) {
                            zeros.emplace_back(i+di, j+dj);
                        } else if (count[i+di][j+dj] == 1) {
                            ones.emplace_back(i+di, j+dj);
                        }
                    }
                }
                continue;
            }

            if (!ones.empty()) {
                auto [i, j] = ones.front();
                ones.pop_front();
                if (count[i][j] != 1) continue;
                if (grid[i][j] == '^') {
                    ok = false;
                    break;
                }

                // remove the tree
                ans[i][j] = '.';
                for (auto [di, dj] : dirs) {
                    if (i+di >= 0 && i+di < r && j+dj >= 0 && j+dj < c && ans[i+di][j+dj] != '#') {
                        count[i+di][j+dj]--;
                        if (count[i+di][j+dj] == 0) {
                            zeros.emplace_back(i+di, j+dj);
                        } else if (count[i+di][j+dj] == 1) {
                            ones.emplace_back(i+di, j+dj);
                        }
                    }
                }
            }
        }

        if (ok) {
            print_ans(Ti, "Possible");
            for (vector<char>& row : ans) {
                for (char ch : row) cout << ch;
                cout << '\n';
            }
        } else {
            print_ans(Ti, "Impossible");
        }
    }
}