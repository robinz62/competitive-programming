#include <bits/stdc++.h>

using namespace std;

void print_ans(int i, string ans) {
    cout << "Case #" << i << ": " << ans << '\n';
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
        char last = c[m-1];  // last is the leaf char
        char other = last == '.' ? '-' : '.';

        string curr = c;
        curr.pop_back();
        vector<string> ans;
        for (int i = 0; i < n; i++) {
            ans.push_back(curr + last);
            curr += other;
        }

        print_ans(Ti, "");
        for (int i = 1; i < n; i++) {
            cout << ans[i] << '\n';
        }
    }
}