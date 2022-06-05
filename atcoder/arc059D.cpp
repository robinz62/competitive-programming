#include <bits/stdc++.h>

using namespace std;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    string s;
    cin >> s;

    int n = (int) s.size();
    if (s[0] == s[1]) {
        cout << "1 2\n";
        return 0;
    }

    for (int i = 2; i < n; i++) {
        if (s[i] == s[i-1] || s[i] == s[i-2]) {
            cout << i-2+1 << ' ' << i+1 << '\n';
            return 0;
        }
    }
    cout << "-1 -1\n";
}