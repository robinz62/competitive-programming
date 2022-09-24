#include <bits/stdc++.h>

using namespace std;

void print_ans(int i, string ans) {
    cout << "Case #" << i << ": " << ans << '\n';
}

constexpr int MAX = 2048;

void dfs(array<bool, MAX>& banned, int curr) {
    if (curr >= MAX) return;
    banned[curr] = true;
    dfs(banned, curr * 2);
    dfs(banned, curr * 2 + 1);
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int T;
    cin >> T;
    for (int Ti = 1; Ti <= T; Ti++) {
        int n;
        cin >> n;
        string c;
        cin >> c;

        int m = (int) c.size();

        array<bool, MAX> banned;
        fill(banned.begin(), banned.end(), false);
        int curr = 1;
        for (int i = 0; i < min(10, m); i++) {
            if (c[i] == '.') curr *= 2;
            else curr = curr * 2 + 1;
        }
        dfs(banned, curr);

        vector<int> ids;
        for (int i = MAX-1; i >= 1 && (int) ids.size() < n-1; i--) {
            if (!banned[i]) ids.push_back(i);
        }

        vector<string> ans;
        for (int id : ids) {
            string s;
            while (id > 1) {
                if (id % 2 == 0) s.push_back('.');
                else s.push_back('-');
                id /= 2;
            }
            reverse(s.begin(), s.end());
            ans.push_back(s);
        }

        print_ans(Ti, "");
        for (string& si : ans) cout << si << '\n';
    }
}