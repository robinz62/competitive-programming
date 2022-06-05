#include <bits/stdc++.h>

using namespace std;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int n, k;
    cin >> n >> k;
    vector<string> s(n);
    for (string& si : s) cin >> si;

    int ans = 0;
    array<int, 26> freq;
    for (int mask = 0; mask < (1 << n); mask++) {
        fill(freq.begin(), freq.end(), 0);
        for (int b = 0; b < n; b++) {
            if ((mask & (1 << b)) == 0) continue;
            for (char c : s[b]) freq[c - 'a']++;
        }
        int count = 0;
        for (int i = 0; i < 26; i++) if (freq[i] == k) count++;
        ans = max(ans, count);
    }

    cout << ans << "\n";
}