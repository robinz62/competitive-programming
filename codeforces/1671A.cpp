#include <bits/stdc++.h>

using namespace std;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int T;
    cin >> T;
    while (T--) {
        string s;
        cin >> s;
        int n = (int) s.size();
        int count = 1;
        bool bad = false;
        for (int i = 1; !bad && i < n; i++) {
            if (s[i] == s[i-1]) count++;
            else {
                if (count == 1) bad = true;
                else count = 1;
            }
        }
        if (count == 1) bad = true;
        cout << (bad ? "NO" : "YES") << '\n';
    }
}