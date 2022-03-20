#include <bits/stdc++.h>

using namespace std;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int T;
    cin >> T;
    while (T--) {
        int n;
        cin >> n;
        string a;
        cin >> a;
        sort(a.begin(), a.end());
        cout << a << '\n';
    }
}