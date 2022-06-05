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
        string s;
        cin >> s;

        int n = (int) s.size();

        vector<pair<char, int>> l;
        int curr = 1;
        for (int i = 1; i < n; i++) {
            if (s[i] == s[i-1]) curr++;
            else {
                l.emplace_back(s[i-1], curr);
                curr = 1;
            }
        }
        l.emplace_back(s[n-1], curr);

        string ans;
        int m = (int) l.size();
        for (int i = 0; i < (int) m-1; i++) {
            char me = l[i].first;
            if (l[i].first < l[i+1].first) {
                for (int x = 0; x < l[i].second; x++) {
                    ans.push_back(me);
                    ans.push_back(me);
                }
            } else {
                for (int x = 0; x < l[i].second; x++) {
                    ans.push_back(me);
                }
            }
        }
        for (int x = 0; x < l[m-1].second; x++) {
            ans.push_back(l[m-1].first);
        }

        print_answer(Ti, ans);
    }
}