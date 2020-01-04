import java.io.*;

public class B851 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] tokens = br.readLine().split(" ");
        long ax = Integer.parseInt(tokens[0]);
        long ay = Integer.parseInt(tokens[1]);
        long bx = Integer.parseInt(tokens[2]);
        long by = Integer.parseInt(tokens[3]);
        long cx = Integer.parseInt(tokens[4]);
        long cy = Integer.parseInt(tokens[5]);

        long ab2 = (ax - bx) * (ax - bx) + (ay - by) * (ay - by);
        long bc2 = (bx - cx) * (bx - cx) + (by - cy) * (by - cy);

        long slopeTop = (by - ay);
        long slopeBot = (bx - ax);
        long lhs = slopeBot * (cy - ay);
        long rhs = slopeTop * (cx - ax);
        if (lhs == rhs) {
            System.out.println("No");
        } else {
            String ans = "";
            if (ab2 == bc2) {
                ans = "Yes";
            } else {
                ans = "No";
            }
            System.out.println(ans);
        }
    }
}