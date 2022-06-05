#include <bits/stdc++.h>

using namespace std;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int n;
    cin >> n;
    vector<int> a(n);
    for (int& ai : a) cin >> ai;

    // For any given prefix, we can only take half
    //
    // Obviously if everything went our way, we'd just pick the biggest n/2 numbers
    //
    //
    // Solve for no cyclic shift.
    // Choose the biggest n/2 elements, lets bias towards the right if there are ties
    // Left to right, if there is ever an even point where we have more than half, then
    // no good. Delete the smallest one, and replace with the largest element to the
    // right that we aren't taking.
    //
    // Ok, now for shifts. is there a way to reuse computations?

    // Conjecture: is it always possible to get the best n/2?
    // Conjecture: should we just start at the longest span of blanks? FALSE
    //             consider _ _ _ _ X X X X X X _ _ _ _ X X
    //             this does not work. other examples exist where it's not tied
    // Conjecture: place the longest streak of Xs last. FALSE
    //             consider _ X X _ X X _ _ _ _ _ _ X X X X 
    
    vector<int> idx(n);
    iota(idx.begin(), idx.end(), 0);
    sort(idx.begin(), idx.end(), [&](int i, int j) { return a[i] > a[j]; });

    vector<bool> take(n);
    int64_t ans = 0;
    for (int i = 0; i < n/2; i++) {
        take[idx[i]] = true;
        ans += a[idx[i]];
    }

    vector<int> deltas;
    for (int i = 0; i < n; i += 2) {
        int sum = take[i] + take[i+1];
        if (sum == 0) deltas.push_back(-1);
        else if (sum == 1) deltas.push_back(0);
        else deltas.push_back(1);
    }

    // want deltas to remain <= 0.
    vector<int> prefix(n);
    prefix[0] = deltas[0];
    for (int i = 1; i < n/2; i++) prefix[i] = prefix[i-1] + deltas[i];

    int idx_max = 0;
    for (int i = 0; i < n/2; i++) {
        if (prefix[i] > prefix[idx_max]) idx_max = i;
    }

    int rotate = idx_max * 2 + 1;
    cout << (rotate + 1) % n << " " << ans << "\n";
}