#include <bits/stdc++.h>

using namespace std;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int n, k;
    cin >> n >> k;

    vector<pair<int, int>> ops(n);
    vector<int> assignments;
    for (int i = 0; i < n; i++) {
        cin >> ops[i].first >> ops[i].second;
        if (ops[i].first == 1) assignments.push_back(i);
    }

    // if we DONT skip an assignment operation (t = 1), then we might as well not skip anything before.
    // i.e. there's no point in skipping an assignment unless we skip all assignments afterwards.
    // SO the solution involves skipping some suffix of assignments.
    //
    // Brute force on this amount skip_count?
    // Then we have a "starting value" which is the assignment not skipped to the left. (If none, it is 0)
    // Then we want to skip the most negative numbers, up to (k - skip_count).

    // Maintain that max(skipped) <= min(negative_increments)
    map<int, int> skipped;
    map<int, int> negative_increments;
    int64_t total_delta = 0;
    int idx = n-1;
    int allowed_skips = k;
    int actually_skipped = 0;
    int64_t ans = numeric_limits<int64_t>::min();
    while (idx >= 0) {
        while (idx >= 0 && ops[idx].first == 2) {
            int value = ops[idx].second;
            if (value >= 0) {
                total_delta += value;
                idx--;
                continue;
            }

            if (actually_skipped < allowed_skips) {
                skipped[value]++;
                actually_skipped++;
            } else if (actually_skipped > 0 && value < skipped.rbegin()->first) {
                auto [ booted_value, booted_count ] = *skipped.rbegin();
                if (booted_count == 1) skipped.erase(booted_value);
                else skipped[booted_value]--;
                negative_increments[booted_value]++;
                total_delta += booted_value;
                skipped[value]++;
            } else {
                negative_increments[value]++;
                total_delta += value;
            }
            idx--;
        }
        int64_t initial = idx < 0 ? 0 : ops[idx].second;
        ans = max(ans, initial + total_delta);

        // Next up:
        idx--;
        allowed_skips--;
        if (allowed_skips < 0) break;
        if (actually_skipped > allowed_skips) {  // +1.
            auto [ value, count ] = *skipped.rbegin();
            if (count == 1) skipped.erase(value);
            else skipped[value]--;

            negative_increments[value]++;
            total_delta += value;  // Add back the negative value
            actually_skipped--;
        }
    }

    cout << ans << '\n';
}