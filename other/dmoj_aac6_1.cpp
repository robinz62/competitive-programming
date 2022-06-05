#include <bits/stdc++.h>

using namespace std;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int n, k;
    cin >> n >> k;

    int64_t sum = 0;
    for (int i = 1; i <= n-1; i++) {
        sum += i;
        cout << i << ' ';
    }

    int k_ceil = n / k * k + k;
    int need = (int) (k - sum % k);
    k_ceil += need;
    cout << k_ceil << '\n';
}