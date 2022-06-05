#include <bits/stdc++.h>

using namespace std;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int n;
    cin >> n;
    string s;
    cin >> s;

    reverse(s.begin(), s.end());
    s.resize(n);

    auto decrement = [&]() {
        for (int i = 0; i < n; i++) {
            if (s[i] == '1') {
                s[i] = '0';
                return true;
            }
            s[i] = '1';
        }
        return false;
    };

    string ans;
    ans.push_back('1');
    decrement();

    // The number [s] is "how many people are less than me".
    for (int i = n-1; i >= 0; i--) {
        if (s[i] == '1') {
            s[i] = '0';
            ans.push_back('1');
        } else {
            bool ok = decrement();
            if (!ok) {
                cout << ans << '\n';
                return 0;
            }
            ans.push_back('0');
        }
    }

    cout << ans << '\n';
}