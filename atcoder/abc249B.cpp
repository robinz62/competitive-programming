#include <bits/stdc++.h>

using namespace std;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    string s;
    cin >> s;
    bool has_upper = false;
    bool has_lower = false;
    set<char> seen;
    for (char c : s) {
        if (c >= 'a' && c <= 'z') has_lower = true;
        if (c >= 'A' && c <= 'Z') has_upper = true;
        if (seen.find(c) != seen.end()) {
            cout << "No\n";
            return 0;
        }
        seen.insert(c);
    }
    if (has_upper && has_lower) cout << "Yes\n";
    else cout << "No\n";
}