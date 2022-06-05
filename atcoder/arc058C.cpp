#include <bits/stdc++.h>

using namespace std;

bool has_banned(int n, array<bool, 10>& banned) {
    while (n > 0) {
        int d = n % 10;
        if (banned[d]) return true;
        n /= 10;
    }
    return false;
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int n, k;
    cin >> n >> k;
    array<bool, 10> banned;
    fill(banned.begin(), banned.end(), false);

    for (int i = 0; i < k; i++) {
        int d;
        cin >> d;
        banned[d] = true;
    }

    while (has_banned(n, banned)) n++;
    cout << n << '\n';
}