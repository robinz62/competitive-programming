import java.io.*;
import java.util.*;

public class KLPM {
    public static void main(String[] args) throws IOException {
        KLPM k = new KLPM();
        k.solve();
    }

    boolean[][] palindrome;

    public void solve() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        String S = br.readLine();
        constructPalindromeMatrix(S);
        long total = 0;
        TrieNode root = new TrieNode();
        root.strangeList = new ArrayList<>();
        for (int j = S.length() - 2; j >= 0; j--) {
            root.strange(S.charAt(j + 1));      // augment the trie
            for (int i = 0; i <= j; i++) {
                int k = i;                      // k is the index in the s1
                TrieNode curr = root;           // the traversing node
                while (k <= j) {
                    char c = (char) (S.charAt(k) - 'a');
                    if (curr.children == null || curr.children[c] == null) {
                        break;
                    }
                    curr = curr.children[c];
                    if (isPalindrome(k + 1, j)) {
                        total += curr.count;
                    }
                    k++;
                }
            }
        }

        S = new StringBuilder(S).reverse().toString();
        constructPalindromeMatrix(S);
        root = new TrieNode();
        root.strangeList = new ArrayList<>();
        for (int j = S.length() - 2; j >= 0; j--) {
            root.strange(S.charAt(j + 1));
            for (int i = 0; i <= j; i++) {
                int k = i;
                TrieNode curr = root;
                while (k <= j - 1) {            // note the changed bound
                    char c = (char) (S.charAt(k) - 'a');
                    if (curr.children == null || curr.children[c] == null) {
                        break;
                    }
                    curr = curr.children[c];
                    if (isPalindrome(k + 1, j)) {
                        total += curr.count;
                    }
                    k++;
                }
            }
        }

        System.out.println(total);
        
        br.close();
    }

    void constructPalindromeMatrix(String S) {
        palindrome = new boolean[S.length()][S.length()];
        for (int i = 0; i < S.length(); i++) {
            palindrome[i][i] = true;
        }
        for (int i = 0; i < S.length() - 1; i++) {
            palindrome[i][i + 1] = S.charAt(i) == S.charAt(i + 1);
        }
        for (int i = 2; i < S.length(); i++) {
            for (int j = 0; j + i < S.length(); j++) {
                palindrome[j][j + i] = palindrome[j + 1][j + i - 1] && S.charAt(j) == S.charAt(j + i);
            }
        }
    }

    boolean isPalindrome(int i, int j) {
        if (i >= j) return true;
        return palindrome[i][j];
    }
}

class TrieNode {
    int count = 0;  // number of times this node was repeated
    TrieNode[] children;

    List<TrieNode> strangeList;     // had no idea what to call this
                                    // maintains the leaves of the trie

    void strange(char c) {          // this function adds c to each leaf
        strangeList.add(this);
        c = (char) (c - 'a');
        for (int i = 0; i < strangeList.size(); i++) {
            TrieNode node = strangeList.get(i);
            if (node.children == null) node.children = new TrieNode[26];
            if (node.children[c] == null) node.children[c] = new TrieNode();
            node.children[c].count++;
            strangeList.set(i, node.children[c]);
        }
    }
}