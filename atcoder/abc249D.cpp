#include <bits/stdc++.h>

using namespace std;

constexpr int MAX_A = 200000;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int n;
    cin >> n;
    vector<int> arr(n);
    for (int& ai : arr) cin >> ai;

    array<int, MAX_A+1> freq;
    fill(freq.begin(), freq.end(), 0);
    for (int ai : arr) freq[ai]++;

    int64_t ans = 0;
    for (int i = 1; i <= MAX_A; i++) {
        int incr = 1;
        for (int j = i; j <= MAX_A; j += i) {
            ans += (int64_t) freq[i] * freq[j] * freq[incr];
            incr++;
        }
    }

    cout << ans << '\n';
}