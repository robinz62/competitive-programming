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
    for (int Ti = 1; Ti <= T; Ti ++) {
        int n;
        cin >> n;

        vector<int> a(n);
        for (int& ai : a) {
            cin >> ai;
            ai--;
        }

        sort(a.begin(), a.end());

        int next = 0;
        for (int ai : a) {
            if (ai >= next) next++;
        }

        print_answer(Ti, to_string(next));
    }
}