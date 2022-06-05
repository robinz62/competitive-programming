#include <bits/stdc++.h>

using namespace std;

// Only solves subtask 1 [10%] and subtask 2 [25%]
int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int n, m, q;
    cin >> n >> m >> q;

    vector<int> t(q);
    for (int& ti : t) cin >> ti;
    set<int> types;
    for (int ti : t) types.insert(ti);

    // Subtask 1:
    if (types.find(2) == types.end() && types.find(4) == types.end()) {
        array<array<int, 2>, 2> white;
        array<array<int, 2>, 2> black;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                white[i][j] = black[i][j] = i * 2 + j;
            }
        }
        for (int ti : t) {
            if (ti == 1) {
                swap(white[0][0], white[0][1]);
                swap(white[1][0], white[1][1]);
                swap(black[0][0], black[0][1]);
                swap(black[1][0], black[1][1]);
            } else if (ti == 3) {
                swap(white[0][0], white[1][0]);
                swap(white[0][1], white[1][1]);
                swap(black[0][0], black[1][0]);
                swap(black[0][1], black[1][1]);
            } else if (ti == 5) {
                int temp = white[0][0];
                white[0][0] = white[1][0];
                white[1][0] = white[1][1];
                white[1][1] = white[0][1];
                white[0][1] = temp;
                temp = black[0][0];
                black[0][0] = black[0][1];
                black[0][1] = black[1][1];
                black[1][1] = black[1][0];
                black[1][0] = temp;
            }
        }
        array<int, 4> white_map;
        array<int, 4> black_map;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                int w = white[i][j];
                white_map[w] = i * 2 + j;
                int b = black[i][j];
                black_map[b] = i * 2 + j;
            }
        }

        vector<vector<int>> grid(2*n, vector<int>(2*m));
        for (int i = 0; i < 2*n; i += 2) {
            bool color = i/2 % 2 == 0;  // even rows are white start
            for (int j = 0; j < 2*m; j += 2) {
                for (int r = 0; r < 2; r++) {
                    for (int c = 0; c < 2; c++) {
                        int me = (i+r) * 2*m + (j+c);
                        if (color) {
                            int pos = white_map[r*2+c];
                            int dr = pos / 2 - r;
                            int dc = pos % 2 - c;
                            grid[i+r+dr][j+c+dc] = me;
                        } else {
                            int pos = black_map[r*2+c];
                            int dr = pos / 2 - r;
                            int dc = pos % 2 - c;
                            grid[i+r+dr][j+c+dc] = me;
                        }
                    }
                }

                color = !color;
            }
        }

        for (vector<int>& row : grid) for (int& elem : row) elem++;

        cout << grid[0][0];
        for (int j = 1; j < 2*m; j++) cout << " " << grid[0][j];
        for (int i = 1; i < 2*n; i++) {
            cout << '\n';
            cout << grid[i][0];
            for (int j = 1; j < 2*m; j++) cout << " " << grid[i][j];
        }
        return 0;
    }

    // Subtask 2:
    if (types.find(3) == types.end() && types.find(4) == types.end() && types.find(5) == types.end()) {
        deque<int> ops;
        for (int ti : t) {
            if (!ops.empty() && ops.back() == ti) {
                ops.pop_back();
                continue;
            }
            ops.push_back(ti);
        }
        
        // ops is now alternating 1,2,1,2,1,2,...
        // If starting with 1, then even columns travel right and odd columns travel left
        vector<int> row(2*m);
        for (int j = 0; j < 2*m; j++) {
            int moves = (int) ops.size();
            moves %= 2*2*m;
            if ((j % 2 == 0 && ops[0] == 1) || (j % 2 == 1 && ops[0] == 2)) {
                int num_moves_til_hit_right = 2*m-1 - j;
                if (moves <= num_moves_til_hit_right) {
                    row[j + moves] = j;
                } else {
                    moves -= num_moves_til_hit_right;
                    moves--;
                    int num_moves_til_hit_left = 2*m-1;
                    if (moves <= num_moves_til_hit_left) {
                        row[2*m-1 - moves] = j;
                    } else {
                        moves -= num_moves_til_hit_left;
                        moves--;
                        row[0 + moves] = j;
                    }
                }
            } else {
                int num_moves_til_hit_left = j;
                if (moves <= num_moves_til_hit_left) {
                    row[j - moves] = j;
                } else {
                    moves -= num_moves_til_hit_left;
                    moves--;
                    int num_moves_til_hit_right = 2*m-1;
                    if (moves <= num_moves_til_hit_right) {
                        row[0 + moves] = j;
                    } else {
                        moves -= num_moves_til_hit_right;
                        moves--;
                        row[2*m-1 - moves] = j;
                    }
                }
            }
        }
        vector<int> mapping(2*m);
        for (int i = 0; i < 2*m; i++) {
            mapping[row[i]] = i;
        }

        vector<vector<int>> grid(2*n, vector<int>(2*m));
        for (int i = 0; i < 2*n; i++) {
            for (int j = 0; j < 2*m; j++) {
                int me = i*2*m + j;
                int col = mapping[j];
                grid[i][col] = me;
            }
        }

        for (vector<int>& roww : grid) for (int& elem : roww) elem++;

        cout << grid[0][0];
        for (int j = 1; j < 2*m; j++) cout << " " << grid[0][j];
        for (int i = 1; i < 2*n; i++) {
            cout << '\n';
            cout << grid[i][0];
            for (int j = 1; j < 2*m; j++) cout << " " << grid[i][j];
        }
        return 0;
    }
}