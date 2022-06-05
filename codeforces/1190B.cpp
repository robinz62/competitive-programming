#include <bits/stdc++.h>

using namespace std;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int n;
    cin >> n;
    vector<int> a(n);
    for (int& ai : a) cin >> ai;

    map<int, int> freq;
    int has_two = -1;
    for (int ai : a) {
        freq[ai]++;
        if (freq[ai] >= 3) {
            cout << "cslnb" << "\n";
            return 0;
        }
        if (freq[ai] == 2) {
            if (has_two == -1) {
                has_two = ai;
            } else {
                cout << "cslnb" << "\n";
                return 0;
            }
        }
    }

    if (has_two == 0) {
        cout << "cslnb" << "\n";
        return 0;
    }
    if (has_two != -1 && freq[has_two-1] >= 1) {
        cout << "cslnb" << "\n";
        return 0;
    }
    bool invert_answer = has_two != -1;

    // End state is [0..n-1].
    for (int& ai : a) {
        if (ai == has_two) {
            ai--;
            break;
        }
    }
    sort(a.begin(), a.end());

    int64_t moves = 0;
    for (int i = 0; i < (int) a.size(); i++) {
        moves += a[i] - i;
    }

    cout << ((moves % 2 == 0) ^ invert_answer ? "cslnb" : "sjfnb") << "\n";
}