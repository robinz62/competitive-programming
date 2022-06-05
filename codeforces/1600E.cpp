#include <bits/stdc++.h>

using namespace std;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int n;
    cin >> n;
    vector<int> a(n);
    for (int& ai : a) cin >> ai;

    vector<int> dp1(n);  // bigger to the left
    dp1[0] = 1;
    for (int i = 1; i < n; i++) dp1[i] = a[i-1] > a[i] ? dp1[i-1] + 1 : 1;
    vector<int> dp2(n);  // bigger to the right
    dp2[n-1] = 1;
    for (int i = n-2; i >= 0; i--) dp2[i] = a[i] < a[i+1] ? dp2[i+1] + 1 : 1;

    int l = 0;
    int r = n-1;
    bool alice_turn = true;
    int last = -1;
    while (l <= r) {
        // Case: at least one is untakeable
        if (a[l] <= last && a[r] <= last) {
            cout << (alice_turn ? "Bob" : "Alice") << "\n";
            return 0;
        }
        if (a[l] <= last) {
            last = a[r];
            r--;
            alice_turn = !alice_turn;
            continue;
        }
        if (a[r] <= last) {
            last = a[l];
            l++;
            alice_turn = !alice_turn;
            continue;
        }

        // Case: both are takeable
        if (a[l] >= a[r] && min(dp2[l], r-l+1) % 2 == 1) {
            cout << (alice_turn ? "Alice" : "Bob") << "\n";
            return 0;
        }
        if (a[r] >= a[l] && min(dp1[r], r-l+1) % 2 == 1) {
            cout << (alice_turn ? "Alice" : "Bob") << "\n";
            return 0;
        }
        if (l == r) {
            cout << (alice_turn ? "Alice" : "Bob") << "\n";
            return 0;
        }
        if (a[l] == a[r]) {
            cout << (alice_turn ? "Bob" : "Alice") << "\n";
            return 0;
        }
        last = min(a[l], a[r]);
        if (a[l] < a[r]) l++;
        else r--;
        alice_turn = !alice_turn;
    }
}