#include <bits/stdc++.h>

using namespace std;

void print_ans(int i, string ans) {
    cout << "Case #" << i << ": " << ans << '\n';
}

vector<int> get_kmp_array(vector<int>& word) {
    int n = (int) word.size();
    vector<int> kmp(n);
    int i = 1;
    int j = 0;
    while (i < n) {
        if (word[i] == word[j]) {
            kmp[i] = j + 1;
            ++i;
            ++j;
        } else if (j != 0) {
            j = kmp[j - 1];
        } else {
            ++i;
        }
    }
    return kmp;
}

// Finds first index where word is found in text.
int search(vector<int>& word, vector<int>& text) {
    vector<int> kmp = get_kmp_array(word);
    int m = (int) word.size();
    int i = 0;
    int j = 0;
    while (i < (int) text.size()) {
        if (text[i] == word[j]) {
            ++i;
            ++j;
            if (j == m) return i - m;
        } else if (j != 0) {
            j = kmp[j - 1];
        } else {
            ++i;
        }
    }
    return -1;
}

string yes = "YES";
string no = "NO";

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    int T;
    cin >> T;
    for (int Ti = 1; Ti <= T; Ti++) {
        int n, k;
        cin >> n >> k;
        vector<int> a(n);
        for (int& ai : a) cin >> ai;
        vector<int> b(n);
        for (int& bi : b) cin >> bi;

        // similar to before, but need to find where to start matching...
        // only need to find one place where it matches, and also consider if it
        // matches at the beginning

        vector<int> a_twice;
        for (int ai : a) a_twice.push_back(ai);
        for (int ai : a) a_twice.push_back(ai);

        // Don't allow matching directly
        a_twice.erase(a_twice.begin());
        a_twice.pop_back();

        bool matches_at_start = a == b;
        bool matches_somewhere_in_middle = search(b, a_twice) != -1;

        if (k == 0) {
            print_ans(Ti, matches_at_start ? yes : no);
            continue;
        }

        if (n == 2) {
            if (matches_at_start && matches_somewhere_in_middle) {
                print_ans(Ti, yes);
                continue;
            }
            if (matches_at_start) {
                print_ans(Ti, k % 2 == 0 ? yes : no);
                continue;
            }
            if (matches_somewhere_in_middle) {
                print_ans(Ti, k % 2 == 1 ? yes : no);
                continue;
            }
            print_ans(Ti, no);
            continue;
        }

        // k >= 1
        // n >= 3

        if (k == 1) {
            if (!matches_somewhere_in_middle) print_ans(Ti, no);
            else print_ans(Ti, yes);
            continue;
        }

        print_ans(Ti, matches_at_start || matches_somewhere_in_middle ? yes : no);
    }
}