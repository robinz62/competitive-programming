#include <bits/stdc++.h>

using namespace std;

map<int, int> prime_factorize(int n) {
    map<int, int> factors;
    while (n % 2 == 0) {
        factors[2]++;
        n /= 2;
    }
    for (int i = 3; i * i <= n; i += 2) {
        while (n % i == 0) {
            factors[i]++;
            n /= i;
        }
    }
    if (n > 2) factors[n]++;
    return factors;
}

int mex(vector<int>& nums) {
    vector<bool> present(nums.size());
    for (int ai : nums) if (ai < (int) present.size()) present[ai] = true;
    for (int i = 0; i < (int) present.size(); i++) if (!present[i]) return i;
    return (int) present.size();
}

map<int, int> memo;  // grundy values
int compute(int mask) {
    if (mask == 0) return 0;
    if (memo.find(mask) != memo.end()) return memo[mask];
    int highest = countr_zero(bit_floor((uint32_t) mask));
    vector<int> grundy;
    for (int i = highest; i > 0; i--) {
        int next = mask;
        for (int j = i; j <= highest; j++) if ((next & (1 << j)) > 0) next ^= 1 << j;
        for (int j = i+1; j <= highest; j++) {
            if ((mask & (1 << j)) > 0) {
                next |= 1 << (j - i);
            }
        }
        grundy.push_back(compute(next));
    }
    int ans = mex(grundy);
    memo[mask] = ans;
    return ans;
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int n;
    cin >> n;
    vector<int> a(n);
    for (int& ai : a) cin >> ai;

    // factorize each number
    map<int, set<int>> pfs;
    for (int ai : a) {
        map<int, int> prime_factors = prime_factorize(ai);
        for (const auto& [ p, count ] : prime_factors) {
            pfs[p].insert(count);
        }
    }

    int x = 0;
    for (const auto& [ prime, counts ] : pfs) {
        int mask = 0;
        for (int c : counts) mask |= 1 << c;
        x ^= compute(mask);
    }

    cout << (x != 0 ? "Mojtaba" : "Arpa") << '\n';
}