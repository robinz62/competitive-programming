#include <bits/stdc++.h>

using namespace std;

int query(int a, int b) {
    cout << "? " << a << " " << b << endl;
    int g;
    cin >> g;
    return g;
}

void answer(int x) {
    cout << "! " << x << endl;
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int T;
    cin >> T;
    while (T--) {
        int ans = 0;
        int b = 1;
        int pow2 = 1;  // the bit we want
        
        for (int i = 0; i < 30; i++) {
            int g = query(pow2 * 2 + b, b);
            if (g == pow2) {
                b += pow2;
            } else if (g == pow2 * 2) {
                ans |= pow2;
            } else {
                while (true);
            }

            pow2 *= 2;
        }

        answer(ans);
    }
}

// Alternate solution using crt. Just wanted to test that my crt function works.
//
// #include <bits/stdc++.h>

// using namespace std;

// // Given (a) and (b), the solution to x*a + y*b = gcd(a, b) is stored in (x)
// // and (y). The explicit return value is gcd(a, b).
// int64_t extended_gcd(int64_t a, int64_t b, int64_t& x, int64_t& y) {
//     x = 1, y = 0;
//     int64_t x1 = 0, y1 = 1, a1 = a, b1 = b;
//     while (b1 != 0) {
//         int64_t q = a1 / b1;

//         tie(x, x1) = make_tuple(x1, x - q * x1);
//         tie(y, y1) = make_tuple(y1, y - q * y1);
//         tie(a1, b1) = make_tuple(b1, a1 - q * b1);
//     }
//     return a1;
// }

// // Solves the system of equations x = a[i] (mod n[i]), returning the answer
// // modulo lcm(n[i]).
// int64_t crt(vector<int64_t> a, vector<int64_t> n) {
//     auto normalize = [](int64_t& x, int64_t mod) {
//         x %= mod;
//         if (x < 0) x += mod;
//     };

//     int t = (int) a.size();
//     for (int i = 0; i < t; i++) normalize(a[i], n[i]);

//     int64_t ans = a[0];
//     int64_t lcm = n[0];
//     for (int i = 1; i < t; i++) {
//         int64_t x, y;
//         int64_t g = extended_gcd(lcm, n[i], x, y);
//         if ((a[i] - ans) % g != 0) return -1;
//         ans += x * (a[i] - ans) / g % (n[i] / g) * lcm;
//         lcm = lcm / g * n[i];
//         normalize(ans, lcm);
//     }

//     return ans;
// }

// int query(int64_t a, int64_t b) {
//     cout << "? " << a << " " << b << endl;
//     int g;
//     cin >> g;
//     return g;
// }

// void answer(int64_t x) {
//     cout << "! " << x << endl;
// }

// int main() {
//     ios_base::sync_with_stdio(false);
//     cin.tie(nullptr);

//     vector<int64_t> n = {23, 19, 17, 13, 11, 9, 7, 5, 4};
//     int64_t lcm = 1338557220;

//     int T;
//     cin >> T;
//     while (T--) {
//         vector<int64_t> a(9);
//         for (int i = 1; i <= 23; i++) {
//             int64_t x = query(i, lcm + i);
//             for (int j = 0; j < 9; j++) {
//                 if (x % n[j] == 0) a[j] = -i;
//             }
//         }

//         answer(crt(a, n));
//     }
// }