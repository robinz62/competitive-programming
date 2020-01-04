import java.io.*;
import java.util.*;

public class Main {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pw = new PrintWriter(System.out);

    public static void main(String[] args) throws IOException {
        Main m = new Main();
        m.solve();
        m.close();
    }

    void close() throws IOException {
        pw.flush();
        pw.close();
        br.close();
    }

    void solve() throws IOException {
        int T = Integer.parseInt(br.readLine());
        for (int t = 0; t < T; t++) {
            char[] cards = br.readLine().toCharArray();
            TreeSet<Integer> indicesOfOnes = new TreeSet<>();
            for (int i = 0; i < cards.length; i++) {
                if (cards[i] == '1') indicesOfOnes.add(i);
            }
            int removed = 0;
            while (!indicesOfOnes.isEmpty()) {
                int firstOne = indicesOfOnes.pollFirst();
                removed++;
                cards[firstOne] = 'X';
                if (firstOne - 1 >= 0 && cards[firstOne - 1] != 'X') {
                    if (cards[firstOne - 1] == '0') {
                        cards[firstOne - 1] = '1';
                        indicesOfOnes.add(firstOne - 1);
                    } else {
                        cards[firstOne - 1] = '0';
                        indicesOfOnes.remove(firstOne - 1);
                    }
                }
                if (firstOne + 1 < cards.length && cards[firstOne + 1] != 'X') {
                    if (cards[firstOne + 1] == '0') {
                        cards[firstOne + 1] = '1';
                        indicesOfOnes.add(firstOne + 1);
                    } else {
                        cards[firstOne + 1] = '0';
                        indicesOfOnes.remove(firstOne + 1);
                    }
                }
            }
            if (removed == cards.length) {
                pw.print("WIN\n");
            } else {
                pw.print("LOSE\n");
            }
        }
    }
}