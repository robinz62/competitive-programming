import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);

        int n = Integer.parseInt(br.readLine());
        char[] s = br.readLine().toCharArray();  // G and H

        int[] nextG = new int[n];
        int[] nextH = new int[n];
        nextG[n-1] = nextH[n-1] = n;
        for (int i = n-2; i >= 0; i--) {
            if (s[i+1] == 'G') nextG[i] = i+1;
            else nextG[i] = nextG[i+1];
            if (s[i+1] == 'H') nextH[i] = i+1;
            else nextH[i] = nextH[i+1];
        }

        int[] prevG = new int[n];
        int[] prevH = new int[n];
        prevG[0] = prevH[0] = -1;
        for (int i = 1; i < n; i++) {
            if (s[i-1] == 'G') prevG[i] = i-1;
            else prevG[i] = prevG[i-1];
            if (s[i-1] == 'H') prevH[i] = i-1;
            else prevH[i] = prevH[i-1];
        }

        // Initialize to all substrings (any size)
        long ans = 0;

        // Remove any lonely photos of any size, where s[i] is the lonely guy
        for (int i = 0; i < n; i++) {
            if (s[i] == 'G') {
                int prev = prevG[i] + 1;
                int next = nextG[i] - 1;
                int left = i - prev + 1;
                int right = next - i + 1;
                ans += (long) left * right;
            } else {
                int prev = prevH[i] + 1;
                int next = nextH[i] - 1;
                int left = i - prev + 1;
                int right = next - i + 1;
                ans += (long) left * right;
            }
        }

        // Remove substrings of size 2 where G and H are both lonely
        for (int i = 0; i+1 < n; i++) {
            if (s[i] != s[i+1]) ans -= 2;
        }

        // Remove lonely singletons
        ans -= n;

        pw.println(ans);
        pw.flush();
    }

    static long brute(char[] s) {
        long ans = 0;
        for (int i = 0; i < s.length; i++) {
            for (int j = i+2; j < s.length; j++) {
                int countG = 0;
                int countH = 0;
                for (int k = i; k <= j; k++) {
                    if (s[k] == 'G') countG++;
                    else countH++;
                }
                if (countG == 1 || countH == 1) {}
                else ans++;
            }
        }
        return ans;
    }
}