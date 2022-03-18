import java.util.*;
import java.io.*;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);

        int n = Integer.parseInt(br.readLine());
        String[] line = br.readLine().split(" ");
        int[] h = new int[n];
        for (int i = 0; i < n; i++) h[i] = Integer.parseInt(line[i]);

        // dp[i]
        // longest strictly decreasing subsequence up to i-1.
        // truncate prefix at j where h[j] > h[i]

        long ans = 0;
        int ptr = 0;
        int[] stack = new int[n];
        long[] sumIdx = new long[n];  // prefix sum
        stack[ptr] = -h[0];
        sumIdx[ptr] = 0;
        ptr++;
        for (int i = 1; i < n; i++) {
            // idx is index in stack of the guy i can play with
            int idx = Arrays.binarySearch(stack, 0, ptr, -h[i]);
            if (idx < 0) idx = -idx-1 - 1;
            idx = Math.max(0, idx);

            long sum = sumIdx[ptr-1] - (idx-1 >= 0 ? sumIdx[idx-1] : 0);
            int count = ptr-1 - idx + 1;
            ans += (long) count * i - sum + count;

            while (ptr-1 >= 0 && h[i] >= -stack[ptr-1]) {
                ptr--;
            }
            stack[ptr] = -h[i];
            sumIdx[ptr] = (ptr-1 >= 0 ? sumIdx[ptr-1] : 0) + i;
            ptr++;
        }

        pw.println(ans);
        pw.flush();
    }
}