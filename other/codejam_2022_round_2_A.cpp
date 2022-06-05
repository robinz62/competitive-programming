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
    for (int Ti = 1; Ti <= T; Ti++) {
        int n, k;
        cin >> n >> k;

        int want_reduce = n * n - 1 - k;
        if (want_reduce % 2 == 1) {
            print_answer(Ti, "IMPOSSIBLE");
            continue;
        }

        map<int, int> reductions_to_value;

        int curr_val = n*n - 1;
        int curr_red = 0;

        int dval = 2;
        while (curr_val >= 1) {
            reductions_to_value[curr_red] = curr_val;
            curr_val -= dval;
            curr_red += 2;
            if (curr_val < 1) break;
            reductions_to_value[curr_red] = curr_val;
            curr_val -= dval;
            curr_red += 2;
            dval++;
        }

        int max_reduction = reductions_to_value.rbegin()->first;
        vector<int> ans;
        while (want_reduce > 0 && max_reduction > 0) {
            int take = min(max_reduction, want_reduce);
            want_reduce -= take;
            ans.push_back(take);
            max_reduction = take - 8;
        }

        if (want_reduce > 0) {
            print_answer(Ti, "IMPOSSIBLE");
            continue;
        }

        print_answer(Ti, to_string(ans.size()));
        for (int i = 0; i < (int) ans.size(); i++) {
            int here = reductions_to_value[ans[i]];
            int next = here + ans[i] + 1;
            cout << here << " " << next << "\n";
        }
    }

    // shortcuts reduce k by "a lot"
}