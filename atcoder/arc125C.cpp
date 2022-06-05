#include <bits/stdc++.h>
 
using namespace std;
 
int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);
 
    int n, k;
    cin >> n >> k;
    vector<int> a(k);
    for (int& ai : a) cin >> ai;
    
    if (k == 1) {
        for (int i = n; i >= 1; i--) cout << i << " \n"[i == 1];
        return 0;
    }

    vector<vector<int>> buckets(k);
    int last_added = 0;
    int b = 0;
    for (int i = 0; i < k-1; i++) {
        int val = last_added + 1;
        b = max(b, i);
        while (val < a[i]) {
            if (b < k-1) {
                buckets[b].push_back(val);
                b++;
            } else {
                for (int x = a[i]-1; x >= val; x--) buckets[k-1].push_back(x);
                break;
            }
            val++;
        }
        last_added = a[i];
    }

    vector<int> ans;
    for (int val = n; val > last_added; val--) {
        buckets[k-1].push_back(val);
    }
    for (int i = 0; i < k; i++) {
        if (i != k-1) ans.push_back(a[i]);
        sort(buckets[i].rbegin(), buckets[i].rend());
        for (int x : buckets[i]) ans.push_back(x);
    }
    
    for (int ansi : ans) cout << ansi << ' ';
    cout << "\n";
}