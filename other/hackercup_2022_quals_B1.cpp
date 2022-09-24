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
        int r, c;
        cin >> r >> c;
        vector<string> grid(r);
        for(string& row : grid) cin >> row;

        if (r == 1 || c == 1) {
            bool exists = false;
            for (string& row : grid) for (char ai : row) if (ai == '^') exists = true;
            if (!exists) {
                print_ans(Ti, "Possible");
                for (int i = 0; i < r; i++) {
                    for (int j = 0; j < c; j++) cout << '.';
                    cout << '\n';
                }
            } else {
                print_ans(Ti, "Impossible");
            }
        } else {
            print_ans(Ti, "Possible");
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < c; j++) cout << '^';
                cout << '\n';
            }
        }
    }
}