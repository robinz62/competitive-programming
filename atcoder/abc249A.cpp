#include <bits/stdc++.h>

using namespace std;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int a, b, c, d, e, f, x;
    cin >> b >> a >> c >> e >> d >> f >> x;

    int T = 0;
    for (int t = 0; t < x; ) {
        int take = min(x - t, b);
        T += take * a;
        t += b + c;
    }
    int A = 0;
    for (int t = 0; t < x; ) {
        int take = min(x - t, e);
        A += take * d;
        t += e + f;
    }

    if (A == T) {
        cout << "Draw\n";
    } else if (T > A) {
        cout << "Takahashi\n";
    } else {
        cout << "Aoki\n";
    }
}