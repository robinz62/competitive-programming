import java.io.*;

public class A851 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] tokens = br.readLine().split(" ");
        int n = Integer.parseInt(tokens[0]);
        int k = Integer.parseInt(tokens[1]);
        int t = Integer.parseInt(tokens[2]);

        int ans = 0;

        if (t >= k && t <= n) {
            ans = k;
        } else if (t >= k) {
            ans = k - (t - n);
        } else {
            ans = t;
        }

        System.out.println(ans);
    }
}