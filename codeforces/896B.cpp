#include <bits/stdc++.h>

using namespace std;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int n, m, c;
    cin >> n >> m >> c;
    int mid = c / 2;

    vector<int> nums(n, -1);
    auto done = [&]() {
        for (int x : nums) if (x == -1) return false;
        return true;
    };

    while (m--) {
        int p;
        cin >> p;

        if (p <= mid) {
            for (int i = 0; i < n; i++) {
                if (nums[i] == -1 || p < nums[i]) {
                    nums[i] = p;
                    cout << i+1 << endl;
                    break;
                }
            }
        } else {
            for (int i = n-1; i >= 0; i--) {
                if (nums[i] == -1 || p > nums[i]) {
                    nums[i] = p;
                    cout << i+1 << endl;
                    break;
                }
            }
        }

        if (done()) return 0;
    }
}